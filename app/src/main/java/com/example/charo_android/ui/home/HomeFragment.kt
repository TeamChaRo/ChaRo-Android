package com.example.charo_android.ui.home

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.charo_android.data.*
import com.example.charo_android.databinding.FragmentHomeBinding
//import com.example.charo_android.replaceFragment
import com.example.charo_android.ui.home.more.MoreViewFragment


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var homeViewPagerAdapter =  HomeViewPagerAdapter()
    private var homeTodayDriveAdapter = HomeTodayDriveAdapter()
    private var homeThemeAdapter = HomeThemeAdapter()
    private var homeHotDriveAdapter = HomeHotDriveAdapter()
    private var homeNightDriveAdapter = HomeNIghtDriveAdapter()
    private var homeLocationDriveAdapter = HomeLocationDriveAdapter()
    val context = activity as? AppCompatActivity

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
        initHomeTheme()
        initHomeHotDrive()
        initHomeNightDrive()
        initHomeLocationDrive()
     //   replaceMoreViewFragment()



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

    private fun initHomeTheme(){
        binding.recyclerviewHomeTheme.adapter = homeThemeAdapter
        homeThemeAdapter.themeData.addAll(LocalHomeThemeDataSource().fetchData())
        homeThemeAdapter.notifyDataSetChanged()
    }

    private fun initHomeHotDrive(){
        binding.recyclerviewHomeHotDrive.adapter = homeHotDriveAdapter
        homeHotDriveAdapter.hotData.addAll(LocalHomeHotDriveDataSource().fetchData().subList(0,4))
        homeHotDriveAdapter.notifyDataSetChanged()
    }

    private fun initHomeNightDrive(){
        binding.recyclerviewHomeNightDrive.adapter = homeNightDriveAdapter
        homeNightDriveAdapter.nightData.addAll(LocalHomeNightDriveDataSource().fetchData().subList(0,4))
        homeNightDriveAdapter.notifyDataSetChanged()
    }

    private fun initHomeLocationDrive(){
        binding.recyclerviewHomeLocationDrive.adapter = homeLocationDriveAdapter
        homeLocationDriveAdapter.locationData.addAll(LocalHomeLocationDataSource().fetchData().subList(0,4))
        homeLocationDriveAdapter.notifyDataSetChanged()
    }


//    private fun replaceMoreViewFragment(){
//        binding.textHomeHotDrivePlus.setOnClickListener {
//            context?.replaceFragment(MoreViewFragment())
//            Log.d("name", "name")
//        }
//
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}