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


class PrivateFragment : Fragment() {
    private lateinit var binding: FragmentPrivateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_private, container, false)

        binding.allTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_privateFragment_to_allFragment)
        }
        binding.privateTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_privateFragment_to_publicFragment)
        }
        return binding.root
    }


}