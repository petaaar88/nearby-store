package com.met.nearby.store

import android.app.Application
import com.met.nearby.store.auth.UserSession
import org.maplibre.android.MapLibre
import org.maplibre.android.WellKnownTileServer

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        UserSession.init(this)

        MapLibre.getInstance(
            this,
            "",
            WellKnownTileServer.MapLibre
        )
    }
}
