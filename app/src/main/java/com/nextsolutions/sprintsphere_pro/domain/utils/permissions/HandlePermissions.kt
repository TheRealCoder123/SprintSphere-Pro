package com.nextsolutions.sprintsphere_pro.domain.utils.permissions

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted

@ExperimentalPermissionsApi
@Composable
fun HandleRequest(
    permissionState: PermissionState,
    deniedContent: @Composable (Boolean) -> Unit,
    content: @Composable () -> Unit
) {

    when (permissionState.status) {
        is PermissionStatus.Granted -> {
            content()
        }
        is PermissionStatus.Denied -> {
            deniedContent((permissionState.status as PermissionStatus.Denied).shouldShowRationale)
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HandleLocationPermissionRequest(
    context: Context,
    permissionState: MultiplePermissionsState,
    deniedContent: @Composable (Boolean) -> Unit,
    content: @Composable () -> Unit
){

    var grantedPerms by remember {
        mutableStateOf(0)
    }

    permissionState.permissions.forEach {
        when (it.permission) {
            Manifest.permission.ACCESS_COARSE_LOCATION -> {
                if (it.status.isGranted){
                    grantedPerms++
                }
            }
            Manifest.permission.ACCESS_FINE_LOCATION -> {
                if (it.status.isGranted){
                    grantedPerms++
                }
            }
        }
    }




    if (grantedPerms == 2){
        content()
    }else{
        deniedContent(permissionState.shouldShowRationale)
    }

}