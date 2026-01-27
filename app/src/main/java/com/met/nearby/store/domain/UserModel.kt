package com.met.nearby.store.domain

data class UserModel(
    val Id: Int = 0,
    val FirstName: String = "",
    val LastName: String = "",
    val Email: String = "",
    val Password: String = "",
    val ImageUrl: String = "",
    val FavoriteStores: List<Int> = emptyList()
)
