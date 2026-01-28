package com.met.nearby.store.domain

import java.io.Serializable


data class StoreModel(
    var Id: Int = 0,
    var CategoryId: String = "",
    var SubCategoryId: String = "",
    var Title: String = "",
    var Latitude: Double = 0.0,
    var Longitude: Double = 0.0,
    var Address: String = "",
    var Call: String = "",
    var Activity: String = "",
    var ShortAddress: String = "",
    var Hours: String = "",
    var ImagePath: String = "",
    var Description: String = ""
): Serializable
