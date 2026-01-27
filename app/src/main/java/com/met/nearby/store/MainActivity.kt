package com.met.nearby.store

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.colorResource
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.met.nearby.store.auth.UserSession
import com.met.nearby.store.domain.StoreModel
import com.met.nearby.store.screens.dashboard.DashboardScreen
import com.met.nearby.store.screens.map.MapScreen
import com.met.nearby.store.screens.results.ResultList
import com.met.nearby.store.screens.favorite.FavoriteScreen
import com.met.nearby.store.screens.login.LoginScreen
import com.met.nearby.store.screens.profile.ProfileScreen
import com.met.nearby.store.screens.splash.SplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainApp()
        }
    }
}

sealed class Screen {
    data object Splash: Screen()
    data object Login: Screen()
    data object Dashboard: Screen()
    data object Favorite: Screen()
    data object Profile: Screen()
    data class Results(val id: String, val title: String): Screen()
    data class Map(val store: StoreModel): Screen()
}

@Composable
fun MainApp() {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = colorResource(R.color.black2))
    systemUiController.setNavigationBarColor(color = colorResource(R.color.black3))

    val backStack = remember { mutableStateListOf<Screen>(Screen.Splash) }
    val currentScreen  = backStack.last()

    fun popBackStack(){
        if(backStack.size > 1){
            backStack.removeAt(backStack.lastIndex)
        }
    }

    BackHandler(enabled = backStack.size > 1) {
        popBackStack()
    }

    when(val screen = currentScreen){
        Screen.Splash -> {
            SplashScreen(onNavigateToDashboard = {
                backStack.removeAll { it == Screen.Splash }
                if (UserSession.isLoggedIn)
                    backStack.add(Screen.Dashboard)
                else
                    backStack.add(Screen.Login)

            })
        }

        Screen.Login -> {
            LoginScreen(
                onLoginClick = {
                    backStack.removeAll { it == Screen.Login }
                    backStack.add(Screen.Dashboard)
                },
                onSkipClick = {
                    backStack.removeAll { it == Screen.Login }
                    backStack.add(Screen.Dashboard)
                }
            )
        }

        Screen.Dashboard -> {
            DashboardScreen(
                onCategoryClick = { id, title ->
                    backStack.add(Screen.Results(id, title))
                },
                onFavoriteClick = {
                    backStack.add(Screen.Favorite)
                },
                onProfileClick = {
                    backStack.add(Screen.Profile)
                }
            )
        }

        Screen.Favorite -> {
            FavoriteScreen(
                onHomeClick = {
                    backStack.clear()
                    backStack.add(Screen.Dashboard)
                },
                onStoreClick = { store ->
                    backStack.add(Screen.Map(store))
                },
                onProfileClick = {
                    backStack.add(Screen.Profile)
                },
                onLoginClick = {
                    backStack.add(Screen.Login)
                }
            )
        }

        Screen.Profile -> {
            ProfileScreen(
                onHomeClick = {
                    backStack.clear()
                    backStack.add(Screen.Dashboard)
                },
                onFavoriteClick = {
                    backStack.add(Screen.Favorite)
                },
                onLogoutClick = {
                    backStack.clear()
                    backStack.add(Screen.Login)
                },
                onLoginClick = {
                    backStack.add(Screen.Login)
                }
            )
        }

        is Screen.Results -> {
            ResultList(
                id = screen.id,
                title = screen.title,
                onHomeClick = {
                    backStack.clear()
                    backStack.add(Screen.Dashboard)
                },
                onFavoriteClick = {
                    backStack.add(Screen.Favorite)
                },
                onStoreClick = { store ->
                    backStack.add(Screen.Map(store))
                },
                onProfileClick = {
                    backStack.add(Screen.Profile)
                }
            )
        }

        is Screen.Map -> {
            MapScreen(
                store = screen.store,
                onBackClick = { popBackStack() }
            )
        }
    }
}
