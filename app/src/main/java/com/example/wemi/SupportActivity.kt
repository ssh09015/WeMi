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

        // BottomNavigation 기능 구현
        nav_bar.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.community -> { // community 이름 버튼 누르면 MainActivity 이동
                        myStartActivity(MainActivity::class.java)
                      // Respond to navigation item 3 click
                        true // 해당 버튼 활성화
                   }
                   R.id.location -> { // location 이름 버튼 누르면 MapViewActivity로 이동
                       myStartActivity(MapViewActivity::class.java)
                       // Respond to navigation item 2 click
                        true // 해당 버튼 활성화
                    }
                    R.id.myPage -> { // myPage 이름 버튼 누르면 MypageActivity로 이동
                        myStartActivity(MypageActivity::class.java)
                        // Respond to navigation item 4 click
                        true // 해당 버튼 활성화
                    }
                }
                true // 지금 선택된 Id 버튼 활성화
            }
            selectedItemId = R.id.support // 지금 선택할 Id 버튼을 support라고 지정
        }
    }

    // 애니메이션 사용하지 않고 Intent로 화면전환하는 함수
    private fun myStartActivity(c: Class<*>) {
        val intent = Intent(this, c)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
    }
}