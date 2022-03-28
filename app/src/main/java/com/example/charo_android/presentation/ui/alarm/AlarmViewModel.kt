package com.example.charo_android.presentation.ui.alarm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.charo_android.data.api.ApiService
import com.example.charo_android.data.model.mypage.*
import com.example.charo_android.presentation.util.enqueueUtil

class AlarmViewModel : ViewModel() {

    private var _isServerConnecting = MutableLiveData(false)
    val isServerConnection: LiveData<Boolean>
        get() = _isServerConnecting

    private var _userInformation = MutableLiveData<UserInformation>(null)
    val userInformation: LiveData<UserInformation>
        get() = _userInformation

    var _pushId = MutableLiveData<Int>(null)
    val pushId: LiveData<Int>
        get() = _pushId

    var _pushCode = MutableLiveData<Int>(null)
    val pushCode: LiveData<Int>
        get() = _pushCode

    var _isRead = MutableLiveData<Int>(null)
    val isRead: LiveData<Int>
        get() = _isRead

    var _token = MutableLiveData<String>(null)
    val token: LiveData<String>
        get() = _token

    var _image = MutableLiveData<String>(null)
    val image: LiveData<String>
        get() = _image

    var _title = MutableLiveData<String>(null)
    val title: LiveData<String>
        get() = _title

    var _body = MutableLiveData<String>(null)
    val body: LiveData<String>
        get() = _body

    var _month = MutableLiveData<String>(null)
    val month: LiveData<String>
        get() = _month

    var _day = MutableLiveData<String>(null)
    val day: LiveData<String>
        get() = _day

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