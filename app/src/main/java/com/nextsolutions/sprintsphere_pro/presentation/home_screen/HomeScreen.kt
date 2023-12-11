package com.nextsolutions.sprintsphere_pro.presentation.home_screen

import android.Manifest
import android.content.Intent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationDisabled
import androidx.compose.material.icons.filled.SafetyCheck
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.nextsolutions.sprintsphere_pro.R
import com.nextsolutions.sprintsphere_pro.presentation.home_screen.components.PermissionDeniedContent
import com.nextsolutions.sprintsphere_pro.presentation.home_screen.components.RunsAndStatisticsBottomSheet
import com.nextsolutions.sprintsphere_pro.presentation.theme.SprintSphereProTheme
import com.utsman.osmandcompose.DefaultMapProperties
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.ZoomButtonVisibility
import com.utsman.osmandcompose.rememberCameraState
import com.utsman.osmandcompose.rememberOverlayManagerState
import kotlinx.coroutines.delay
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.filled.LocationSearching
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavController
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.nextsolutions.sprintsphere_pro.domain.utils.permissions.HandleLocationPermissionRequest
import com.nextsolutions.sprintsphere_pro.domain.utils.permissions.HasPermissions.hasLocationPermission
import com.nextsolutions.sprintsphere_pro.presentation.app.NavGraph
import com.nextsolutions.sprintsphere_pro.presentation.home_screen.components.RunningDetailsBar
import com.nextsolutions.sprintsphere_pro.service.running_service.Constants.ACTION_SERVICE_CANCEL
import com.nextsolutions.sprintsphere_pro.service.running_service.Constants.ACTION_SERVICE_START
import com.nextsolutions.sprintsphere_pro.service.running_service.Constants.ACTION_SERVICE_STOP
import com.nextsolutions.sprintsphere_pro.service.running_service.RunningService
import com.nextsolutions.sprintsphere_pro.service.running_service.RunningServiceState
import com.nextsolutions.sprintsphere_pro.service.running_service.ServiceHelper
import com.utsman.osmandcompose.MarkerLabeled
import com.utsman.osmandcompose.rememberMarkerState
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    runningService: RunningService,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val runningServiceState by runningService.runningState
    val time by runningService.time
    val distance by runningService.distance
    val speed by runningService.speed
    val calories by runningService.calories
    val startLocation = runningService.startLocation
    val userCurrentLocation = runningService.location



    val permissionState = rememberMultiplePermissionsState(
        permissions = arrayListOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    val cameraState = rememberCameraState {
        geoPoint = GeoPoint(40.7128,74.0060)
        zoom = 17.5
    }


//    DisposableEffect(key1 = lifecycleOwner){
//        val observer = LifecycleEventObserver { _, event ->
//            when(event){
//                Lifecycle.Event.ON_START -> {
//                    permissionState.launchMultiplePermissionRequest()
//                }
//                else -> {}
//            }
//        }
//        lifecycleOwner.lifecycle.addObserver(observer)
//        onDispose {
//            lifecycleOwner.lifecycle.removeObserver(observer)
//        }
//
//    }

    val overlayManagerState = rememberOverlayManagerState()

    var mapProperties by remember {
        mutableStateOf(DefaultMapProperties)
    }

    val startLocationMarker = rememberMarkerState()

    val startLocationMarkerIcon: Drawable? by remember {
        mutableStateOf(context.getDrawable(R.drawable.baseline_location_on_24))
    }


    SideEffect {
        mapProperties = mapProperties
            .copy(isTilesScaledToDpi = true)
            .copy(tileSources = TileSourceFactory.MAPNIK)
            .copy(isEnableRotationGesture = true)
            .copy(zoomButtonVisibility = ZoomButtonVisibility.NEVER)
    }

    LaunchedEffect(key1 = Unit){
        delay(1000)
        viewModel.shouldShowLocationOffText = false
    }


    LaunchedEffect(key1 = startLocation){
        if (startLocation != null){
            startLocationMarker.geoPoint = GeoPoint(startLocation)
        }
    }





    val locationOffTextSizeAnim by animateDpAsState(
        targetValue = if (viewModel.shouldShowLocationOffText) 50.dp else 0.dp,
        label = "locationOffTextSizeAnim"
    )


    HandleLocationPermissionRequest(
        context = context,
        permissionState = permissionState,
        deniedContent = {
            PermissionDeniedContent(
                rationaleMessage = stringResource(R.string.normal_location_text),
                shouldShowRationale = viewModel.dialogPermissionState,
                onRequestPermission = {
                    viewModel.dialogPermissionState = false
                    permissionState.launchMultiplePermissionRequest()
                },
                onDenyPermission = {
                    viewModel.dialogPermissionState = false
                },
            )
        },
        content = {
            //Toast.makeText(context, "Permission has been granted", Toast.LENGTH_SHORT).show()
        }
    )






    BottomSheetScaffold(
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        sheetPeekHeight = 60.dp,
        sheetBackgroundColor = SprintSphereProTheme.colors.background,
        sheetContent = {
            RunsAndStatisticsBottomSheet()
        },
        topBar = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                TopAppBar(
                    title = {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            IconButton(onClick = {}) {
                                Icon(
                                    imageVector = Icons.Default.SafetyCheck,
                                    contentDescription = "User Settings",
                                    tint = SprintSphereProTheme.colors.iconTint
                                )
                            }

                            Text(
                                text = stringResource(id = R.string.app_name),
                                color = SprintSphereProTheme.colors.text
                            )


                        }
                    },
                    backgroundColor = SprintSphereProTheme.colors.background,
                    actions = {
                        IconButton(onClick = {
                            navController.navigate(NavGraph.SettingsScreen.route)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "User Settings",
                                tint = SprintSphereProTheme.colors.iconTint
                            )
                        }
                    }
                )

                AnimatedVisibility(
                    visible = runningServiceState == RunningServiceState.Started ||
                            runningServiceState == RunningServiceState.Stopped
                ) {
                    RunningDetailsBar(
                        time = time,
                        distance = distance,
                        speed = speed,
                        calories = calories
                    )
                }

            }


        },
    ) {




        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it), contentAlignment = Alignment.BottomCenter
        ){

            OpenStreetMap(
                modifier = Modifier.fillMaxSize(),
                cameraState = cameraState,
                properties = mapProperties,
                overlayManagerState = overlayManagerState,
                onFirstLoadListener = { mapView ->
                    val locationOverlay = MyLocationNewOverlay(mapView)
                    locationOverlay.enableMyLocation()
                    locationOverlay.enableFollowLocation()
                    mapView.overlays.add(locationOverlay)
                },
            ){
                if (startLocation != null){
                    MarkerLabeled(
                        state = startLocationMarker,
                        icon = startLocationMarkerIcon,
                        label = "Start Location",
                    )
                }
            }


            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd){

                Column(
                    modifier = Modifier.padding(10.dp),
                    horizontalAlignment = Alignment.End
                ) {

                    if (!permissionState.allPermissionsGranted){
                        Button(
                            onClick = {
                                viewModel.dialogPermissionState = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SprintSphereProTheme.colors.onBackground,
                                contentColor = SprintSphereProTheme.colors.onBackgroundText
                            )
                        ) {
                            Text(
                                modifier = Modifier
                                    .width(locationOffTextSizeAnim)
                                    .height(18.dp),
                                text = stringResource(R.string.location_perm_off)
                            )
                            if (viewModel.shouldShowLocationOffText){
                                Spacer(modifier = Modifier.width(10.dp))
                            }
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = Icons.Filled.LocationDisabled,
                                contentDescription = "Location Perm",
                                tint = SprintSphereProTheme.colors.onBackgroundText
                            )
                        }
                    }

                    if (permissionState.allPermissionsGranted){
                        Button(
                            onClick = {
                                cameraState.apply {
                                    animateTo(GeoPoint(userCurrentLocation))
                                    zoom = 17.5
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SprintSphereProTheme.colors.onBackground,
                                contentColor = SprintSphereProTheme.colors.onBackgroundText
                            )
                        ) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = Icons.Filled.LocationSearching,
                                contentDescription = "Location Perm",
                                tint = SprintSphereProTheme.colors.onBackgroundText
                            )
                        }
                    }

                }
            }



            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {


                AnimatedVisibility(visible = when(runningServiceState){
                    RunningServiceState.Idle -> false
                    RunningServiceState.Started -> true
                    RunningServiceState.Stopped -> true
                    RunningServiceState.Canceled -> false
                }) {
                    Button(
                        onClick = {
                            ServiceHelper.triggerForegroundService(
                                context = context,
                                action = if (runningServiceState == RunningServiceState.Started) ACTION_SERVICE_STOP else ACTION_SERVICE_START
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SprintSphereProTheme.colors.onBackground,
                            contentColor = SprintSphereProTheme.colors.onBackgroundText
                        )
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = if (runningServiceState == RunningServiceState.Started) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                            contentDescription = "",
                            tint = SprintSphereProTheme.colors.onBackgroundText
                        )
                    }
                }

                Button(
                    modifier = Modifier.padding(10.dp),
                    onClick = {
                        if (context.hasLocationPermission()){
                            ServiceHelper.triggerForegroundService(
                                context = context,
                                action = when(runningServiceState){
                                    RunningServiceState.Idle -> ACTION_SERVICE_START
                                    RunningServiceState.Started -> ACTION_SERVICE_CANCEL
                                    RunningServiceState.Stopped -> ACTION_SERVICE_CANCEL
                                    RunningServiceState.Canceled -> ACTION_SERVICE_START
                                }
                            )
                        }else{
                            Toast.makeText(
                                context,
                                "Please grant precise location permission",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SprintSphereProTheme.colors.onBackground,
                        contentColor = SprintSphereProTheme.colors.onBackgroundText
                    )
                ) {
                    Text(text = when(runningServiceState){
                        RunningServiceState.Idle -> "Start Run"
                        RunningServiceState.Started -> "Cancel Run"
                        RunningServiceState.Stopped -> "Cancel Run"
                        RunningServiceState.Canceled -> "Start Run"
                    })
                }
            }
        }




    }



}



