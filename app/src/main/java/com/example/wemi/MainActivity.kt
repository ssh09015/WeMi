package com.example.wemi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wemi.community.EditCommunityActivity
import com.example.wemi.databinding.ActivityMainBinding
import com.example.wemi.fragments.ComAllFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        var sort = intent.getStringExtra("sort")
        var nickname = intent.getStringExtra("nickname")
        var title = intent.getStringExtra("title")
        var content = intent.getStringExtra("content")
        val bundle = Bundle()

        bundle.putString("sort", sort)
        bundle.putString("nickname", nickname)
        bundle.putString("title", title)
        bundle.putString("content", content)

        val fragmentA = ComAllFragment()
        fragmentA.arguments = bundle

        val nav_bar = findViewById<BottomNavigationView>(R.id.nav_bar)

        addContent.setOnClickListener {
            myStartActivity(EditCommunityActivity::class.java)
        }

        // BottomNavigation 기능 구현
        nav_bar.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
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
                    R.id.myPage -> { // myPage 이름 버튼 누르면 MypageActivity로 이동
                        myStartActivity(MypageActivity::class.java)
                        // Respond to navigation item 4 click
                        true
                    }
                }
                true // 지금 선택된 Id 버튼 활성화
            }
            selectedItemId=R.id.community // 지금 선택할 Id 버튼을 community라고 지정
        }
    }

    // 애니메이션 사용하지 않고 Intent로 화면전환하는 함수
    private fun myStartActivity(c: Class<*>) {
        val intent = Intent(this, c)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
    }
}