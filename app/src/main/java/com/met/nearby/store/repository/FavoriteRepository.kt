package com.met.nearby.store.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.met.nearby.store.auth.UserSession
import com.met.nearby.store.domain.StoreModel

sealed class FavoriteResult {
    data object Success : FavoriteResult()
    data class Error(val message: String) : FavoriteResult()
    data object Loading : FavoriteResult()
}

class FavoriteRepository {
    private val firebaseDatabase = FirebaseDatabase.getInstance(
        "https://nearby-store-24e14-default-rtdb.europe-west1.firebasedatabase.app"
    )

    fun addFavorite(storeId: Int): LiveData<FavoriteResult> {
        val result = MutableLiveData<FavoriteResult>()
        result.value = FavoriteResult.Loading

        val userIndex = UserSession.userId - 1
        val newFavorites = UserSession.favoriteStores.toMutableList().apply {
            if (!contains(storeId)) add(storeId)
        }

        firebaseDatabase.getReference("Users")
            .child(userIndex.toString())
            .child("FavoriteStores")
            .setValue(newFavorites)
            .addOnSuccessListener {
                UserSession.addFavorite(storeId)
                result.value = FavoriteResult.Success
            }
            .addOnFailureListener { error ->
                result.value = FavoriteResult.Error(error.message ?: "Failed to add favorite")
            }

        return result
    }

    fun removeFavorite(storeId: Int): LiveData<FavoriteResult> {
        val result = MutableLiveData<FavoriteResult>()
        result.value = FavoriteResult.Loading

        val userIndex = UserSession.userId - 1
        val newFavorites = UserSession.favoriteStores.toMutableList().apply {
            remove(storeId)
        }

        firebaseDatabase.getReference("Users")
            .child(userIndex.toString())
            .child("FavoriteStores")
            .setValue(newFavorites)
            .addOnSuccessListener {
                UserSession.removeFavorite(storeId)
                result.value = FavoriteResult.Success
            }
            .addOnFailureListener { error ->
                result.value = FavoriteResult.Error(error.message ?: "Failed to remove favorite")
            }

        return result
    }

    fun toggleFavorite(storeId: Int): LiveData<FavoriteResult> {
        return if (UserSession.isFavorite(storeId)) {
            removeFavorite(storeId)
        } else {
            addFavorite(storeId)
        }
    }

    fun loadFavoriteStores(): LiveData<List<StoreModel>> {
        val result = MutableLiveData<List<StoreModel>>()
        val favoriteIds = UserSession.favoriteStores

        if (favoriteIds.isEmpty()) {
            result.value = emptyList()
            return result
        }

        val allStores = mutableListOf<StoreModel>()
        var loadedCollections = 0

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (child in snapshot.children) {
                    val store = child.getValue(StoreModel::class.java)
                    if (store != null && favoriteIds.contains(store.Id)) {
                        if (allStores.none { it.Id == store.Id }) {
                            allStores.add(store)
                        }
                    }
                }
                loadedCollections++
                if (loadedCollections >= 2) {
                    result.value = allStores.toList()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                loadedCollections++
                if (loadedCollections >= 2) {
                    result.value = allStores.toList()
                }
            }
        }

        firebaseDatabase.getReference("Stores").addListenerForSingleValueEvent(listener)
        firebaseDatabase.getReference("Nearest").addListenerForSingleValueEvent(listener)

        return result
    }
}
