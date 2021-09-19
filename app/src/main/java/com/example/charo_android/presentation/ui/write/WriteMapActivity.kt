package com.example.charo_android.presentation.ui.write


import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.charo_android.MainActivity
import com.example.charo_android.R
import com.example.charo_android.databinding.ActivityWriteMapBinding
import com.example.charo_android.data.api.hidden.Hidden
import com.skt.Tmap.*
import kotlinx.android.synthetic.main.activity_write_map.*
import java.util.*
import java.lang.Exception

class WriteMapActivity : AppCompatActivity() {

    //    좌표 배열
    var path = arrayListOf<TMapPoint>()

    private lateinit var binding: ActivityWriteMapBinding

    private lateinit var locationFlag: String
    private lateinit var textview: String
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private lateinit var userId: String
    private lateinit var nickName: String

    private var mapData = WriteData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteMapBinding.inflate(layoutInflater)
        setContentView(binding.root)



        if (mapData.startAddress == ""||binding.etWriteMapStart.text==null||binding.etWriteMapStart.text=="") {
            AlertDialog.Builder(this)
                .setMessage("출발지와 목적지를 입력해 다녀온 드라이브 코스를 표현해주세요. 완성된 경로를 확인 후, 경유지를 초가해 원하는 경로로 수정이 가능합니다.")
                .setPositiveButton("예") { dialog, which ->
                }
                .show()
        }

