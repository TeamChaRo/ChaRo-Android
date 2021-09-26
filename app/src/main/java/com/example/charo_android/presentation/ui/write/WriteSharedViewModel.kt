package com.example.charo_android.presentation.ui.write

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.charo_android.data.WriteImgInfo
import okhttp3.MultipartBody
import retrofit2.http.Multipart

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

    val course = MutableLiveData<Any>()    //서버에 보낼 경로
    var startAddress = MutableLiveData<String>().default("")
    var mid1Address = MutableLiveData<String>().default("")
    var mid2Address = MutableLiveData<String>().default("")
    var endAddress = MutableLiveData<String>().default("")
    var startLat = MutableLiveData<Double>().default(0.0)
    var startLong = MutableLiveData<Double>().default(0.0)
    var mid1Lat = MutableLiveData<Double>().default(0.0)
    var mid1Long = MutableLiveData<Double>().default(0.0)
    var mid2Lat = MutableLiveData<Double>().default(0.0)
    var mid2Long = MutableLiveData<Double>().default(0.0)
    var endLat = MutableLiveData<Double>().default(0.0)
    var endLong = MutableLiveData<Double>().default(0.0)

    //writeMapLocationFragment
    val locationName = MutableLiveData<String>().default("")
    val locationAddress = MutableLiveData<String>().default("")
    val resultLocation = MutableLiveData<String>().default("")


//    private val _userId = MutableLiveData<String>()
//    val userId: LiveData<String> get() = _userId


    fun initData() {
        userEmail.value = "and@naver.com"
        title.value = ""
        province.value = ""            //경기도
        region.value = ""            //수원
        warning.value = ArrayList<String>()                          //["highway", "mountainRoad"]
        theme.value = ArrayList<String>()                            //["summer", "sea"]
        isParking.value = false      //true
        parkingDesc.value = ""
        courseDesc.value = ""
        image.value = ArrayList<MultipartBody.Part>()

        //writeMapFragment
        locationFlag.value = "" //출발 경유 도착
        latitude.value = 0.0     //위도
        longitude.value = 0.0    //경도

        startAddress.value = ""
        mid1Address.value = ""
        mid2Address.value = ""
        endAddress.value = ""
        startLat.value = 0.0
        startLong.value = 0.0
        mid1Lat.value = 0.0
        mid1Long.value = 0.0
        mid2Lat.value = 0.0
        mid2Long.value = 0.0
        endLat.value = 0.0
        endLong.value = 0.0

        //writeMapLocationFragment
        locationName.value = ""
        locationAddress.value = ""
        resultLocation.value = ""
    }
}