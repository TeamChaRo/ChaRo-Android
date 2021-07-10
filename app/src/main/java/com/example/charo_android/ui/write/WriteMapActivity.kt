package com.example.charo_android.ui.write

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.charo_android.R
import com.example.charo_android.databinding.ActivityWriteMapBinding
import com.skt.Tmap.*
import com.skt.Tmap.TMapView.OnClickListenerCallback
import java.util.*
import java.lang.Exception
import kotlin.concurrent.thread

class WriteMapActivity : AppCompatActivity() {

    // 좌표 상수
    val inha_univ_latitude = 37.450026
    val inha_univ_longitude = 126.651299
    val soraepogu_station_latitude = 37.400979
    val soraepogu_station_longitude = 126.731358
    val pangyo_station_latitude = 37.394780
    val pangyo_station_longitude = 127.108971
    val gangnam_station_latitude = 37.497161
    val gangnam_station_longitude = 127.025548
    var markerCount = 0
    var pathMarkerCount = 0
    val path = arrayListOf<TMapPoint>()

    private lateinit var binding: ActivityWriteMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgWriteMapBack.setOnClickListener {
            onBackPressed()

        }

        binding.etWriteMapStart.setOnClickListener {
            val intent = Intent(this@WriteMapActivity, WriteMapSearchActivity::class.java)
            startActivity(intent)

        }

        val tMapView = TMapView(this@WriteMapActivity)

        /*************커밋 푸시 머지할 때 키 삭제************/
        tMapView.setSKTMapApiKey("")
        binding.clWriteTmapView.addView(tMapView)


        // 강남역 마커
//        markGangnamStation(tMapView)

        // 지도 레벨 확대
  //      btnSizeUpOnClickEvent(tMapView)
        // 지도 레벨 축소
  //      btnSizeDownOnClickEvent(tMapView)

        // 자동차 경로안내
//        findRoadFromSoraepoguViaPangyoToGangnam(tMapView)

        // 현위치 마커
        btnMarkOnClickEvent(tMapView)

        // 직접 마커 찍어서 경로 탐색
        btnPathMarkOnClickEvent(tMapView)
        btnFindOnClickEvent(tMapView)
    }


    private fun markGangnamStation(tmapView: TMapView) {
        val marker_gangnam_station = TMapMarkerItem()
        val tmapPoint_gangnam_station =
            TMapPoint(gangnam_station_latitude, gangnam_station_longitude)
        val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.poi_dot)
        marker_gangnam_station.icon = bitmap
        marker_gangnam_station.setPosition(0.5F, 1.0F)
        marker_gangnam_station.tMapPoint = tmapPoint_gangnam_station
        marker_gangnam_station.name = "강남역"
        tmapView.addMarkerItem("marker_gangnam_station", marker_gangnam_station)
    }

