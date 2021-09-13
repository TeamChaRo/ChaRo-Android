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
import com.example.charo_android.data.mypage.Post
import com.example.charo_android.data.mypage.ResponseMyPageSortedByPopularData
import com.example.charo_android.data.mypage.UserInformation
import com.example.charo_android.hidden.Hidden
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharoViewModel : ViewModel() {

//    private val _text = MutableLiveData<String>().apply { value = "This is Charo Fragment" }
//    val text: LiveData<String> = _text

    private var _userInformation = MutableLiveData<UserInformation>(null)
    val userInformation: LiveData<UserInformation>
        get() = _userInformation

    private var _writtenPost = MutableLiveData<Post>(null)
    val writtenPost: LiveData<Post>
        get() = _writtenPost

    private var _savedPost = MutableLiveData<Post>(null)
    val savedPost: LiveData<Post>
        get() = _savedPost

    fun getServerData() {
        val call: Call<ResponseMyPageSortedByPopularData> =
            ApiService.myPageViewSortedByPopularService.getMyPage(Hidden.userId)
        call.enqueue(object : Callback<ResponseMyPageSortedByPopularData> {
            override fun onResponse(
                call: Call<ResponseMyPageSortedByPopularData>,
                response: Response<ResponseMyPageSortedByPopularData>
            ) {
                if (response.isSuccessful) {
                    Log.d("server connect : My Page", "success")
                    val data = response.body()?.data
                    _userInformation.value = data?.userInformation
                    _writtenPost.value = data?.writtenPost
                    _savedPost.value = data?.savedPost

                    Log.d("writtenPost size", writtenPost.value?.drive?.size.toString())
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