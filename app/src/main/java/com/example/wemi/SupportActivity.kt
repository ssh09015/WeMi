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

        nav_bar.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.community -> {
                        myStartActivity(MainActivity::class.java)
                      // Respond to navigation item 3 click
                        true
                   }
                   R.id.location -> {
                       myStartActivity(MapViewActivity::class.java)
                       // Respond to navigation item 2 click
                        true
                    }
                    R.id.myPage -> {
                        myStartActivity(MypageActivity::class.java)
                        // Respond to navigation item 4 click
                        true
                    }
                }
                true
            }
            selectedItemId = R.id.support
        }
    }
    private fun myStartActivity(c: Class<*>) {
        val intent = Intent(this, c)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
    }
}