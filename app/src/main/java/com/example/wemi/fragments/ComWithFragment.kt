package com.example.wemi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.wemi.R
import com.example.wemi.community.CommunityModel
import com.example.wemi.community.CommunityWithAdapter
import com.example.wemi.databinding.FragmentComWithBinding
import kotlinx.android.synthetic.main.fragment_com_all.*


class ComWithFragment : Fragment() {
    private lateinit var binding: FragmentComWithBinding

    private val items = mutableListOf<CommunityModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_com_with, container, false)

        binding.comAllTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_comWithFragment_to_comAllFragment)
        }
        binding.questionTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_comWithFragment_to_comQuestionFragment)
        }
        binding.shareTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_comWithFragment_to_comShareFragment)
        }

        items.add(CommunityModel("카테고리", "닉네임", "시간","제목","내용","댓글"))

        val adapter = CommunityWithAdapter(items)
        communityListView.adapter = adapter

        communityListView.setOnItemClickListener { parent : AdapterView<*>, view : View, position : Int, id : Long ->
            // 커뮤 안으로 들어가기
        }

        return binding.root
    }
}