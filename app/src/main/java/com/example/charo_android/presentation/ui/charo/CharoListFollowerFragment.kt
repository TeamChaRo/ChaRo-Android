package com.example.charo_android.presentation.ui.charo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.charo_android.databinding.FragmentCharoListFollowerBinding

class CharoListFollowerFragment : Fragment() {
    private var _binding: FragmentCharoListFollowerBinding? = null
    private val binding get() = _binding!!
    private val charoViewModel: CharoViewModel by activityViewModels()
    private val followAdapter = CharoListFollowAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharoListFollowerBinding.inflate(layoutInflater, container, false)
        initRecyclerView()
        charoViewModel.followData.observe(viewLifecycleOwner, {
            followAdapter.itemList.addAll(it.follower)
            Log.d("itemList.size", followAdapter.itemList.size.toString())
            followAdapter.notifyDataSetChanged()
        })
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initRecyclerView() {
        with(binding) {
            recyclerViewCharoListFollower.adapter = followAdapter
            followAdapter.notifyDataSetChanged()
        }
    }
}