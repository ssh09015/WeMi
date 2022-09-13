package com.example.wemi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.wemi.R
import com.example.wemi.databinding.FragmentComQuestionBinding

class ComQuestionFragment : Fragment() {
    private lateinit var binding: FragmentComQuestionBinding

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