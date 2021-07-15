package com.example.charo_android.ui.write

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.charo_android.MainActivity
import com.example.charo_android.R
import com.example.charo_android.databinding.ActivityWriteMapBinding
import com.example.charo_android.hidden.Hidden
import com.skt.Tmap.*
import kotlinx.android.synthetic.main.activity_write_map.*
import java.util.*
import java.lang.Exception

class WriteMapActivity : AppCompatActivity() {

    // 좌표 상수
    var markerCount = 0
    var pathMarkerCount = 0
    var path = arrayListOf<TMapPoint>()

    private lateinit var binding: ActivityWriteMapBinding

    private lateinit var locationFlag: String
    private lateinit var textview: String
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    private var mapData = WriteMapPointData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationFlag = intent.getStringExtra("locationFlag").toString()
        Log.d("locationFlag", locationFlag)
        textview = intent.getStringExtra("textview").toString()
        Log.d("textview", textview)
        latitude = intent.getDoubleExtra("pointLat", 0.0)
        Log.d("latitude", latitude.toString())
        longitude = intent.getDoubleExtra("pointLong", 0.0)
        Log.d("longitude", longitude.toString())

        // 뒤로가기 image click하면 뒤로 가짐
        binding.imgWriteMapBack.setOnClickListener {
            mapData.startAddress = ""
            mapData.mid1Address = ""
            mapData.mid2Address = ""
            mapData.endAddress = ""
            mapData.startLat = 0.0
            mapData.startLong = 0.0
            mapData.mid1Lat = 0.0
            mapData.mid1Long = 0.0
            mapData.mid2Lat = 0.0
            mapData.mid2Long = 0.0
            mapData.endLat = 0.0
            mapData.endLong = 0.0

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("userId", "and")
            startActivity(intent)
        }

        // 출발지 누르면 검색창으로 감
        binding.etWriteMapStart.setOnClickListener {
            val intent = Intent(applicationContext, WriteMapSearchActivity::class.java)
            intent.putExtra("locationM", "출발지를 입력하세요")
            intent.putExtra("locationFlag", "1")
            startActivity(intent)
        }

        // 경유지1 누르면 검색창으로 감
        binding.etWriteMapMid1.setOnClickListener {
            val intent = Intent(applicationContext, WriteMapSearchActivity::class.java)
            intent.putExtra("locationM", "경유지1를 입력하세요")
            intent.putExtra("locationFlag", "2")
            startActivity(intent)
        }
        // 경유지2 누르면 검색창으로 감
        binding.etWriteMapMid2.setOnClickListener {
            val intent = Intent(applicationContext, WriteMapSearchActivity::class.java)
            intent.putExtra("locationM", "경유지2를 입력하세요")
            intent.putExtra("locationFlag", "3")
            startActivity(intent)
        }
        // 도착지 누르면 검색창으로 감
        binding.etWriteMapEnd.setOnClickListener {
            val intent = Intent(applicationContext, WriteMapSearchActivity::class.java)
            intent.putExtra("locationM", "도착지를 입력하세요")
            intent.putExtra("locationFlag", "4")
            startActivity(intent)
        }

        val tMapView = TMapView(this@WriteMapActivity)

        /*************커밋 푸시 머지할 때 키 삭제************/
        tMapView.setSKTMapApiKey(Hidden().tMapApiKey)
        binding.clWriteTmapView.addView(tMapView)

