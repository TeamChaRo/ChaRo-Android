package com.charo.android.presentation.ui.home

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.viewpager2.widget.ViewPager2
import com.charo.android.R
import com.charo.android.data.api.ApiService
import com.charo.android.data.datasource.local.home.LocalHomeThemeDataSourceImpl
import com.charo.android.data.model.request.home.RequestHomeLikeData
import com.charo.android.data.model.response.alarm.ResponseAlarmListData
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
import com.charo.android.presentation.ui.write.WriteShareActivity
import com.charo.android.presentation.util.LoginUtil
import com.charo.android.presentation.util.SharedInformation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val sharedViewModel: SharedViewModel by sharedViewModel()
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var homeViewPagerLocalAdapter: HomeViewPagerLocalAdapter
    private lateinit var homeTodayDriveAdapter: HomeTodayDriveAdapter
    private lateinit var homeThemeAdapter: HomeThemeAdapter
    private lateinit var homeHotDriveAdapter: HomeTrendDriveAdapter
    private lateinit var homeCustomThemeAdapter: HomeCustomThemeAdapter
    private lateinit var homeLocalDriveAdapter: HomeLocalDriveAdapter

    var links = DataToHomeLike()

    val context = activity as? AppCompatActivity
    var bundle = Bundle()

    private val homeResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                result.data?.let {
                    val updateLike = it.getIntExtra("updateLike", -1)
                    val postId = it.getIntExtra("postId", -1)
                    Timber.d("homeLauncher updateLike $updateLike postId $postId")

                    if(updateLike != -1 && postId != -1){
                        when(updateLike){
                            0 -> {
                                homeTodayDriveAdapter.setLike(postId, false)
                                homeHotDriveAdapter.setLike(postId, false)
                                homeCustomThemeAdapter.setLike(postId , false)
                                homeLocalDriveAdapter.setLike(postId, false)
                            }
                            1 -> {
                                homeTodayDriveAdapter.setLike(postId, true)
                                homeHotDriveAdapter.setLike(postId, true)
                                homeCustomThemeAdapter.setLike(postId, true)
                                homeLocalDriveAdapter.setLike(postId, true)
                            }
                        }
                    }
                }
            }
            Timber.d("homeLauncher result $result")
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nickName: String = Hidden.nickName

        initDefaultView()
        goSearchView(nickName)
        goAlarm()
        initToolBar()
        replaceMoreViewFragment()
        initBannerLocal()
        carAnimation()
        initTrendDrive()
        initLocalDrive()
        initTodayCharoDrive()
        initCustomThemeDrive()
        initHomeTitle()
        initThemeDrive()
        initEmptyView()
        getInitAlarmData()
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

        val Banner1 : BannerLocal = BannerLocal(R.drawable.banner_img_one,getString(R.string.txt_banner_title_gangneung),"강릉 8년차가 소개해주는",0,28f,false)
        val Banner2 : BannerLocal = BannerLocal(R.drawable.banner_img_two,getString(R.string.txt_banner_spring_playlist_2),"",0,28f,false)
        val Banner3 : BannerLocal = BannerLocal(R.drawable.banner_img_three,getString(R.string.txt_banner_title_theater),"",R.drawable.drive_in_theater,28f,false)
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
        homeHotDriveAdapter = HomeTrendDriveAdapter({item ->
            //아이템 클릭
            val intent = Intent(requireContext(), WriteShareActivity::class.java)
            intent.apply {
                putExtra("destination", "detail")
                putExtra("postId", item.homeTrendDrivePostId)
                putExtra("from", "MainActivity")
            }
            homeResultLauncher.launch(intent)

            //좋아요 클릭
        }, { postId, updateLike ->
            homeTodayDriveAdapter.setLike(postId, updateLike)
            homeCustomThemeAdapter.setLike(postId , updateLike)
            homeLocalDriveAdapter.setLike(postId, updateLike)
        }, userEmail, links)
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
        homeLocalDriveAdapter = HomeLocalDriveAdapter({item, userId ->
            //아이템 클릭
            val intent = Intent(requireContext(), WriteShareActivity::class.java)
            intent.apply {
                putExtra("destination", "detail")
                putExtra("postId", item.homeLocationDrivePostId)
                putExtra("userId", userId)
                putExtra("from", "MainActivity")
            }
            homeResultLauncher.launch(intent)

            //좋아요 클릭
        }, { postId, updateLike ->
            homeTodayDriveAdapter.setLike(postId, updateLike)
            homeCustomThemeAdapter.setLike(postId , updateLike)
            homeHotDriveAdapter.setLike(postId, updateLike)
        }, userEmail, links)
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
        homeTodayDriveAdapter = HomeTodayDriveAdapter({item ->
            //아이템 클릭
            val intent = Intent(requireContext(), WriteShareActivity::class.java)
            intent.apply {
                putExtra("destination", "detail")
                putExtra("postId", item.homeTodayDrivePostId)
                putExtra("from", "MainActivity")
            }
            homeResultLauncher.launch(intent)

            //좋아요 클릭
        }, { postId, updateLike ->
            homeHotDriveAdapter.setLike(postId, updateLike)
            homeCustomThemeAdapter.setLike(postId , updateLike)
            homeLocalDriveAdapter.setLike(postId, updateLike)
        },userEmail, links)
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
        homeCustomThemeAdapter = HomeCustomThemeAdapter({item ->
            //아이템 클릭
            val intent = Intent(requireContext(), WriteShareActivity::class.java)
            intent.apply {
                putExtra("destination", "detail")
                putExtra("postId", item.homeNightDrivePostId)
                putExtra("from", "MainActivity")
            }
            homeResultLauncher.launch(intent)

            //좋아요 클릭
        }, { postId, updateLike ->
            homeTodayDriveAdapter.setLike(postId, updateLike)
            homeHotDriveAdapter.setLike(postId , updateLike)
            homeLocalDriveAdapter.setLike(postId, updateLike)
        }, userEmail, links)
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
        homeThemeAdapter.setThemeNum {
            sharedViewModel.themeNum.value = it
        }
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

    //새로운 alarm 여부
    private fun getInitAlarmData(){
        val userEmail = SharedInformation.getEmail(requireActivity())
        Timber.e("home getAlarmList param $userEmail")
        val call: Call<ResponseAlarmListData> = ApiService.alarmViewService.getAlarmList(userEmail)
        call.enqueue(object : Callback<ResponseAlarmListData> {
            override fun onResponse(
                call: Call<ResponseAlarmListData>,
                response: Response<ResponseAlarmListData>
            ) {
                if (response.isSuccessful) {
                    Timber.d("server connect : home Alarm success")
                    Timber.d("server connect : home Alarm ${response.body()}")
                    val pushList = response.body()?.pushList

                    //기본 알림 아이콘 : 비활 상태
                    binding.imgMainAlarm.isActivated = false
                    if(pushList != null){
                        for(item in pushList){
                            //새로운 알림이 하나라도 있을 경우 아이콘 활성화
                            if(item.isRead == 1){
                                binding.imgMainAlarm.isActivated = true
                                break
                            }
                        }
                    }
                } else {
                    binding.imgMainAlarm.isActivated = false
                    Timber.d("server connect : home Alarm error")
                    Timber.d("server connect : home Alarm ${response.errorBody()}")
                    Timber.d("server connect : home Alarm ${response.message()}")
                    Timber.d("server connect : home Alarm ${response.code()}")
                    Timber.d("server connect : home Alarm  ${response.raw().request.url}")
                }
            }

            override fun onFailure(call: Call<ResponseAlarmListData>, t: Throwable) {
                binding.imgMainAlarm.isActivated = false
                Timber.d("server connect : home Alarm error: ${t.message}")
            }
        })
    }
}