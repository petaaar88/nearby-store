package com.met.nearby.store.navigation

import com.met.nearby.store.domain.StoreModel
import kotlinx.serialization.Serializable

@Serializable
data object SplashRoute

@Serializable
data object LoginRoute

@Serializable
data object DashboardRoute

@Serializable
data object FavoriteRoute

@Serializable
data object ProfileRoute

@Serializable
data class ResultsRoute(val id: String, val title: String)

@Serializable
data class StoreDetailsRoute(val store: StoreModel)

@Serializable
data class MapRoute(val store: StoreModel)
