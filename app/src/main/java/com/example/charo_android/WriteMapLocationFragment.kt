package com.example.charo_android

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.charo_android.databinding.FragmentWriteMapLocationBinding
import com.example.charo_android.hidden.Hidden
import com.skt.Tmap.TMapAddressInfo
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapView

class WriteMapLocationFragment : Fragment() {

    private lateinit var locationAddress: String
    private lateinit var locationName: String
    private lateinit var locationFlag: String
    private lateinit var address: TMapAddressInfo
    private lateinit var userId: String
    private lateinit var nickName: String
    private var lat = 0.0
    private var lon = 0.0

    val markerCount = 1

    companion object {
        fun newInstance() = WriteMapLocationFragment()
    }
    private var _binding: FragmentWriteMapLocationBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: WriteMapLocationViewModel

    var writeShareActivity: WriteShareActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        writeShareActivity = context as WriteShareActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentWriteMapLocationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        Log.d("Created WriteMapLocationFragment","jjjjjjjj")


//        userId = intent.getStringExtraxtra("userId").toString()
//        nickName = intent.getStringExtra("nickName").toString()
//
//        locationFlag = intent.getStringExtra("locationFlag").toString()

        binding.imgWriteMapLocationBack.setOnClickListener {
            writeShareActivity?.onBackPressed()
        }



//        var resultLocation = intent.getStringExtra("text").toString()

//        Log.d("writeMapLocationActivity", intent.getStringExtra("text").toString())
//        binding.btnSetLocation.text = resultLocation

        val tMapView = TMapView(context)

        /*************커밋 푸시 머지할 때 키 삭제************/
        tMapView.setSKTMapApiKey(Hidden().tMapApiKey)
        binding.clWriteMapLocationView.addView(tMapView)

//        locationName = intent.getStringExtra("locationName").toString()
//        locationAddress = intent.getStringExtra("locationAddress").toString()

        val tmapdata = TMapData()
//        if (locationFlag == "1") {
//            tmapdata.findAllPOI(locationName) { poiItem ->
//                Log.d("poi", poiItem[0].poiPoint.toString())
//                val marker = TMapMarkerItem()
//                val mapPoint = poiItem[0].poiPoint
//                val bitmap: Bitmap =
//                    BitmapFactory.decodeResource(resources, R.drawable.ic_route_start)
//                marker.icon = bitmap
//                marker.setPosition(0.5F, 1.0F)
//                marker.tMapPoint = mapPoint
//                marker.name = "marker${locationFlag}"
//                tMapView.addMarkerItem(marker.name, marker)
//            }
//        } else if (locationFlag == "4") {
//            tmapdata.findAllPOI(locationName) { poiItem ->
//                Log.d("poi", poiItem[0].poiPoint.toString())
//                val marker = TMapMarkerItem()
//                val mapPoint = poiItem[0].poiPoint
//                val bitmap: Bitmap =
//                    BitmapFactory.decodeResource(resources, R.drawable.ic_route_end)
//                marker.icon = bitmap
//                marker.setPosition(0.5F, 1.0F)
//                marker.tMapPoint = mapPoint
//                marker.name = "marker${locationFlag}"
//                tMapView.addMarkerItem(marker.name, marker)
//            }
//        } else {
//            tmapdata.findAllPOI(locationName) { poiItem ->
//                Log.d("poi", poiItem[0].poiPoint.toString())
//                val marker = TMapMarkerItem()
//                val mapPoint = poiItem[0].poiPoint
//                val bitmap: Bitmap =
//                    BitmapFactory.decodeResource(resources, R.drawable.ic_route_waypoint)
//                marker.icon = bitmap
//                marker.setPosition(0.5F, 1.0F)
//                marker.tMapPoint = mapPoint
//                marker.name = "marker${locationFlag}"
//                tMapView.addMarkerItem(marker.name, marker)
//            }
//        }

