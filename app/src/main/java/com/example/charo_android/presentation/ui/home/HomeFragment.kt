package com.example.charo_android.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.charo_android.R
import com.example.charo_android.data.repository.local.home.LocalHomeThemeDataSourceImpl
import com.example.charo_android.databinding.FragmentHomeBinding
import com.example.charo_android.hidden.Hidden
import com.example.charo_android.presentation.base.BaseFragment
import com.example.charo_android.presentation.ui.alarm.AlarmActivity
import com.example.charo_android.presentation.ui.home.adapter.*
import com.example.charo_android.presentation.ui.home.viewmodel.HomeViewModel
import com.example.charo_android.presentation.ui.main.SharedViewModel
import com.example.charo_android.presentation.ui.more.MoreThemeViewFragment
import com.example.charo_android.presentation.ui.more.MoreViewFragment
import com.example.charo_android.presentation.ui.search.SearchActivity
import com.example.charo_android.presentation.util.LocationUtil
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

    val context = activity as? AppCompatActivity
    var bundle = Bundle()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId: String = Hidden.userId
        val nickName: String = Hidden.nickName
        goSearchView(userId,nickName)
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
   /*
    private fun deleteKakao(){
        binding.btnSocialKakaoLogout.setOnClickListener {
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Toast.makeText(requireActivity(), "로그아웃 실패 $error", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(requireActivity(), "로그아웃 성공", Toast.LENGTH_SHORT).show()
                }
                val intent = Intent(requireActivity(), SocialSignInActivity::class.java)
                startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP))
                requireActivity().finish()
            }
        }

    } */
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

    private fun initThemeDrive(){
        val themeData = LocalHomeThemeDataSourceImpl().fetchData()
        homeThemeAdapter = HomeThemeAdapter()
        binding.recyclerviewHomeTheme.adapter = homeThemeAdapter
        homeThemeAdapter.themeData.addAll(themeData)
    }

    private fun initHomeTitle(){
        sharedViewModel.getHomeTitle("and@naver.com")
       binding.lifecycleOwner = viewLifecycleOwner
        binding.sharedViewModel = sharedViewModel
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
                replace(R.id.nav_host_fragment_activity_main, MoreThemeViewFragment())
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


}