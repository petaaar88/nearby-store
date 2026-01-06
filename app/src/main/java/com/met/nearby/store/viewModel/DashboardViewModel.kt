package com.met.nearby.store.viewModel

import androidx.lifecycle.LiveData
import com.met.nearby.store.domain.BannerModel
import com.met.nearby.store.domain.CategoryModel

class DashboardViewModel {

    private val repository = DashboardViewModel()

    fun loadCategory(): LiveData<MutableList<CategoryModel>>{
        return repository.loadCategory()
    }

    fun loadBanner(): LiveData<MutableList<BannerModel>>{
        return repository.loadBanner()
    }
}