package com.nextsolutions.sprintsphere_pro.service.running_service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.animation.ExperimentalAnimationApi
import com.nextsolutions.sprintsphere_pro.presentation.app.MainActivity
import com.nextsolutions.sprintsphere_pro.service.running_service.Constants.CANCEL_REQUEST_CODE
import com.nextsolutions.sprintsphere_pro.service.running_service.Constants.CLICK_REQUEST_CODE
import com.nextsolutions.sprintsphere_pro.service.running_service.Constants.RESUME_REQUEST_CODE
import com.nextsolutions.sprintsphere_pro.service.running_service.Constants.RUNNING_SERVICE_STATE
import com.nextsolutions.sprintsphere_pro.service.running_service.Constants.STOP_REQUEST_CODE

object ServiceHelper {

    private val flag = PendingIntent.FLAG_IMMUTABLE

    fun clickPendingIntent(context: Context): PendingIntent {
        val clickIntent = Intent(context, MainActivity::class.java).apply {
            putExtra(RUNNING_SERVICE_STATE, RunningServiceState.Started.name)
        }
        return PendingIntent.getActivity(
            context, CLICK_REQUEST_CODE, clickIntent, flag
        )
    }

    fun stopPendingIntent(context: Context): PendingIntent {
        val stopIntent = Intent(context, RunningService::class.java).apply {
            putExtra(RUNNING_SERVICE_STATE, RunningServiceState.Stopped.name)
        }
        return PendingIntent.getService(
            context, STOP_REQUEST_CODE, stopIntent, flag
        )
    }

    fun resumePendingIntent(context: Context): PendingIntent {
        val resumeIntent = Intent(context, RunningService::class.java).apply {
            putExtra(RUNNING_SERVICE_STATE, RunningServiceState.Started.name)
        }
        return PendingIntent.getService(
            context, RESUME_REQUEST_CODE, resumeIntent, flag
        )
    }

    fun cancelPendingIntent(context: Context): PendingIntent {
        val cancelIntent = Intent(context, RunningService::class.java).apply {
            putExtra(RUNNING_SERVICE_STATE, RunningServiceState.Canceled.name)
        }
        return PendingIntent.getService(
            context, CANCEL_REQUEST_CODE, cancelIntent, flag
        )
    }


    fun triggerForegroundService(context: Context, action: String) {
        Intent(context, RunningService::class.java).apply {
            this.action = action
            context.startService(this)
        }
    }


}