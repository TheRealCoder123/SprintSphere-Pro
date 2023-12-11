package com.nextsolutions.sprintsphere_pro.di

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationServices
import com.nextsolutions.sprintsphere_pro.R
import com.nextsolutions.sprintsphere_pro.domain.utils.Location.DefaultLocationClient
import com.nextsolutions.sprintsphere_pro.domain.utils.Location.LocationClient
import com.nextsolutions.sprintsphere_pro.service.running_service.Constants.NOTIFICATION_CHANNEL_ID
import com.nextsolutions.sprintsphere_pro.service.running_service.ServiceHelper
import com.nextsolutions.starfpro.domain.user_preferences.data_store.DataStoreManager
import com.nextsolutions.starfpro.domain.user_preferences.preferences.PreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {


    @Provides
    @Singleton
    fun providePreferencesManager(@ApplicationContext context: Context) : PreferencesManager {
        return PreferencesManager(context)
    }

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context) : DataStoreManager {
        return DataStoreManager(context)
    }

    @Singleton
    @Provides
    fun provideLocationClient(
        @ApplicationContext context: Context
    ): LocationClient = DefaultLocationClient(
        context,
        LocationServices.getFusedLocationProviderClient(context)
    )



}