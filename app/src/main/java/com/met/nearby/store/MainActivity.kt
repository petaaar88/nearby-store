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
import com.met.nearby.store.screens.dashboard.DashboardScreen

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
    object Dashboard : Screen()
}

@Composable
fun MainApp() {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = colorResource(R.color.white))

    val backStack = remember { mutableStateListOf<Screen>(Screen.Dashboard) }
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
        Screen.Dashboard -> {
            DashboardScreen()
        }
    }
}
