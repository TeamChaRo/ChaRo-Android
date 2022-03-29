package com.example.charo_android.data.model.request.write

data class RequestWriteHistoryData(
    val userEmail : String,
    val searchHistory : ArrayList<SearchHistory>
){
  data class SearchHistory(
      val title : String,
      val address : String,
      val latitude : Double,
      val longitude : Double,
  )
}
