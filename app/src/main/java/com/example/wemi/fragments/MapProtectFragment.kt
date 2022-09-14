package com.example.wemi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.wemi.R
import com.example.wemi.databinding.FragmentMapProtectBinding

class MapProtectFragment : Fragment() {

    private lateinit var binding : FragmentMapProtectBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map_all, container, false)

        binding.mapAllTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_mapCureFragment_to_mapAllFragment)
        }
        binding.hospitalTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_mapCureFragment_to_mapHospitalFragment)
        }
        binding.protectTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_mapCureFragment_to_mapProtectFragment)
        }
        return binding.root
    }
}