package com.nextsolutions.keyysafe.common.data.data_store

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    val AppTheme = stringPreferencesKey(name = "app_theme")
    val Age = intPreferencesKey(name = "Age")
    val Gender = stringPreferencesKey(name = "Gender")
    val Weight = floatPreferencesKey(name = "Weight")
    val Height = floatPreferencesKey(name = "Height")
    val MetricAndImperialUnit = stringPreferencesKey(name = "MetricAndImperialUnit")
}