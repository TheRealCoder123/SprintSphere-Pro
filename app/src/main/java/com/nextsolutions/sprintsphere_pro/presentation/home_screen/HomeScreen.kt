@file:OptIn(ExperimentalMaterialApi::class)

package com.nextsolutions.sprintsphere_pro.presentation.home_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SafetyCheck
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.nextsolutions.sprintsphere_pro.R
import com.nextsolutions.sprintsphere_pro.presentation.app.NavGraph
import com.nextsolutions.sprintsphere_pro.presentation.home_screen.components.RunsAndStatisticsBottomSheet
import com.nextsolutions.sprintsphere_pro.presentation.theme.SprintSphereProTheme
import com.nextsolutions.sprintsphere_pro.testing.SetupScreenTestTags
import com.utsman.osmandcompose.DefaultMapProperties
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.Polyline
import com.utsman.osmandcompose.PolylineCap
import com.utsman.osmandcompose.ZoomButtonVisibility
import com.utsman.osmandcompose.rememberCameraState
import com.utsman.osmandcompose.rememberOverlayManagerState
import kotlinx.coroutines.launch
import org.osmdroid.api.IGeoPoint
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.CopyrightOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import ovh.plrapps.mapcompose.api.addLayer
import ovh.plrapps.mapcompose.api.enableRotation
import ovh.plrapps.mapcompose.core.TileStreamProvider
import ovh.plrapps.mapcompose.ui.MapUI
import ovh.plrapps.mapcompose.ui.state.MapState
import java.io.File
import java.io.FileInputStream

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val cameraState = rememberCameraState {
        geoPoint = GeoPoint(0.0,0.0)
        zoom = 12.0
    }

    var mapProperties by remember {
        mutableStateOf(DefaultMapProperties)
    }

    val overlayManagerState = rememberOverlayManagerState()

    SideEffect {
        mapProperties = mapProperties
            .copy(isTilesScaledToDpi = true)
            .copy(tileSources = TileSourceFactory.MAPNIK)
            .copy(isEnableRotationGesture = true)
            .copy(zoomButtonVisibility = ZoomButtonVisibility.NEVER)
    }



    BottomSheetScaffold(
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        sheetPeekHeight = 60.dp,
        sheetBackgroundColor = SprintSphereProTheme.colors.background,
        sheetContent = {
            RunsAndStatisticsBottomSheet()
        },
        topBar = {
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
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "User Settings",
                            tint = SprintSphereProTheme.colors.iconTint
                        )
                    }
                }
            )
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
                onFirstLoadListener = {
                    val copyright = CopyrightOverlay(context)
                    overlayManagerState.overlayManager.add(copyright)
                }
            ){

            }

            Button(
                modifier = Modifier.padding(10.dp),
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = SprintSphereProTheme.colors.onBackground,
                    contentColor = SprintSphereProTheme.colors.onBackgroundText
                )
            ) {
                Text(text = "Start Run")
            }
        }




    }



}



