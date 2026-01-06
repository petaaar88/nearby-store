package com.met.nearby.store.screens.dashboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.met.nearby.store.R

@Composable
fun DashboardScreen(){
    Scaffold(
        containerColor = colorResource(id = R.color.black2),
        bottomBar = { BottomBar() }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.fillMaxSize()
            .padding(paddingValues = paddingValues)
        ) {
            item{TopBar()}
        }
    }
}