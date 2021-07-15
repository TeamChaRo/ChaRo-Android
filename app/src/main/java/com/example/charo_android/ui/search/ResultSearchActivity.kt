package com.example.charo_android.ui.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.example.charo_android.R
import com.example.charo_android.api.ApiService
import com.example.charo_android.api.RequestSearchViewData
import com.example.charo_android.api.ResponseSearchViewData
import com.example.charo_android.databinding.ActivityResultSearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultSearchActivity : AppCompatActivity() {
    private var resultSearchAdapter = ResultSearchAdapter()
    private lateinit var binding: ActivityResultSearchBinding
    private lateinit var userId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        resultSearchAdapter = ResultSearchAdapter()
        userId = intent.getStringExtra("userId").toString()
        Log.d("jiwon", userId)
        loadSearchData(userId)
        backSearch(userId)
        initSpinner()


    }


    fun loadSearchData(userId: String) {
        if (intent.hasExtra("province")) {
            binding.chipResultSearch1.text = "#" + intent.getStringExtra("province")
        }
        if (intent.hasExtra("city")) {
            binding.chipResultSearch2.text = "#" + intent.getStringExtra("city")
        }
        if (intent.hasExtra("theme")) {
            binding.chipResultSearch3.text = "#" + intent.getStringExtra("theme")
        }
        if (intent.hasExtra("caution")) {
            binding.chipResultSearch4.text = "#" + intent.getStringExtra("caution") + "x"
        }
        val userId = intent.getStringExtra("userId").toString()
        val city = intent.getStringExtra("city").toString()
        val theme = intent.getStringExtra("theme").toString()
        val caution = intent.getStringExtra("caution").toString()

        Log.d("userId", userId)
        Log.d("muyaho", city)
        Log.d("muyaho", theme)
        Log.d("no", caution)
        binding.recyclerviewResultSearch.adapter = resultSearchAdapter
        val requet = RequestSearchViewData(userId, city, theme, caution)
        val call: Call<ResponseSearchViewData> =
            ApiService.searchViewService.postSearch(requet)
        call.enqueue(object : Callback<ResponseSearchViewData> {
            override fun onResponse(
                call: Call<ResponseSearchViewData>,
                response: Response<ResponseSearchViewData>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.data?.drive
                    val count = response.body()?.data?.totalCourse
                    resultSearchAdapter.searchData.addAll(data!!)
                    resultSearchAdapter.notifyDataSetChanged()
                    binding.textResultSearchCount.text = "전체 ${count}개 게시물"

                    Log.d("muyaho", "무야효~~~")
                }else{
                    Log.d("server connect", "failed")
                    Log.d("server connect", "${response.errorBody()}")
                    Log.d("server connect", "${response.message()}")
                    Log.d("server connect", "${response.code()}")
                    Log.d("server connect", "${response.raw().request.url}")
                }
            }

            override fun onFailure(call: Call<ResponseSearchViewData>, t: Throwable) {

            }
        })

    }



    fun backSearch(userId: String) {
        binding.imgBackSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }
    }


    private fun initSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.search_spinner,
            android.R.layout.simple_spinner_item
        )
        binding.spinnerResultSearch.adapter = adapter
    }
}
