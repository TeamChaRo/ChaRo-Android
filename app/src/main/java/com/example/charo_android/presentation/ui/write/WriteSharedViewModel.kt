package com.example.charo_android.presentation.ui.write

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.charo_android.data.WriteImgInfo
import com.example.charo_android.data.api.ApiService
import com.example.charo_android.presentation.util.ThemeUtil
import com.example.charo_android.presentation.util.enqueueUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody


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
    val warning = MutableLiveData<ArrayList<MultipartBody.Part>>()                             //["highway", "mountainRoad"]
    val theme = MutableLiveData<ArrayList<String>>()                               //["summer", "sea"]
    val isParking = MutableLiveData<Boolean>().default(false)       //true
    val parkingDesc = MutableLiveData<String>().default("")
    val courseDesc = MutableLiveData<String>().default("")
    val imageMultiPart = MutableLiveData<ArrayList<MultipartBody.Part>>()
    val imageUriRecyclerView = MutableLiveData<MutableList<WriteImgInfo>>()

    //writeMapFragment
    val locationFlag = MutableLiveData<String>() //출발 경유 도착
    val latitude = MutableLiveData<Double>().default(0.0)     //위도
    val longitude = MutableLiveData<Double>().default(0.0)    //경도

    val course = MutableLiveData<ArrayList<HashMap<String, String>>>()    //서버에 보낼 경로
//    val course = MutableLiveData<ArrayList<HashMap<String, RequestBody>>>()    //서버에 보낼 경로
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
        val themeUtil = ThemeUtil()

        for(sharedTheme in theme.value!!){
            themeUtil.themeMap[sharedTheme]?.let { it1 ->
                sendTheme.add(MultipartBody.Part.createFormData("theme",it1))
            }
        }

        val userEmailRB : RequestBody = userEmail.value!!.toRequestBody("text/plain".toMediaTypeOrNull())
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
        userEmail.value = "and@naver.com"
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