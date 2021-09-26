package com.example.charo_android.presentation.ui.write

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentWriteMapBinding
import com.example.charo_android.hidden.Hidden
import com.example.charo_android.presentation.util.CustomToast
import com.skt.Tmap.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import java.lang.Exception

class WriteMapFragment : Fragment() {

    companion object {
        fun newInstance() = WriteMapFragment()
    }

    private val sharedViewModel: WriteSharedViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                WriteSharedViewModel() as T
        }
    }

    //    좌표 배열
    var path = arrayListOf<TMapPoint>()

    private lateinit var locationFlag: String
    private lateinit var locationName: String
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private lateinit var userId: String
    private lateinit var nickName: String

    private var mapData = WriteData


    private var _binding: FragmentWriteMapBinding? = null

    private val binding get() = _binding!!

    var writeShareActivity: WriteShareActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        writeShareActivity = context as WriteShareActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWriteMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        Log.d("Created WriteMapFragment", "jjjjjjjj")

        if (mapData.startAddress == "" || binding.etWriteMapStart.text == null || binding.etWriteMapStart.text == "") {
//            AlertDialog.Builder(this)
//                .setMessage("출발지와 목적지를 입력해 다녀온 드라이브 코스를 표현해주세요. 완성된 경로를 확인 후, 경유지를 초가해 원하는 경로로 수정이 가능합니다.")
//                .setPositiveButton("예") { dialog, which ->
//                }
//                .show()
        }

        userId = sharedViewModel.userId.value.toString()
        nickName = sharedViewModel.nickName.value.toString()
        locationFlag = sharedViewModel.locationFlag.value.toString()
        locationName = sharedViewModel.locationName.value.toString()
        latitude = sharedViewModel.latitude.value!!
        longitude = sharedViewModel.longitude.value!!

        Log.d("uuuwritemap", userId)
        Log.d("uuuwritemap", nickName)
        Log.d("uuuwritemap", locationFlag)
        Log.d("uuuwritemap", locationName)
        Log.d("uuuwritemap", latitude.toString())
        Log.d("uuuwritemap", longitude.toString())

