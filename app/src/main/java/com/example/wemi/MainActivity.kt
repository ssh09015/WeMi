package com.example.wemi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var button2: Button
    lateinit var supportBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button2=findViewById<Button>(R.id.button2)
        button2.setOnClickListener {
            startActivity(Intent(this,MapViewActivity::class.java))
        }

        supportBtn = findViewById<Button>(R.id.supportBtn)
        supportBtn.setOnClickListener {
            val intent = Intent(this, SupportActivity::class.java)
            startActivity(intent)
        }
    }
}