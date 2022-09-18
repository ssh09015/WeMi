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
import com.example.wemi.community.CommunityQuestionAdapter
import com.example.wemi.databinding.FragmentComQuestionBinding
import kotlinx.android.synthetic.main.fragment_com_all.*

class ComQuestionFragment : Fragment() {
    private lateinit var binding: FragmentComQuestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val items = mutableListOf<CommunityModel>()

        items.add(CommunityModel("카테고리", "닉네임", "시간","제목","내용","댓글"))

        val adapter = CommunityQuestionAdapter(items)
        communityListView.adapter = adapter

        communityListView.setOnItemClickListener { parent : AdapterView<*>, view : View, position : Int, id : Long ->
            // 커뮤 안으로 들어가기
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_com_question, container, false)

        binding.comAllTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_comQuestionFragment_to_comAllFragment)
        }
        binding.shareTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_comQuestionFragment_to_comShareFragment)
        }

        binding.withTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_comQuestionFragment_to_comWithFragment)
        }
        return binding.root
    }
}