package com.met.nearby.store.screens.results

import ResultsViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.met.nearby.store.R
import com.met.nearby.store.domain.CategoryModel
import com.met.nearby.store.domain.StoreModel

@Composable
fun ResultList(id: String, title: String, onBackClick: () -> Unit, onStoreClick: (StoreModel) -> Unit){

    val viewModel = ResultsViewModel()

    val subCategory = remember { mutableStateListOf<CategoryModel>() }
    val popular = remember { mutableStateListOf<StoreModel>() }
    val nearest = remember { mutableStateListOf<StoreModel>() }

    var searchText by remember { mutableStateOf("") }

    val filteredNearest = remember(nearest.toList(), searchText) {
        if (searchText.isBlank()) nearest.toList()
        else nearest.filter { it.Title.contains(searchText, ignoreCase = true) }
    }

    var showSubCategoryLoading by remember { mutableStateOf(true) }
    var showPopularLoading by remember { mutableStateOf(true) }
    var showNearestLoading by remember { mutableStateOf(true) }

    LaunchedEffect(id){
        viewModel.loadSubCategory(id).observeForever {
            subCategory.clear()
            subCategory.addAll(it)
            showSubCategoryLoading = false
        }
    }

    LaunchedEffect(id){
        viewModel.loadPopluar(id).observeForever {
            popular.clear()
            popular.addAll(it)
            showPopularLoading = false
        }
    }

    LaunchedEffect(id){
        viewModel.loadNearest(id).observeForever {
            nearest.clear()
            nearest.addAll(it)
            showNearestLoading = false
        }
    }

    LazyColumn(modifier = Modifier.fillMaxSize()
        .background(color = colorResource(R.color.black2))) {
        item { TopTitle(title, onBackClick) }
        item { Search(searchText = searchText, onSearchChange = { searchText = it }) }
        item { SubCategory(subCategory, showSubCategoryLoading) }
        item { PopularSection(popular.toList(), showPopularLoading, onStoreClick)  }
        item { NearestList(list = filteredNearest, showNearestLoading, onStoreClick) }
    }
}

@Composable
@Preview
fun ResultListPreview(){
    ResultList(
        id = "1",
        title = "Sample Title",
        onBackClick = {},
        onStoreClick = {}
    )
}