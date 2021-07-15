package com.example.charo_android.ui.write

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.example.charo_android.R
import com.example.charo_android.databinding.ActivityWriteMapLocationBinding
import com.example.charo_android.hidden.Hidden
import com.skt.Tmap.*
import kotlin.concurrent.timer
import java.lang.Exception
import kotlin.concurrent.timerTask

class WriteMapLocationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWriteMapLocationBinding
    private val viewModel: WriteViewModel by viewModels()
    private lateinit var locationAddress: String
    private lateinit var locationName: String
    private lateinit var locationFlag: String
    private lateinit var address: TMapAddressInfo
    private var lat = 0.0
    private var lon = 0.0

    val markerCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteMapLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationFlag = intent.getStringExtra("locationFlag").toString()

        binding.imgWriteMapLocationBack.setOnClickListener {
            onBackPressed()
        }

        var resultLocation = intent.getStringExtra("text").toString()

        Log.d("writeMapLocationActivity", intent.getStringExtra("text").toString())
        binding.btnSetLocation.text = resultLocation

        val tMapView = TMapView(this@WriteMapLocationActivity)

        /*************커밋 푸시 머지할 때 키 삭제************/
        tMapView.setSKTMapApiKey("")
        binding.clWriteMapLocationView.addView(tMapView)

        if (locationFlag == "1") {
            binding.imgWriteMapMarker.setImageResource(R.drawable.ic_route_start)
        } else if (locationFlag == "4") {
            binding.imgWriteMapMarker.setImageResource(R.drawable.ic_route_end)
        } else {
            binding.imgWriteMapMarker.setImageResource(R.drawable.ic_route_waypoint)
        }
        binding.imgWriteMapMarker.bringToFront()

        locationName = intent.getStringExtra("locationName").toString()
        locationAddress = intent.getStringExtra("locationAddress").toString()
        //    var location = intent.getStringExtra("location")

        binding.textLocationName.text = locationName
        binding.textLocationAddress.text = locationAddress

        val tmapdata = TMapData()
        tmapdata.findAllPOI(locationName) { poiItem ->
            Log.d("poi", poiItem[0].poiPoint.toString())

            var tmapPointCurrentSpot = poiItem[0].poiPoint
            if (poiItem[0].upperAddrName != null) {
                locationAddress = poiItem[0].upperAddrName
                Log.d("address upper", poiItem[0].upperAddrName)
            }
            if (poiItem[0].middleAddrName != null) {
                locationAddress += " " + poiItem[0].middleAddrName
                Log.d("address middle", poiItem[0].middleAddrName)
            }
            if (poiItem[0].lowerAddrName != null) {
                locationAddress += " " + poiItem[0].lowerAddrName
                Log.d("address lower", poiItem[0].lowerAddrName)
            }
            if (poiItem[0].detailAddrName != null) {
                locationAddress += " " + poiItem[0].detailAddrName
                Log.d("address detail", poiItem[0].detailAddrName)
            }
            if (poiItem[0].firstNo != null) {
                locationAddress += " " + poiItem[0].firstNo
                Log.d("address firstNo", poiItem[0].firstNo)
            }
            if (poiItem[0].secondNo != null && poiItem[0].secondNo != "0") {
                locationAddress += " " + poiItem[0].secondNo
                Log.d("address secondNo", poiItem[0].secondNo)
            }
            tMapView.setCenterPoint(tmapPointCurrentSpot.longitude, tmapPointCurrentSpot.latitude)
            lat = tmapPointCurrentSpot.latitude
            lon = tmapPointCurrentSpot.longitude
            address = tmapdata.reverseGeocoding(lat, lon, "A10")
        }

        val tmr = timer(period = 1000, initialDelay = 1000) {
            lat = tMapView.centerPoint.latitude
            lon = tMapView.centerPoint.longitude
            if (tmapdata.reverseGeocoding(lat, lon, "A00") != null) {
                tmapdata.findAllPOI(
                    tmapdata.reverseGeocoding(
                        lat,
                        lon,
                        "A00"
                    ).strFullAddress
                ) { poiItem ->
                    locationAddress = poiItem[0].name
                }
                if (tmapdata.reverseGeocoding(lat, lon, "A10").strRoadName != null) {
                    locationName = tmapdata.reverseGeocoding(lat, lon, "A10").strRoadName
                    Log.d("myLog", locationName)
                }
                if (tmapdata.reverseGeocoding(lat, lon, "A10").strBuildingName != null) {
                    locationName = tmapdata.reverseGeocoding(lat, lon, "A10").strBuildingName
                    Log.d("myLog", locationName)
                }
            }
            runOnUiThread {
                setLocationInfo()
            }
        }

        binding.btnSetLocation.setOnClickListener {
            tmr.cancel()
            lat = tMapView.centerPoint.latitude
            lon = tMapView.centerPoint.longitude

            Log.d("test lat", lat.toString())
            Log.d("test lon", lon.toString())
            Log.d("test addr", address.strFullAddress)

            val intent = Intent(this, WriteMapActivity::class.java)
            intent.putExtra("textview", locationAddress)
                .putExtra("pointLong", lon)
                .putExtra("pointLat", lat)
                .putExtra("locationFlag", locationFlag)

            startActivity(intent)
        }

    }

    private fun setLocationInfo() {
        binding.textLocationAddress.text = locationAddress
        binding.textLocationName.text = locationName
    }
}
