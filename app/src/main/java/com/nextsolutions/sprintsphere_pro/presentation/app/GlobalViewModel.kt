package com.nextsolutions.sprintsphere_pro.presentation.app

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextsolutions.keyysafe.common.data.data_store.DataStoreKeys
import com.nextsolutions.sprintsphere_pro.domain.utils.Gender
import com.nextsolutions.sprintsphere_pro.domain.utils.MetricAndImperialUnit
import com.nextsolutions.starfpro.domain.user_preferences.data_store.DataStoreManager
import com.nextsolutions.starfpro.domain.user_preferences.preferences.PreferencesKeys
import com.nextsolutions.starfpro.domain.user_preferences.preferences.PreferencesManager
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import javax.inject.Inject

@HiltViewModel
class GlobalViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    var isSetupFinished by mutableStateOf(false)


    init {
        checkSetupState()
    }

    private fun checkSetupState() {
        val isSetupFinished = preferencesManager.getBool(PreferencesKeys.isSetupFinished, false)
        this.isSetupFinished = isSetupFinished
    }

    init {

        viewModelScope.launch {
            dataStoreManager.get(DataStoreKeys.Age).collectLatest {
                Log.e(" AB age", it.toString())
            }
        }

        viewModelScope.launch {
            dataStoreManager.get(DataStoreKeys.Gender).collectLatest {
                Log.e(" AB gender", it.toString())
            }
        }

        viewModelScope.launch {
            dataStoreManager.get(DataStoreKeys.Weight).collectLatest {
                Log.e(" AB weight", it.toString())
            }
        }

        viewModelScope.launch {
            dataStoreManager.get(DataStoreKeys.Height).collectLatest {
                Log.e(" AB height", it.toString())
            }

        }

        viewModelScope.launch {
            dataStoreManager.get(DataStoreKeys.MetricAndImperialUnit).collectLatest {
                Log.e(" AB MetricAndImperialUnit", it.toString())
            }
        }


    }



}