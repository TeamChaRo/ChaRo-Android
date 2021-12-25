package com.example.charo_android.presentation.ui.charo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentCharoListFollowerBinding

class CharoListFollowerFragment : Fragment() {
    private var _binding: FragmentCharoListFollowerBinding? = null
    private val binding get() = _binding!!
    private val charoViewModel: CharoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharoListFollowerBinding.inflate(layoutInflater, container, false)
        initRecyclerView()
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initRecyclerView() {
        with(binding) {
            val followAdapter = CharoListFollowAdapter()
            recyclerViewCharoListFollower.adapter = followAdapter
            followAdapter.notifyDataSetChanged()
        }
    }
}