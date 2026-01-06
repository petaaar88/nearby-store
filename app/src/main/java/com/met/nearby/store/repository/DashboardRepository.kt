package com.met.nearby.store.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.met.nearby.store.domain.CategoryModel

class DashboardRepository{
    private val firebaseDatabase  = FirebaseDatabase.getInstance("https://nearby-store-24e14-default-rtdb.europe-west1.firebasedatabase.app")

    fun loadCategory(): LiveData<MutableList<CategoryModel>>{
        val listData = MutableLiveData<MutableList<CategoryModel>>()

        val ref = firebaseDatabase.getReference("Category")
        ref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("FIREBASE", snapshot.value.toString())

                val list  = mutableListOf<CategoryModel>()

                for(childSnapshot in snapshot.children){
                    val item = childSnapshot.getValue(CategoryModel::class.java)

                    item?.let{
                        list.add(it)
                    }

                }

                listData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        return listData
    }
}