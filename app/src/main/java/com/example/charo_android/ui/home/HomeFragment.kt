package com.example.charo_android.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.charo_android.R
import com.example.charo_android.api.ApiService
import com.example.charo_android.api.ResponseHomeViewData
import com.example.charo_android.api.ResponseMoreViewData
import com.example.charo_android.data.*
import com.example.charo_android.databinding.FragmentHomeBinding
import com.example.charo_android.ui.home.more.MoreViewFragment
import com.example.charo_android.ui.search.SearchActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding ?: error("view")
    private lateinit var homeViewPagerAdapter : HomeViewPagerAdapter
    private var homeTodayDriveAdapter = HomeTodayDriveAdapter()
    private var homeThemeAdapter = HomeThemeAdapter()
    private var homeHotDriveAdapter = HomeHotDriveAdapter()
    private var homeNightDriveAdapter = HomeNIghtDriveAdapter()
    private var homeLocationDriveAdapter = HomeLocationDriveAdapter()
    val context = activity as? AppCompatActivity
    var bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewPagerAdapter = HomeViewPagerAdapter()
        binding.vpMain.adapter = homeViewPagerAdapter


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolBar()
        initHomeViewPager()
        initHomeTodayDrive()
        initHomeTheme()
        initHomeHotDrive()
        initHomeNightDrive()
        initHomeLocationDrive()
        goSearchView()
        replaceMoreViewFragment()
    }

    private fun initToolBar() {
        val toolbar = binding.toolbarMain
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }

    private fun initHomeViewPager() {
        val call: Call<ResponseHomeViewData> = ApiService.mainViewService.getMain("111")
        call.enqueue(object : Callback<ResponseHomeViewData> {
            override fun onResponse(
                call: Call<ResponseHomeViewData>,
                response: Response<ResponseHomeViewData>
            ) {
                if (response.isSuccessful) {
                    Log.d("please","제발")
                    val data = response.body()?.data?.banner
                    homeViewPagerAdapter.item.addAll(data!!)
                    homeViewPagerAdapter.localItem.addAll(LocalHomeViewPagerDataSource().fetchData())
                    homeViewPagerAdapter.notifyDataSetChanged()
                    Log.d("server connect", "success")
                } else {
                    Log.d("server connect", "failed")
                    Log.d("server connect", "${response.errorBody()}")
                    Log.d("server connect", "${response.message()}")
                    Log.d("server connect", "${response.code()}")
                    Log.d("server connect", "${response.raw().request.url}")
                }

            }

            override fun onFailure(call: Call<ResponseHomeViewData>, t: Throwable) {
                Log.d("server connect", "failed")
            }
        })

    }

    private fun initHomeTodayDrive() {
        binding.recyclerviewHomeTodayDrive.adapter = homeTodayDriveAdapter
        val call : Call<ResponseHomeViewData> = ApiService.mainViewService.getMain("111")
        call.enqueue(object : Callback<ResponseHomeViewData>{
            override fun onResponse(
                call: Call<ResponseHomeViewData>,
                response: Response<ResponseHomeViewData>
            ) {
                if (response.isSuccessful) {
                    Log.d("please","제발")
                    val data = response.body()?.data?.todayCharoDrive
                    homeTodayDriveAdapter.driveData.addAll(data!!)
                    homeTodayDriveAdapter.notifyDataSetChanged()
                    Log.d("server connect", "success")
                } else {
                    Log.d("server connect", "failed")
                    Log.d("server connect", "${response.errorBody()}")
                    Log.d("server connect", "${response.message()}")
                    Log.d("server connect", "${response.code()}")
                    Log.d("server connect", "${response.raw().request.url}")
                }
            }

            override fun onFailure(call: Call<ResponseHomeViewData>, t: Throwable) {

            }
        })

    }

    private fun initHomeTheme() {
        binding.recyclerviewHomeTheme.adapter = homeThemeAdapter
        homeThemeAdapter.themeData.addAll(LocalHomeThemeDataSource().fetchData())
        homeThemeAdapter.notifyDataSetChanged()
    }

    private fun initHomeHotDrive() {
        binding.recyclerviewHomeHotDrive.adapter = homeHotDriveAdapter
        val call : Call<ResponseHomeViewData> = ApiService.mainViewService.getMain("111")
        call.enqueue(object : Callback<ResponseHomeViewData>{
            override fun onResponse(
                call: Call<ResponseHomeViewData>,
                response: Response<ResponseHomeViewData>
            ) {
                if (response.isSuccessful) {
                    Log.d("please","제발")
                    val data = response.body()?.data?.trendDrive
                    homeHotDriveAdapter.hotData.addAll(data!!)
                    homeHotDriveAdapter.notifyDataSetChanged()
                    Log.d("server connect", "success")
                } else {
                    Log.d("server connect", "failed")
                    Log.d("server connect", "${response.errorBody()}")
                    Log.d("server connect", "${response.message()}")
                    Log.d("server connect", "${response.code()}")
                    Log.d("server connect", "${response.raw().request.url}")
                }
            }

            override fun onFailure(call: Call<ResponseHomeViewData>, t: Throwable) {

            }
        })
    }

    private fun initHomeNightDrive() {
        binding.recyclerviewHomeNightDrive.adapter = homeNightDriveAdapter
        val call : Call<ResponseHomeViewData> = ApiService.mainViewService.getMain("111")
        call.enqueue(object : Callback<ResponseHomeViewData>{
            override fun onResponse(
                call: Call<ResponseHomeViewData>,
                response: Response<ResponseHomeViewData>
            ) {
                if (response.isSuccessful) {
                    Log.d("please","제발")
                    val data = response.body()?.data?.customThemeDrive
                    homeNightDriveAdapter.nightData.addAll(data!!)
                    homeNightDriveAdapter.notifyDataSetChanged()
                    Log.d("server connect", "success")
                } else {
                    Log.d("server connect", "failed")
                    Log.d("server connect", "${response.errorBody()}")
                    Log.d("server connect", "${response.message()}")
                    Log.d("server connect", "${response.code()}")
                    Log.d("server connect", "${response.raw().request.url}")
                }
            }

            override fun onFailure(call: Call<ResponseHomeViewData>, t: Throwable) {

            }
        })

    }

    private fun initHomeLocationDrive() {
        binding.recyclerviewHomeLocationDrive.adapter = homeLocationDriveAdapter
        val call : Call<ResponseHomeViewData> = ApiService.mainViewService.getMain("111")
        call.enqueue(object : Callback<ResponseHomeViewData>{
            override fun onResponse(
                call: Call<ResponseHomeViewData>,
                response: Response<ResponseHomeViewData>
            ) {
                if (response.isSuccessful) {
                    Log.d("please","제발")
                    val data = response.body()?.data?.localDrive
                    homeLocationDriveAdapter.locationData.addAll(data!!)
                    homeLocationDriveAdapter.notifyDataSetChanged()
                    Log.d("server connect", "success")
                } else {
                    Log.d("server connect", "failed")
                    Log.d("server connect", "${response.errorBody()}")
                    Log.d("server connect", "${response.message()}")
                    Log.d("server connect", "${response.code()}")
                    Log.d("server connect", "${response.raw().request.url}")
                }
            }

            override fun onFailure(call: Call<ResponseHomeViewData>, t: Throwable) {

            }
        })

    }


    private fun goSearchView() {
        binding.imgMainSearch.setOnClickListener {
            val intent = Intent(activity, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun replaceMoreViewFragment() {
        binding.textHomeHotDrivePlus.setOnClickListener {
            val result = binding.textHomeHotDrive.text
            val num = 0
            setFragmentResult("title", bundleOf("title" to result, "num" to num))
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
            setFragmentResult("title", bundleOf("title" to result, "num" to num))
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
            setFragmentResult("title", bundleOf("title" to result, "num" to num))
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.apply {
                replace(R.id.nav_host_fragment_activity_main, MoreViewFragment())
                addToBackStack(null)
                commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}