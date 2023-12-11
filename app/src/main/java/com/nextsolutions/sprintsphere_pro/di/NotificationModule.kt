package com.nextsolutions.sprintsphere_pro.di

import android.app.NotificationManager
import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.core.app.NotificationCompat
import com.nextsolutions.sprintsphere_pro.R
import com.nextsolutions.sprintsphere_pro.service.running_service.Constants
import com.nextsolutions.sprintsphere_pro.service.running_service.ServiceHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@ExperimentalAnimationApi
@Module
@InstallIn(ServiceComponent::class)
object NotificationModule {



    @ServiceScoped
    @Provides
    fun provideNotificationBuilder(
        @ApplicationContext context: Context
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL_ID)
            .setContentTitle("00:00:00")
            .setContentText("Distance: 0.0 km - Speed: 0.0 km/h - Calories 0.0 kcal")
            .setSubText("Running App")
            .setSmallIcon(R.drawable.baseline_directions_run_24)
            .setOngoing(true)
            .addAction(0, "Stop", ServiceHelper.stopPendingIntent(context))
            .addAction(0, "Cancel", ServiceHelper.cancelPendingIntent(context))
            .setContentIntent(ServiceHelper.clickPendingIntent(context))
    }

    @ServiceScoped
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }


}