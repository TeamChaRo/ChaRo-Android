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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadSearchData()
        backSearch()
        initSpinner()




        
    }




    fun loadSearchData(){
        if(intent.hasExtra("province")){
            binding.chipResultSearch1.text = "#"+intent.getStringExtra("province")
        }
        if(intent.hasExtra("city")){
            binding.chipResultSearch2.text = "#"+intent.getStringExtra("city")
        }
        if(intent.hasExtra("theme")){
            binding.chipResultSearch3.text = "#"+intent.getStringExtra("theme")
        }
        if(intent.hasExtra("caution")){
            binding.chipResultSearch4.text= "#"+intent.getStringExtra("caution")+"x"
        }

        val city = intent.getStringExtra("city")
        val theme = intent.getStringExtra("theme")
        val caution = intent.getStringExtra("caution")

        Log.d("muyaho", city.toString())
        Log.d("muyaho", theme.toString())
        Log.d("muyaho", caution.toString())
        val requestSearchViewData = RequestSearchViewData("111", city.toString(), theme.toString(), caution.toString())

        val call: Call<ResponseSearchViewData> = ApiService.searchViewService.postSearch(requestSearchViewData)
        call.enqueue(object : Callback<ResponseSearchViewData>{
            override fun onResponse(
                call: Call<ResponseSearchViewData>,
                response: Response<ResponseSearchViewData>
            ) {
                if (response.isSuccessful){
                    val data = response.body()?.data?.drive
                    val count = response.body()?.data?.totalCourse
                    binding.recyclerviewResultSearch.adapter = resultSearchAdapter
                    resultSearchAdapter.searchData.addAll(data!!)
                    resultSearchAdapter.notifyDataSetChanged()
                    binding.textResultSearchCount.text = "전체 ${count}개 게시물"

                    Log.d("muyaho","무야효~~~")
                }
            }

            override fun onFailure(call: Call<ResponseSearchViewData>, t: Throwable) {

            }
        })

    }


    fun backSearch(){
        binding.imgBackSearch.setOnClickListener{
        val intent = Intent(this, SearchActivity::class.java)
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