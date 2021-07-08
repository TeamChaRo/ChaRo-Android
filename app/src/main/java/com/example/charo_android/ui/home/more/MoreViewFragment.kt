package com.example.charo_android.ui.home.more

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.charo_android.data.LocalHomeLocationDataSource
import com.example.charo_android.data.LocalMoreViewDataSource
import com.example.charo_android.databinding.FragmentMoreViewBinding


class MoreViewFragment : Fragment() {
    private var _binding: FragmentMoreViewBinding? = null
    private val binding get() = _binding!!
    private var moreViewAdapter = MoreViewAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoreViewBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initMoreView()
        return root
    }

    private fun initMoreView(){
        binding.recyclerviewMoreView.adapter = moreViewAdapter
        moreViewAdapter.moreData.addAll(LocalMoreViewDataSource().fetchData())
        moreViewAdapter.notifyDataSetChanged()

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}