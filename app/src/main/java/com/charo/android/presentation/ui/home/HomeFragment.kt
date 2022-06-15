package com.charo.android.presentation.ui.home

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.viewpager2.widget.ViewPager2
import com.charo.android.R
import com.charo.android.data.datasource.local.home.LocalHomeThemeDataSourceImpl
import com.charo.android.data.model.request.home.RequestHomeLikeData
import com.charo.android.databinding.FragmentHomeBinding
import com.charo.android.domain.model.home.BannerLocal
import com.charo.android.hidden.Hidden
import com.charo.android.presentation.base.BaseFragment
import com.charo.android.presentation.ui.alarm.AlarmActivity
import com.charo.android.presentation.ui.home.adapter.*
import com.charo.android.presentation.ui.home.viewmodel.HomeViewModel
import com.charo.android.presentation.ui.main.SharedViewModel
import com.charo.android.presentation.ui.more.MoreViewFragment
import com.charo.android.presentation.ui.search.SearchActivity
import com.charo.android.presentation.util.LocationUtil
import com.charo.android.presentation.util.LoginUtil
import com.charo.android.presentation.util.SharedInformation
import com.charo.android.presentation.util.ThemeUtil
import kotlinx.coroutines.flow.callbackFlow
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val sharedViewModel: SharedViewModel by sharedViewModel()
    private val homeViewModel: HomeViewModel by viewModel()
    private var theme = ThemeUtil()
    private var location = LocationUtil()
    private lateinit var homeViewPagerAdapter: HomeViewPagerAdapter
    private lateinit var homeViewPagerLocalAdapter: HomeViewPagerLocalAdapter
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
        val nickName: String = Hidden.nickName

        initDefaultView()

        goSearchView(nickName)
        goAlarm()
        initToolBar()
        replaceMoreViewFragment()

        initBannerLocal()
