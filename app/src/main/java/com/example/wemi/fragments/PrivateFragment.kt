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
import com.example.wemi.databinding.FragmentPrivateBinding
import com.example.wemi.support.SupportInsideActivity
import com.example.wemi.support.SupportModel
import com.example.wemi.support.SupportPrivateListVAdapter
import com.example.wemi.support.SupportPublicListVAdapter
import com.example.wemi.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.NonDisposableHandle.parent


class PrivateFragment : Fragment() {

    private lateinit var binding: FragmentPrivateBinding

    private val supportPrivateList = mutableListOf<SupportModel>()
    private val supportPrivateKeyList = mutableListOf<String>()

    private lateinit var supportPrivateRVAdapter: SupportPrivateListVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_private, container, false)

        //상단메뉴
        binding.allTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_privateFragment_to_allFragment)
        }
        binding.publicTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_privateFragment_to_publicFragment)
        }

        supportPrivateRVAdapter = SupportPrivateListVAdapter(supportPrivateList)
        binding.supportPrivateListView.adapter = supportPrivateRVAdapter

        // 내부 페이지로 이동
        binding.supportPrivateListView.setOnItemClickListener{ parent, view, position, id ->

            val intent = Intent(context, SupportInsideActivity::class.java)
            intent.putExtra("key", supportPrivateKeyList[position])
            intent.putExtra("url", supportPrivateList[position].webUrl)
            startActivity(intent)

        }

        getFBSupportPrivateData()

        return binding.root
    }

    //파이어베이스 데이터 불러오기
    private fun getFBSupportPrivateData(){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                supportPrivateList.clear()

                for (dataModel in dataSnapshot.children) {

                    val item = dataModel.getValue(SupportModel::class.java)

                    if(item!!.sort == "민간지원"){
                        supportPrivateList.add(item!!)
                        supportPrivateKeyList.add(dataModel.key.toString())
                    }

                }
                supportPrivateKeyList.reverse()
                supportPrivateList.reverse()
                supportPrivateRVAdapter.notifyDataSetChanged()

                Log.d("supportListtest", supportPrivateList.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("supportListtest", "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.supportRef.addValueEventListener(postListener)
    }
}

