package com.charo.android.presentation.ui.write

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.charo.android.data.model.write.WriteImgInfo
import okhttp3.MultipartBody
import okhttp3.RequestBody

class WriteSharedViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }

    //WriteFragment
    val title = MutableLiveData<String>().default("")
    val province = MutableLiveData<String>().default("")            //경기도
    val region = MutableLiveData<String>().default("")              //수원
    val warning = MutableLiveData<ArrayList<MultipartBody.Part>>()
    val warningUI = MutableLiveData<ArrayList<String>>()            //["highway", "mountainRoad"]
    val theme = MutableLiveData<ArrayList<String>>()                          //["summer", "sea"]
    val isParking = MutableLiveData<Boolean>().default(false)       //true
    val parkingDesc = MutableLiveData<String>().default("")
    val courseDesc = MutableLiveData<String>().default("")
    val imageMultiPart = MutableLiveData<ArrayList<MultipartBody.Part>>()       //서버에 보내기
    val imageUriRecyclerView = MutableLiveData<MutableList<WriteImgInfo>>()     //뷰에 보여주기

    //writeMapFragment
    val locationFlag = MutableLiveData<String>() //출발 경유 도착
    val latitude = MutableLiveData<Double>().default(0.0)     //위도
    val longitude = MutableLiveData<Double>().default(0.0)    //경도

    val course = MutableLiveData<ArrayList<MultipartBody.Part>>()   //서버에 보낼 경로
    var startAddress = MutableLiveData<String>().default("")
    var midFrstAddress = MutableLiveData<String>().default("")
    var midSecAddress = MutableLiveData<String>().default("")
    var endAddress = MutableLiveData<String>().default("")
    var startLat = MutableLiveData<Double>().default(0.0)
    var startLong = MutableLiveData<Double>().default(0.0)
    var midFrstLat = MutableLiveData<Double>().default(0.0)
    var midFrstLong = MutableLiveData<Double>().default(0.0)
    var midSecLat = MutableLiveData<Double>().default(0.0)
    var midSecLong = MutableLiveData<Double>().default(0.0)
    var endLat = MutableLiveData<Double>().default(0.0)
    var endLong = MutableLiveData<Double>().default(0.0)

    fun initData() {
        title.value = ""
        province.value = ""            //경기도
        region.value = ""            //수원
        warning.value = ArrayList<MultipartBody.Part>()                          //["highway", "mountainRoad"]
        theme.value = ArrayList<String>()                            //["summer", "sea"]
        isParking.value = false      //true
        parkingDesc.value = ""
        courseDesc.value = ""
        imageMultiPart.value = ArrayList<MultipartBody.Part>()

        //writeMapFragment
        locationFlag.value = "" //출발 경유 도착
        latitude.value = 0.0     //위도
        longitude.value = 0.0    //경도

        startAddress.value = ""
        midFrstAddress.value = ""
        midSecAddress.value = ""
        endAddress.value = ""
        startLat.value = 0.0
        startLong.value = 0.0
        midFrstLat.value = 0.0
        midFrstLong.value = 0.0
        midSecLat.value = 0.0
        midSecLong.value = 0.0
        endLat.value = 0.0
        endLong.value = 0.0

    }
}