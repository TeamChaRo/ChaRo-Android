package com.example.charo_android.presentation.ui.write

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.charo_android.data.WriteImgInfo
import com.example.charo_android.data.api.ApiService
import com.example.charo_android.hidden.Hidden
import com.example.charo_android.presentation.util.enqueueUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody


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

    val course = MutableLiveData<ArrayList<HashMap<String, String>>>()    //서버에 보낼 경로
//    val course = MutableLiveData<ArrayList<HashMap<String, RequestBody>>>()    //서버에 보낼 경로
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

    fun serveWriteData() {
        val param : HashMap<String, RequestBody> = HashMap()

        val courseParam : HashMap<String, ArrayList<HashMap<String,RequestBody>>> = HashMap()
//        val sendCourse : ArrayList<MultipartBody.Part> = ArrayList()
//        val startCourse : HashMap<String, String> = hashMapOf<String, String>("address" to "출발",
//            "longtitude" to "37.54612327",
//            "latitude" to "126.98049364")
//
//        sendCourse.add(MultipartBody.Part.createFormData("course",startCourse))
//

        val sendTheme : ArrayList<MultipartBody.Part> = ArrayList()
        for(sharedTheme in theme.value!!){
            sendTheme.add(MultipartBody.Part.createFormData("theme",sharedTheme))
        }

        val userEmailRB : RequestBody = Hidden.userEmail.toRequestBody("text/plain".toMediaTypeOrNull())
        val titleRB : RequestBody = title.value!!.toRequestBody("text/plain".toMediaTypeOrNull())
        val provinceRB : RequestBody = province.value!!.toRequestBody("text/plain".toMediaTypeOrNull())
        val regionRB : RequestBody = region.value!!.toRequestBody("text/plain".toMediaTypeOrNull())
        val parkingDescRB : RequestBody = parkingDesc.value!!.toRequestBody("text/plain".toMediaTypeOrNull())
        val courseDescRB : RequestBody = courseDesc.value!!.toRequestBody("text/plain".toMediaTypeOrNull())

        param["userEmail"] = userEmailRB
        param["title"] = titleRB
        param["province"] = provinceRB
        param["region"] = regionRB
        param["parkingDesc"] = parkingDescRB
        param["courseDesc"] = courseDescRB
//        courseParam["course"] = course.value

        Log.d("param", ""+warning.value + sendTheme + isParking.value + course.value + imageMultiPart.value + param)
        Log.d("from", "serveWriteData")
        val call = ApiService.writeViewService
            .writePost(warning.value, sendTheme, isParking.value, course.value, imageMultiPart.value, param
        )
        call.enqueueUtil(
            onSuccess = {
                Log.d("serveWriteData", "success")
                Log.d("serveWriteData", it.toString())
            },
            onError = {
                Log.d("serveWriteData", "failed")
                Log.d("serveWriteData",param.toString())
                Log.d("serveWriteData",warning.value.toString())
                Log.d("serveWriteData",sendTheme.toString())
                Log.d("serveWriteData",isParking.value.toString())
                Log.d("serveWriteData",course.value.toString())
            },
        )
    }

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