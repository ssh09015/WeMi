package com.example.wemi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.wemi.R
import com.example.wemi.databinding.FragmentComShareBinding

class ComShareFragment : Fragment() {
    private lateinit var binding : FragmentComShareBinding

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

        return binding.root
    }
}