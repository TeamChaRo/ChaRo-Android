package com.example.charo_android.ui.charo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.charo_android.api.ApiService
import com.example.charo_android.data.mypage.*
import com.example.charo_android.hidden.Hidden
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharoViewModel : ViewModel() {

//    private val _text = MutableLiveData<String>().apply { value = "This is Charo Fragment" }
//    val text: LiveData<String> = _text

    private var _isServerConnecting = MutableLiveData(false)
    val isServerConnection: LiveData<Boolean>
        get() = _isServerConnecting

    private var _userInformation = MutableLiveData<UserInformation>(null)
    val userInformation: LiveData<UserInformation>
        get() = _userInformation

    private var _writtenPostSortedByPopular = MutableLiveData<Post>(null)
    val writtenPostSortedByPopular: LiveData<Post>
        get() = _writtenPostSortedByPopular

    private var _writtenPostSortedByDate = MutableLiveData<Post>(null)
    val writtenPostSortedByDate: LiveData<Post>
        get() = _writtenPostSortedByDate

    private var _savedPostSortedByPopular = MutableLiveData<Post>(null)
    val savedPostSortedByPopular: LiveData<Post>
        get() = _savedPostSortedByPopular

    private var _savedPostSortedByDate = MutableLiveData<Post>(null)
    val savedPostSortedByDate: LiveData<Post>
        get() = _savedPostSortedByDate

    fun getServerDataSortedByPopular() {
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
                    _writtenPostSortedByPopular.value = data?.writtenPost
                    _savedPostSortedByPopular.value = data?.savedPost
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

    fun getServerDataSortedByDate() {
        val call: Call<ResponseMyPageSortedByDateData> =
            ApiService.myPageViewSortedByDateService.getMyPage(Hidden.userId)
        call.enqueue(object: Callback<ResponseMyPageSortedByDateData> {
            override fun onResponse(
                call: Call<ResponseMyPageSortedByDateData>,
                response: Response<ResponseMyPageSortedByDateData>
            ) {
                if(response.isSuccessful) {
                    Log.d("server connect : My Page", "success")
                    val data = response.body()?.data
                    _userInformation.value = data?.userInformation
                    _writtenPostSortedByDate.value = data?.writtenPost
                    _savedPostSortedByDate.value = data?.savedPost
                } else {
                    Log.d("server connect : My Page", "error")
                    Log.d("server connect : My Page", "$response.errorBody()")
                    Log.d("server connect : My Page", response.message())
                    Log.d("server connect : My Page", "${response.code()}")
                    Log.d("server connect : My Page", "${response.raw().request.url}")
                }
            }

            override fun onFailure(call: Call<ResponseMyPageSortedByDateData>, t: Throwable) {
                Log.d("server connect : My Page", "error: ${t.message}")
            }
        })
    }
}