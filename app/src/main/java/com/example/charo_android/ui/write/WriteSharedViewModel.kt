package com.example.charo_android.ui.write

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WriteSharedViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }

    val userId = MutableLiveData<String>().default("And\'s Rabbit")
    val nickName = MutableLiveData<String>().default("안드네 토끼양")

    //writeMapFragment
    val locationFlag = MutableLiveData<String>() //출발 경유 도착
    val latitude = MutableLiveData<Double>().default(0.0)     //위도
    val longitude = MutableLiveData<Double>().default(0.0)    //경도

    //writeMapLocationFragment
    val locationName = MutableLiveData<String>()
    val locationAddress = MutableLiveData<String>()
    val resultLocation = MutableLiveData<String>()



//    private val _userId = MutableLiveData<String>()
//    val userId: LiveData<String> get() = _userId


//    fun insertCountTwo() {
//        _updateActivityCount.value = (updateActivityCount.value ?: 0) + 2
//    }
}