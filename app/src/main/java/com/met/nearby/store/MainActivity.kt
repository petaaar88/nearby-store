package com.met.nearby.store

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.met.nearby.store.auth.UserSession
import com.met.nearby.store.domain.StoreModel
import com.met.nearby.store.navigation.*
import com.met.nearby.store.screens.dashboard.DashboardScreen
import com.met.nearby.store.screens.details.StoreDetailsScreen
import com.met.nearby.store.screens.favorite.FavoriteScreen
import com.met.nearby.store.screens.login.LoginScreen
import com.met.nearby.store.screens.map.MapScreen
import com.met.nearby.store.screens.profile.ProfileScreen
import com.met.nearby.store.screens.results.ResultList
import com.met.nearby.store.screens.splash.SplashScreen
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainApp()
        }
    }
}

@Composable
fun MainApp() {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = colorResource(R.color.black2))
    systemUiController.setNavigationBarColor(color = colorResource(R.color.black3))

    val navController = rememberNavController()
    val storeTypeMap = mapOf(typeOf<StoreModel>() to StoreModelNavType)

    NavHost(
        navController = navController,
        startDestination = SplashRoute
    ) {
        composable<SplashRoute> {
            SplashScreen(
                onNavigateToDashboard = {
                    if (UserSession.isLoggedIn) {
                        navController.navigate(DashboardRoute) {
                            popUpTo(SplashRoute) { inclusive = true }
                        }
                    } else {
                        navController.navigate(LoginRoute) {
                            popUpTo(SplashRoute) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable<LoginRoute> {
            LoginScreen(
                onLoginClick = {
                    navController.navigate(DashboardRoute) {
                        popUpTo(LoginRoute) { inclusive = true }
                    }
                },
                onSkipClick = {
                    navController.navigate(DashboardRoute) {
                        popUpTo(LoginRoute) { inclusive = true }
                    }
                }
            )
        }

        composable<DashboardRoute> {
            DashboardScreen(
                onCategoryClick = { id, title ->
                    navController.navigate(ResultsRoute(id, title))
                },
                onFavoriteClick = {
                    navController.navigate(FavoriteRoute)
                },
                onProfileClick = {
                    navController.navigate(ProfileRoute)
                }
            )
        }

        composable<FavoriteRoute> {
            FavoriteScreen(
                onHomeClick = {
                    navController.navigate(DashboardRoute) {
                        popUpTo(DashboardRoute) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onStoreClick = { store ->
                    navController.navigate(StoreDetailsRoute(store))
                },
                onProfileClick = {
                    navController.navigate(ProfileRoute)
                },
                onLoginClick = {
                    navController.navigate(LoginRoute)
                }
            )
        }

        composable<ProfileRoute> {
            ProfileScreen(
                onHomeClick = {
                    navController.navigate(DashboardRoute) {
                        popUpTo(DashboardRoute) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onFavoriteClick = {
                    navController.navigate(FavoriteRoute)
                },
                onLogoutClick = {
                    navController.navigate(LoginRoute) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.navigate(LoginRoute)
                }
            )
        }

        composable<ResultsRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<ResultsRoute>()
            ResultList(
                id = route.id,
                title = route.title,
                onHomeClick = {
                    navController.navigate(DashboardRoute) {
                        popUpTo(DashboardRoute) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onFavoriteClick = {
                    navController.navigate(FavoriteRoute)
                },
                onStoreClick = { store ->
                    navController.navigate(StoreDetailsRoute(store))
                },
                onProfileClick = {
                    navController.navigate(ProfileRoute)
                }
            )
        }

        composable<StoreDetailsRoute>(
            typeMap = storeTypeMap
        ) { backStackEntry ->
            val route = backStackEntry.toRoute<StoreDetailsRoute>()
            StoreDetailsScreen(
                store = route.store,
                onBackClick = { navController.popBackStack() },
                onShowMapClick = {
                    navController.navigate(MapRoute(route.store))
                }
            )
        }

        composable<MapRoute>(
            typeMap = storeTypeMap
        ) { backStackEntry ->
            val route = backStackEntry.toRoute<MapRoute>()
            MapScreen(
                store = route.store,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
