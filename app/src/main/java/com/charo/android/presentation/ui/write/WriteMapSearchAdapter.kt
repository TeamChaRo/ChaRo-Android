package com.charo.android.presentation.ui.write

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.charo_android.data.model.write.MapSearchInfo
import com.example.charo_android.data.model.request.write.RequestWriteHistoryData
import com.example.charo_android.data.model.response.ResponseStatusCode
import com.example.charo_android.databinding.ItemAutoSearchBinding
import com.example.charo_android.presentation.util.SharedInformation
import com.skt.Tmap.TMapData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WriteMapSearchAdapter(
//    val locationFlag: String, val text: String, val userId: String, val nickName: String
) : RecyclerView.Adapter<WriteMapSearchAdapter.MapSearchViewHolder>() {

    var userList = mutableListOf<MapSearchInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapSearchViewHolder {
        val binding = ItemAutoSearchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return MapSearchViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: MapSearchViewHolder, position: Int) {
        holder.onBind(userList[position])

        var writeShareActivity = holder.itemView.context as WriteShareActivity
        var sharedViewModel = ViewModelProvider(writeShareActivity).get(WriteSharedViewModel::class.java)

        holder.itemView.setOnClickListener {
            var lat = 0.0
            var lon = 0.0
            val tmapdata = TMapData()
            tmapdata.findAllPOI(userList[position].locationName.toString()) { poiItem ->
                var tmapPointCurrentSpot = poiItem[0].poiPoint

                lat = tmapPointCurrentSpot.latitude
                lon = tmapPointCurrentSpot.longitude
            }
            //검색어 저장
            postSaveHistory(userList[position].locationName.toString(), userList[position].locationAddress
                , lat, lon, writeShareActivity
            )

            val locationName = userList[position].locationName.toString()
            val locationAddress = userList[position].locationAddress

            writeShareActivity.replaceAddStackFragment(WriteMapLocationFragment.newInstance(locationName, locationAddress), "writeMapLocation")

        }
    }

    class MapSearchViewHolder(
        private val binding: ItemAutoSearchBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(mapSearchInfo: MapSearchInfo) {
            binding.mapData = mapSearchInfo
        }
    }

    fun clearItem(){
        userList.clear()
    }

    private fun postSaveHistory(title: String, address: String, latitude: Double, longitude: Double, context: Context){

        val searchHistory : ArrayList<RequestWriteHistoryData.SearchHistory> = ArrayList()
        searchHistory.add(RequestWriteHistoryData.SearchHistory(title, address, latitude, longitude))

        val userEmail = SharedInformation.getEmail(context)
        val requestWriteHistoryData: RequestWriteHistoryData
            = RequestWriteHistoryData(userEmail, searchHistory)

        val call: Call<ResponseStatusCode> =
            com.charo.android.data.api.ApiService.writeViewService.postSaveHistory(requestWriteHistoryData)
        call.enqueue(object : Callback<ResponseStatusCode> {
            override fun onResponse(
                call: Call<ResponseStatusCode>,
                response: Response<ResponseStatusCode>
            ) {
                if (response.isSuccessful) {
                    Log.d("server connect : WriteMap search save", "success ${response.body()?.success}")
                    Log.d("server connect : WriteMap search save", "${response.body()?.msg}")

                } else {
                    Log.d("server connect : WriteMap search save", "error")
                    Log.d("server connect : WriteMap search save", "$response.errorBody()")
                    Log.d("server connect : WriteMap search save", response.message())
                    Log.d("server connect : WriteMap search save", "${response.code()}")
                    Log.d(
                        "server connect : WriteMap search save",
                        "${response.raw().request.url}"
                    )
                }
            }

            override fun onFailure(call: Call<ResponseStatusCode>, t: Throwable) {
                Log.d("server connect : WriteMap search save", "error: ${t.message}")
            }
        })
    }
}
