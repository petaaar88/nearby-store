package com.met.nearby.store.domain

import java.io.Serializable


data class StoreModel(
    var Id: Int = 0,
    var CategoryId: String = "",
    var Title: String = "",
    var Latitude: Double = 0.0,
    var Longitude: Double = 0.0,
    var Address: String = "",
    var Call: String = "",
    var Activity: String = "",
    var ShortAddress: String = "",
    var ImagePath: String = ""
): Serializable
