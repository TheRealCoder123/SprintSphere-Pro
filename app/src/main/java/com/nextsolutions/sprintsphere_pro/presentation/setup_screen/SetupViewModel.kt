package com.nextsolutions.sprintsphere_pro.presentation.setup_screen

import android.widget.Toast
import androidx.compose.runtime.State
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetupViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {


    var ageState by mutableStateOf("")
    var genderState by mutableStateOf<Gender?>(null)
    var weightState by mutableStateOf("")
    var heightState by mutableStateOf("")
    var metricAndImperialUnit by mutableStateOf<MetricAndImperialUnit?>(null)

    fun updateSetupState(state: Boolean) {
        preferencesManager.saveBool(PreferencesKeys.isSetupFinished, state)
    }

    fun saveDetails() = viewModelScope.launch {
        dataStoreManager.save(DataStoreKeys.Age, ageState.toInt())
        dataStoreManager.save(DataStoreKeys.Gender, genderState?.name.toString())
        dataStoreManager.save(DataStoreKeys.Weight, weightState.toFloat())
        dataStoreManager.save(DataStoreKeys.Height, heightState.toFloat())
        dataStoreManager.save(DataStoreKeys.MetricAndImperialUnit, metricAndImperialUnit?.name.toString())
    }






}