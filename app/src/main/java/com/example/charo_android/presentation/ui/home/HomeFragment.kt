package com.example.charo_android.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.charo_android.R
import com.example.charo_android.data.model.request.home.RequestHomeLikeData
import com.example.charo_android.data.datasource.local.home.LocalHomeThemeDataSourceImpl
import com.example.charo_android.databinding.FragmentHomeBinding
import com.example.charo_android.hidden.Hidden
import com.example.charo_android.presentation.base.BaseFragment
import com.example.charo_android.presentation.ui.alarm.AlarmActivity
import com.example.charo_android.presentation.ui.home.adapter.*
import com.example.charo_android.presentation.ui.home.viewmodel.HomeViewModel
import com.example.charo_android.presentation.ui.main.SharedViewModel
import com.example.charo_android.presentation.ui.more.MoreViewFragment
import com.example.charo_android.presentation.ui.search.SearchActivity
import com.example.charo_android.presentation.util.LocationUtil
import com.example.charo_android.presentation.util.LoginUtil
import com.example.charo_android.presentation.util.SharedInformation
import com.example.charo_android.presentation.util.ThemeUtil
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val sharedViewModel : SharedViewModel by sharedViewModel()
    private val homeViewModel: HomeViewModel by viewModel()
    private var theme = ThemeUtil()
    private var location = LocationUtil()
    private lateinit var homeViewPagerAdapter: HomeViewPagerAdapter
    private lateinit var homeTodayDriveAdapter: HomeTodayDriveAdapter
    private lateinit var homeThemeAdapter: HomeThemeAdapter
    private lateinit var homeHotDriveAdapter: HomeTrendDriveAdapter
    private lateinit var homeCustomThemeAdapter: HomeCustomThemeAdapter
    private lateinit var homeLocalDriveAdapter: HomeLocalDriveAdapter

    var links = DataToHomeLike()



    val context = activity as? AppCompatActivity
    var bundle = Bundle()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userEmail = SharedInformation.getEmail(requireActivity())
        val nickName: String = Hidden.nickName
        goSearchView(nickName)
        goAlarm()
        initToolBar()
        replaceMoreViewFragment(Hidden.userId)
        initBanner()
        initTrendDrive()
        initLocalDrive()
        initTodayCharoDrive()
        initCustomThemeDrive()
        initHomeTitle()
        initThemeDrive()
    }

    private fun initToolBar() {
        val toolbar = binding.toolbarMain
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }


    //배너 설정
    private fun initBanner() {
        val userEmail = SharedInformation.getEmail(requireActivity())
        homeViewModel.getBanner(userEmail)
        homeViewPagerAdapter = HomeViewPagerAdapter()
        binding.vpMain.adapter = homeViewPagerAdapter
        homeViewModel.banner.observe(viewLifecycleOwner) {
            homeViewPagerAdapter.setHomeBanner(it, homeViewModel.getBannerRoad())
        }
    }

    private fun initTrendDrive(){
        val userEmail = SharedInformation.getEmail(requireActivity())
        homeViewModel.getTrendDrive(userEmail)
        homeHotDriveAdapter = HomeTrendDriveAdapter(userEmail, links)
        binding.recyclerviewHomeHotDrive.adapter = homeHotDriveAdapter
        homeViewModel.trendDrive.observe(viewLifecycleOwner){
            homeHotDriveAdapter.setHomeTrendDrive(it)
        }
    }

    private fun initLocalDrive(){
        val userEmail = SharedInformation.getEmail(requireActivity())
        homeViewModel.getLocalDrive(userEmail)
        homeLocalDriveAdapter = HomeLocalDriveAdapter(userEmail, links)
        binding.recyclerviewHomeLocationDrive.adapter = homeLocalDriveAdapter
        homeViewModel.localDrive.observe(viewLifecycleOwner){
            homeLocalDriveAdapter.setLocalDrive(it)
        }
    }

    //오늘 드라이브
    private fun initTodayCharoDrive(){
        val userEmail = SharedInformation.getEmail(requireActivity())
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerviewHomeTodayDrive)
        homeViewModel.getTodayCharoDrive(userEmail)
        homeTodayDriveAdapter = HomeTodayDriveAdapter(userEmail, links)
        binding.recyclerviewHomeTodayDrive.adapter = homeTodayDriveAdapter
        homeViewModel.todayCharoDrive.observe(viewLifecycleOwner){
            homeTodayDriveAdapter.setTodayDrive(it)
        }
    }

    private fun initCustomThemeDrive(){
        val userEmail = SharedInformation.getEmail(requireActivity())
        homeViewModel.getCustomTheme(userEmail)
        homeCustomThemeAdapter = HomeCustomThemeAdapter(userEmail, links)
        binding.recyclerviewHomeNightDrive.adapter = homeCustomThemeAdapter
        homeViewModel.customThemeDrive.observe(viewLifecycleOwner){
            homeCustomThemeAdapter.setCustomThemeDrive(it)
        }
    }

    private fun initThemeDrive(){
        val themeData = LocalHomeThemeDataSourceImpl().fetchData()
        homeThemeAdapter = HomeThemeAdapter()
        binding.recyclerviewHomeTheme.adapter = homeThemeAdapter
        homeThemeAdapter.themeData.addAll(themeData)
    }

    private fun initHomeTitle(){
        val userEmail = SharedInformation.getEmail(requireActivity())
        sharedViewModel.getHomeTitle(userEmail)
       binding.lifecycleOwner = viewLifecycleOwner
        binding.sharedViewModel = sharedViewModel
    }


    //검색 뷰 이동
    private fun goSearchView(nickName: String) {
        val userEmail = SharedInformation.getEmail(requireActivity())
        binding.imgMainSearch.setOnClickListener {
            if(userEmail == null || userEmail == "@"){
                //로그인 유도 필요한 곳에 작성
                LoginUtil.loginPrompt(requireActivity())
            }else{
                val intent = Intent(activity, SearchActivity::class.java)
                intent.apply {
                    putExtra("nickName", nickName)
                }
                startActivity(intent)
            }

        }
    }

    private fun goAlarm() {
        val userEmail = SharedInformation.getEmail(requireActivity())
        binding.imgMainAlarm.setOnClickListener {
            if(userEmail == null || userEmail == "@"){
                //로그인 유도 필요한 곳에 작성
                LoginUtil.loginPrompt(requireActivity())
            }else{
                val intent = Intent(activity, AlarmActivity::class.java)
                startActivity(intent)
            }

        }
    }


    private fun replaceMoreViewFragment(userId: String) {
        binding.textHomeHotDrivePlus.setOnClickListener {
            sharedViewModel.num.value = 0
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.apply {
                replace(R.id.nav_host_fragment_activity_main, MoreViewFragment())
                addToBackStack(null)
                commit()
            }
        }
        binding.textHomeNightDrivePlus.setOnClickListener {
            sharedViewModel.num.value = 3
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.apply {
                replace(R.id.nav_host_fragment_activity_main, MoreViewFragment())
                addToBackStack(null)
                commit()
            }
        }
        binding.textHomeLocationDrivePlus.setOnClickListener {
            sharedViewModel.num.value = 2

            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.apply {
                replace(R.id.nav_host_fragment_activity_main, MoreViewFragment())
                addToBackStack(null)
                commit()
            }
        }
    }


    //좋아요 POST 보내기
    inner class DataToHomeLike(){
        fun getPostId(postId : Int){
            val userEmail = SharedInformation.getEmail(requireActivity())
            sharedViewModel.postId.value = postId

            sharedViewModel.postId.observe(viewLifecycleOwner){
                homeViewModel.postLike(RequestHomeLikeData(userEmail,it))
            }
        }

    }


}