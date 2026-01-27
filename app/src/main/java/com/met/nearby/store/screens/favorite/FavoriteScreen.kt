package com.met.nearby.store.screens.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.met.nearby.store.domain.StoreModel
import com.met.nearby.store.screens.results.ItemsNearest

@Composable
fun FavoriteScreen(
    onBackClick: () -> Unit,
    onStoreClick: (StoreModel) -> Unit
) {
    val favoriteStores = listOf(
        StoreModel(
            Id = 0,
            CategoryId = "0",
            Title = "Tasty Bites",
            Address = "45 Main St. Riverside Park",
            ShortAddress = "45 Main St.",
            Activity = "Dine-in . Takeaway . Delivery",
            Hours = "10 am - 10pm",
            ImagePath = "https://res.cloudinary.com/dkikc5ywq/image/upload/v1742567633/store_5_ihfszx.jpg",
            Latitude = 40.71324,
            Longitude = -73.8815,
            Call = "+123456789"
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.black2))
    ) {
        item { FavoriteTopTitle(onBackClick) }
        items(favoriteStores) { store ->
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                ItemsNearest(item = store, onClick = { onStoreClick(store) })
            }
        }
    }
}

@Composable
fun FavoriteTopTitle(onBackClick: () -> Unit = {}) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .height(100.dp)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 16.dp)
                .padding(top = 55.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.back),
                contentDescription = null,
                modifier = Modifier.clickable { onBackClick() }
            )

            Text(
                text = "Favorite Stores",
                fontSize = 20.sp,
                color = colorResource(R.color.gold),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                fontWeight = FontWeight.Bold
            )

            Image(
                painter = painterResource(R.drawable.btn_3),
                contentDescription = null
            )
        }
    }
}

@Composable
@Preview
fun FavoriteScreenPreview() {
    FavoriteScreen(
        onBackClick = {},
        onStoreClick = {}
    )
}
