package com.charo.android.presentation.ui.alarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.charo.android.data.model.mypage.UserInformation


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
}