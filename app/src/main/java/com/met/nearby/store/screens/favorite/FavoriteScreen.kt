package com.met.nearby.store.screens.favorite

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.met.nearby.store.R
import com.met.nearby.store.auth.UserSession
import com.met.nearby.store.domain.StoreModel
import com.met.nearby.store.repository.FavoriteRepository
import com.met.nearby.store.screens.dashboard.BottomBar
import com.met.nearby.store.screens.results.ItemsNearest

@Composable
fun FavoriteScreen(
    onHomeClick: () -> Unit,
    onStoreClick: (StoreModel) -> Unit,
    onProfileClick: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    val favoriteRepository = remember { FavoriteRepository() }
    var favoriteStores by remember { mutableStateOf<List<StoreModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(UserSession.favoriteStores) {
        if (UserSession.isLoggedIn) {
            isLoading = true
            favoriteRepository.loadFavoriteStores().observeForever { stores ->
                favoriteStores = stores
                isLoading = false
            }
        } else {
            isLoading = false
        }
    }

    Scaffold(
        containerColor = colorResource(id = R.color.black2),
        bottomBar = {
            BottomBar(
                selectedTab = "Favorite",
                onHomeClick = onHomeClick,
                onFavoriteClick = {},
                onProfileClick = onProfileClick
            )
        }
    ) { paddingValues ->
        if (UserSession.isLoggedIn) {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(color = colorResource(R.color.black2)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp),
                        color = colorResource(R.color.gold)
                    )
                }
            } else if (favoriteStores.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(color = colorResource(R.color.black2)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No favorite stores yet",
                        fontSize = 18.sp,
                        color = colorResource(R.color.gray),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(color = colorResource(R.color.black2))
                ) {
                    item { FavoriteTopTitle() }
                    items(favoriteStores) { store ->
                        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                            ItemsNearest(item = store, onClick = { onStoreClick(store) })
                        }
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(color = colorResource(R.color.black2)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(32.dp)
                ) {
                    Text(
                        text = "Login to add favorite stores",
                        fontSize = 18.sp,
                        color = colorResource(R.color.gray),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onLoginClick,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.gold)
                        )
                    ) {
                        androidx.compose.material3.Text(
                            text = "Login",
                            color = colorResource(R.color.black2),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteTopTitle() {
    Box(
        modifier = Modifier.fillMaxWidth()
            .height(100.dp)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Favorite Stores",
                fontSize = 20.sp,
                color = colorResource(R.color.gold),
                modifier = Modifier.weight(1f),
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
        onHomeClick = {},
        onStoreClick = {}
    )
}
