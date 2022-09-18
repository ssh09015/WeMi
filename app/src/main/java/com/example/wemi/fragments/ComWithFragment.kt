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
import com.example.wemi.databinding.FragmentComWithBinding


class ComWithFragment : Fragment() {
    private lateinit var binding: FragmentComWithBinding

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

        return binding.root
    }
}