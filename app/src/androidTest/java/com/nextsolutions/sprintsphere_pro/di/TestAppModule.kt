package com.nextsolutions.sprintsphere_pro.di

import android.content.Context
import com.nextsolutions.starfpro.domain.user_preferences.data_store.DataStoreManager
import com.nextsolutions.starfpro.domain.user_preferences.preferences.PreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

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

}