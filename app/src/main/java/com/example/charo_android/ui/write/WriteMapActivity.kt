package com.example.charo_android.ui.write

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PointF
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.charo_android.R
import com.example.charo_android.databinding.ActivityWriteMapBinding
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPOIItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import com.skt.Tmap.TMapView.OnClickListenerCallback
import java.util.*


class WriteMapActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWriteMapBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriteMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tMapView = TMapView(this@WriteMapActivity)

        /*************커밋 푸시 머지할 때 키 삭제************/
        tMapView.setSKTMapApiKey("")
        binding.clWriteTmapView.addView( tMapView )

        val markerItem1 = TMapMarkerItem()

        val tMapPoint1 = TMapPoint(10.97, 200.985302) // SKT타워

//60 100
        // 마커 아이콘
        val bitmap: Bitmap =
            BitmapFactory.decodeResource(this.getResources(), R.drawable.end) //, R.drawable.pin_r_m_a

        markerItem1.icon = bitmap // 마커 아이콘 지정
        markerItem1.setPosition(0.5f, 1.0f) // 마커의 중심점을 중앙, 하단으로 설정
        markerItem1.tMapPoint = tMapPoint1 // 마커의 좌표 지정
      //  markerItem1.name = "SKT타워" // 마커의 타이틀 지정
        tMapView.addMarkerItem("markerItem1", markerItem1) // 지도에 마커 추가
     //   tMapView.setCenterPoint(126.985302, 37.570841)
        tMapView.setZoomLevel(7);

        //클릭 이벤트 설정
        tMapView.setOnClickListenerCallBack(object : OnClickListenerCallback {
            override fun onPressEvent(
                p0: java.util.ArrayList<TMapMarkerItem>?,
                p1: java.util.ArrayList<TMapPOIItem>?,
                tMapPoint: TMapPoint?,
                pointF: PointF?
            ): Boolean {
                //Toast.makeText(MapEvent.this, "onPress~!", Toast.LENGTH_SHORT).show();
                return false
            }

            override fun onPressUpEvent(
                p0: ArrayList<TMapMarkerItem>?,
                p1: ArrayList<TMapPOIItem>?,
                tMapPoint: TMapPoint?,
                pointF: PointF?
            ): Boolean {
                //Toast.makeText(MapEvent.this, "onPressUp~!", Toast.LENGTH_SHORT).show();
                return false
            }
        })

        // 롱 클릭 이벤트 설정
        tMapView.setOnLongClickListenerCallback { arrayList, arrayList1, tMapPoint ->
            //Toast.makeText(MapEvent.this, "onLongPress~!", Toast.LENGTH_SHORT).show();
        }

        // 지도 스크롤 종료
//        tMapView.setOnDisableScrollWithZoomLevelListener { zoom, centerPoint ->
//            Toast.makeText(
//                this@WriteMapActivity,
//                """
//            zoomLevel=$zoom
//            lon=${centerPoint.longitude}
//            lat=${centerPoint.latitude}
//            """.trimIndent(),
//                Toast.LENGTH_SHORT
//            ).show()
//        }

        var items = arrayOf("SM3", "SM5", "SM7", "SONATA", "AVANTE", "SOUL", "K5", "K7")

        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, items)
        binding.etWriteMapStart.setAdapter(adapter)



    }
}