package com.met.nearby.store.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.met.nearby.store.domain.StoreModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object StoreModelNavType : NavType<StoreModel>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): StoreModel? {
        return bundle.getString(key)?.let { Json.decodeFromString(it) }
    }

    override fun parseValue(value: String): StoreModel {
        return Json.decodeFromString(Uri.decode(value))
    }

    override fun serializeAsValue(value: StoreModel): String {
        return Uri.encode(Json.encodeToString(value))
    }

    override fun put(bundle: Bundle, key: String, value: StoreModel) {
        bundle.putString(key, Json.encodeToString(value))
    }
}
