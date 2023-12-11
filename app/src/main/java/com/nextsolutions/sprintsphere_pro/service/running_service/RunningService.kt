package com.nextsolutions.sprintsphere_pro.service.running_service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.NotificationCompat
import com.nextsolutions.keyysafe.common.data.data_store.DataStoreKeys
import com.nextsolutions.sprintsphere_pro.domain.utils.Location.LocationClient
import com.nextsolutions.sprintsphere_pro.domain.utils.MetricAndImperialUnit
import com.nextsolutions.sprintsphere_pro.domain.utils.Utils.formatTime
import com.nextsolutions.sprintsphere_pro.domain.utils.Utils.pad
import com.nextsolutions.sprintsphere_pro.service.running_service.Constants.ACTION_SERVICE_CANCEL
import com.nextsolutions.sprintsphere_pro.service.running_service.Constants.ACTION_SERVICE_START
import com.nextsolutions.sprintsphere_pro.service.running_service.Constants.ACTION_SERVICE_STOP
import com.nextsolutions.sprintsphere_pro.service.running_service.Constants.NOTIFICATION_CHANNEL_ID
import com.nextsolutions.sprintsphere_pro.service.running_service.Constants.NOTIFICATION_CHANNEL_NAME
import com.nextsolutions.sprintsphere_pro.service.running_service.Constants.NOTIFICATION_ID
import com.nextsolutions.sprintsphere_pro.service.running_service.Constants.RUNNING_SERVICE_STATE
import com.nextsolutions.starfpro.domain.user_preferences.data_store.DataStoreManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.fixedRateTimer
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@AndroidEntryPoint
class RunningService : Service() {

    @Inject
    lateinit var notificationManager: NotificationManager
    @Inject
    lateinit var notificationBuilder: NotificationCompat.Builder
    @Inject
    lateinit var defaultLocationClient: LocationClient
    @Inject
    lateinit var dataStoreManager: DataStoreManager

