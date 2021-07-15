package com.example.charo_android.api

import java.io.File
import java.nio.file.Files

data class RequestWriteData (
    val title : String,
    val userId : String,
    val province : String,
    val region : String,
    val theme : List<String>,
    val warning : List<Boolean>,
    val isParking : Boolean,
    val parkingDesc : String,
    val courseDesc : String,
    val course : List<Course>,
    val image : File,
    ){
    data class Course(
        val address : List<String>,
        val latitude : List<String>,
        val longtitude : List<String>,
    )
}