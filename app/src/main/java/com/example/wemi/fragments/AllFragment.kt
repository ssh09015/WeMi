package com.example.wemi.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.wemi.R
import com.example.wemi.databinding.FragmentAllBinding
import com.example.wemi.support.SupportInsideActivity
import com.example.wemi.support.SupportListVAdapter
import com.example.wemi.support.SupportModel
import com.example.wemi.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class AllFragment : Fragment() {

    private lateinit var binding: FragmentAllBinding

    private val supportList = mutableListOf<SupportModel>()
    private val supportKeyList = mutableListOf<String>()

    private lateinit var supportRVAdapter: SupportListVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all, container, false)

        // 상단 메뉴
        binding.publicTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_allFragment_to_publicFragment)
        }
        binding.privateTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_allFragment_to_privateFragment)
        }

        supportRVAdapter = SupportListVAdapter(supportList)
        binding.supportListView.adapter = supportRVAdapter

//        //데이터 삽입
//        FBRef.supportRef
//            .push()
//            .setValue(SupportModel
//                ("정부지원",
//                "서울형 긴급복지",
//                "서울시",
//                " ~ ",
//                "* 서울 거주 저소득 위기가구",
//                "갑작스러운 위기 사유의 발생으로 생계유지가 곤란한 저소득 위기가구에 대해 생계비 등을 신속하게 제공하여 안정적 생활을 유지하도록 지원",
//                "uid",
//                "https://news.seoul.go.kr/welfare/archives/48196")
//            )
//        //데이터 삽입
//        FBRef.supportRef
//            .push()
//            .setValue(SupportModel
//                ("민간지원",
//                "청소년 미혼모 경제적 자립 지원",
//                "여성인권 동감",
//                " ~ ",
//                "* 출산예정 또는 48계월이하 자녀를 둔 청소년 미혼모 여성",
//                "재가 청소년(만 24세 이하) 미혼모 가정의 건강한 경제적 독립을 위한 지원사업",
//                "uid",
//                "http://humanagree.com/activityboard1/183361")
//            )

        // 내부 페이지로 이동
        binding.supportListView.setOnItemClickListener{ parent, view, position, id ->

            val intent = Intent(context, SupportInsideActivity::class.java)
            intent.putExtra("key", supportKeyList[position])
            intent.putExtra("url", supportList[position].webUrl)
            startActivity(intent)

        }

        getFBSupportData()

        return binding.root
    }

    //파이어베이스 데이터 불러오기
    private fun getFBSupportData(){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                supportList.clear()

                for (dataModel in dataSnapshot.children) {

                    Log.d("supportListtest", dataModel.toString())

                    val item = dataModel.getValue(SupportModel::class.java)
                    supportList.add(item!!)
                    supportKeyList.add(dataModel.key.toString())

                }
                supportKeyList.reverse()
                supportList.reverse()
                supportRVAdapter.notifyDataSetChanged()

                Log.d("supportListtest", supportList.toString())


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("supportListtest", "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.supportRef.addValueEventListener(postListener)
    }

}