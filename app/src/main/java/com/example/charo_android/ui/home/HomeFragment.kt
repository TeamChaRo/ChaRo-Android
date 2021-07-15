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
import com.example.charo_android.MainActivity
import com.example.charo_android.R
import com.example.charo_android.api.ApiService
import com.example.charo_android.api.ResponseHomeViewData
import com.example.charo_android.data.*
import com.example.charo_android.databinding.FragmentHomeBinding
import com.example.charo_android.ui.home.more.MoreViewFragment
import com.example.charo_android.ui.search.SearchActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewPagerAdapter : HomeViewPagerAdapter
    private lateinit var homeTodayDriveAdapter : HomeTodayDriveAdapter
    private lateinit var homeThemeAdapter : HomeThemeAdapter
    private lateinit var homeHotDriveAdapter : HomeHotDriveAdapter
    private lateinit var homeNightDriveAdapter : HomeNightDriveAdapter
    private lateinit var homeLocationDriveAdapter : HomeLocationDriveAdapter
    val context = activity as? AppCompatActivity
    var bundle = Bundle()
    private lateinit var userId : String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root : View = binding.root



        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId :String = (activity as MainActivity).getUserId()
        homeViewPagerAdapter = HomeViewPagerAdapter()
        homeNightDriveAdapter = HomeNightDriveAdapter(userId)
        homeLocationDriveAdapter = HomeLocationDriveAdapter(userId)
        homeTodayDriveAdapter = HomeTodayDriveAdapter(userId)
        homeThemeAdapter = HomeThemeAdapter()
        homeHotDriveAdapter = HomeHotDriveAdapter(userId)
        initHomeViewPager(userId)
        initHomeTodayDrive(userId)
        initHomeHotDrive(userId)
        initHomeNightDrive(userId)
        initHomeLocationDrive(userId)
        goSearchView(userId)

        initToolBar()
        initHomeTheme()
        replaceMoreViewFragment(userId)
    }

    private fun initToolBar() {
        val toolbar = binding.toolbarMain
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }


    private fun initHomeViewPager(userId : String) {
        binding.vpMain.adapter = homeViewPagerAdapter
        val call: Call<ResponseHomeViewData> = ApiService.mainViewService.getMain(userId)
        Log.d("userId", userId)

        call.enqueue(object : Callback<ResponseHomeViewData> {
            override fun onResponse(
                call: Call<ResponseHomeViewData>,
                response: Response<ResponseHomeViewData>
            ) {
                if (response.isSuccessful) {
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

    private fun initHomeTodayDrive(userId : String) {
        binding.recyclerviewHomeTodayDrive.adapter = homeTodayDriveAdapter
        val call : Call<ResponseHomeViewData> = ApiService.mainViewService.getMain(userId)
        call.enqueue(object : Callback<ResponseHomeViewData>{
            override fun onResponse(
                call: Call<ResponseHomeViewData>,
                response: Response<ResponseHomeViewData>
            ) {
                if (response.isSuccessful) {
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

    private fun initHomeHotDrive(userId : String) {
        binding.recyclerviewHomeHotDrive.adapter = homeHotDriveAdapter
        val call : Call<ResponseHomeViewData> = ApiService.mainViewService.getMain(userId)
        call.enqueue(object : Callback<ResponseHomeViewData>{
            override fun onResponse(
                call: Call<ResponseHomeViewData>,
                response: Response<ResponseHomeViewData>
            ) {
                if (response.isSuccessful) {
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

    private fun initHomeNightDrive(userId : String) {
        binding.recyclerviewHomeNightDrive.adapter = homeNightDriveAdapter
        val call : Call<ResponseHomeViewData> = ApiService.mainViewService.getMain(userId)
        call.enqueue(object : Callback<ResponseHomeViewData>{
            override fun onResponse(
                call: Call<ResponseHomeViewData>,
                response: Response<ResponseHomeViewData>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.data?.customThemeDrive
                    val customTheme = response.body()?.data?.customThemeTitle
                    homeNightDriveAdapter.nightData.addAll(data!!)
                    homeNightDriveAdapter.notifyDataSetChanged()
                    _binding?.textHomeNightDrive?.text = customTheme
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



    private fun initHomeLocationDrive(userId : String) {
        binding.recyclerviewHomeLocationDrive.adapter = homeLocationDriveAdapter
        val call : Call<ResponseHomeViewData> = ApiService.mainViewService.getMain(userId)
        call.enqueue(object : Callback<ResponseHomeViewData>{
            override fun onResponse(
                call: Call<ResponseHomeViewData>,
                response: Response<ResponseHomeViewData>
            ) {
                if (response.isSuccessful) {

                    val data = response.body()?.data?.localDrive
                    val location = response.body()?.data?.localTitle
                    homeLocationDriveAdapter.locationData.addAll(data!!)
                    homeLocationDriveAdapter.notifyDataSetChanged()
                    _binding?.textHomeLocationDrive?.text = location


                    Log.d("server connect", "local success")
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


    private fun goSearchView(userId: String) {
        binding.imgMainSearch.setOnClickListener {
            val intent = Intent(activity, SearchActivity::class.java)
            intent.putExtra("userId",userId)
            startActivity(intent)
        }
    }

    private fun replaceMoreViewFragment(userId: String) {
        binding.textHomeHotDrivePlus.setOnClickListener {
            val result = binding.textHomeHotDrive.text
            val num = 0
            setFragmentResult("title", bundleOf("title" to result, "num" to num ,"userId" to userId))
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
            setFragmentResult("title", bundleOf("title" to result, "num" to num ,"userId" to userId))
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
            setFragmentResult("title", bundleOf("title" to result, "num" to num ,"userId" to userId))
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