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
import com.example.wemi.databinding.FragmentPublicBinding
import com.example.wemi.support.SupportInsideActivity
import com.example.wemi.support.SupportModel
import com.example.wemi.support.SupportPublicListVAdapter
import com.example.wemi.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_join.*

class PublicFragment : Fragment() {

    private lateinit var binding: FragmentPublicBinding

    private val supportPublicList = mutableListOf<SupportModel>()
    private val supportPublicKeyList = mutableListOf<String>()

    private lateinit var supportPublicRVAdapter: SupportPublicListVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_public, container, false)


        // 상단 메뉴
        binding.allTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_publicFragment_to_allFragment)
        }
        binding.privateTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_publicFragment_to_privateFragment)
        }

        supportPublicRVAdapter = SupportPublicListVAdapter(supportPublicList)
        binding.supportPublicListView.adapter = supportPublicRVAdapter

        // 내부 페이지로 이동
        binding.supportPublicListView.setOnItemClickListener{ parent, view, position, id ->

            val intent = Intent(context, SupportInsideActivity::class.java)
            intent.putExtra("key", supportPublicKeyList[position])
            intent.putExtra("url", supportPublicList[position].webUrl)
            startActivity(intent)

        }

        getFBSupportPublicData()

        return binding.root
    }

    //파이어베이스 데이터 불러오기
    private fun getFBSupportPublicData(){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                supportPublicList.clear()

                for (dataModel in dataSnapshot.children) {

                        val item = dataModel.getValue(SupportModel::class.java)

                        if(item!!.sort == "정부지원"){
                            supportPublicList.add(item!!)
                            supportPublicKeyList.add(dataModel.key.toString())
                        }

                }
                supportPublicKeyList.reverse()
                supportPublicList.reverse()
                supportPublicRVAdapter.notifyDataSetChanged()

                Log.d("supportListtest", supportPublicList.toString())


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("supportListtest", "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.supportRef.addValueEventListener(postListener)
    }

}