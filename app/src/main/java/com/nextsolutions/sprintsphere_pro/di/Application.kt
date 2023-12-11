package com.nextsolutions.sprintsphere_pro.di

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.nextsolutions.sprintsphere_pro.service.running_service.Constants
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class Application : Application()