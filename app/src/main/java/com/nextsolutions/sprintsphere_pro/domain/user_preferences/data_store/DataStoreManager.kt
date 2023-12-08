package com.nextsolutions.starfpro.domain.user_preferences.data_store


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {

     private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "sprint_sphere_pro_data_store_pref")

     suspend fun <T> save(key: Preferences.Key<T>, value: T) {
          context.dataStore.edit { preferences ->
               preferences[key] = value
          }
     }

     fun <T> get(key: Preferences.Key<T>) : Flow<T?> {
          return context.dataStore.data
               .map { preferences ->
                    val data = preferences[key]
                    data
               }
     }

     suspend fun <T> getOnce(key: Preferences.Key<T>) : T? {
          return context.dataStore.data
               .map { preferences ->
                    val data = preferences[key]
                    data
               }.first()
     }

}