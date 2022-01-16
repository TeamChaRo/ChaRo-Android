package com.example.charo_android.presentation.ui.alarm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.charo_android.data.api.ApiService
import com.example.charo_android.data.model.mypage.*
import com.example.charo_android.data.model.response.alarm.ResponseAlarmListData
import com.example.charo_android.hidden.Hidden
import com.example.charo_android.presentation.util.enqueueUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlarmViewModel : ViewModel() {

    private var _isServerConnecting = MutableLiveData(false)
    val isServerConnection: LiveData<Boolean>
        get() = _isServerConnecting

    private var _userInformation = MutableLiveData<UserInformation>(null)
    val userInformation: LiveData<UserInformation>
        get() = _userInformation

    private var _pushId = MutableLiveData<Int>(null)
    val pushId: LiveData<Int>
        get() = _pushId

    private var _pushCode = MutableLiveData<Int>(null)
    val pushCode: LiveData<Int>
        get() = _pushCode

    private var _isRead = MutableLiveData<Int>(null)
    val isRead: LiveData<Int>
        get() = _isRead

    private var _token = MutableLiveData<String>(null)
    val token: LiveData<String>
        get() = _token

    private var _image = MutableLiveData<String>(null)
    val image: LiveData<String>
        get() = _image

    private var _title = MutableLiveData<String>(null)
    val title: LiveData<String>
        get() = _title

    private var _body = MutableLiveData<String>(null)
    val body: LiveData<String>
        get() = _body

    private var _month = MutableLiveData<String>(null)
    val month: LiveData<String>
        get() = _month

    private var _day = MutableLiveData<String>(null)
    val day: LiveData<String>
        get() = _day

    suspend fun getInitAlarmData() {
        val call: Call<ResponseAlarmListData> =
            ApiService.alarmViewService.getAlarmList(Hidden.otherUserEmail)
        call.enqueue(object : Callback<ResponseAlarmListData> {
            override fun onResponse(
                call: Call<ResponseAlarmListData>,
                response: Response<ResponseAlarmListData>
            ) {
                if (response.isSuccessful) {
                    Log.d("server connect : Alarm", "success")
                    val pushList = response.body()?.pushList
                    _pushId.value = pushList?.pushId
                    _pushCode.value = pushList?.pushCode
                    _isRead.value = pushList?.isRead
                    _token.value = pushList?.token
                    _image.value = pushList?.image
                    _title.value = pushList?.title
                    _body.value = pushList?.body
                    _month.value = pushList?.month
                    _day.value = pushList?.day
                } else {
                    Log.d("server connect : Alarm ", "error")
                    Log.d("server connect : Alarm ", "$response.errorBody()")
                    Log.d("server connect : Alarm ", response.message())
                    Log.d("server connect : Alarm ", "${response.code()}")
                    Log.d("server connect : Alarm ", "${response.raw().request.url}")
                }
            }

            override fun onFailure(call: Call<ResponseAlarmListData>, t: Throwable) {
                Log.d("server connect : Alarm ", "error: ${t.message}")
            }
        })
    }

    suspend fun getInitOtherAlarmData(userEmail: String) {
        _isServerConnecting.value = true
        Log.d("from", "AlarmViewModel.getInitOtherAlarmData")
        val call = ApiService.alarmViewService.getAlarmList(userEmail)
        call.enqueueUtil(
            onSuccess = {
                _pushId.value = it.pushList.pushId
                _pushCode.value = it.pushList.pushCode
                _isRead.value = it.pushList.isRead
                _token.value = it.pushList.token
                _image.value = it.pushList.image
                _title.value = it.pushList.title
                _body.value = it.pushList.body
                _month.value = it.pushList.month
                _day.value = it.pushList.day
            }
        )
        _isServerConnecting.value = false
    }
}