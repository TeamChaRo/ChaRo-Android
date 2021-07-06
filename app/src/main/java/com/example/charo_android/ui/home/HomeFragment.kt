package com.example.charo_android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.charo_android.data.LocalHomeTodayDriveDataSource
import com.example.charo_android.data.LocalHomeViewPagerDataSource
import com.example.charo_android.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var homeViewPagerAdapter = HomeViewPagerAdapter()
    private var homeTodayDriveAdapter = HomeTodayDriveAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initToolBar()
        initHomeViewPager()
        initHomeTodayDrive()
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

    private fun initHomeTodayDrive(){
        binding.recyclerviewHomeTodayDrive.adapter = homeTodayDriveAdapter
        homeTodayDriveAdapter.driveData.addAll(LocalHomeTodayDriveDataSource().fetchData())
        homeTodayDriveAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}