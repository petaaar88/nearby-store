package com.met.nearby.store.auth

import android.content.Context
import android.content.SharedPreferences

object UserSession {
    private const val PREFS_NAME = "nearby_store_prefs"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"
    private const val KEY_USER_EMAIL = "user_email"
    private const val KEY_USER_FIRST_NAME = "user_first_name"
    private const val KEY_USER_LAST_NAME = "user_last_name"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_USER_IMAGE_URL = "user_image_url"

    private lateinit var prefs: SharedPreferences

    var isLoggedIn: Boolean
        get() = if (::prefs.isInitialized) prefs.getBoolean(KEY_IS_LOGGED_IN, false) else false
        private set(value) = prefs.edit().putBoolean(KEY_IS_LOGGED_IN, value).apply()

    var userEmail: String?
        get() = if (::prefs.isInitialized) prefs.getString(KEY_USER_EMAIL, null) else null
        private set(value) = prefs.edit().putString(KEY_USER_EMAIL, value).apply()

    var userFirstName: String?
        get() = if (::prefs.isInitialized) prefs.getString(KEY_USER_FIRST_NAME, null) else null
        private set(value) = prefs.edit().putString(KEY_USER_FIRST_NAME, value).apply()

    var userLastName: String?
        get() = if (::prefs.isInitialized) prefs.getString(KEY_USER_LAST_NAME, null) else null
        private set(value) = prefs.edit().putString(KEY_USER_LAST_NAME, value).apply()

    var userId: Int
        get() = if (::prefs.isInitialized) prefs.getInt(KEY_USER_ID, 0) else 0
        private set(value) = prefs.edit().putInt(KEY_USER_ID, value).apply()

    var userImageUrl: String?
        get() = if (::prefs.isInitialized) prefs.getString(KEY_USER_IMAGE_URL, null) else null
        private set(value) = prefs.edit().putString(KEY_USER_IMAGE_URL, value).apply()

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun login(id: Int, email: String, firstName: String, lastName: String, imageUrl: String?) {
        isLoggedIn = true
        userId = id
        userEmail = email
        userFirstName = firstName
        userLastName = lastName
        userImageUrl = imageUrl
    }

    fun logout() {
        isLoggedIn = false
        userId = 0
        userEmail = null
        userFirstName = null
        userLastName = null
        userImageUrl = null
    }
}
