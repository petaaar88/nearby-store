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
import com.met.nearby.store.domain.StoreModel
import com.met.nearby.store.screens.dashboard.DashboardScreen
import com.met.nearby.store.screens.map.MapScreen
import com.met.nearby.store.screens.results.ResultList
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
    data object Dashboard: Screen()
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
                backStack.add(Screen.Dashboard)
            })
        }

        Screen.Dashboard -> {
            DashboardScreen(onCategoryClick = { id, title ->
                backStack.add(Screen.Results(id, title))
            })
        }

        is Screen.Results -> {
            ResultList(
                id = screen.id,
                title = screen.title,
                onBackClick = {
                    popBackStack()
                },
                onStoreClick = { store ->
                    backStack.add(Screen.Map(store))
                }
            )
        }

        is Screen.Map -> {
            MapScreen(store = screen.store)
        }
    }
}
