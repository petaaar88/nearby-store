package com.met.nearby.store

import android.app.Application
import org.maplibre.android.MapLibre
import org.maplibre.android.WellKnownTileServer

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        MapLibre.getInstance(
            this,
            "",
            WellKnownTileServer.MapLibre
        )
    }
}
