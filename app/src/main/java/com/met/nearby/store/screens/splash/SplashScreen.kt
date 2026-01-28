package com.met.nearby.store.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.met.nearby.store.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateToDashboard: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000)
        onNavigateToDashboard()
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(colorResource(R.color.black2)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Nearby Store",
            color = colorResource(R.color.gold),
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )
    }
}
