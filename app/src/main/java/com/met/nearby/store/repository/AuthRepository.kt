package com.met.nearby.store.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.met.nearby.store.domain.UserModel

sealed class AuthResult {
    data class Success(val user: UserModel) : AuthResult()
    data class Error(val message: String) : AuthResult()
    data object Loading : AuthResult()
}

class AuthRepository {
    private val firebaseDatabase = FirebaseDatabase.getInstance(
        "https://nearby-store-24e14-default-rtdb.europe-west1.firebasedatabase.app"
    )

    fun authenticate(email: String, password: String): LiveData<AuthResult> {
        val result = MutableLiveData<AuthResult>()
        result.value = AuthResult.Loading

        firebaseDatabase.getReference("Users")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (child in snapshot.children) {
                        val user = child.getValue(UserModel::class.java)
                        if (user != null &&
                            user.Email.equals(email, ignoreCase = true) &&
                            user.Password == password
                        ) {
                            result.value = AuthResult.Success(user)
                            return
                        }
                    }
                    result.value = AuthResult.Error("Invalid email or password")
                }

                override fun onCancelled(error: DatabaseError) {
                    result.value = AuthResult.Error("Connection error: ${error.message}")
                }
            })

        return result
    }
}
