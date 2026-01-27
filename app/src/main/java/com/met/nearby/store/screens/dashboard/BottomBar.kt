package com.met.nearby.store.screens.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.met.nearby.store.R


data class BottomMenuItem(
    val label: String, val icon: Painter
)


@Composable
fun prepareBottomMenu(): List<BottomMenuItem>{
    val bottomMenuItemList = arrayListOf<BottomMenuItem>()

    bottomMenuItemList.add(
        BottomMenuItem(label = "Home", icon = painterResource(id = R.drawable.btn_1))
    )
    bottomMenuItemList.add(
        BottomMenuItem(label = "Favorite", icon = painterResource(id = R.drawable.btn_3))
    )
    bottomMenuItemList.add(
        BottomMenuItem(label = "Profile", icon = painterResource(id = R.drawable.btn_4))
    )

    return bottomMenuItemList
}

@Composable
fun BottomBar(
    selectedTab: String = "Home",
    onHomeClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
){
    val bottomMenuItemList = prepareBottomMenu()

    Column(
        modifier = Modifier.navigationBarsPadding()
    ) {
        Divider(
            color = colorResource(id = R.color.black2),
            thickness = 1.dp
        )

        BottomAppBar(
            backgroundColor = colorResource(id = R.color.black3),
            elevation = 0.dp
        ) {
            bottomMenuItemList.forEach { menuItem ->
                BottomNavigationItem(
                    selected = (selectedTab == menuItem.label),
                    onClick = {
                        when (menuItem.label) {
                            "Home" -> onHomeClick()
                            "Favorite" -> onFavoriteClick()
                            "Profile" -> onProfileClick()
                        }
                    }, icon = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                painter = menuItem.icon,
                                contentDescription = null,
                                tint = colorResource(id = R.color.white),
                                modifier = Modifier.padding(top = 8.dp)
                                    .size(20.dp)
                            )

                            Text(
                                text = menuItem.label,
                                fontSize = 12.sp,
                                color = colorResource(id = R.color.white),
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                )
            }
        }
    }
}