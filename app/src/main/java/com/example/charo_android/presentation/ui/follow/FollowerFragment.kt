package com.example.charo_android.presentation.ui.follow

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentFollowerBinding
import com.example.charo_android.presentation.ui.follow.adapter.FollowAdapter
import com.example.charo_android.presentation.ui.follow.viewmodel.FollowViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FollowerFragment : Fragment() {
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding ?: error("binding not initialized")
    private lateinit var adapter: FollowAdapter
    private val viewModel by sharedViewModel<FollowViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_follower, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecyclerView() {
        adapter = FollowAdapter()
        viewModel.follower.observe(viewLifecycleOwner) {
            adapter.itemList.addAll(it)
            adapter.notifyDataSetChanged()
        }
        binding.rv.adapter = adapter
    }
}