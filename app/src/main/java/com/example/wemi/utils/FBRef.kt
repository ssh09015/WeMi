package com.example.wemi.utils

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FBRef {

    companion object{
        private val database = Firebase.database

        val supportRef = database.getReference("support")
        val applyRef = database.getReference("apply_list")
    }

}