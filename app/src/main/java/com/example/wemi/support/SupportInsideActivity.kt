package com.example.wemi.support

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.wemi.R
import com.example.wemi.databinding.ActivitySupportInsideBinding
import com.example.wemi.fragments.AllFragment
import com.example.wemi.mypage.FindPWActivity
import com.example.wemi.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_mypage.*
import kotlinx.android.synthetic.main.activity_support_inside.*
import java.lang.Exception

class SupportInsideActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySupportInsideBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_support_inside)

        //key값 받아오기
        val key = intent.getStringExtra("key")
        if (key != null) {
            getSupportData(key)
        }

        //뒤로가기 버튼
        backBtn.setOnClickListener {
            finish()
        }

        // 신청하기 버튼
        applyBtn.setOnClickListener {
            val getUrl = intent.getStringExtra("webUrl")
            val i = Intent(Intent.ACTION_VIEW)

            i.data = Uri.parse(getUrl.toString())
            startActivity(i)
        }

    }

    private fun getSupportData(key : String){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val dataModel = dataSnapshot.getValue(SupportModel::class.java)
                //Log.d("SupportTest", dataModel!!.title)

                binding.sortArea.text = dataModel!!.sort
                binding.titleArea.text = dataModel!!.title
                binding.organizationArea.text = dataModel!!.organization
                binding.periodArea.text = dataModel!!.period
                binding.targetArea.text = dataModel!!.target
                binding.contentArea.text = dataModel!!.content

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("SupportTEst", "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.supportRef.child(key).addValueEventListener(postListener)
    }
}