//    private fun btnSizeUpOnClickEvent(tmapView: TMapView) {
//        binding.btnSizeUp.setOnClickListener() {
//            tmapView.MapZoomIn()
//        }
//    }
//
//    private fun btnSizeDownOnClickEvent(tmapView: TMapView) {
//        binding.btnSizeDown.setOnClickListener() {
//            tmapView.MapZoomOut()
//        }
//    }

    private fun findRoadFromSoraepoguViaPangyoToGangnam(tmapView: TMapView) {
        Thread() {
            try {
                val tMapPointStart =
                    TMapPoint(soraepogu_station_latitude, soraepogu_station_longitude)
                val tMapPointVia = TMapPoint(pangyo_station_latitude, pangyo_station_longitude)
                val tMapPointEnd = TMapPoint(gangnam_station_latitude, gangnam_station_longitude)
                val tMapPolyLineSV: TMapPolyLine =
                    TMapData().findPathData(tMapPointStart, tMapPointVia)
                tMapPolyLineSV.lineColor = Color.BLUE
                tMapPolyLineSV.lineWidth = 3F
                tmapView.addTMapPolyLine("tMapPolyLineSV", tMapPolyLineSV)
                val tMapPolyLineVE: TMapPolyLine =
                    TMapData().findPathData(tMapPointVia, tMapPointEnd)
                tMapPolyLineVE.lineColor = Color.BLUE
                tMapPolyLineVE.lineWidth = 3F
                tmapView.addTMapPolyLine("tMapPolyLineVE", tMapPolyLineVE)
                val arr = arrayListOf<TMapPoint>()
                arr.add(tMapPointStart)
                arr.add(tMapPointVia)
                arr.add(tMapPointEnd)
                val info: TMapInfo = tmapView.getDisplayTMapInfo(arr)
                tmapView.setCenterPoint(info.tMapPoint.longitude, info.tMapPoint.latitude)
                tmapView.zoomLevel = info.tMapZoomLevel
            } catch (e: Exception) {
                Log.d("error", e.printStackTrace().toString())
                e.printStackTrace()
            }
        }.start()
    }

    //마커 찍기
    private fun btnMarkOnClickEvent(tmapView: TMapView) {
        binding.btnMark.setOnClickListener() {
            val markerCurrentSpot = TMapMarkerItem()
            val tmapPointCurrentSpot = tmapView.centerPoint
            val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.poi_dot)
            markerCurrentSpot.icon = bitmap
            markerCurrentSpot.setPosition(0.5F, 1.0F)
            markerCurrentSpot.tMapPoint = tmapPointCurrentSpot
            markerCurrentSpot.name = "currentSpot$markerCount"
            tmapView.addMarkerItem("marker_current_spot$markerCount", markerCurrentSpot)
            markerCount++
        }
    }

    //마커 찍고 find 누르면 경로 생성
    private fun btnPathMarkOnClickEvent(tmapView: TMapView) {
        binding.btnPathMark.setOnClickListener() {
            val markerPathSpot = TMapMarkerItem()
            val tmapPointCurrentSpot = tmapView.centerPoint
            val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.poi_dot)
            markerPathSpot.icon = bitmap
            markerPathSpot.setPosition(0.5F, 1.0F)
            markerPathSpot.tMapPoint = tmapPointCurrentSpot
            markerPathSpot.name = "pathSpot$pathMarkerCount"
            tmapView.addMarkerItem("path_spot_$pathMarkerCount", markerPathSpot)
            pathMarkerCount++

            path.add(tmapPointCurrentSpot)
        }
    }

    //경로 연결
    private fun btnFindOnClickEvent(tmapView: TMapView) {
        binding.btnFind.setOnClickListener() {
            for (i in 0 until pathMarkerCount - 1) {
                var flag:Boolean = true
                if(i%2 == 0){
                    flag = !flag
                }
                drawPath(tmapView, path[i], path[i+1], i, flag)
            }
            val info: TMapInfo = tmapView.getDisplayTMapInfo(path)
            tmapView.setCenterPoint(info.tMapPoint.longitude, info.tMapPoint.latitude)
            tmapView.zoomLevel = info.tMapZoomLevel
            Log.d("error", "finish")
        }
    }

    private fun drawPath(tmapView: TMapView, tmapPointStart: TMapPoint, tmapPointEnd: TMapPoint, cnt:Int, flag:Boolean) {
//        thread() {
//            try{
//                val tmapPolyLine: TMapPolyLine =
//                    TMapData().findPathData(tmapPointStart, tmapPointEnd)
//                tmapPolyLine.lineColor = Color.BLUE
//                tmapPolyLine.lineWidth = 3F
//                tmapView.addTMapPolyLine("tmapPolyLine$cnt", tmapPolyLine)
//                Log.d("error", "tmapPolyLine$cnt")
//            } catch(e:Exception){
//                Log.d("error", e.printStackTrace().toString())
//                e.printStackTrace()
//            }
//        }.start()
        val thr : Thread = Thread(){
            try{
                val tmapPolyLine: TMapPolyLine =
                    TMapData().findPathData(tmapPointStart, tmapPointEnd)
                tmapPolyLine.lineColor = Color.BLUE
                tmapPolyLine.lineWidth = 3F
                tmapView.addTMapPolyLine("tmapPolyLine$cnt", tmapPolyLine)
                Log.d("try", "tmapPolyLine$cnt")
            } catch(e:Exception){
                Log.d("catch", e.printStackTrace().toString())
                e.printStackTrace()
            }
        }
        thr.start()

        try{
            thr.join()
        } catch (e:Exception){
            Log.d("catch", e.printStackTrace().toString())
            e.printStackTrace()
        }

        if(flag){
            Thread.sleep(1000)
        }
    }
}

//        var items = arrayOf("SM3", "SM5", "SM7", "SONATA", "AVANTE", "SOUL", "K5", "K7")
//
//        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, items)
//        binding.etWriteMapStart.setAdapter(adapter)

//        val tMapPOIItem = TMapPOIItem()
//        val tmapdata = TMapData()
//     //   val POIItem = tmapdata.findAllPOI("SKT타워")
//
//        tmapdata.findAllPOI("SKT타워") { poiItem ->
//            for (i in 0 until poiItem.size) {
//                val item = poiItem[i]
//                Toast.makeText(this, item.poiName, Toast.LENGTH_LONG).show()
//                Log.d(
//                    "POI Name: ", item.poiName.toString() + ", " +
//                            "Address: " + item.poiAddress.replace("null", "") + ", " +
//                            "Point: " + item.poiPoint.toString()
//                )
//            }
//        }


//WritwActivity에 값 보내기
//   fun sendData(){
//        var intent = Intent(this, WriteActivity::class.java)
////        intent.putExtra("theme1", theme11)
////            .putExtra("theme2",theme22)
//
//        setResult(RESULT_OK, intent)
//        finish()
//    }