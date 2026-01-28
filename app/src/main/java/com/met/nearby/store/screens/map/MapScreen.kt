package com.met.nearby.store.screens.map

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import org.maplibre.geojson.Feature
import org.maplibre.geojson.Point
import com.met.nearby.store.R
import com.met.nearby.store.domain.StoreModel
import com.met.nearby.store.screens.results.ItemsNearest
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.Style
import org.maplibre.android.style.layers.PropertyFactory
import org.maplibre.android.style.layers.SymbolLayer
import org.maplibre.android.style.sources.GeoJsonSource


@Composable
fun MapScreen(store: StoreModel, onBackClick: () -> Unit) {

    val context = LocalContext.current
    val lat = store.Latitude
    val lon = store.Longitude

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (map, detail, backButton) = createRefs()

        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(map) { centerTo(parent) },
            factory = { ctx ->
                MapView(ctx).apply {
                    onCreate(null)

                    getMapAsync { mapboxMap ->
                        mapboxMap.setStyle(
                            Style.Builder()
                                .fromUri("https://basemaps.cartocdn.com/gl/positron-gl-style/style.json")
                        ) { style ->

                            mapboxMap.cameraPosition = CameraPosition.Builder()
                                .target(LatLng(lat, lon))
                                .zoom(15.0)
                                .build()

                            val bitmap = BitmapFactory.decodeResource(
                                resources,
                                R.drawable.location
                            )
                            style.addImage("store-icon", bitmap)

                            val source = GeoJsonSource(
                                "store-source",
                                Feature.fromGeometry(
                                    Point.fromLngLat(lon, lat)
                                )
                            )
                            style.addSource(source)

                            val layer = SymbolLayer(
                                "store-layer",
                                "store-source"
                            ).withProperties(
                                PropertyFactory.iconImage("store-icon"),
                                PropertyFactory.iconAllowOverlap(true),
                                PropertyFactory.iconIgnorePlacement(true)
                            )

                            style.addLayer(layer)
                        }
                    }
                }
            },
            update = { it.onResume() }
        )

        Box(
            modifier = Modifier
                .size(48.dp)
                .background(colorResource(R.color.black3), RoundedCornerShape(50))
                .clickable { onBackClick() }
                .constrainAs(backButton) {
                    top.linkTo(parent.top, margin = 48.dp)
                    start.linkTo(parent.start, margin = 24.dp)
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        LazyColumn(
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 24.dp, vertical = 32.dp)
                .fillMaxWidth()
                .background(colorResource(R.color.black3), RoundedCornerShape(10.dp))
                .padding(16.dp)
                .constrainAs(detail) {
                    centerHorizontallyTo(parent)
                    bottom.linkTo(parent.bottom, margin = 30.dp)
                }
        ) {
            item { ItemsNearest(store) }

            item {
                Button(
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.gold)
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    onClick = {
                        val intent = Intent(
                            Intent.ACTION_DIAL,
                            Uri.parse("tel:${store.Call}")
                        )
                        context.startActivity(intent)
                    }
                ) {
                    Text(
                        "Call to Store",
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
