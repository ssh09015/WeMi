package com.example.wemi.community

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wemi.fragments.ComAllFragment
import kotlinx.android.synthetic.main.activity_edit_community.*

class EditCommunityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        comBackBtn.setOnClickListener {
            val intent = Intent(this, ComAllFragment::class.java)
            startActivity(intent)
        }

    }
}