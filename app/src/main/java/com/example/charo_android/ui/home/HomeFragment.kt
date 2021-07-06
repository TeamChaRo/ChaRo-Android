package com.example.charo_android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.charo_android.data.LocalHomeViewPagerDataSource
import com.example.charo_android.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var homeViewPagerAdapter = HomeViewPagerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initToolBar()
        initHomeViewPager()
        return root
    }

    private fun initToolBar(){
        val toolbar = binding.toolbarMain
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }

    private fun initHomeViewPager(){
        binding.vpMain.adapter = homeViewPagerAdapter
        homeViewPagerAdapter.item.addAll(LocalHomeViewPagerDataSource().fetchData())
        homeViewPagerAdapter.notifyDataSetChanged()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}