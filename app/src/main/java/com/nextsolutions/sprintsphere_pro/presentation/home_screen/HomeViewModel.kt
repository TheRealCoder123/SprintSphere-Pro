package com.nextsolutions.sprintsphere_pro.presentation.home_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextsolutions.sprintsphere_pro.domain.utils.Location.DefaultLocationClient
import com.nextsolutions.sprintsphere_pro.domain.utils.Location.LocationClient
import com.nextsolutions.starfpro.domain.user_preferences.preferences.PreferencesKeys
import com.nextsolutions.starfpro.domain.user_preferences.preferences.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val defaultLocationClient: LocationClient
) : ViewModel() {

    var dialogPermissionState by mutableStateOf(false)
    var backgroundDialogPermState by mutableStateOf(false)
    var shouldShowLocationOffText by mutableStateOf(true)




    init {
        getLocationPermDialogAfterLaunchState()
    }

    private fun updateLocationPermDialogAfterLaunchState(state: Boolean){
        preferencesManager.saveBool(PreferencesKeys.showedOnceLocationPermDialog, state)
    }




    private fun getLocationPermDialogAfterLaunchState() {
        preferencesManager.getBool(PreferencesKeys.showedOnceLocationPermDialog, false).let {
            if (!it){
                dialogPermissionState = true
                updateLocationPermDialogAfterLaunchState(true)
            }
        }
    }



}