//        this.customToast()
        blackOut()
        Log.d("bbb",binding.etWriteMapEnd.text.toString())




        // 뒤로가기 image click
        binding.imgWriteMapBack.setOnClickListener {
            writeShareActivity?.onBackPressed()
        }

        // 출발지 누르면 검색창으로 감
        binding.etWriteMapStart.setOnClickListener {
            sharedViewModel.locationFlag.value = "1"
            writeShareActivity!!.replaceFragment(
                WriteMapSearchFragment.newInstance(),
                "writeMapSearch"
            )

//            val intent = Intent(applicationContext, WriteMapSearchActivity::class.java)
//            intent.putExtra("locationM", "출발지를 입력하세요")
        }

        // 경유지1 누르면 검색창으로 감
        binding.etWriteMapMid1.setOnClickListener {
            sharedViewModel.locationFlag.value = "2"
            writeShareActivity!!.replaceFragment(
                WriteMapSearchFragment.newInstance(),
                "writeMapSearch"
            )

//            val intent = Intent(applicationContext, WriteMapSearchActivity::class.java)
//            intent.putExtra("locationM", "경유지1를 입력하세요")
        }
        // 경유지2 누르면 검색창으로 감
        binding.etWriteMapMid2.setOnClickListener {
            sharedViewModel.locationFlag.value = "3"
            writeShareActivity!!.replaceFragment(
                WriteMapSearchFragment.newInstance(),
                "writeMapSearch"
            )

//            val intent = Intent(applicationContext, WriteMapSearchActivity::class.java)
//            intent.putExtra("locationM", "경유지2를 입력하세요")
        }
        // 도착지 누르면 검색창으로 감
        binding.etWriteMapEnd.setOnClickListener {
            if (mapData.startAddress != "") {
                sharedViewModel.locationFlag.value = "4"
                writeShareActivity!!.replaceFragment(
                    WriteMapSearchFragment.newInstance(),
                    "writeMapSearch"
                )
            }

//            if (mapData.startAddress != "") {
//                val intent = Intent(applicationContext, WriteMapSearchActivity::class.java)
//                intent.putExtra("locationM", "도착지를 입력하세요")
//            }
        }

        val tMapView = TMapView(context)

        /*************커밋 푸시 머지할 때 키 삭제************/
        tMapView.setSKTMapApiKey(Hidden().tMapApiKey)
        binding.clWriteTmapView.addView(tMapView)

        fillTextView(locationFlag, locationName, latitude, longitude, tMapView)
        btnWriteCompleteOnClickEvent()

        return root
    }

    private fun fillTextView(
        locationFlag: String,
        locationName: String,
        latitude: Double,
        longitude: Double,
        tMapView: TMapView
    ) {
        if (locationFlag == "1") { // 출발지인 경우
            mapData.startAddress = locationName
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

            mapData.mid1Address = locationName
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

            mapData.mid2Address = locationName
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
            mapData.endAddress = locationName
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
//                tMapPolyLine.lineColor = getColor(activity, R.color.blue_main)
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

    fun customDialog() = runBlocking<Unit> {
        withTimeout(1300L) {
            launch {
                CustomToast.createDialog(requireContext())
            }
        }
    }

    lateinit var fadeoutAnim : Animation
    fun customToast() = runBlocking<Unit>{
        fadeoutAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)

        binding.grayBackgroundForToast.visibility = View.VISIBLE
        CustomToast.createToast(requireContext(), "출발지와 목적지를 입력하여 경로를 확인 후, \n경유지를 추가해 경로를 수정할 수 있습니다.")?.show()
        //ToastMessage 사라질 때 회색화면도 같이 사라짐
//        Handler().postDelayed({
//            binding.grayBackgroundForToast.startAnimation(fadeoutAnim)
//            binding.grayBackgroundForToast.visibility = View.GONE
//        },3800L)
        }

    fun blackOut(){
//        var isShowBlackout = binding.etWriteMapStart == null && binding.etWriteMapMid1 == null && binding.etWriteMapMid2 == null && binding.etWriteMapEnd == null
        var isShowBlackout = sharedViewModel.locationName.value == "" || sharedViewModel.locationName.value == null

        if(isShowBlackout){
            binding.grayBackgroundForToast.visibility = View.VISIBLE
            CustomToast.createToast(requireContext(), "출발지와 목적지를 입력하여 경로를 확인 후, \n경유지를 추가해 경로를 수정할 수 있습니다.")?.show()

        }else{
            binding.grayBackgroundForToast.visibility = View.GONE
        }
    }

    private fun btnWriteCompleteOnClickEvent() {
            binding.btnWriteComplete.setOnClickListener() {
                if ((binding.etWriteMapMid1.visibility == View.VISIBLE && mapData.mid1Address == "") ||
                    binding.etWriteMapMid2.visibility == View.VISIBLE && mapData.mid2Address == ""
                ) {
//                Toast.makeText(this, "경유지를 입력해주세요!", Toast.LENGTH_LONG).show()
                } else {
                    AlertDialog.Builder(requireContext())
                        .setMessage("게시물 작성을 완료하시겠습니까??")
                        .setNeutralButton("아니오") { dialog, which ->
                        }
                        .setPositiveButton("예") { dialog, which ->
                            if ((binding.etWriteMapMid1.visibility == View.VISIBLE && mapData.mid1Address == "") ||
                                binding.etWriteMapMid2.visibility == View.VISIBLE && mapData.mid2Address == ""
                            ) {
////                            Toast.makeText(this, "경유지를 입력해주세요!", Toast.LENGTH_LONG).show()
                            } else {
                                //서버에 값 보내기
                                Log.e("sharedViewModel Data",sharedViewModel.userEmail.value.toString())
                                Log.e("sharedViewModel Data",sharedViewModel.title.value.toString())
                                Log.e("sharedViewModel Data",sharedViewModel.image.value.toString())
                                Log.e("sharedViewModel Data",sharedViewModel.province.value.toString())
                                Log.e("sharedViewModel Data",sharedViewModel.region.value.toString())
                                Log.e("sharedViewModel Data",sharedViewModel.warning.value.toString())
                                Log.e("sharedViewModel Data",sharedViewModel.theme.value.toString())
                                Log.e("sharedViewModel Data",sharedViewModel.isParking.value.toString())
                                Log.e("sharedViewModel Data",sharedViewModel.parkingDesc.value.toString())
                                Log.e("sharedViewModel Data",sharedViewModel.courseDesc.value.toString())
                                Log.e("sharedViewModel Data",sharedViewModel.locationAddress.value.toString())
                                Log.e("sharedViewModel Data",sharedViewModel.latitude.value.toString())
                                Log.e("sharedViewModel Data",sharedViewModel.longitude.value.toString())





//                            mapData.startAddress = ""
//                            mapData.mid1Address = ""
//                            mapData.mid2Address = ""
//                            mapData.endAddress = ""
//                            mapData.startLat = 0.0
//                            mapData.startLong = 0.0
//                            mapData.mid1Lat = 0.0
//                            mapData.mid1Long = 0.0
//                            mapData.mid2Lat = 0.0
//                            mapData.mid2Long = 0.0
//                            mapData.endLat = 0.0
//                            mapData.endLong = 0.0
//
//                            mapData.courseDesc = ""
//                            mapData.isParking = false
//                            mapData.parkingDesc = ""
//                            mapData.province = ""
//                            mapData.region = ""
//                            mapData.theme.clear()
//                            mapData.title = ""
//                            mapData.userId = ""
//                            mapData.warning.clear()
//                            mapData.fileList.clear()
//
//                            val intent = Intent(applicationContext, MainActivity::class.java)
//                            intent.putExtra("userId", Hidden.userId)
//                            startActivity(intent)
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
                        }
                        .show()
//                })
                }
            }

        }
    }
//            val intent = Intent(applicationContext, MainActivity::class.java)
//            intent.putExtra("userId", userId)
//            intent.putExtra("nickName", nickName)
//            startActivity(intent)

//        writeShareActivity!!.replaceFragment(WriteMapSearchFragment.newInstance())

//    }

// 시작
//    override fun onBackPressed() {
//        super.onBackPressed()
//    }
