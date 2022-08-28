package com.example.wemi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ReviewMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_main)
        val floatingActionButton:View=findViewById(R.id.floatingActionButton)
        floatingActionButton.setOnClickListener { view ->
            val intent=Intent(this,WriteReviewActivity::class.java)
            startActivity(intent)
        }

    }
}