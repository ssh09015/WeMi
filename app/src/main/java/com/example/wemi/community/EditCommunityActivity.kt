package com.example.wemi.community

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wemi.MainActivity
import com.example.wemi.R
import kotlinx.android.synthetic.main.activity_edit_community.*
import kotlinx.android.synthetic.main.item_community_content.*
class EditCommunityActivity : AppCompatActivity() {

    lateinit var sort : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_community)

        comSortChoice.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.sortNum1 -> {
                    sort = "질문"
                }
                R.id.sortNum2 -> {
                    sort = "용품 나눔"
                }
                R.id.sortNum3 -> {
                    sort = "같이해요"
                }
                R.id.sortNum4 -> {
                    sort = "기타"
                }
            }
        }

        btnSave.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("sort", comSort.text.toString())
            intent.putExtra("nickname", comNickname.text.toString())
            intent.putExtra("title", comTitle.text.toString())
            intent.putExtra("content", comContent.text.toString())
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        comBackBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

    }
}