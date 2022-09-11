package com.example.wemi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class SupportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)

        val nav_bar = findViewById<BottomNavigationView>(R.id.nav_bar)

        nav_bar.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.community -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                    // Respond to navigation item 3 click
                    true
                }
                R.id.location -> {
                    val intent = Intent(this, MapViewActivity::class.java)
                    intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                    // Respond to navigation item 2 click
                    true
                }
                R.id.myPage -> {
                    val intent = Intent(this, MypageActivity::class.java)
                    intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                    // Respond to navigation item 4 click
                    true
                }
                else -> false
            }
        }
    }
}