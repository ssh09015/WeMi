package com.example.wemi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.wemi.R
import com.example.wemi.databinding.FragmentComAllBinding

class ComAllFragment : Fragment() {
    private lateinit var binding : FragmentComAllBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_com_all, container, false)

        binding.questionTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_comAllFragment_to_comQuestionFragment)
        }
        binding.shareTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_comAllFragment_to_comShareFragment)
        }

        binding.withTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_comAllFragment_to_comWithFragment)
        }

        binding.content.setOnClickListener {
            // 여기에 넣어줘
        }

        return binding.root
    }
}