        userId = intent.getStringExtra("userId").toString()
        nickName = intent.getStringExtra("nickName").toString()


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
            AlertDialog.Builder(this)
                .setMessage("드라이브 코스 작성을 중단하시겠습니까?")
                .setNeutralButton("아니오") { dialog, which ->
                }
                .setPositiveButton("예") { dialog, which ->
                    WriteData.startAddress = ""
                    WriteData.mid1Address = ""
                    WriteData.mid2Address = ""
                    WriteData.endAddress = ""
                    WriteData.startLat = 0.0
                    WriteData.startLong = 0.0
                    WriteData.mid1Lat = 0.0
                    WriteData.mid1Long = 0.0
                    WriteData.mid2Lat = 0.0
                    WriteData.mid2Long = 0.0
                    WriteData.endLat = 0.0
                    WriteData.endLong = 0.0

                    WriteData.courseDesc = ""
                    WriteData.isParking = false
                    WriteData.parkingDesc = ""
                    WriteData.province = ""
                    WriteData.region = ""
                    WriteData.theme.clear()
                    WriteData.title = ""
                    WriteData.userId = ""
                    WriteData.warning.clear()
                    WriteData.fileList.clear()

                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("userId", "and")
                    startActivity(intent)
                }
                .show()

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("nickName", nickName)
            startActivity(intent)
        }

        // 출발지 누르면 검색창으로 감
        binding.etWriteMapStart.setOnClickListener {
            val intent = Intent(applicationContext, WriteMapSearchActivity::class.java)
            intent.putExtra("locationM", "출발지를 입력하세요")
            intent.putExtra("locationFlag", "1")
            intent.putExtra("userId", userId)
            intent.putExtra("nickName", nickName)
            startActivity(intent)
        }

        // 경유지1 누르면 검색창으로 감
        binding.etWriteMapMid1.setOnClickListener {
            val intent = Intent(applicationContext, WriteMapSearchActivity::class.java)
            intent.putExtra("locationM", "경유지1를 입력하세요")
            intent.putExtra("locationFlag", "2")
            intent.putExtra("userId", userId)
            intent.putExtra("nickName", nickName)
            startActivity(intent)
        }
        // 경유지2 누르면 검색창으로 감
        binding.etWriteMapMid2.setOnClickListener {
            val intent = Intent(applicationContext, WriteMapSearchActivity::class.java)
            intent.putExtra("locationM", "경유지2를 입력하세요")
            intent.putExtra("locationFlag", "3")
            intent.putExtra("userId", userId)
            intent.putExtra("nickName", nickName)
            startActivity(intent)
        }
        // 도착지 누르면 검색창으로 감
        binding.etWriteMapEnd.setOnClickListener {
            if (mapData.startAddress != "") {
                val intent = Intent(applicationContext, WriteMapSearchActivity::class.java)
                intent.putExtra("locationM", "도착지를 입력하세요")
                intent.putExtra("locationFlag", "4")
                intent.putExtra("userId", userId)
                intent.putExtra("nickName", nickName)
                startActivity(intent)
            }
        }

        val tMapView = TMapView(this@WriteMapActivity)

        /*************커밋 푸시 머지할 때 키 삭제************/
        tMapView.setSKTMapApiKey(Hidden().tMapApiKey)
        binding.clWriteTmapView.addView(tMapView)

        fillTextView(locationFlag, textview, latitude, longitude, tMapView)
        btnWriteCompleteOnClickEvent()
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

            if (mapData.mid1Address != "") {
                binding.etWriteMapMid1.visibility = View.VISIBLE
                binding.imgWriteMapDelete1.visibility = View.VISIBLE
            }
            if (mapData.mid2Address != "") {
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

            if (mapData.mid1Address != "") {
                binding.etWriteMapMid1.visibility = View.VISIBLE
                binding.imgWriteMapDelete1.visibility = View.VISIBLE
            }
            if (mapData.mid2Address != "") {
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

            if (mapData.mid1Address != "") {
                binding.etWriteMapMid1.visibility = View.VISIBLE
                binding.imgWriteMapDelete1.visibility = View.VISIBLE
            }
            if (mapData.mid2Address != "") {
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

            if (mapData.mid1Address != "") {
                binding.etWriteMapMid1.visibility = View.VISIBLE
                binding.imgWriteMapDelete1.visibility = View.VISIBLE
            }
            if (mapData.mid2Address != "") {
                binding.etWriteMapMid2.visibility = View.VISIBLE
                binding.imgWriteMapDelete2.visibility = View.VISIBLE
            }
        }

        if (mapData.endAddress != "") {
            binding.btnWriteComplete.visibility = View.VISIBLE
        }

        imgWriteMapAddAddressOnClickEvent()
        imgWriteMapDelete1OnClickEvent(tMapView)
        imgWriteMapDelete2OnClickEvent(tMapView)
        addList(tMapView)
    }

    private fun imgWriteMapAddAddressOnClickEvent() {
        binding.imgWriteMapAddAdress.setOnClickListener() {
            if (mapData.startAddress != "" && mapData.endAddress != "") {
                if (binding.etWriteMapMid1.visibility == View.GONE) {
                    binding.etWriteMapMid1.visibility = View.VISIBLE
                    binding.imgWriteMapDelete1.visibility = View.VISIBLE

                    binding.etWriteMapMid1.text = mapData.mid1Address
                }
                if (binding.etWriteMapMid1.visibility == View.VISIBLE && mapData.mid1Address != "") {
                    binding.etWriteMapMid2.visibility = View.VISIBLE
                    binding.imgWriteMapDelete2.visibility = View.VISIBLE

                    binding.etWriteMapMid2.text = mapData.mid2Address
                }
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
        if (mapData.mid1Lat != 0.0) {
            path.add(TMapPoint(mapData.mid1Lat, mapData.mid1Long))
        }
        if (mapData.mid2Lat != 0.0) {
            path.add(TMapPoint(mapData.mid2Lat, mapData.mid2Long))
        }
        if (mapData.endLat != 0.0) {
            path.add(TMapPoint(mapData.endLat, mapData.endLong))
        }

        if (path.isNotEmpty()) {
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
        for (i in 0 until path.size) {
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

        if (path.size > 1) {
            findPath(tMapView)
        }
    }

    private fun findPath(tMapView: TMapView) {
        for (i in 0 until path.size - 1) {
            var flag: Boolean = true
            if (i != 0 && i % 2 == 0) {
                flag = !flag
            }
            drawPath(tMapView, path[i], path[i + 1], i, flag)
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

    private fun btnWriteCompleteOnClickEvent() {
        binding.btnWriteComplete.setOnClickListener() {
            if ((binding.etWriteMapMid1.visibility == View.VISIBLE && mapData.mid1Address == "") ||
                binding.etWriteMapMid2.visibility == View.VISIBLE && mapData.mid2Address == ""
            ) {
                Toast.makeText(this, "경유지를 입력해주세요!", Toast.LENGTH_LONG).show()
            } else {
                AlertDialog.Builder(this)
                    .setMessage("게시물 작성을 완료하시겠습니까??")
                    .setNeutralButton("아니오") { dialog, which ->
                    }
                    .setPositiveButton("예") { dialog, which ->
                        if ((binding.etWriteMapMid1.visibility == View.VISIBLE && mapData.mid1Address == "") ||
                            binding.etWriteMapMid2.visibility == View.VISIBLE && mapData.mid2Address == ""
                        ) {
                            Toast.makeText(this, "경유지를 입력해주세요!", Toast.LENGTH_LONG).show()
                        } else {
                            WriteData.startAddress = ""
                            WriteData.mid1Address = ""
                            WriteData.mid2Address = ""
                            WriteData.endAddress = ""
                            WriteData.startLat = 0.0
                            WriteData.startLong = 0.0
                            WriteData.mid1Lat = 0.0
                            WriteData.mid1Long = 0.0
                            WriteData.mid2Lat = 0.0
                            WriteData.mid2Long = 0.0
                            WriteData.endLat = 0.0
                            WriteData.endLong = 0.0

                            WriteData.courseDesc = ""
                            WriteData.isParking = false
                            WriteData.parkingDesc = ""
                            WriteData.province = ""
                            WriteData.region = ""
                            WriteData.theme.clear()
                            WriteData.title = ""
                            WriteData.userId = ""
                            WriteData.warning.clear()
                            WriteData.fileList.clear()
                        }
//                var addressList = mutableListOf<String>()
//                addressList.add(WriteData.startAddress)
//                if(WriteData.mid1Address != "")
//                    addressList.add(WriteData.mid1Address)
//                if(WriteData.mid2Address != "")
//                    addressList.add(WriteData.mid2Address)
//                addressList.add(WriteData.endAddress)
//
//                var latList = mutableListOf<String>()
//                latList.add(WriteData.startLat.toString())
//                if(WriteData.mid1Lat != 0.0)
//                    latList.add(WriteData.mid1Lat.toString())
//                if(WriteData.mid2Lat != 0.0)
//                    latList.add(WriteData.mid2Lat.toString())
//                latList.add(WriteData.endLat.toString())
//
//                var longList = mutableListOf<String>()
//                longList.add(WriteData.startLong.toString())
//                if(WriteData.mid1Long != 0.0)
//                    longList.add(WriteData.mid1Long.toString())
//                if(WriteData.mid2Long != 0.0)
//                    longList.add(WriteData.mid2Long.toString())
//                longList.add(WriteData.endLong.toString())
//
//                var courseList = RequestWriteData.Course(addressList, latList, longList)
//
//                val requestWriteData = RequestWriteData(
//                    courseList,
//                    WriteData.courseDesc,
//                    WriteData.isParking,
//                    WriteData.parkingDesc,
//                    WriteData.province,
//                    WriteData.region,
//                    WriteData.theme,
//                    WriteData.title,
//                    Hidden.userId,
//                    WriteData.warning
//                )

                        // 원래 주석이었음
                        /*@Part course: RequestWriteData.Course,
                        @Part courseDesc: String,
                        @Part isParking: Boolean,
                        @Part parkingDesc: String,
                        @Part province: String,
                        @Part region: String,
                        @Part theme: List<String>,
                        @Part title: String,
                        @Part userId: String,
                        @Part warning: List<Boolean>,
                        @Part files: List<MultipartBody.Part>*/

//                val hashMap = HashMap<String, RequestBody>()
//                val requestString: String = Gson().toJson(requestWriteData)
//                val body = RequestBody.create("application/json".toMediaTypeOrNull(), requestString)
//                hashMap.put("request", body)

                        // 이따 이어서 코딩할거다 진수야 기다려봐
//                val requestCourse = Gson().toJson(courseList)
//                val multipartCourse = MultipartBody.Part.createFormData("course", requestCourse)
//
//                val requestCourseDesc = Gson().toJson(WriteData.courseDesc)
//                val multipartCourseDesc = MultipartBody.Part.createFormData("courseDesc", requestCourseDesc)
//
//                val requestIsParking = Gson().toJson(WriteData.isParking)
//                val multipartIsParking = MultipartBody.Part.createFormData("isParking", requestIsParking)
//
//                val requestParkingDesc = Gson().toJson(WriteData.parkingDesc)
//                val multipartParkingDesc = MultipartBody.Part.createFormData("parkingDesc", requestParkingDesc)
//
//                val requestProvince = Gson().toJson(WriteData.province)
//                val multipartProvince = MultipartBody.Part.createFormData("province", requestProvince)
//
//                val requestRegion = Gson().toJson(WriteData.region)
//                val multipartRegion = MultipartBody.Part.createFormData("region", requestRegion)
//
//                val requestTheme = Gson().toJson(WriteData.theme)
//                val multipartTheme = MultipartBody.Part.createFormData("theme", requestTheme)
//
//                val requestTitle = Gson().toJson(WriteData.title)
//                val multipartTitle = MultipartBody.Part.createFormData("title", requestTitle)
//
//                val requestUserId = Gson().toJson(Hidden.userId)
//                val multipartUserId = MultipartBody.Part.createFormData("userId", requestUserId)
//
//                val requestWarning = Gson().toJson(WriteData.warning)
//                val multipartWarning = MultipartBody.Part.createFormData("warning", requestWarning)
//
//                val imageFileList: MutableList<MultipartBody.Part> = mutableListOf()
//                for(img in WriteData.fileList) {
//                    var file = File(img.toString())
//                    var fileList = listOf<Any>()
//                    val reFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
//                    val multipartItem = MultipartBody.Part.createFormData("files", file.name, reFile)
//                    imageFileList.add(multipartItem)
//                }
//
//                val call: Call<ResponseWriteData> = ApiService.writeViewService
//                    .writePost(multipartCourse, multipartCourseDesc, multipartIsParking, multipartParkingDesc, multipartProvince,
//                        multipartRegion, multipartTheme, multipartTitle, multipartUserId, multipartWarning, imageFileList)
//
//                call.enqueue(object: Callback<ResponseWriteData> {
//                    override fun onResponse(
//                        call: Call<ResponseWriteData>,
//                        response: Response<ResponseWriteData>
//                    ) {
//                        if(response.isSuccessful){
//                            Log.d("write_server_connect", "success")
//
//                            val intent = Intent(applicationContext, MainActivity::class.java)
//                            intent.putExtra("userId", Hidden.userId)
//                            startActivity(intent)
//                        } else {
//                            Log.d("server connect", "fail")
//                            Log.d("server connect", "${response.errorBody()}")
//                            Log.d("server connect", "${response.message()}")
//                            Log.d("server connect", "${response.code()}")
//                            Log.d("server connect", "${response.raw().request.url}")
//                        }
//                    }
//                    override fun onFailure(call: Call<ResponseWriteData>, t: Throwable) {
//                        Log.d("server connect", "error:${t.message}")
//                    }
//                })
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        intent.putExtra("userId", Hidden.userId)
                        startActivity(intent)
                    }.show()
            }
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("nickName", nickName)
            startActivity(intent)
        }
    }

    // 시작
    override fun onBackPressed() {
//        super.onBackPressed()
    }
}