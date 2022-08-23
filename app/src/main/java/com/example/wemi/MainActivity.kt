package com.example.wemi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var button2: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button2=findViewById<Button>(R.id.button2)
        button2.setOnClickListener {
            startActivity(Intent(this@MainActivity,MapView::class.java))
        }
    }
}