package com.example.wemi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.wemi.auth.IntroActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MypageActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        auth = Firebase.auth

        findViewById<TextView>(R.id.settingBtn2).setOnClickListener {
            auth.signOut()

            val intent = Intent(this, IntroActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        val nav_bar = findViewById<BottomNavigationView>(R.id.nav_bar)

        // BottomNavigation 기능 구현
        nav_bar.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.community -> { // community 이름 버튼 누르면 MainActivity로 이동
                        myStartActivity(MainActivity::class.java)
                        // Respond to navigation item 4 click
                        true
                    }
                    R.id.location -> { // location 이름 버튼 누르면 MapViewActivity로 이동
                        myStartActivity(MapViewActivity::class.java)
                        // Respond to navigation item 2 click
                        true // 해당 버튼 활성화
                    }
                    R.id.support -> { // support 이름 버튼 누르면 SupportActivity로 이동
                        myStartActivity(SupportActivity::class.java)
                        // Respond to navigation item 3 click
                        true
                    }
                }
                true // 지금 선택된 Id 버튼 활성화
            }
            selectedItemId = R.id.myPage // 지금 선택할 Id 버튼을 community라고 지정
        }
    }
    // 애니메이션 사용하지 않고 Intent로 화면전환하는 함수
    private fun myStartActivity(c: Class<*>) {
        val intent = Intent(this, c)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}