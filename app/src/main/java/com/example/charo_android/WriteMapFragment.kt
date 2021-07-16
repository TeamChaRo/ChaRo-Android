package com.example.charo_android

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.charo_android.databinding.ActivityWriteMapBinding
import com.example.charo_android.databinding.FragmentWriteBinding
import com.example.charo_android.databinding.FragmentWriteMapBinding
import com.example.charo_android.ui.write.WriteAdapter
import com.example.charo_android.ui.write.WriteViewModel
import com.skt.Tmap.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [WriteMapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WriteMapFragment : Fragment() {
    /// 좌표 상수
    var markerCount = 0
    var pathMarkerCount = 0
    val path = arrayListOf<TMapPoint>()

    private lateinit var writeViewModel: WriteViewModel
    private var _binding: FragmentWriteMapBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        writeViewModel =
            ViewModelProvider(this).get(WriteViewModel::class.java)

        _binding = FragmentWriteMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.imgWriteMapBack.setOnClickListener {
            //  onBackPressed()
        }


//ViewModel 사용한 부분
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_write_map)
//        binding.lifecycleOwner = this
//        binding.viewModel = viewModel


        binding.etWriteMapStart.setOnClickListener {
            //       viewModel.data = 0

//            val intent = Intent(this@WriteMapActivity, WriteMapSearchActivity::class.java)
//            intent.putExtra("locationM", "출발지를 입력하세요")
//
//            startActivity(intent)
        }
        binding.etWriteMapMid1.setOnClickListener {
            //    viewModel.data = 1

//            val intent = Intent(this@WriteMapActivity, WriteMapSearchActivity::class.java)
//            intent.putExtra("locationM", "경유지1를 입력하세요")
//            startActivity(intent)
        }
        binding.etWriteMapMid2.setOnClickListener {
            //   viewModel.data = 2

//            val intent = Intent(this@WriteMapActivity, WriteMapSearchActivity::class.java)
//            intent.putExtra("locationM", "경유지2를 입력하세요")
//            startActivity(intent)

        }
        binding.etWriteMapEnd.setOnClickListener {
            //   viewModel.data = 3

//            val intent = Intent(this@WriteMapActivity, WriteMapSearchActivity::class.java)
//            intent.putExtra("locationM", "도착지를 입력하세요")
//
//            startActivity(intent)


        }

//        var getLocation = intent.getStringExtra("resultLocation")
//        Log.d("getlocation", getLocation.toString())
//
//        when (getLocation.toString()) {
//            "출발지로 설정" -> binding.etWriteMapStart.text = intent.getStringExtra("locationName")
//            "경유지1로 설정" -> binding.etWriteMapMid1.text = intent.getStringExtra("locationName")
//            "경유지2로 설정" -> binding.etWriteMapMid2.text = intent.getStringExtra("locationName")
//            "도착지로 설정" -> binding.etWriteMapEnd.text = intent.getStringExtra("locationName")
//        }


        val tMapView = TMapView(this.context)

        /*************커밋 푸시 머지할 때 키 삭제************/
        tMapView.setSKTMapApiKey("l7xx94a7679a3e1d41a782105327ae7af1cd")
        binding.clWriteTmapView.addView(tMapView)

//        var long = intent.getDoubleExtra("pointLong", 0.0)
//        var lat = intent.getDoubleExtra("pointLat", 0.0)

//        if (long != null && lat != null) {
//            btnPathMarkKeywordOnClickEvent(tMapView, long, lat)
//
//            //도착지가 있으면 <- 조건문 걸어주기
//            btnFindKeywordOnClickEvent(tMapView)
//        }


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


        // Inflate the layout for this fragment
        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WriteMapFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WriteMapFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


//
//    private fun markGangnamStation(tmapView: TMapView) {
//        val marker_gangnam_station = TMapMarkerItem()
//        val tmapPoint_gangnam_station =
//            TMapPoint(gangnam_station_latitude, gangnam_station_longitude)
//        val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.poi_dot)
//        marker_gangnam_station.icon = bitmap
//        marker_gangnam_station.setPosition(0.5F, 1.0F)
//        marker_gangnam_station.tMapPoint = tmapPoint_gangnam_station
//        marker_gangnam_station.name = "강남역"
//        tmapView.addMarkerItem("marker_gangnam_station", marker_gangnam_station)
//    }

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


    //마커 찍기
    fun btnMarkOnClickEvent(tmapView: TMapView) {
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

    private fun btnPathMarkKeywordOnClickEvent(tmapView: TMapView, long: Double, lat: Double) {
        val markerPathSpot = TMapMarkerItem()
        tmapView.setCenterPoint(long, lat);
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

    //경로 연결
    private fun btnFindOnClickEvent(tmapView: TMapView) {
        binding.btnFind.setOnClickListener() {
            for (i in 0 until pathMarkerCount - 1) {
                var flag: Boolean = true
                if (i % 2 == 0) {
                    flag = !flag
                }
                drawPath(tmapView, path[i], path[i + 1], i, flag)
            }
            val info: TMapInfo = tmapView.getDisplayTMapInfo(path)
            tmapView.setCenterPoint(info.tMapPoint.longitude, info.tMapPoint.latitude)
            tmapView.zoomLevel = info.tMapZoomLevel
            Log.d("error", "finish")
        }
    }

    private fun btnFindKeywordOnClickEvent(tmapView: TMapView) {
        //    if(pathMarkerCount >= 2)
        for (i in 0 until pathMarkerCount - 1) {
            var flag: Boolean = true
            if (i % 2 == 0) {
                flag = !flag
            }
            drawPath(tmapView, path[i], path[i + 1], i, flag)
        }
        val info: TMapInfo = tmapView.getDisplayTMapInfo(path)
        tmapView.setCenterPoint(info.tMapPoint.longitude, info.tMapPoint.latitude)
        tmapView.zoomLevel = info.tMapZoomLevel
        Log.d("error", "finish")

    }

    private fun drawPath(
        tmapView: TMapView,
        tmapPointStart: TMapPoint,
        tmapPointEnd: TMapPoint,
        cnt: Int,
        flag: Boolean
    ) {
        val thr: Thread = Thread() {
            try {
                val tmapPolyLine: TMapPolyLine =
                    TMapData().findPathData(tmapPointStart, tmapPointEnd)
                tmapPolyLine.lineColor = Color.BLUE
                tmapPolyLine.lineWidth = 3F
                tmapView.addTMapPolyLine("tmapPolyLine$cnt", tmapPolyLine)
                Log.d("try", "tmapPolyLine$cnt")
            } catch (e: Exception) {
                Log.d("catch", e.printStackTrace().toString())
                e.printStackTrace()
            }
        }
        thr.start()

        try {
            thr.join()
        } catch (e: Exception) {
            Log.d("catch", e.printStackTrace().toString())
            e.printStackTrace()
        }

        if (flag) {
            Thread.sleep(1000)
        }
    }
}