//        initBanner()
        carAnimation()

        initTrendDrive()
        initLocalDrive()
        initTodayCharoDrive()
        initCustomThemeDrive()
        initHomeTitle()
        initThemeDrive()

        initEmptyView()
    }

    private fun initToolBar() {
        val toolbar = binding.toolbarMain
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }

    //차 애니메이션
    private fun carAnimation(){
        binding.vpMain.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                val displayMetrics = DisplayMetrics()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    requireContext().display!!.getRealMetrics(displayMetrics)
                } else {
                    requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
                    displayMetrics.widthPixels
                }

                val width = displayMetrics.widthPixels

                val xValues: Float = (width * (position.toFloat()/4))
                val anim : ObjectAnimator = ObjectAnimator.ofFloat(view, "translationX", xValues)

                anim.target = binding.ivHomeCharoCar
                anim.duration = 700
                anim.start()
            }
        })
    }

    //배너 디지인
    private fun initBannerLocal() {
        val bannerLocal : List<BannerLocal>
        val userEmail = SharedInformation.getEmail(requireActivity())
        homeViewModel.getBanner(userEmail)
        homeViewPagerLocalAdapter = HomeViewPagerLocalAdapter()
        binding.vpMain.adapter = homeViewPagerLocalAdapter

        val Banner1 : BannerLocal = BannerLocal(R.drawable.banner_img_one,"강릉 해변 \n드라이브 코스와 맛집","강릉 8년차가 소개해주는",0,28f,false)
        val Banner2 : BannerLocal = BannerLocal(R.drawable.banner_img_two,"봄의 선선한 바람 \n플레이리스트","",R.drawable.spring_playlist,28f,false)
        val Banner3 : BannerLocal = BannerLocal(R.drawable.banner_img_three,"자동차 극장\n드라이브 코스","",R.drawable.drive_in_theater,28f,false)
        val Banner4 : BannerLocal = BannerLocal(R.drawable.banner_img_four,"차에서의 \n오늘이 최고가 될 수 있게\n당신의 드라이브 메이트","",0,22f,true)
        bannerLocal = listOf(Banner1,Banner2,Banner3,Banner4)

        homeViewPagerLocalAdapter.setHomeBanner(bannerLocal)
    }

    //초기 뷰
    private fun initDefaultView(){
        binding.clHomeTodayDrive.visibility = View.GONE
        binding.clHomeHotDrive.visibility = View.GONE
        binding.clHomeNightDrive.visibility = View.GONE
        binding.clHomeLocationDrive.visibility = View.GONE
        binding.clEmptyList.visibility = View.VISIBLE
    }

    //요즘 뜨는 드라이브
    private fun initTrendDrive() {
        val userEmail = SharedInformation.getEmail(requireActivity())
        Timber.d("home 이메일 $userEmail")
        homeViewModel.getTrendDrive(userEmail)
        homeHotDriveAdapter = HomeTrendDriveAdapter(userEmail, links)
        binding.recyclerviewHomeHotDrive.adapter = homeHotDriveAdapter
        homeViewModel.trendDrive.observe(viewLifecycleOwner) {
            if(it == null || it.isEmpty()){
                binding.clHomeHotDrive.visibility = View.GONE
            } else {
                binding.clHomeHotDrive.visibility = View.VISIBLE
                homeHotDriveAdapter.setHomeTrendDrive(it)
            }
        }
    }

    private fun initLocalDrive() {
        val userEmail = SharedInformation.getEmail(requireActivity())
        homeViewModel.getLocalDrive(userEmail)
        homeLocalDriveAdapter = HomeLocalDriveAdapter(userEmail, links)
        binding.recyclerviewHomeLocationDrive.adapter = homeLocalDriveAdapter
        homeViewModel.localDrive.observe(viewLifecycleOwner) {
            if(it == null || it.isEmpty()){
                binding.clHomeLocationDrive.visibility = View.GONE
            } else {
                binding.clHomeLocationDrive.visibility = View.VISIBLE
                homeLocalDriveAdapter.setLocalDrive(it)
            }
        }
    }

    //오늘 드라이브
    private fun initTodayCharoDrive() {
        val userEmail = SharedInformation.getEmail(requireActivity())
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerviewHomeTodayDrive)
        homeViewModel.getTodayCharoDrive(userEmail)
        homeTodayDriveAdapter = HomeTodayDriveAdapter(userEmail, links)
        binding.recyclerviewHomeTodayDrive.adapter = homeTodayDriveAdapter
        homeViewModel.todayCharoDrive.observe(viewLifecycleOwner) {
            if(it == null || it.isEmpty()){
                binding.clHomeTodayDrive.visibility = View.GONE
            } else {
                binding.clHomeTodayDrive.visibility = View.VISIBLE
                homeTodayDriveAdapter.setTodayDrive(it)
            }
        }
    }

    private fun initCustomThemeDrive() {
        val userEmail = SharedInformation.getEmail(requireActivity())
        homeViewModel.getCustomTheme(userEmail)
        homeCustomThemeAdapter = HomeCustomThemeAdapter(userEmail, links)
        binding.recyclerviewHomeNightDrive.adapter = homeCustomThemeAdapter
        homeViewModel.customThemeDrive.observe(viewLifecycleOwner) {
            if(it == null || it.isEmpty()){
                binding.clHomeNightDrive.visibility = View.GONE
            } else {
                binding.clHomeNightDrive.visibility = View.VISIBLE
                homeCustomThemeAdapter.setCustomThemeDrive(it)
            }
        }
    }

    private fun initEmptyView(){
        if(homeViewModel.trendDrive.value?.isEmpty() == true
            && homeViewModel.customThemeDrive.value?.isEmpty() == true
            && homeViewModel.localDrive.value?.isEmpty() == true) {
            binding.clEmptyList.visibility = View.VISIBLE
        } else {
            binding.clEmptyList.visibility = View.GONE
        }
    }

    //테마 뷰 이동
    private fun initThemeDrive() {
        val themeData = LocalHomeThemeDataSourceImpl().fetchData()
        homeThemeAdapter = HomeThemeAdapter()
        binding.recyclerviewHomeTheme.adapter = homeThemeAdapter
        homeThemeAdapter.themeData.addAll(themeData)
    }

    private fun initHomeTitle() {
        val userEmail = SharedInformation.getEmail(requireActivity())
        sharedViewModel.getHomeTitle(userEmail)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.sharedViewModel = sharedViewModel
    }

    //검색 뷰 이동
    private fun goSearchView(nickName: String) {
        binding.imgMainSearch.setOnClickListener {
            val userEmail = SharedInformation.getEmail(requireActivity())
            if(userEmail=="@"){
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
    //알림 뷰 이동
    private fun goAlarm() {
        val userEmail = SharedInformation.getEmail(requireActivity())
        binding.imgMainAlarm.setOnClickListener {
            if (userEmail == null || userEmail == "@") {
                LoginUtil.loginPrompt(requireActivity())

            } else {
                val intent = Intent(activity, AlarmActivity::class.java)
                startActivity(intent)
            }
        }
    }
    //moreView 이동
    private fun replaceMoreViewFragment() {
        binding.textHomeHotDrivePlus.setOnClickListener {
            sharedViewModel.num.value = 0
            goMoreView()
        }
        binding.textHomeNightDrivePlus.setOnClickListener {
            sharedViewModel.num.value = 3
            goMoreView()
        }
        binding.textHomeLocationDrivePlus.setOnClickListener {
            sharedViewModel.num.value = 2
            goMoreView()
        }
    }

    //moreView 이동
    private fun goMoreView(){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.apply {
            replace(R.id.nav_host_fragment_activity_main, MoreViewFragment())
            addToBackStack(null)
            commit()
        }

    }

    //좋아요 POST 보내기
    inner class DataToHomeLike() {
        fun getPostId(postId: Int) {
            val userEmail = SharedInformation.getEmail(requireActivity())
            homeViewModel.postLike(RequestHomeLikeData(userEmail, postId))
        }

    }
}