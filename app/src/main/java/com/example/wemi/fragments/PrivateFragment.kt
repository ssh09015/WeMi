package com.example.wemi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.wemi.R
import com.example.wemi.databinding.FragmentPrivateBinding
import com.example.wemi.support.SupportModel
import com.example.wemi.support.SupportPublicListVAdapter
import com.example.wemi.utils.FBRef


class PrivateFragment : Fragment() {

    private lateinit var binding: FragmentPrivateBinding

    private val supportPrivateList = mutableListOf<SupportModel>()
    private val supportPrivateKeyList = mutableListOf<String>()

    private lateinit var supportPrivateRVAdapter: SupportPublicListVAdapter

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
        binding.privateTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_privateFragment_to_publicFragment)
        }

        //데이터 삽입
        FBRef.supportRef
            .push()
            .setValue(SupportModel
                ("민간지원",
                "청소년 미혼모 경제적 자립 지원",
                "여성인권 동감",
                " ~ ",
                "* 출산예정 또는 48계월이하 자녀를 둔 청소년 미혼모 여성",
                "재가 청소년(만 24세 이하) 미혼모 가정의 건강한 경제적 독립을 위한 지원사업",
                "uid",
                "http://humanagree.com/activityboard1/183361")
            )
        return binding.root
    }

}