    private var duration: Duration = Duration.ZERO
    private lateinit var timer: Timer

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)



    var seconds = mutableStateOf("00")
        private set
    var minutes = mutableStateOf("00")
        private set
    var hours = mutableStateOf("00")
        private set

    var distance = mutableStateOf("0.0")
        private set

    var speed = mutableStateOf("0.0")
        private set

    var calories = mutableStateOf("0.0")
        private set

    var weight = mutableStateOf(0f)
        private set

    var time = mutableStateOf("00:00:00")
        private set

    var metricUnit = mutableStateOf<MetricAndImperialUnit>(MetricAndImperialUnit.KG_CM)
        private set

    var location by mutableStateOf<Location?>(null)
        private set
    var startLocation by mutableStateOf<Location?>(null)
        private set

    var runningState = mutableStateOf(RunningServiceState.Idle)
        private set

    private val binder = RunningServiceBinder()

    override fun onBind(p0: Intent?): IBinder = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("onStartCommand", "Start")

        when (intent?.getStringExtra(RUNNING_SERVICE_STATE)) {
            RunningServiceState.Started.name -> {
                startRun()
            }
            RunningServiceState.Stopped.name -> {
                stopRun()
            }
            RunningServiceState.Canceled.name -> {
                cancelRun()
            }
        }

        intent?.action.let {
            when (it) {
                ACTION_SERVICE_START -> {
                    startRun()
                }
                ACTION_SERVICE_STOP -> {
                    stopRun()
                }
                ACTION_SERVICE_CANCEL -> {
                    cancelRun()
                }
            }
        }

        return START_STICKY
    }

    private fun startRun() {
        runningState.value = RunningServiceState.Started
        startForegroundService()
        setStopButton()
        getUserPreferences()
        collectUsersLocation()
        startTimer { hours, minutes, seconds ->
            Log.e("time",  formatTime(hours = hours, minutes = minutes, seconds = seconds,))
            time.value = formatTime(hours = hours, minutes = minutes, seconds = seconds,)
            updateNotification(hours = hours, minutes = minutes, seconds = seconds)
        }
    }

    private fun getUserPreferences(){
        dataStoreManager.get(DataStoreKeys.Weight).onEach {
            Log.e(" AB weight", it.toString())
            weight.value = it ?: 0.0f
        }.launchIn(serviceScope)

        dataStoreManager.get(DataStoreKeys.MetricAndImperialUnit).onEach {
            Log.e(" AB MetricAndImperialUnit", it.toString())
            metricUnit.value = MetricAndImperialUnit.valueOf(it ?: MetricAndImperialUnit.KG_CM.name)
        }.launchIn(serviceScope)
    }

    private fun calculateCaloriesBurned(
        weightKg: Double,
        distanceKm: Double,
        paceMinPerKm: Double
    ): Double {
        val metValue = 7.0
        val timeHours = distanceKm * paceMinPerKm / 60.0
        return metValue * weightKg * timeHours
    }


    private fun stopRun(){
        runningState.value = RunningServiceState.Stopped
        stopTimer()
        setResumeButton()
    }

    private fun cancelRun(){
        runningState.value = RunningServiceState.Canceled
        cancelTimer()
        stopForegroundService()
    }

    private fun collectUsersLocation() {
        defaultLocationClient.getLocationUpdates(100)
            .catch { cause: Throwable ->
                if (cause is LocationClient.PermissionNotGrantedException){
                    updateNotification("Location permission not granted")
                }else{
                    updateNotification("GPS is disabled")
                }
            }
            .onEach { newLocation ->
                location = newLocation

                Log.e("location lat", newLocation.latitude.toString())
                Log.e("location lng", newLocation.longitude.toString())


                if (startLocation == null && runningState.value == RunningServiceState.Started || runningState.value == RunningServiceState.Stopped){
                    startLocation = newLocation
                }

                if(runningState.value == RunningServiceState.Started){
                    val calculatedDistance = calculateDistance(startLocation?.latitude ?: 41.9981, startLocation?.longitude ?: 21.4254, newLocation.latitude, newLocation.longitude)
                    distance.value = formatToThreeDecimalPlaces(calculatedDistance)
                    val speedCalc = calculateSpeed(calculatedDistance, duration.inWholeMilliseconds)
                    speed.value = formatToThreeDecimalPlaces(speedCalc)
                    val caloriesBurned = calculateCaloriesBurned(weight.value.toDouble(), calculatedDistance, speedCalc)
                    calories.value = formatToThreeDecimalPlaces(caloriesBurned)
                    updateNotification("Distance: ${distance.value} km - Speed: ${speed.value} km/h - Calories: ${calories.value} kcal")
                    Log.e("distance/speed/kcal", "Distance: ${distance.value} km - Speed: ${speed.value} km/h - Calories: ${calories.value} kcal")
                    Log.e(" start location lat", startLocation?.latitude.toString())
                    Log.e(" start location lng", startLocation?.longitude.toString())
                }


            }.launchIn(serviceScope)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }




    private fun startForegroundService() {
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun stopForegroundService() {
        notificationManager.cancel(NOTIFICATION_ID)
        stopForeground(STOP_FOREGROUND_REMOVE)
        startLocation = null
        stopSelf()
    }

    fun calculateSpeed(distance: Double, timeInMillis: Long): Double {
        // Convert time from milliseconds to seconds
        val timeInSeconds = timeInMillis / 1000.0

        // Calculate speed (speed = distance / time)
        return if (timeInSeconds > 0) {
            distance / timeInSeconds
        } else {
            0.0 // Avoid division by zero
        }
    }


    fun formatToThreeDecimalPlaces(value: Double): String {
        return BigDecimal(value).setScale(3, RoundingMode.HALF_EVEN).toString()
    }

    private fun updateNotification(hours: String, minutes: String, seconds: String) {
        notificationManager.notify(
            NOTIFICATION_ID,
            notificationBuilder.setContentTitle(
                formatTime(
                    hours = hours,
                    minutes = minutes,
                    seconds = seconds,
                )
            ).build()
        )
    }

    private fun updateNotification(text: String) {
        notificationManager.notify(
            NOTIFICATION_ID,
            notificationBuilder.setContentText(text).build()
        )
    }



    fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371.0 // Earth radius in kilometers
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return R * c // Distance in kilometers
    }

    private fun startTimer(onTick: (h: String, m: String, s: String) -> Unit) {
        timer = fixedRateTimer(initialDelay = 1000L, period = 1000L) {
            duration = duration.plus(1.seconds)
            updateTimeUnits()
            onTick(hours.value, minutes.value, seconds.value)
        }
    }

    private fun updateTimeUnits() {
        duration.toComponents { hours, minutes, seconds, _ ->
            this.hours.value = hours.toInt().pad()
            this.minutes.value = minutes.pad()
            this.seconds.value = seconds.pad()
        }
    }

    private fun cancelTimer() {
        duration = Duration.ZERO
        stopTimer()
        updateTimeUnits()
    }

    private fun  stopTimer() {
        if (this::timer.isInitialized) {
            timer.cancel()
        }
    }

    private fun setStopButton() {
        notificationBuilder.mActions.removeAt(0)
        notificationBuilder.mActions.add(
            0,
            NotificationCompat.Action(
                0,
                "Stop",
                ServiceHelper.stopPendingIntent(this)
            )
        )
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun setResumeButton() {
        notificationBuilder.mActions.removeAt(0)
        notificationBuilder.mActions.add(
            0,
            NotificationCompat.Action(
                0,
                "Resume",
                ServiceHelper.resumePendingIntent(this)
            )
        )
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    inner class RunningServiceBinder() : Binder() {
        fun getService() : RunningService = this@RunningService
    }

}