package com.example.wemi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    lateinit var button2: Button
    lateinit var supportBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navi_bar = findViewById<BottomNavigationView>(R.id.nav_bar)
        button2=findViewById<Button>(R.id.button2)
        button2.setOnClickListener {
            startActivity(Intent(this,MapViewActivity::class.java))
        }

        supportBtn = findViewById<Button>(R.id.supportBtn)
        supportBtn.setOnClickListener {
            val intent = Intent(this, SupportActivity::class.java)
            startActivity(intent)
        }

        navi_bar.setOnItemSelectedListener { item ->

            when(item.itemId) {
                R.id.location -> {
                    val intent = Intent(this, MapViewActivity::class.java)
                    startActivity(intent)
                    // Respond to navigation item 2 click
                    true
                }
                R.id.support -> {
                    val intent = Intent(this, SupportActivity::class.java)
                    startActivity(intent)
                    // Respond to navigation item 3 click
                    true
                }
                R.id.myPage -> {
                    val intent = Intent(this, MypageActivity::class.java)
                    startActivity(intent)
                    // Respond to navigation item 4 click
                    true
                }
                else -> false
            }
        }
    }
}