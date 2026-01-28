package com.met.nearby.store.screens.results

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.met.nearby.store.R

@Composable
@Preview
fun TopTitle(title: String = "title"){
    Box(modifier = Modifier.fillMaxWidth()
        .height(100.dp)
    ) {
        Row(modifier = Modifier.align(Alignment.Center)
            .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title+" Stores Result",
                fontSize = 20.sp,
                color = colorResource(R.color.gold),
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold
            )

            Image(painter = painterResource(R.drawable.sample),
                contentDescription = null,
            )
        }
    }
}