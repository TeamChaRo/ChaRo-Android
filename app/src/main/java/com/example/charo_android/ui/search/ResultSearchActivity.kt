package com.example.charo_android.ui.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.view.isGone
import com.example.charo_android.MainActivity
import com.example.charo_android.R
import com.example.charo_android.api.ApiService
import com.example.charo_android.api.RequestSearchViewData
import com.example.charo_android.api.ResponseSearchViewData
import com.example.charo_android.databinding.ActivityResultSearchBinding
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultSearchBinding
    private lateinit var userId : String
    private lateinit var nickName : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getStringExtra("userId").toString()
        val nickName = intent.getStringExtra("nickName").toString()
        Log.d("nickName", nickName)


        loadSearchData(userId)
        backSearch()
        initSpinner()
        backHome()


    }


    fun loadSearchData(userId: String) {
        val userId = intent.getStringExtra("userId").toString()
        val province = intent.getStringExtra("province").toString()
        val city = intent.getStringExtra("city").toString()
        val theme = intent.getStringExtra("theme").toString()
        val caution = intent.getStringExtra("caution").toString()

        if (province == ""){
            binding.chipResultSearch1.visibility = View.GONE
        }else{
            binding.chipResultSearch1.text = "#${province}"
        }
        if (city == ""){
            binding.chipResultSearch2.visibility = View.GONE
        }else{
            binding.chipResultSearch2.text = "#${city}"
        }

        if (theme == ""){
            binding.chipResultSearch3.visibility = View.GONE
        }else{
            binding.chipResultSearch3.text = "#${theme}"
        }

        if (caution == ""){
            binding.chipResultSearch4.visibility = View.GONE
        } else{
            binding.chipResultSearch4.text = "#${caution}x"
        }

        Log.d("ju", city)
        Log.d("ju", province)
        Log.d("ju", theme)
        Log.d("ju", caution)

        val resultSearchAdapter = ResultSearchAdapter(userId)
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



    private fun backSearch() {
        binding.imgBackSearchView.setOnClickListener {
        val intent = Intent(this, SearchActivity::class.java)
            intent.putExtra("userId",userId)
            intent.putExtra("nickName", nickName)
            startActivity(intent)

        }
    }

    private fun backHome() {
        binding.imgBackSearchView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("userId",userId)
            intent.putExtra("nickName", nickName)
            startActivity(intent)

        }
    }

    private fun initSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.search_spinner,
            R.layout.custom_spinner_item
        )
        binding.spinnerResultSearch.adapter = adapter
    }
}
