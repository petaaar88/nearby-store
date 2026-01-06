package com.met.nearby.store.screens.dashboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.met.nearby.store.R
import com.met.nearby.store.domain.CategoryModel
import com.met.nearby.store.repository.DashboardRepository

@Composable
fun DashboardScreen(onCategoryClick: (id: String, title: String) -> Unit){

    val viewModel = DashboardRepository()

    val categories = remember { mutableStateListOf<CategoryModel>() }

    var showCategoryLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.loadCategory().observeForever {
            categories.clear()
            categories.addAll(it)
            showCategoryLoading = false
        }
    }

    Scaffold(
        containerColor = colorResource(id = R.color.black2),
        bottomBar = { BottomBar() }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.fillMaxSize()
            .padding(paddingValues = paddingValues)
        ) {
            item { TopBar() }
            item { CategorySection(categories, showCategoryLoading, onCategoryClick) }
        }
    }
}