package com.example.wemi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wemi.support.SupportModel

class SupportActivity : AppCompatActivity() {

    private val supportDataList = mutableListOf<SupportModel>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)

        val supportList = mutableListOf<SupportModel>()
        supportList.add(SupportModel("a", "b", "c", "d"))



    }
}