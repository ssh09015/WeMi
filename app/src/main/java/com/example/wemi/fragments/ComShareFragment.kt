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
import com.example.wemi.community.CommunityShareAdapter
import com.example.wemi.databinding.FragmentComShareBinding
import kotlinx.android.synthetic.main.fragment_com_all.*

class ComShareFragment : Fragment() {
    private lateinit var binding : FragmentComShareBinding

    private val items = mutableListOf<CommunityModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_com_share, container, false)

        binding.comAllTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_comShareFragment_to_comAllFragment)
        }
        binding.questionTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_comShareFragment_to_comQuestionFragment)
        }

        binding.withTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_comShareFragment_to_comWithFragment)
        }

        items.add(CommunityModel("카테고리", "닉네임", "제목","내용"))

        val adapter = CommunityShareAdapter(items)
        communityListView.adapter = adapter

        communityListView.setOnItemClickListener { parent : AdapterView<*>, view : View, position : Int, id : Long ->
            // 커뮤 안으로 들어가기
        }

        return binding.root
    }
}