        //    var location = intent.getStringExtra("location")

//        binding.textLocationName.text = locationName
//        binding.textLocationAddress.text = locationAddress
//
//        tmapdata.findAllPOI(locationName) { poiItem ->
//            Log.d("poi", poiItem[0].poiPoint.toString())
//
//            var tmapPointCurrentSpot = poiItem[0].poiPoint
//            if (poiItem[0].upperAddrName != null) {
//                locationAddress = poiItem[0].upperAddrName
//                Log.d("address upper", poiItem[0].upperAddrName)
//            }
//            if (poiItem[0].middleAddrName != null) {
//                locationAddress += " " + poiItem[0].middleAddrName
//                Log.d("address middle", poiItem[0].middleAddrName)
//            }
//            if (poiItem[0].lowerAddrName != null) {
//                locationAddress += " " + poiItem[0].lowerAddrName
//                Log.d("address lower", poiItem[0].lowerAddrName)
//            }
//            if (poiItem[0].detailAddrName != null) {
//                locationAddress += " " + poiItem[0].detailAddrName
//                Log.d("address detail", poiItem[0].detailAddrName)
//            }
//            if (poiItem[0].firstNo != null) {
//                locationAddress += " " + poiItem[0].firstNo
//                Log.d("address firstNo", poiItem[0].firstNo)
//            }
//            if (poiItem[0].secondNo != null && poiItem[0].secondNo != "0") {
//                locationAddress += " " + poiItem[0].secondNo
//                Log.d("address secondNo", poiItem[0].secondNo)
//            }
//            tMapView.setCenterPoint(tmapPointCurrentSpot.longitude, tmapPointCurrentSpot.latitude)
////            binding.textLocationAddress.text = locationAddress
//            lat = tmapPointCurrentSpot.latitude
//            lon = tmapPointCurrentSpot.longitude
//            address = tmapdata.reverseGeocoding(lat, lon, "A10")
//        }

//        val tmr = timer(period = 1000, initialDelay = 0) {
//            lat = tMapView.centerPoint.latitude
//            lon = tMapView.centerPoint.longitude
//            if (tmapdata.reverseGeocoding(lat, lon, "A00") != null) {
//                tmapdata.findAllPOI(
//                    tmapdata.reverseGeocoding(
//                        lat,
//                        lon,
//                        "A00"
//                    ).strFullAddress
//                ) { poiItem ->
//                    if(poiItem.size != 0) {
//                        locationAddress = poiItem[0].name
//                    }
//                }
//                if (tmapdata.reverseGeocoding(lat, lon, "A10").strRoadName != null) {
//                    locationName = tmapdata.reverseGeocoding(lat, lon, "A10").strRoadName
//                    Log.d("myLog", locationName)
//                }
//                if (tmapdata.reverseGeocoding(lat, lon, "A10").strBuildingName != null) {
//                    locationName = tmapdata.reverseGeocoding(lat, lon, "A10").strBuildingName
//                    Log.d("myLog", locationName)
//                }
//            }
//            runOnUiThread {
//                setLocationInfo()
//            }
//        }

//        binding.btnSetLocation.setOnClickListener {
////            tmr.cancel()
//            lat = tMapView.centerPoint.latitude
//            lon = tMapView.centerPoint.longitude
//
//            Log.d("test lat", lat.toString())
//            Log.d("test lon", lon.toString())
//
////            val intent = Intent(this, WriteMapActivity::class.java)
////            intent.putExtra("textview", locationAddress)
////                .putExtra("pointLong", lon)
////                .putExtra("pointLat", lat)
////                .putExtra("locationFlag", locationFlag)
////                .putExtra("userId", userId)
////                .putExtra("nickName",nickName)
////
////            startActivity(intent)
//        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WriteMapLocationViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun setLocationInfo() {
        binding.textLocationAddress.text = locationAddress
        binding.textLocationName.text = locationName
    }

}