        fillTextView(locationFlag, textview, latitude, longitude, tMapView)
    }

    private fun fillTextView(
        locationFlag: String,
        textview: String,
        latitude: Double,
        longitude: Double,
        tMapView: TMapView
    ) {
        if (locationFlag == "1") { // 출발지인 경우
            mapData.startAddress = textview
            mapData.startLat = latitude
            mapData.startLong = longitude

            binding.etWriteMapStart.text = mapData.startAddress
            binding.etWriteMapMid1.text = mapData.mid1Address
            binding.etWriteMapMid2.text = mapData.mid2Address
            binding.etWriteMapEnd.text = mapData.endAddress

            if(mapData.mid1Address != "") {
                binding.etWriteMapMid1.visibility = View.VISIBLE
                binding.imgWriteMapDelete1.visibility = View.VISIBLE
            }
            if(mapData.mid2Address != ""){
                binding.etWriteMapMid2.visibility = View.VISIBLE
                binding.imgWriteMapDelete2.visibility = View.VISIBLE
            }
        } else if (locationFlag == "2") { // 경유지1인 경우
            binding.etWriteMapMid1.visibility = View.VISIBLE
            binding.imgWriteMapDelete1.visibility = View.VISIBLE

            mapData.mid1Address = textview
            mapData.mid1Lat = latitude
            mapData.mid1Long = longitude

            binding.etWriteMapStart.text = mapData.startAddress
            binding.etWriteMapMid1.text = mapData.mid1Address
            binding.etWriteMapMid2.text = mapData.mid2Address
            binding.etWriteMapEnd.text = mapData.endAddress

            if(mapData.mid1Address != "") {
                binding.etWriteMapMid1.visibility = View.VISIBLE
                binding.imgWriteMapDelete1.visibility = View.VISIBLE
            }
            if(mapData.mid2Address != ""){
                binding.etWriteMapMid2.visibility = View.VISIBLE
                binding.imgWriteMapDelete2.visibility = View.VISIBLE
            }
        } else if (locationFlag == "3") { // 경유지2인 경우
            binding.etWriteMapMid1.visibility = View.VISIBLE
            binding.imgWriteMapDelete1.visibility = View.VISIBLE
            binding.etWriteMapMid2.visibility = View.VISIBLE
            binding.imgWriteMapDelete2.visibility = View.VISIBLE

            mapData.mid2Address = textview
            mapData.mid2Lat = latitude
            mapData.mid2Long = longitude

            binding.etWriteMapStart.text = mapData.startAddress
            binding.etWriteMapMid1.text = mapData.mid1Address
            binding.etWriteMapMid2.text = mapData.mid2Address
            binding.etWriteMapEnd.text = mapData.endAddress

            if(mapData.mid1Address != "") {
                binding.etWriteMapMid1.visibility = View.VISIBLE
                binding.imgWriteMapDelete1.visibility = View.VISIBLE
            }
            if(mapData.mid2Address != ""){
                binding.etWriteMapMid2.visibility = View.VISIBLE
                binding.imgWriteMapDelete2.visibility = View.VISIBLE
            }
        } else if (locationFlag == "4") { // 도착지인 경우
            mapData.endAddress = textview
            mapData.endLat = latitude
            mapData.endLong = longitude

            binding.etWriteMapStart.text = mapData.startAddress
            binding.etWriteMapMid1.text = mapData.mid1Address
            binding.etWriteMapMid2.text = mapData.mid2Address
            binding.etWriteMapEnd.text = mapData.endAddress

            if(mapData.mid1Address != "") {
                binding.etWriteMapMid1.visibility = View.VISIBLE
                binding.imgWriteMapDelete1.visibility = View.VISIBLE
            }
            if(mapData.mid2Address != ""){
                binding.etWriteMapMid2.visibility = View.VISIBLE
                binding.imgWriteMapDelete2.visibility = View.VISIBLE
            }
        }

        imgWriteMapAddAddressOnClickEvent()
        imgWriteMapDelete1OnClickEvent(tMapView)
        imgWriteMapDelete2OnClickEvent(tMapView)
        addList(tMapView)
    }

    private fun imgWriteMapAddAddressOnClickEvent() {
        binding.imgWriteMapAddAdress.setOnClickListener() {
            if(binding.etWriteMapMid1.visibility == View.GONE) {
                binding.etWriteMapMid1.visibility = View.VISIBLE
                binding.imgWriteMapDelete1.visibility = View.VISIBLE

                binding.etWriteMapMid1.text = mapData.mid1Address
            } else {
                binding.etWriteMapMid2.visibility = View.VISIBLE
                binding.imgWriteMapDelete2.visibility = View.VISIBLE

                binding.etWriteMapMid2.text = mapData.mid2Address
            }
        }
    }

    private fun imgWriteMapDelete1OnClickEvent(tMapView: TMapView) {
        binding.imgWriteMapDelete1.setOnClickListener() {
            if (mapData.mid2Address != "") {
                binding.etWriteMapMid2.visibility = View.GONE
                binding.imgWriteMapDelete2.visibility = View.GONE

                mapData.mid1Address = mapData.mid2Address
                mapData.mid1Lat = mapData.mid2Lat
                mapData.mid1Long = mapData.mid2Long

                mapData.mid2Address = ""
                mapData.mid2Lat = 0.0
                mapData.mid2Long = 0.0

                binding.etWriteMapMid1.text = mapData.mid1Address
            } else {
                binding.etWriteMapMid1.visibility = View.GONE
                binding.imgWriteMapDelete1.visibility = View.GONE

                mapData.mid1Address = ""
                mapData.mid1Lat = 0.0
                mapData.mid1Long = 0.0
            }
            path.clear()
            addList(tMapView)
        }
    }

    private fun imgWriteMapDelete2OnClickEvent(tMapView: TMapView) {
        binding.imgWriteMapDelete2.setOnClickListener() {
            binding.etWriteMapMid2.visibility = View.GONE
            binding.imgWriteMapDelete2.visibility = View.GONE

            mapData.mid2Address = ""
            mapData.mid2Lat = 0.0
            mapData.mid2Long = 0.0

            path.clear()
            addList(tMapView)
        }
    }

    private fun addList(tMapView: TMapView) {
        if (mapData.startLat != 0.0) {
            path.add(TMapPoint(mapData.startLat, mapData.startLong))
        }
        if(mapData.mid1Lat != 0.0) {
            path.add(TMapPoint(mapData.mid1Lat, mapData.mid1Long))
        }
        if(mapData.mid2Lat != 0.0) {
            path.add(TMapPoint(mapData.mid2Lat, mapData.mid2Long))
        }
        if(mapData.endLat != 0.0) {
            path.add(TMapPoint(mapData.endLat, mapData.endLong))
        }

        if(path.isNotEmpty()){
            setCenter(tMapView)
        }
        tMapView.removeAllTMapPolyLine()
        mark(tMapView)
    }

    private fun setCenter(tMapView: TMapView) {
        Handler(Looper.getMainLooper()).postDelayed({
            val info: TMapInfo = tMapView.getDisplayTMapInfo(path)
            tMapView.setCenterPoint(info.tMapPoint.longitude, info.tMapPoint.latitude)
            tMapView.zoomLevel = info.tMapZoomLevel - 1
        }, 500)
    }

    private fun mark(tMapView: TMapView) {
        for(i in 0 until path.size) {
            val marker = TMapMarkerItem()
            val mapPoint = path[i]
            var bitmap: Bitmap = if (i == 0) {
                BitmapFactory.decodeResource(resources, R.drawable.ic_route_start)
            } else if (i == path.size - 1) {
                BitmapFactory.decodeResource(resources, R.drawable.ic_route_end)
            } else {
                BitmapFactory.decodeResource(resources, R.drawable.ic_route_waypoint)
            }
            marker.icon = bitmap
            marker.setPosition(0.5F, 1.0F)
            marker.tMapPoint = mapPoint
            marker.name = "marker$i"
            tMapView.addMarkerItem(marker.name, marker)
        }

        if(path.size > 1){
            findPath(tMapView)
        }
    }

    private fun findPath(tMapView: TMapView){
        for(i in 0 until path.size - 1) {
            var flag: Boolean = true
            if(i != 0 && i % 2 == 0){
                flag = !flag
            }
            drawPath(tMapView, path[i], path[i+1], i, flag)
        }
    }

    private fun drawPath(
        tMapView: TMapView,
        start: TMapPoint,
        end: TMapPoint,
        cnt: Int,
        flag: Boolean
    ) {


        val thread: Thread = Thread() {
            try {
                val tMapPolyLine: TMapPolyLine = TMapData().findPathData(start, end)
                tMapPolyLine.lineWidth = 3F
                tMapPolyLine.lineColor = getColor(R.color.blue_main)
                tMapView.addTMapPolyLine("tMapPolyLine$cnt", tMapPolyLine)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()

        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (!flag) {
            Thread.sleep(1000)
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }
}