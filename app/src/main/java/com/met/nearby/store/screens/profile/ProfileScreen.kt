package com.met.nearby.store.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.met.nearby.store.R
import com.met.nearby.store.auth.UserSession
import com.met.nearby.store.screens.dashboard.prepareBottomMenu

@Composable
fun ProfileScreen(
    onHomeClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onLoginClick: () -> Unit = {}
) {
    val firstName = UserSession.userFirstName ?: "User"
    val lastName = UserSession.userLastName ?: ""
    val email = UserSession.userEmail ?: ""

    Scaffold(
        containerColor = colorResource(R.color.black2),
        bottomBar = {
            ProfileBottomBar(
                onHomeClick = onHomeClick,
                onFavoriteClick = onFavoriteClick
            )
        }
    ) { paddingValues ->
        if (UserSession.isLoggedIn) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(colorResource(R.color.black3)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_person),
                        contentDescription = "Profile",
                        tint = colorResource(R.color.gray),
                        modifier = Modifier.size(60.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (lastName.isNotEmpty()) "$firstName $lastName" else firstName,
                    color = colorResource(R.color.white),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = email,
                    color = colorResource(R.color.gray),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                if (firstName.isNotEmpty()) {
                    ProfileInfoCard(label = "First Name", value = firstName)
                    Spacer(modifier = Modifier.height(12.dp))
                }
                if (lastName.isNotEmpty()) {
                    ProfileInfoCard(label = "Last Name", value = lastName)
                    Spacer(modifier = Modifier.height(12.dp))
                }
                ProfileInfoCard(label = "Email", value = email)

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        UserSession.logout()
                        onLogoutClick()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.gold)
                    )
                ) {
                    Text(
                        text = "Logout",
                        color = colorResource(R.color.black2),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
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
                        text = "Login to view your profile",
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
                        Text(
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
private fun ProfileInfoCard(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(colorResource(R.color.black3))
            .padding(16.dp)
    ) {
        Text(
            text = label,
            color = colorResource(R.color.gray),
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            color = colorResource(R.color.white),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun ProfileBottomBar(
    onHomeClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    val bottomMenuItemList = prepareBottomMenu()

    Column(
        modifier = Modifier.navigationBarsPadding()
    ) {
        Divider(
            color = colorResource(R.color.black2),
            thickness = 1.dp
        )

        BottomAppBar(
            backgroundColor = colorResource(R.color.black3),
            elevation = 0.dp
        ) {
            bottomMenuItemList.forEach { menuItem ->
                BottomNavigationItem(
                    selected = (menuItem.label == "Profile"),
                    onClick = {
                        when (menuItem.label) {
                            "Home" -> onHomeClick()
                            "Favorite" -> onFavoriteClick()
                        }
                    },
                    icon = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            androidx.compose.material.Icon(
                                painter = menuItem.icon,
                                contentDescription = null,
                                tint = colorResource(R.color.white),
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .size(20.dp)
                            )

                            androidx.compose.material.Text(
                                text = menuItem.label,
                                fontSize = 12.sp,
                                color = colorResource(R.color.white),
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                )
            }
        }
    }
}
