package com.example.wemi

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.wemi.auth.IntroActivity
import com.example.wemi.mypage.FindPWActivity
import com.example.wemi.support.SupportModel
import com.example.wemi.utils.FBAuth
import com.example.wemi.utils.FBAuth.Companion.getUid
import com.example.wemi.utils.FBRef
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_mypage.*


class MypageActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        auth = Firebase.auth

        FBAuth.auth = FirebaseAuth.getInstance()
        val email = FBAuth.auth.currentUser?.email.toString()
        val uid = userName as TextView
        uid.setText(email)

        val applyIdList = mutableListOf<String>()
        val items = ArrayList<SupportModel>()
        val itemKeyList = ArrayList<String>()

        //lateinit var rvAdapter : ApplyListVAdapter

        // 비밀번호 변경 버튼
        settingBtn1.setOnClickListener {
            val intent = Intent(this, FindPWActivity::class.java)
            startActivity(intent)
        }
        //로그아웃
        settingBtn2.setOnClickListener {
            auth.signOut()

            val intent = Intent(this, IntroActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        // 개발자 소개
        devBtn1.setOnClickListener {
            showDialog()
        }
        // 문의메일
        devBtn2.setOnClickListener {
            showDialog2()
        }

        //apply list
        //getFBApplyData()

        //rvAdapter = ApplyListVAdapter(items, itemKeyList, applyIdList)


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

    private fun showDialog(){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)

        mBuilder.show()
    }
    private fun showDialog2(){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog2, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)

        mBuilder.show()
    }
    //support list object 가져오기
//    private fun getFBSupportData(){
//
//        val postListener = object : ValueEventListener {
//            @SuppressLint("NotifyDataSetChanged")
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//
//                //supportList.clear()
//
//                for (dataModel in dataSnapshot.children) {
//                    val item = dataModel.getValue(SupportModel::class.java)
//
//                    if(applyIdList.contains(dataModel.key.toString())){
//                        items.add(item!!)
//                        itemKeyList.add(dataModel.key.toString())
//                    }
//                }
//                rvAdapter.notifyDataSetChanged()
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Getting Post failed, log a message
//                Log.w("ApplyListtest", "loadPost:onCancelled", databaseError.toException())
//            }
//        }
//        FBRef.supportRef.addValueEventListener(postListener)
//    }
    //apply 데이터 불러오기
//    private fun getFBApplyData(){
//
//        val postListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//
//                for (dataModel in dataSnapshot.children) {
//                    applyIdList.add(dataModel.key.toString())
//                }
//
//                getFBSupportData()
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Getting Post failed, log a message
//                Log.w("applyListtest", "loadPost:onCancelled", databaseError.toException())
//            }
//        }
//        FBRef.supportRef.child(getUid()).addValueEventListener(postListener)
//    }
}