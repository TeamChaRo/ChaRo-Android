package com.example.charo_android.ui.home.more

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.setFragmentResultListener
import com.example.charo_android.R
import com.example.charo_android.api.ApiService
import com.example.charo_android.api.ResponseMoreViewData
import com.example.charo_android.databinding.FragmentMoreViewBinding
import com.example.charo_android.ui.home.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MoreViewFragment : Fragment() {
    private var _binding: FragmentMoreViewBinding? = null
    private val binding get() = _binding!!
    private var homeFragment = HomeFragment()
    private lateinit var moreViewAdapter: MoreViewAdapter
    private lateinit var moreNewViewAdapter : MoreNewViewAdapter
    private lateinit var userId : String



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoreViewBinding.inflate(inflater, container, false)
        moreViewAdapter = MoreViewAdapter()
        moreNewViewAdapter = MoreNewViewAdapter()




        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = arguments?.getString("userId").toString()
        Log.d("userIdeas",userId)
        moreViewLoadData(userId)
        initSpinner()
        clickBackButton(userId)
        clickSpinner()
    }


    fun moreViewLoadData(userId : String) {
        setFragmentResultListener("title") { requestKey, bundle ->
            val result = bundle.getString("title")
            val num = bundle.getInt("num")
            val userId = bundle.getString("userId").toString()
            Log.d("result", result.toString())
            Log.d("result", num.toString())

            if (num == 0) {
                val call: Call<ResponseMoreViewData> =
                    ApiService.moreViewService.getPreview(userId, "0","")
                call.enqueue(object : Callback<ResponseMoreViewData> {
                    override fun onResponse(
                        call: Call<ResponseMoreViewData>,
                        response: Response<ResponseMoreViewData>
                    ) {
                        if (response.isSuccessful) {

                            val data = response.body()?.data?.drive
                            val count = response.body()?.data?.totalCourse
                            binding.recyclerviewMoreView.adapter = moreViewAdapter
                            moreViewAdapter.moreData.addAll(data!!)
                            moreViewAdapter.notifyDataSetChanged()
                            binding.textMoreViewCount.text = "전체 ${count}개 게시물"
                            binding.textToolbarTitle.text = result

                            Log.d("server connect", "success")
                        } else {
                            Log.d("server connect", "failed")
                            Log.d("server connect", "${response.errorBody()}")
                            Log.d("server connect", "${response.message()}")
                            Log.d("server connect", "${response.code()}")
                            Log.d("server connect", "${response.raw().request.url}")
                        }
                    }

                    override fun onFailure(call: Call<ResponseMoreViewData>, t: Throwable) {
                        Log.d("server connect", "error:${t.message}")
                    }
                })

            } else if (num == 2) {
                val call: Call<ResponseMoreViewData> =
                    ApiService.moreViewService.getPreview(userId, "2", "busan")
                call.enqueue(object : Callback<ResponseMoreViewData> {
                    override fun onResponse(
                        call: Call<ResponseMoreViewData>,
                        response: Response<ResponseMoreViewData>
                    ) {
                        if (response.isSuccessful) {
                            val data = response.body()?.data?.drive
                            val count = response.body()?.data?.totalCourse
                            binding.recyclerviewMoreView.adapter = moreViewAdapter
                            moreViewAdapter.moreData.addAll(data!!)
                            moreViewAdapter.notifyDataSetChanged()
                            binding.textMoreViewCount.text = "전체 ${count}개 게시물"
                            binding.textToolbarTitle.text = result
                            Log.d("server connect", "success")
                        } else {
                            Log.d("server connect", "failed")
                            Log.d("server connect", "${response.errorBody()}")
                            Log.d("server connect", "${response.message()}")
                            Log.d("server connect", "${response.code()}")
                            Log.d("server connect", "${response.raw().request.url}")
                        }
                    }

                    override fun onFailure(call: Call<ResponseMoreViewData>, t: Throwable) {
                        Log.d("server connect", "error:${t.message}")
                    }
                })

            } else {
                val call: Call<ResponseMoreViewData> =
                    ApiService.moreViewService.getPreview(userId, "1", "summer")
                call.enqueue(object : Callback<ResponseMoreViewData> {
                    override fun onResponse(
                        call: Call<ResponseMoreViewData>,
                        response: Response<ResponseMoreViewData>
                    ) {
                        if (response.isSuccessful) {
                            val data = response.body()?.data?.drive
                            val count = response.body()?.data?.totalCourse
                            val size = response.body()?.data?.drive?.size
                            Log.d("please", data.toString())
                            binding.recyclerviewMoreView.adapter = moreViewAdapter
                            moreViewAdapter.moreData.addAll(data!!)
                            moreViewAdapter.notifyDataSetChanged()
                            binding.textMoreViewCount.text = "전체 ${count}개 게시물"
                            binding.textToolbarTitle.text = result
                            Log.d("server connect", "success")
                            Log.d("ideas", userId)
                        } else {
                            Log.d("server connect", "failed")
                            Log.d("server connect", "${response.errorBody()}")
                            Log.d("server connect", "${response.message()}")
                            Log.d("server connect", "${response.code()}")
                            Log.d("server connect", "${response.raw().request.url}")
                        }
                    }

                    override fun onFailure(call: Call<ResponseMoreViewData>, t: Throwable) {
                        Log.d("server connect", "error:${t.message}")
                    }
                })
            }
        }
    }

    fun moreViewNewData(userId : String) {
        setFragmentResultListener("title") { requestKey, bundle ->
            val result = bundle.getString("title")
            val num = bundle.getInt("num")
            Log.d("result", result.toString())
            Log.d("result", num.toString())

            if (num == 0) {
                val call: Call<ResponseMoreViewData> =
                    ApiService.moreViewNewService.getNewPreview(userId, "0", "")
                call.enqueue(object : Callback<ResponseMoreViewData> {
                    override fun onResponse(
                        call: Call<ResponseMoreViewData>,
                        response: Response<ResponseMoreViewData>
                    ) {
                        if (response.isSuccessful) {

                            val data = response.body()?.data?.drive
                            Log.d("please", data.toString())
                            val count = response.body()?.data?.totalCourse
                            binding.recyclerviewMoreView.adapter = moreViewAdapter
                            moreViewAdapter.moreData.addAll(data!!)
                            moreViewAdapter.notifyDataSetChanged()
                            binding.textMoreViewCount.text = "전체 ${count}개 게시물"
                            binding.textToolbarTitle.text = result

                            Log.d("server connect", "success")
                        } else {
                            Log.d("server connect", "failed")
                            Log.d("server connect", "${response.errorBody()}")
                            Log.d("server connect", "${response.message()}")
                            Log.d("server connect", "${response.code()}")
                            Log.d("server connect", "${response.raw().request.url}")
                        }
                    }

                    override fun onFailure(call: Call<ResponseMoreViewData>, t: Throwable) {
                        Log.d("server connect", "error:${t.message}")
                    }
                })

            } else if (num == 2) {
                val call: Call<ResponseMoreViewData> =
                    ApiService.moreViewNewService.getNewPreview(userId = userId, "2", "busan")
                call.enqueue(object : Callback<ResponseMoreViewData> {
                    override fun onResponse(
                        call: Call<ResponseMoreViewData>,
                        response: Response<ResponseMoreViewData>
                    ) {
                        if (response.isSuccessful) {
                            val data = response.body()?.data?.drive
                            val count = response.body()?.data?.totalCourse
                            binding.recyclerviewMoreView.adapter = moreViewAdapter
                            moreViewAdapter.moreData.addAll(data!!)
                            moreViewAdapter.notifyDataSetChanged()
                            binding.textMoreViewCount.text = "전체 ${count}개 게시물"
                            binding.textToolbarTitle.text = result
                            Log.d("server connect", "success")
                        } else {
                            Log.d("server connect", "failed")
                            Log.d("server connect", "${response.errorBody()}")
                            Log.d("server connect", "${response.message()}")
                            Log.d("server connect", "${response.code()}")
                            Log.d("server connect", "${response.raw().request.url}")
                        }
                    }

                    override fun onFailure(call: Call<ResponseMoreViewData>, t: Throwable) {
                        Log.d("server connect", "error:${t.message}")
                    }
                })

            } else if (num == 1) {
                val call: Call<ResponseMoreViewData> =
                    ApiService.moreViewNewService.getNewPreview(userId, "1", "sea")
                call.enqueue(object : Callback<ResponseMoreViewData> {
                    override fun onResponse(
                        call: Call<ResponseMoreViewData>,
                        response: Response<ResponseMoreViewData>
                    ) {
                        if (response.isSuccessful) {
                            val data = response.body()?.data?.drive
                            val count = response.body()?.data?.totalCourse
                            Log.d("please", data.toString())
                            binding.recyclerviewMoreView.adapter = moreViewAdapter
                            moreViewAdapter.moreData.addAll(data!!)
                            moreViewAdapter.notifyDataSetChanged()
                            binding.textMoreViewCount.text = "전체 ${count}개 게시물"
                            binding.textToolbarTitle.text = result
                            Log.d("server connect", "success")
                        } else {
                            Log.d("server connect", "failed")
                            Log.d("server connect", "${response.errorBody()}")
                            Log.d("server connect", "${response.message()}")
                            Log.d("server connect", "${response.code()}")
                            Log.d("server connect", "${response.raw().request.url}")
                        }
                    }

                    override fun onFailure(call: Call<ResponseMoreViewData>, t: Throwable) {
                        Log.d("server connect", "error:${t.message}")
                    }
                })
            }
        }

    }


    private fun initSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.search_spinner,
            android.R.layout.simple_spinner_item
        )
        binding.spinnerMoreView.adapter = adapter
    }


    private fun clickSpinner() {
        binding.spinnerMoreView.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        Log.d("click", "1")
                        moreViewAdapter.notifyItemRangeRemoved(0,moreViewAdapter.itemCount)
                        moreViewLoadData(userId)
                    }
                    1 -> {
                        Log.d("click", "2")
                        moreViewAdapter.notifyItemRangeRemoved(0,moreViewAdapter.itemCount)
                        moreViewNewData(userId)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }


        }

    }


    private fun clickBackButton(userId: String) {
        binding.imgBackHome.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("userId", userId)
            homeFragment.arguments = bundle
            val fragmentManager = activity?.supportFragmentManager
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_main, homeFragment)
                ?.commit()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

