package com.example.charo_android.presentation.ui.write

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.charo_android.data.WriteImgInfo
import okhttp3.MultipartBody

class WriteSharedViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }

    val userId = MutableLiveData<String>().default("And\'s Rabbit")
    val nickName = MutableLiveData<String>().default("안드네 토끼양")

    //WriteFragment
    val userEmail = MutableLiveData<String>().default("and@naver.com")
    val title = MutableLiveData<String>().default("")
    val province = MutableLiveData<String>().default("")            //경기도
    val region = MutableLiveData<String>().default("")              //수원
    val warning = MutableLiveData<ArrayList<String>>()                             //["highway", "mountainRoad"]
    val theme = MutableLiveData<ArrayList<String>>()                               //["summer", "sea"]
    val isParking = MutableLiveData<Boolean>().default(false)       //true
    val parkingDesc = MutableLiveData<String>().default("")
    val courseDesc = MutableLiveData<String>().default("")
    val image = MutableLiveData<ArrayList<MultipartBody.Part>>()
    val imageUri = MutableLiveData<MutableList<WriteImgInfo>>()

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