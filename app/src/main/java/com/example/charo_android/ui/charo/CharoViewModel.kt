package com.example.charo_android.ui.charo

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.charo_android.R
import com.example.charo_android.api.ApiService
import com.example.charo_android.data.mypage.ResponseMyPageSortedByPopularData
import com.example.charo_android.data.mypage.UserInformation
import com.example.charo_android.hidden.Hidden
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharoViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply { value = "This is Charo Fragment" }
    val text: LiveData<String> = _text

    private var _userInformation = MutableLiveData<UserInformation>(null)
    val userInformation: LiveData<UserInformation>
        get() = _userInformation



    fun getServerData(userEmail: String) {
        val call: Call<ResponseMyPageSortedByPopularData> =
            ApiService.myPageViewSortedByPopularService.getMyPage(userEmail)
        call.enqueue(object : Callback<ResponseMyPageSortedByPopularData> {
            override fun onResponse(
                call: Call<ResponseMyPageSortedByPopularData>,
                response: Response<ResponseMyPageSortedByPopularData>
            ) {
                if (response.isSuccessful) {
                    Log.d("server connect : My Page", "success")
                    val data = response.body()?.data
                    _userInformation.value = data!!.userInformation
                } else {
                    Log.d("server connect : My Page", "error")
                    Log.d("server connect : My Page", "$response.errorBody()")
                    Log.d("server connect : My Page", response.message())
                    Log.d("server connect : My Page", "${response.code()}")
                    Log.d("server connect : My Page", "${response.raw().request.url}")
                }
            }
            override fun onFailure(call: Call<ResponseMyPageSortedByPopularData>, t: Throwable) {
                    Log.d("server connect : My Page", "error: ${t.message}")
            }
        })
    }
}