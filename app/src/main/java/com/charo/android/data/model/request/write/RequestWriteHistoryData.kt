package com.charo.android.data.model.request.write

import com.google.gson.annotations.SerializedName

data class RequestWriteHistoryData(
    @SerializedName("userEmail")
    val userEmail : String,
    @SerializedName("searchHistory")
    val searchHistory : ArrayList<SearchHistory>
){
  data class SearchHistory(
      @SerializedName("title")
      val title : String,
      @SerializedName("address")
      val address : String,
      @SerializedName("latitude")
      val latitude : Double,
      @SerializedName("longitude")
      val longitude : Double,
  )
}
