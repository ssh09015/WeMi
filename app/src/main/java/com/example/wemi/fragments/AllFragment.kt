package com.example.wemi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.wemi.R
import com.example.wemi.databinding.FragmentAllBinding
import com.example.wemi.support.SupportListVAdapter
import com.example.wemi.support.SupportModel

class AllFragment : Fragment() {
    private lateinit var binding: FragmentAllBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all, container, false)

        val supportList = mutableListOf<SupportModel>()
        supportList.add(SupportModel("a", "b", "c", "d"))

        val supportRVAadapter = SupportListVAdapter(supportList)
        binding.supportListArea.adapter = supportRVAadapter

        binding.publicTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_allFragment_to_publicFragment)
        }
        binding.privateTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_allFragment_to_privateFragment)
        }
        return binding.root
    }
}