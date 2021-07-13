package com.example.charo_android.ui.write

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WriteViewModel : ViewModel() {

//    private val _text = MutableLiveData<String>().apply {
//        value = "This is write Fragment"
//    }
//    val text: LiveData<String> = _text


    private val _text = MutableLiveData<String>().apply {
//        when(data) {
//            0 ->  value = "출발지"
//            1 ->  value = "경유지1"
//            2 ->  value = "경유지2"
//            3 ->  value = "도착지"
//        }
        value
      //  value = "This is write Fragment"
    }
    val text: LiveData<String> = _text



//    var location = MutableLiveData<String>()
//    lateinit var text : String

//    init{
//        location.value = "출발지"
//        //0 start, 1 mid1, 2 mid2, 3 end
//        Log.d("location", location.value.toString())
//        }
//
//    fun setLocation(data : Int) : String{
//        when(data) {
//            0 ->  location.value = "출발지"
//            1 ->  location.value = "경유지1"
//            2 ->  location.value = "경유지2"
//            3 ->  location.value = "도착지"
//        }
//        text = location.value + "로 설정"
//        Log.d("location", location.value.toString())
//        return location.value.toString()
//    }
}