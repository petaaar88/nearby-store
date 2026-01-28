package com.met.nearby.store.screens.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.met.nearby.store.R

@Composable
fun TopBar(
    isLoggedIn: Boolean = false,
    firstName: String? = null,
    lastName: String? = null,
    imageUrl: String? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Hello",
                fontSize = 14.sp,
                color = colorResource(id = R.color.gold)
            )
            Text(
                text = buildFullName(firstName, lastName),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.white)
            )
        }

        if (isLoggedIn && !imageUrl.isNullOrEmpty()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Profile",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.ic_person),
                error = painterResource(id = R.drawable.ic_person)
            )
        } else {
            Image(
                painter = painterResource(id = if (isLoggedIn) R.drawable.profile else R.drawable.ic_person),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

private fun buildFullName(firstName: String?, lastName: String?): String {
    val first = firstName?.takeIf { it.isNotBlank() } ?: ""
    val last = lastName?.takeIf { it.isNotBlank() } ?: ""
    return "$first $last".trim().ifEmpty { "Guest" }
}
