package com.utsman.osmandcompose

import android.content.Context
import android.content.om.OverlayIdentifier
import android.graphics.drawable.Drawable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.nextsolutions.sprintsphere_pro.R
import kotlinx.coroutines.awaitCancellation
import org.osmdroid.events.MapListener
import org.osmdroid.tileprovider.MapTileProviderBasic
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.util.MapTileIndex
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.TilesOverlay
import org.osmdroid.views.overlay.mylocation.DirectedLocationOverlay
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

internal typealias OsmMapView = MapView

@Composable
fun rememberMapViewWithLifecycle(vararg mapListener: MapListener): OsmMapView {
    val context = LocalContext.current
    val mapView = remember {
        OsmMapView(context)
    }

    val lifecycleObserver = rememberMapLifecycleObserver(context, mapView, *mapListener)
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle) {
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }

    return mapView
}

@Composable
fun rememberMapLifecycleObserver(
    context: Context,
    mapView: OsmMapView,
    vararg mapListener: MapListener
): LifecycleEventObserver =
    remember(mapView) {
        LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> {
                    org.osmdroid.config.Configuration.getInstance()
                        .load(context, context.getSharedPreferences("osm", Context.MODE_PRIVATE))
                }

                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_DESTROY -> {
                    mapListener.onEach { mapView.removeMapListener(it) }
                }

                else -> {}
            }
        }
    }

@LayoutScopeMarker
interface OsmAndroidScope

// public enum Visibility {ALWAYS, NEVER, SHOW_AND_FADEOUT}

enum class ZoomButtonVisibility {
    ALWAYS, NEVER, SHOW_AND_FADEOUT
}

@Composable
fun OpenStreetMap(
    modifier: Modifier = Modifier,
    cameraState: CameraState = rememberCameraState(),
    overlayManagerState: OverlayManagerState = rememberOverlayManagerState(),
    properties: MapProperties = DefaultMapProperties,
    onMapClick: (GeoPoint) -> Unit = {},
    onMapLongClick: (GeoPoint) -> Unit = {},
    onFirstLoadListener: (MapView) -> Unit = {},
    content: (@Composable @OsmAndroidComposable OsmAndroidScope.() -> Unit)? = null
) {

    val mapView = rememberMapViewWithLifecycle()

    val mapListeners = remember {
        MapListeners()
    }.also {
        it.onMapClick = onMapClick
        it.onMapLongClick = onMapLongClick
        it.onFirstLoadListener = {
            onFirstLoadListener.invoke(mapView)
        }
    }

    val mapProperties by rememberUpdatedState(properties)
    val isDarkMode = isSystemInDarkTheme()

    val parentComposition = rememberCompositionContext()
    val currentContent by rememberUpdatedState(content)



    LaunchedEffect(Unit) {
        disposingComposition {
            mapView.newComposition(parentComposition) {
                MapViewUpdater(mapProperties, mapListeners, cameraState, overlayManagerState)
                currentContent?.invoke(object : OsmAndroidScope {})
            }
        }
    }

    AndroidView(
        modifier = modifier,
        factory = {
            mapView.overlayManager.tilesOverlay.setColorFilter(if (isDarkMode) TilesOverlay.INVERT_COLORS else null)
            mapView
        },
        update = {}
    )
}

internal suspend inline fun disposingComposition(factory: () -> Composition) {
    val composition = factory()
    try {
        awaitCancellation()
    } finally {
        composition.dispose()
    }
}

private fun OsmMapView.newComposition(
    parent: CompositionContext,
    content: @Composable () -> Unit
): Composition {
    return Composition(
        MapApplier(this), parent
    ).apply {
        setContent(content)
    }
}