package com.nextsolutions.sprintsphere_pro.presentation.app

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.LocationServices
import com.nextsolutions.sprintsphere_pro.domain.utils.Location.DefaultLocationClient
import com.nextsolutions.sprintsphere_pro.presentation.theme.SprintSphereProTheme
import com.nextsolutions.sprintsphere_pro.service.running_service.RunningService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {



    private var isBound by mutableStateOf(false)
    private lateinit var runningService: RunningService
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as RunningService.RunningServiceBinder
            runningService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            isBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, RunningService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            SprintSphereProTheme {
                if (isBound){
                    val globalViewModel: GlobalViewModel = hiltViewModel()
                    Application(runningService, globalViewModel)
                }
            }

        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        isBound = false
    }
}

