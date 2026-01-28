package com.met.nearby.store.screens.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.met.nearby.store.R
import com.met.nearby.store.auth.UserSession
import com.met.nearby.store.domain.StoreModel
import com.met.nearby.store.repository.FavoriteRepository

@Composable
fun StoreDetailsScreen(
    store: StoreModel,
    onBackClick: () -> Unit,
    onShowMapClick: () -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val favoriteRepository = remember { FavoriteRepository() }
    var isFavorite by remember { mutableStateOf(UserSession.isFavorite(store.Id)) }
    val isLoggedIn = UserSession.isLoggedIn

    Scaffold(
        containerColor = colorResource(id = R.color.black2),
        topBar = {
            StoreDetailsTopBar(
                title = "Store Details",
                onBackClick = onBackClick,
                showFavoriteButton = isLoggedIn,
                isFavorite = isFavorite,
                onFavoriteClick = {
                    favoriteRepository.toggleFavorite(store.Id).observeForever { result ->
                        isFavorite = UserSession.isFavorite(store.Id)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .background(color = colorResource(R.color.black2))
        ) {
            AsyncImage(
                model = store.ImagePath,
                contentDescription = store.Title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = store.Title,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = store.Activity,
                    color = colorResource(R.color.gold),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.location),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = store.Address,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_clock),
                        contentDescription = null,
                        tint = colorResource(R.color.gold),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = store.Hours,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }

                if (store.Description.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "About",
                        color = colorResource(R.color.gold),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = store.Description,
                        color = Color.White,
                        fontSize = 14.sp,
                        lineHeight = 22.sp
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = onShowMapClick,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.gold)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text(
                        text = "Show on Map",
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = {
                        val intent = Intent(
                            Intent.ACTION_DIAL,
                            Uri.parse("tel:${store.Call}")
                        )
                        context.startActivity(intent)
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text(
                        text = "Call Store",
                        fontSize = 16.sp,
                        color = colorResource(R.color.gold),
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun StoreDetailsTopBar(
    title: String,
    onBackClick: () -> Unit,
    showFavoriteButton: Boolean = false,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(95.dp)
            .padding(top = 10.dp)
            .background(colorResource(R.color.black2))
            .padding(horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Text(
                text = title,
                color = colorResource(R.color.gold),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            if (showFavoriteButton) {
                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                        tint = colorResource(R.color.gold)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun StoreDetailsScreenPreview() {
    val store = StoreModel(
        Id = 0,
        Title = "Tasty Bites",
        Address = "45 Main St. Riverside Park",
        Activity = "Dine-in . Takeaway . Delivery",
        Hours = "10 am - 10pm",
        Description = "Savor the flavors of home-cooked goodness at Tasty Bites. We specialize in generous portions of comfort food made with love and fresh ingredients.",
        ImagePath = "",
        Call = "+123456789"
    )

    StoreDetailsScreen(
        store = store,
        onBackClick = {},
        onShowMapClick = {}
    )
}
