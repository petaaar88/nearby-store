package com.met.nearby.store.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.met.nearby.store.domain.CategoryModel

class ResultsRepository{
    private val firebaseDatabase  = FirebaseDatabase.getInstance("https://nearby-store-24e14-default-rtdb.europe-west1.firebasedatabase.app")

    fun loadSubCategory(id: String): LiveData<MutableList<CategoryModel>>{
        val listData = MutableLiveData<MutableList<CategoryModel>>()
        val ref = firebaseDatabase.getReference("SubCategory")
        val query: Query = ref.orderByChild("CategoryId").equalTo(id)

        query.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<CategoryModel>()

                for(child in snapshot.children){
                    val model = child.getValue(CategoryModel::class.java)
                    if(model != null){
                        lists.add(model)
                    }
                }

                listData.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        return listData
    }
}