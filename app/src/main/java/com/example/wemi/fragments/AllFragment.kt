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

        // 데이터 삽입
        FBRef.supportRef
            .push()
            .setValue(SupportModel("민간지원","에너지 바우처 난방비 지원(국민행복카드)", "한국에너지공단", "22.07.01 ~ 22.12.30", "* 생계급여/의료급여/주거급여 수급자", "한국 에너지 공단 지원의 내용입니다.", "uid"))


        supportRVAdapter = SupportListVAdapter(supportList)
        binding.supportListView.adapter = supportRVAdapter

        binding.supportListView.setOnItemClickListener{ parent, view, position, id ->

            val intent = Intent(context, SupportInsideActivity::class.java)
            intent.putExtra("key", supportKeyList[position])
            startActivity(intent)

        }

        // 상단 메뉴
        binding.publicTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_allFragment_to_publicFragment)
        }
        binding.privateTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_allFragment_to_privateFragment)
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
//                    dataModel.key

                    val item = dataModel.getValue(SupportModel::class.java)
                    supportList.add(item!!)

                }
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