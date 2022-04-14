package com.charo.android.presentation.ui.charo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.charo.android.data.api.ApiService
import com.charo.android.data.model.charo.*
import com.charo.android.hidden.Hidden
import com.charo.android.presentation.util.enqueueUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class CharoViewModel : ViewModel() {

//    private val _text = MutableLiveData<String>().apply { value = "This is Charo Fragment" }
//    val text: LiveData<String> = _text

    private var _isServerConnecting = MutableLiveData(false)
    val isServerConnection: LiveData<Boolean>
        get() = _isServerConnecting

    private var _userInformation = MutableLiveData<UserInformation>(null)
    val userInformation: LiveData<UserInformation>
        get() = _userInformation

    private var _writtenLikeData = MutableLiveData<Post>(null)
    val writtenLikeData: LiveData<Post>
        get() = _writtenLikeData

    private var _writtenNewData = MutableLiveData<Post>(null)
    val writtenNewData: LiveData<Post>
        get() = _writtenNewData

    private var _savedLikeData = MutableLiveData<Post>(null)
    val savedLikeData: LiveData<Post>
        get() = _savedLikeData

    private var _savedNewData = MutableLiveData<Post>(null)
    val savedNewData: LiveData<Post>
        get() = _savedNewData

    private var _writtenMoreLikeData = MutableLiveData<Post>(null)
    val writtenMoreLikeData: LiveData<Post>
        get() = _writtenMoreLikeData

    private var _writtenMoreNewData = MutableLiveData<Post>(null)
    val writtenMoreNewData: LiveData<Post>
        get() = _writtenMoreNewData

    private var _savedMoreLikeData = MutableLiveData<Post>(null)
    val savedMoreLikeData: LiveData<Post>
        get() = _savedMoreLikeData

    private var _savedMoreNewData = MutableLiveData<Post>(null)
    val savedMoreNewData: LiveData<Post>
        get() = _savedMoreNewData

    private var _otherInformation = MutableLiveData<UserInformation>(null)
    val otherInformation: LiveData<UserInformation>
        get() = _otherInformation

    private var _otherWrittenNewData = MutableLiveData<Post>(null)
    val otherWrittenNewData: LiveData<Post> get() = _otherWrittenNewData

    private var _otherWrittenLikeData = MutableLiveData<Post>(null)
    val otherWrittenLikeData: LiveData<Post> get() = _otherWrittenLikeData

    private var _otherWrittenMoreNewData = MutableLiveData<Post>(null)
    val otherWrittenMoreNewData: LiveData<Post> get() = _otherWrittenMoreNewData

    private var _otherWrittenMoreLikeData = MutableLiveData<Post>(null)
    val otherWrittenMoreLikeData: LiveData<Post> get() = _otherWrittenMoreLikeData

    private var _followData = MutableLiveData<FollowData>(null)
    val followData: LiveData<FollowData> get() = _followData

    fun getInitLikeData() {
        val call: Call<ResponseMyPageLikeData> =
            com.charo.android.data.api.ApiService.myPageViewLikeService.getMyPage(Hidden.userId)
        call.enqueue(object : Callback<ResponseMyPageLikeData> {
            override fun onResponse(
                call: Call<ResponseMyPageLikeData>,
                response: Response<ResponseMyPageLikeData>
            ) {
                if (response.isSuccessful) {
                    Timber.d("server connect : My Page success")
                    val data = response.body()?.data
                    _userInformation.value = data?.userInformation
                    _writtenLikeData.value = data?.writtenPost
                    _savedLikeData.value = data?.savedPost
                } else {
                    Timber.d("server connect : My Page error")
                    Timber.d("server connect : My Page ${response.errorBody()}")
                    Timber.d("server connect : My Page ${response.message()}")
                    Timber.d("server connect : My Page ${response.code()}")
                    Timber.d("server connect : My Page ${response.raw().request.url}")
                }
            }

            override fun onFailure(call: Call<ResponseMyPageLikeData>, t: Throwable) {
                Timber.d("server connect : My Page error: ${t.message}")
            }
        })
    }

    fun getInitNewData() {
        val call: Call<ResponseMyPageLikeData> =
            com.charo.android.data.api.ApiService.myPageViewNewService.getMyPage(Hidden.userId)
        call.enqueue(object : Callback<ResponseMyPageLikeData> {
            override fun onResponse(
                call: Call<ResponseMyPageLikeData>,
                response: Response<ResponseMyPageLikeData>
            ) {
                if (response.isSuccessful) {
                    Timber.d("server connect : My Page   success")
                    val data = response.body()?.data
                    _userInformation.value = data?.userInformation
                    _writtenNewData.value = data?.writtenPost
                    _savedNewData.value = data?.savedPost
                } else {
                    Timber.d("server connect : My Page   error")
                    Timber.d("server connect : My Page   ${response.errorBody()}")
                    Timber.d("server connect : My Page   ${response.message()}")
                    Timber.d("server connect : My Page   ${response.code()}")
                    Timber.d("server connect : My Page   ${response.raw().request.url}")
                }
            }

            override fun onFailure(call: Call<ResponseMyPageLikeData>, t: Throwable) {
                Timber.d("server connect : My Page   error: ${t.message}")
            }
        })
    }

    fun getMoreWrittenLikeData() {
        _isServerConnecting.value = true
        val call: Call<ResponseMyPageMoreData> =
            com.charo.android.data.api.ApiService.myPageViewMoreService.getMoreWrittenLikeData(
                Hidden.userId,
                writtenLikeData.value!!.lastId,
                writtenLikeData.value!!.lastCount
            )
        call.enqueue(object : Callback<ResponseMyPageMoreData> {
            override fun onResponse(
                call: Call<ResponseMyPageMoreData>,
                response: Response<ResponseMyPageMoreData>
            ) {
                _isServerConnecting.value = false
                if (response.isSuccessful) {
                    Timber.d("server connect : My Page Infinite Scrolling   success")
                    val data = response.body()?.data
                    _writtenMoreLikeData.value = data!!
                    _writtenLikeData.value?.drive?.addAll(data.drive)
                    if (data.lastId != 0) {
                        _writtenLikeData.value?.lastId = data.lastId
                        _writtenLikeData.value?.lastCount = data.lastCount
                    }
                } else {
                    Timber.d("server connect : My Page Infinite Scrolling   error")
                    Timber.d("server connect : My Page Infinite Scrolling   ${response.errorBody()}")
                    Timber.d("server connect : My Page Infinite Scrolling   ${response.message()}")
                    Timber.d("server connect : My Page Infinite Scrolling   ${response.code()}")
                    Timber.d(
                        "server connect : My Page Infinite Scrolling ${response.raw().request.url}"
                    )
                }
            }

            override fun onFailure(call: Call<ResponseMyPageMoreData>, t: Throwable) {
                _isServerConnecting.value = false
                Timber.d("server connect : My Page Infinite Scrolling   error: ${t.message}")
            }
        })
    }

    fun getMoreWrittenNewData() {
        _isServerConnecting.value = true
        val call: Call<ResponseMyPageMoreData> =
            com.charo.android.data.api.ApiService.myPageViewMoreService.getMoreWrittenNewData(
                Hidden.userId,
                writtenNewData.value!!.lastId
            )
        call.enqueue(object : Callback<ResponseMyPageMoreData> {
            override fun onResponse(
                call: Call<ResponseMyPageMoreData>,
                response: Response<ResponseMyPageMoreData>
            ) {
                _isServerConnecting.value = false
                if (response.isSuccessful) {
                    Timber.d("server connect : My Page Infinite Scrolling   success")
                    val data = response.body()?.data
                    _writtenMoreNewData.value = data!!
                    _writtenNewData.value?.drive?.addAll(data.drive)
                    if (data.lastId != 0) {
                        _writtenNewData.value?.lastId = data.lastId
                        _writtenNewData.value?.lastCount = data.lastCount
                    }
                } else {
                    Timber.d("server connect : My Page Infinite Scrolling   error")
                    Timber.d("server connect : My Page Infinite Scrolling   ${response.errorBody()}")
                    Timber.d("server connect : My Page Infinite Scrolling    ${response.message()}")
                    Timber.d("server connect : My Page Infinite Scrolling   ${response.code()}")
                    Timber.d(
                        "server connect : My Page Infinite Scrolling ${response.raw().request.url}"
                    )
                }
            }

            override fun onFailure(call: Call<ResponseMyPageMoreData>, t: Throwable) {
                _isServerConnecting.value = false
                Timber.d("server connect : My Page Infinite Scrolling   error: ${t.message}")
            }
        })
    }

    fun getMoreSavedLikeData() {
        _isServerConnecting.value = true
        val call: Call<ResponseMyPageMoreData> =
            com.charo.android.data.api.ApiService.myPageViewMoreService.getMoreSavedLikeData(
                Hidden.userId,
                savedLikeData.value!!.lastId,
                savedLikeData.value!!.lastCount
            )
        call.enqueue(object : Callback<ResponseMyPageMoreData> {
            override fun onResponse(
                call: Call<ResponseMyPageMoreData>,
                response: Response<ResponseMyPageMoreData>
            ) {
                if (response.isSuccessful) {
                    Timber.d("server connect : My Page Infinite Scrolling   success")
                    val data = response.body()?.data
                    _savedMoreLikeData.value = data!!
                    _savedLikeData.value?.drive?.addAll(data.drive)
                    if (data.lastId != 0) {
                        _savedLikeData.value?.lastId = data.lastId
                        _savedLikeData.value?.lastCount = data.lastCount
                    }
                } else {
                    Timber.d("server connect : My Page Infinite Scrolling   error")
                    Timber.d("server connect : My Page Infinite Scrolling   ${response.errorBody()}")
                    Timber.d("server connect : My Page Infinite Scrolling   ${response.message()}")
                    Timber.d("server connect : My Page Infinite Scrolling   ${response.code()}")
                    Timber.d(
                        "server connect : My Page Infinite Scrolling ${response.raw().request.url}"
                    )
                }
            }

            override fun onFailure(call: Call<ResponseMyPageMoreData>, t: Throwable) {
                _isServerConnecting.value = false
                Timber.d("server connect : My Page Infinite Scrolling   error: ${t.message}")
            }
        })
    }

    fun getMoreSavedNewData() {
        _isServerConnecting.value = true
        val call: Call<ResponseMyPageMoreData> =
            ApiService.myPageViewMoreService.getMoreSavedNewData(
                Hidden.userId,
                savedNewData.value!!.lastId
            )
        call.enqueue(object : Callback<ResponseMyPageMoreData> {
            override fun onResponse(
                call: Call<ResponseMyPageMoreData>,
                response: Response<ResponseMyPageMoreData>
            ) {
                if (response.isSuccessful) {
                    Timber.d("server connect : My Page Infinite Scrolling   success")
                    val data = response.body()?.data
                    _savedMoreNewData.value = data!!
                    _savedNewData.value?.drive?.addAll(data.drive)
                    if (data.lastId != 0) {
                        _savedNewData.value?.lastId = data.lastId
                        _savedNewData.value?.lastCount = data.lastCount
                    }
                } else {
                    Timber.d("server connect : My Page Infinite Scrolling   error")
                    Timber.d("server connect : My Page Infinite Scrolling   ${response.errorBody()}")
                    Timber.d("server connect : My Page Infinite Scrolling    ${response.message()}")
                    Timber.d("server connect : My Page Infinite Scrolling   ${response.code()}")
                    Timber.d(
                        "server connect : My Page Infinite Scrolling ${response.raw().request.url}"
                    )
                }
            }

            override fun onFailure(call: Call<ResponseMyPageMoreData>, t: Throwable) {
                _isServerConnecting.value = false
                Timber.d("server connect : My Page Infinite Scrolling   error: ${t.message}")
            }
        })
    }

    fun getInitOtherLikeData(userEmail: String) {
        _isServerConnecting.value = true
        Timber.d("from   CharoViewModel.getInitOtherLikeData")
        val call = ApiService.myPageViewLikeService.getMyPage(userEmail)
        call.enqueueUtil(
            onSuccess = {
                _otherInformation.value = it.data.userInformation
                _otherWrittenLikeData.value = it.data.writtenPost
            }
        )
        _isServerConnecting.value = false
    }

    fun getInitOtherNewData(userEmail: String) {
        _isServerConnecting.value = true
        Timber.d("from   CharoViewModel.getInitOtherNewData")
        val call = ApiService.myPageViewNewService.getMyPage(userEmail)
        call.enqueueUtil(
            onSuccess = {
                _otherInformation.value = it.data.userInformation
                _otherWrittenNewData.value = it.data.writtenPost
            }
        )
        _isServerConnecting.value = false
    }

    fun getMoreOtherWrittenLikeData(userEmail: String) {
        _isServerConnecting.value = true
        Timber.d("from   getMoreOtherWrittenLikeData")
        val call = ApiService.myPageViewMoreService.getMoreWrittenLikeData(
            userEmail,
            otherWrittenLikeData.value!!.lastId,
            otherWrittenLikeData.value!!.lastCount
        )
        call.enqueueUtil(
            onSuccess = {
                _otherWrittenMoreLikeData.value = it.data
                _otherWrittenLikeData.value?.drive?.addAll(it.data.drive)
                if (it.data.lastId != 0) {
                    _otherWrittenLikeData.value?.lastId = it.data.lastId
                    _otherWrittenLikeData.value?.lastCount = it.data.lastCount
                }
            }
        )
        _isServerConnecting.value = false
    }

    fun getMoreOtherWrittenNewData(userEmail: String) {
        _isServerConnecting.value = true
        Timber.d("from   getMoreOtherWrittenNewData")
        val call = com.charo.android.data.api.ApiService.myPageViewMoreService.getMoreWrittenNewData(
            userEmail,
            otherWrittenNewData.value!!.lastId
        )
        call.enqueueUtil(
            onSuccess = {
                _otherWrittenMoreNewData.value = it.data
                _otherWrittenNewData.value?.drive?.addAll(it.data.drive)
                if (it.data.lastId != 0) {
                    _otherWrittenNewData.value?.lastId = it.data.lastId
                    _otherWrittenNewData.value?.lastCount = it.data.lastCount
                }
            }
        )
        _isServerConnecting.value = false
    }

    fun getFollowData(userEmail: String, myPageEmail: String) {
        _isServerConnecting.value = true
        Timber.d("from   getFollowData")
        val call = com.charo.android.data.api.ApiService.myPageViewFollowService.getFollowInfo(userEmail, myPageEmail)
        call.enqueueUtil(
            onSuccess = {
                _followData.value = it.data
                Timber.d("getFollowData   success")
            },
            onError = {
                Timber.d("getFollowData   failed")
            }
        )
    }

    fun clearPostData() {
        _writtenLikeData = MutableLiveData<Post>(null)
        _writtenNewData = MutableLiveData<Post>(null)
        _savedLikeData = MutableLiveData<Post>(null)
        _savedNewData = MutableLiveData<Post>(null)
        _writtenMoreLikeData = MutableLiveData<Post>(null)
        _writtenMoreNewData = MutableLiveData<Post>(null)
        _savedMoreLikeData = MutableLiveData<Post>(null)
        _savedMoreNewData = MutableLiveData<Post>(null)
    }
}