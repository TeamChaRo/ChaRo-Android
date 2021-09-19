package com.example.charo_android.presentation.ui.home

import android.content.Intent
import android.os.Bundle

import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf

import androidx.fragment.app.setFragmentResult
import com.example.charo_android.R
import com.example.charo_android.presentation.ui.alarm.AlarmActivity

import com.example.charo_android.databinding.FragmentHomeBinding
import com.example.charo_android.presentation.base.BaseFragment
import com.example.charo_android.presentation.ui.home.adapter.*
import com.example.charo_android.presentation.ui.home.viewmodel.HomeViewModel
import com.example.charo_android.presentation.ui.main.MainActivity
import com.example.charo_android.presentation.ui.more.MoreViewFragment
import com.example.charo_android.presentation.ui.search.SearchActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModel()

    private lateinit var homeViewPagerAdapter: HomeViewPagerAdapter
    private lateinit var homeTodayDriveAdapter: HomeTodayDriveAdapter
    private lateinit var homeThemeAdapter: HomeThemeAdapter
    private lateinit var homeHotDriveAdapter: HomeTrendDriveAdapter
    private lateinit var homeCustomThemeAdapter: HomeCustomThemeAdapter
    private lateinit var homeLocalDriveAdapter: HomeLocalDriveAdapter
    val context = activity as? AppCompatActivity
    var bundle = Bundle()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId: String = (activity as MainActivity).getUserId()
        val nickName: String = (activity as MainActivity).getNickName()
        goSearchView(userId, nickName)
        goAlarm()
        initToolBar()
        replaceMoreViewFragment(userId)
        initBanner()
        initTrendDrive()
        initLocalDrive()
        initTodayCharoDrive()
        initCustomThemeDrive()


    }

    private fun initToolBar() {
        val toolbar = binding.toolbarMain
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }



    private fun initBanner() {
        homeViewModel.getBanner("and@naver.com")
        homeViewPagerAdapter = HomeViewPagerAdapter()
        binding.vpMain.adapter = homeViewPagerAdapter
        homeViewModel.banner.observe(viewLifecycleOwner) {
            homeViewPagerAdapter.setHomeBanner(it)
        }
    }

    private fun initTrendDrive(){
        homeViewModel.getTrendDrive("and@naver.com")
        homeHotDriveAdapter = HomeTrendDriveAdapter("and@naver.com")
        binding.recyclerviewHomeHotDrive.adapter = homeHotDriveAdapter
        homeViewModel.trendDrive.observe(viewLifecycleOwner){
            homeHotDriveAdapter.setHomeTrendDrive(it)
        }
    }

    private fun initLocalDrive(){
        homeViewModel.getLocalDrive("and@naver.com")
        homeLocalDriveAdapter = HomeLocalDriveAdapter("and@naver.com")
        binding.recyclerviewHomeLocationDrive.adapter = homeLocalDriveAdapter
        homeViewModel.localDrive.observe(viewLifecycleOwner){
            homeLocalDriveAdapter.setLocalDrive(it)
        }
    }

    private fun initTodayCharoDrive(){
        homeViewModel.getTodayCharoDrive("and@naver.com")
        homeTodayDriveAdapter = HomeTodayDriveAdapter("and@naver.com")
        binding.recyclerviewHomeTodayDrive.adapter = homeTodayDriveAdapter
        homeViewModel.todayCharoDrive.observe(viewLifecycleOwner){
            homeTodayDriveAdapter.setTodayDrive(it)
        }
    }

    private fun initCustomThemeDrive(){
        homeViewModel.getCustomTheme("and@naver.com")
        homeCustomThemeAdapter = HomeCustomThemeAdapter("and@naver.com")
        binding.recyclerviewHomeNightDrive.adapter = homeCustomThemeAdapter
        homeViewModel.customThemeDrive.observe(viewLifecycleOwner){
            homeCustomThemeAdapter.setCustomThemeDrive(it)
        }
    }



    private fun goSearchView(userId: String, nickName: String) {
        binding.imgMainSearch.setOnClickListener {
            val intent = Intent(activity, SearchActivity::class.java)
            intent.apply {
                putExtra("userId", userId)
                putExtra("nickName", nickName)
            }
            startActivity(intent)
        }
    }

    private fun goAlarm() {
        binding.imgMainAlarm.setOnClickListener {
            val intent = Intent(activity, AlarmActivity::class.java)
            startActivity(intent)
        }
    }


    private fun replaceMoreViewFragment(userId: String) {
        binding.textHomeHotDrivePlus.setOnClickListener {
            val result = binding.textHomeHotDrive.text
            val num = 0
            setFragmentResult(
                "title",
                bundleOf("title" to result, "num" to num, "userId" to userId)
            )
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.apply {
                replace(R.id.nav_host_fragment_activity_main, MoreViewFragment())
                addToBackStack(null)
                commit()
            }
        }
        binding.textHomeNightDrivePlus.setOnClickListener {
            val result = binding.textHomeNightDrive.text
            val num = 1
            setFragmentResult(
                "title",
                bundleOf("title" to result, "num" to num, "userId" to userId)
            )
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.apply {
                replace(R.id.nav_host_fragment_activity_main, MoreViewFragment())
                addToBackStack(null)
                commit()
            }
        }
        binding.textHomeLocationDrivePlus.setOnClickListener {
            val result = binding.textHomeLocationDrive.text
            val num = 2
            setFragmentResult(
                "title",
                bundleOf("title" to result, "num" to num, "userId" to userId)
            )
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.apply {
                replace(R.id.nav_host_fragment_activity_main, MoreViewFragment())
                addToBackStack(null)
                commit()
            }
        }
    }


}