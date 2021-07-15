package com.example.charo_android.ui.home.more

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.charo_android.R
import com.example.charo_android.api.ApiService
import com.example.charo_android.api.ResponseMoreViewData
import com.example.charo_android.data.LocalHomeThemeDataSource
import com.example.charo_android.data.LocalHomeViewPagerDataSource
import com.example.charo_android.data.LocalMoreThemeDataSource
import com.example.charo_android.databinding.FragmentMoreThemeContentViewBinding
import com.example.charo_android.databinding.FragmentMoreViewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MoreThemeContentViewFragment(userId :String, identity :String, value :String
    ) : Fragment() {
    private var _binding: FragmentMoreThemeContentViewBinding? = null
    private val binding get() = _binding!!
    private val moreThemeContentViewAdapter = MoreThemeContentViewAdapter()

    val userId = userId
    val identity = identity
    val value = value

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoreThemeContentViewBinding.inflate(inflater, container, false)
        val root: View = binding.root


        initSpinner()
        loadMoreThemeData()

        return root
    }




    private fun initSpinner(){
        val adapter = ArrayAdapter.createFromResource(requireContext(), R.array.search_spinner, android.R.layout.simple_spinner_item)
        binding.spinnerMoreTheme.adapter = adapter
    }


    fun loadMoreThemeData(){
        binding.recyclerviewMoreTheme.adapter = moreThemeContentViewAdapter
        val call: Call<ResponseMoreViewData> = ApiService.moreViewService
            .getPreview(userId = userId, identifier =  identity, value = value )
        call.enqueue(object : Callback<ResponseMoreViewData>{
            override fun onResponse(
                call: Call<ResponseMoreViewData>,
                response: Response<ResponseMoreViewData>
            ) {
                if (response.isSuccessful){
                    val data = response.body()?.data?.drive
                    val count = response.body()?.data?.totalCourse
                    moreThemeContentViewAdapter.moreThemeData.addAll(data!!)
                    binding.textMoreThemeCount.text = "전체 ${count}개 게시물"
                    moreThemeContentViewAdapter.notifyDataSetChanged()
                    Log.d("servers", "success")
                }else{
                    Log.d("servers", "failed")
                    Log.d("servers", "${response.errorBody()}")
                    Log.d("servers", "${response.message()}")
                    Log.d("servers", "${response.code()}")
                    Log.d("servers", "${response.raw().request.url}")
                }

            }

            override fun onFailure(call: Call<ResponseMoreViewData>, t: Throwable) {

            }
        })




    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}