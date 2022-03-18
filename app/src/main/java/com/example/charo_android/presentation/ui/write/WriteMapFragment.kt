package com.example.charo_android.presentation.ui.write

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentWriteMapBinding
import com.example.charo_android.hidden.Hidden
import com.example.charo_android.presentation.util.CustomToast
import com.skt.Tmap.*
import java.lang.Exception
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class WriteMapFragment : Fragment(), View.OnClickListener {

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

        blackOut()

        locationFlag = sharedViewModel.locationFlag.value.toString()

        val tMapView = TMapView(context)

        tMapView.setSKTMapApiKey(Hidden().tMapApiKey)
        binding.clWriteTmapView.addView(tMapView)

        fillTextView(locationFlag, tMapView)

        binding.imgWriteMapBack.setOnClickListener(this)      // 뒤로가기 click
        binding.etWriteMapStart.setOnClickListener(this)
        binding.etWriteMapMid1.setOnClickListener(this)
        binding.etWriteMapMid2.setOnClickListener(this)
        binding.etWriteMapEnd.setOnClickListener(this)
        binding.btnWriteComplete.setOnClickListener(this)

        return root
    }

    override fun onResume() {
        super.onResume()
        isEditTextFill()
    }

    private fun isEditTextFill() {
        binding.etWriteMapStart.isSelected = !TextUtils.isEmpty(binding.etWriteMapStart.text)
        binding.etWriteMapMid1.isSelected = !TextUtils.isEmpty(binding.etWriteMapMid1.text)
        binding.etWriteMapMid2.isSelected = !TextUtils.isEmpty(binding.etWriteMapMid2.text)
        binding.etWriteMapEnd.isSelected = !TextUtils.isEmpty(binding.etWriteMapEnd.text)
    }

    private fun fillTextView(
        locationFlag: String,
        tMapView: TMapView
    ) {
        when(locationFlag){
            "2" -> { // 경유지1인 경우
                binding.etWriteMapMid1.visibility = View.VISIBLE
                binding.imgWriteMapDelete1.visibility = View.VISIBLE
            }
            "3" -> { // 경유지2인 경우
                binding.etWriteMapMid1.visibility = View.VISIBLE
                binding.imgWriteMapDelete1.visibility = View.VISIBLE
                binding.etWriteMapMid2.visibility = View.VISIBLE
                binding.imgWriteMapDelete2.visibility = View.VISIBLE
            }
            else -> { }
        }

        if (!TextUtils.isEmpty(sharedViewModel.midFrstAddress.value)) {
            binding.etWriteMapMid1.visibility = View.VISIBLE
            binding.imgWriteMapDelete1.visibility = View.VISIBLE
        }
        if (!TextUtils.isEmpty(sharedViewModel.midSecAddress.value)) {
            binding.etWriteMapMid2.visibility = View.VISIBLE
            binding.imgWriteMapDelete2.visibility = View.VISIBLE
        }
        if (!TextUtils.isEmpty(sharedViewModel.endAddress.value)) {
            binding.btnWriteComplete.visibility = View.VISIBLE
        }

        binding.etWriteMapStart.text = sharedViewModel.startAddress.value
        binding.etWriteMapMid1.text = sharedViewModel.midFrstAddress.value
        binding.etWriteMapMid2.text = sharedViewModel.midSecAddress.value
        binding.etWriteMapEnd.text = sharedViewModel.endAddress.value

        binding.imgWriteMapAddAdress.setOnClickListener{
            clickAddAddress()
        }
        binding.imgWriteMapDelete1.setOnClickListener {
            clickWriteMapDelete1(tMapView)
        }
        binding.imgWriteMapDelete2.setOnClickListener {
            clickWriteMapDelete2(tMapView)
        }
        
        addList(tMapView)
    }

    private fun clickAddAddress(){
        if (!TextUtils.isEmpty(sharedViewModel.startAddress.value) && !TextUtils.isEmpty(sharedViewModel.endAddress.value)) {
            if (binding.etWriteMapMid1.visibility == View.GONE) {
                binding.etWriteMapMid1.visibility = View.VISIBLE
                binding.imgWriteMapDelete1.visibility = View.VISIBLE

                binding.etWriteMapMid1.text = sharedViewModel.midFrstAddress.value
            }
            if (binding.etWriteMapMid1.visibility == View.VISIBLE && !TextUtils.isEmpty(sharedViewModel.midFrstAddress.value)) {
                binding.etWriteMapMid2.visibility = View.VISIBLE
                binding.imgWriteMapDelete2.visibility = View.VISIBLE

                binding.etWriteMapMid2.text = sharedViewModel.midSecAddress.value
            }else{
                Toast.makeText(requireContext(),"경유지를 입력해주세요.",Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(requireContext(),"출발지와 도착지 모두 입력 후 추가 가능합니다.",Toast.LENGTH_LONG).show()
        }
    }
    
    private fun clickWriteMapDelete1(tMapView: TMapView) {
        if (!TextUtils.isEmpty(sharedViewModel.midSecAddress.value)) {
            binding.etWriteMapMid2.visibility = View.GONE
            binding.imgWriteMapDelete2.visibility = View.GONE

            sharedViewModel.midFrstAddress = sharedViewModel.midSecAddress
            sharedViewModel.midFrstLat = sharedViewModel.midSecLat
            sharedViewModel.midFrstLong = sharedViewModel.midSecLong

            sharedViewModel.midSecAddress.value = ""
            sharedViewModel.midSecLat.value = 0.0
            sharedViewModel.midSecLong.value = 0.0

            binding.etWriteMapMid1.text = sharedViewModel.midFrstAddress.value
        } else {
            binding.etWriteMapMid1.visibility = View.GONE
            binding.imgWriteMapDelete1.visibility = View.GONE

            sharedViewModel.midFrstAddress.value = ""
            sharedViewModel.midFrstLat.value = 0.0
            sharedViewModel.midFrstLong.value = 0.0
        }
        path.clear()
        addList(tMapView)
    }

    private fun clickWriteMapDelete2(tMapView: TMapView) {
        binding.etWriteMapMid2.visibility = View.GONE
        binding.imgWriteMapDelete2.visibility = View.GONE

        sharedViewModel.midSecAddress.value = ""
        sharedViewModel.midSecLat.value = 0.0
        sharedViewModel.midSecLong.value = 0.0

        path.clear()
        addList(tMapView)
    }

    private fun addList(tMapView: TMapView) {
        if (sharedViewModel.startLat.value != 0.0) {
            path.add(TMapPoint(sharedViewModel.startLat.value!!, sharedViewModel.startLong.value!!))
        }
        if (sharedViewModel.midFrstLat.value != 0.0) {
            path.add(TMapPoint(sharedViewModel.midFrstLat.value!!, sharedViewModel.midFrstLong.value!!))
        }
        if (sharedViewModel.midSecLat.value != 0.0) {
            path.add(TMapPoint(sharedViewModel.midSecLat.value!!, sharedViewModel.midSecLong.value!!))
        }
        if (sharedViewModel.endLat.value != 0.0) {
            path.add(TMapPoint(sharedViewModel.endLat.value!!, sharedViewModel.endLong.value!!))
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

    fun blackOut(){
        val isShowBlackout = sharedViewModel.startAddress.value == "" && sharedViewModel.midFrstAddress.value == "" && sharedViewModel.midSecAddress.value == ""

        if(isShowBlackout){
            binding.grayBackgroundForToast.visibility = View.VISIBLE
            CustomToast.createToast(requireContext(), "출발지와 목적지를 입력하여 경로를 확인 후, \n경유지를 추가해 경로를 수정할 수 있습니다.")?.show()

        }else{
            binding.grayBackgroundForToast.visibility = View.GONE
        }
    }

    private fun clickWriteComplete() {
        if ((binding.etWriteMapMid1.visibility == View.VISIBLE && !TextUtils.isEmpty(sharedViewModel.midFrstAddress.value)) ||
            binding.etWriteMapMid2.visibility == View.VISIBLE && !TextUtils.isEmpty(sharedViewModel.midSecAddress.value)
        ) {
//                Toast.makeText(this, "경유지를 입력해주세요!", Toast.LENGTH_LONG).show()
        } else {

//                val course = ArrayList<HashMap<String, RequestBody>>()
//                val startCourse : HashMap<String, RequestBody> = hashMapOf<String, RequestBody>("address" to sharedViewModel.startAddress.value.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
//                    "longtitude" to sharedViewModel.startLong.value.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
//                    "latitude" to sharedViewModel.startLat.value.toString().toRequestBody("text/plain".toMediaTypeOrNull()))
//                val middleCourse : HashMap<String, RequestBody> = hashMapOf<String, RequestBody>("address" to sharedViewModel.midFrstAddress.value.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
//                    "longtitude" to sharedViewModel.midFrstLong.value.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
//                    "latitude" to sharedViewModel.midFrstLat.value.toString().toRequestBody("text/plain".toMediaTypeOrNull()))
//                val endCourse : HashMap<String, RequestBody> = hashMapOf<String, RequestBody>("address" to sharedViewModel.endAddress.value.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
//                    "longtitude" to sharedViewModel.endLong.value.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
//                    "latitude" to sharedViewModel.endLat.value.toString().toRequestBody("text/plain".toMediaTypeOrNull()))

                val course = ArrayList<HashMap<String, String>>()
                val startCourse : HashMap<String, String> = hashMapOf<String, String>("address" to sharedViewModel.startAddress.value.toString(),
                    "longtitude" to sharedViewModel.startLong.value.toString(),
                    "latitude" to sharedViewModel.startLat.value.toString())
                val middleCourse : HashMap<String, String> = hashMapOf<String, String>("address" to sharedViewModel.midFrstAddress.value.toString(),
                    "longtitude" to sharedViewModel.midFrstLong.value.toString(),
                    "latitude" to sharedViewModel.midFrstLat.value.toString())
                val endCourse : HashMap<String, String> = hashMapOf<String, String>("address" to sharedViewModel.endAddress.value.toString(),
                    "longtitude" to sharedViewModel.endLong.value.toString(),
                    "latitude" to sharedViewModel.endLat.value.toString())

            course.add(startCourse)
            if(sharedViewModel.midFrstAddress.value !== ""){
                course.add(middleCourse)
            }
            course.add(endCourse)

            sharedViewModel.course.value = course

            AlertDialog.Builder(requireContext())
                .setMessage("게시물 작성을 완료하시겠습니까??")
                .setNeutralButton("아니오") { dialog, which ->
                }
                .setPositiveButton("예") { dialog, which ->
                    if ((binding.etWriteMapMid1.visibility == View.VISIBLE && !TextUtils.isEmpty(sharedViewModel.midFrstAddress.value)) ||
                        binding.etWriteMapMid2.visibility == View.VISIBLE && !TextUtils.isEmpty(sharedViewModel.midSecAddress.value)
                    ) {
////                            Toast.makeText(this, "경유지를 입력해주세요!", Toast.LENGTH_LONG).show()
                    } else {

                        sharedViewModel.serveWriteData()
////
//                            //서버에 값 보내기
//                            val sendTheme : ArrayList<MultipartBody.Part> = ArrayList()
//                            val themeUtil = ThemeUtil()
//
//                            for(sharedTheme in sharedViewModel.theme.value!!){
//                                themeUtil.themeMap[sharedTheme]?.let { it1 ->
//                                    sendTheme.add(MultipartBody.Part.createFormData("theme",it1))
//
////                                    sendTheme.add(it1)
//                                }
//                            }
//
//                            val userEmailRB : RequestBody = sharedViewModel.userEmail.value!!.toRequestBody("text/plain".toMediaTypeOrNull())
//                            val titleRB : RequestBody = sharedViewModel.title.value!!.toRequestBody("text/plain".toMediaTypeOrNull())
//                            val provinceRB : RequestBody = sharedViewModel.province.value!!.toRequestBody("text/plain".toMediaTypeOrNull())
//                            val regionRB : RequestBody = sharedViewModel.region.value!!.toRequestBody("text/plain".toMediaTypeOrNull())
//                            val parkingDescRB : RequestBody = sharedViewModel.parkingDesc.value!!.toRequestBody("text/plain".toMediaTypeOrNull())
//                            val courseDescRB : RequestBody = sharedViewModel.courseDesc.value!!.toRequestBody("text/plain".toMediaTypeOrNull())
//
//                            val call: Call<ResponseStatusCode> =
//                                ApiService.writeViewService.writePost(
//                                    userEmailRB,
//                                    titleRB,
//                                    provinceRB,
//                                    regionRB,
//                                    sharedViewModel.warning.value,
//                                    sendTheme,
//                                    sharedViewModel.isParking.value,
//                                    parkingDescRB,
//                                    courseDescRB,
//                                    sharedViewModel.course.value,
//                                    sharedViewModel.imageMultiPart.value,
//                                )
//
//                            call.enqueue(object : Callback<ResponseStatusCode> {
//                                override fun onResponse(
//                                    call: Call<ResponseStatusCode>,
//                                    response: Response<ResponseStatusCode>
//                                ) {
//                                    if (response.isSuccessful) {
//                                        Log.d("server connect : Write ", "success")
//
//                                        //sharedViewMoel 값 초기화(다 지우기)
//                                        sharedViewModel.initData()
//
//                                        //상세보기로 이동
//                                        activity?.let{
//                                            val intent = Intent(context, DetailActivity::class.java)
//                                            startActivity(intent)
//                                        }
//
//                                    }else{
//                                        Log.d("server connect : Write ", "error")
//                                        Log.d("server connect : Write ", "$response.errorBody()")
//                                        Log.d("server connect : Write ", response.message())
//                                        Log.d("server connect : Write ", "${response.code()}")
//                                        Log.d("server connect : Write ", "${response.raw().request.url}")
//                                    }
//                                }
//
//                                override fun onFailure(
//                                    call: Call<ResponseStatusCode>,
//                                    t: Throwable
//                                ) {
//                                    Log.d("server connect", "fail:${t.message}")
//                                }
//                            })
                    }
                }
                .show()
        }
    }

    override fun onClick(v: View?) {
        when(v){
            binding.imgWriteMapBack -> {
                writeShareActivity?.onBackPressed()
            }
            binding.etWriteMapStart -> {
                sharedViewModel.locationFlag.value = "1"
                writeShareActivity!!.replaceAddStackFragment(
                    WriteMapSearchFragment.newInstance(),
                    "writeMapSearch"
                )
            }
            binding.etWriteMapMid1 -> {
                sharedViewModel.locationFlag.value = "2"
                writeShareActivity!!.replaceAddStackFragment(
                    WriteMapSearchFragment.newInstance(),
                    "writeMapSearch"
                )
            }
            binding.etWriteMapMid2 -> {
                sharedViewModel.locationFlag.value = "3"
                writeShareActivity!!.replaceAddStackFragment(
                    WriteMapSearchFragment.newInstance(),
                    "writeMapSearch"
                )
            }
            binding.etWriteMapEnd -> {
                if (!TextUtils.isEmpty(sharedViewModel.startAddress.value)) {
                    sharedViewModel.locationFlag.value = "4"
                    writeShareActivity!!.replaceAddStackFragment(
                        WriteMapSearchFragment.newInstance(),
                        "writeMapSearch"
                    )
                }else{
                    Toast.makeText(requireContext(),getString(R.string.start),Toast.LENGTH_LONG).show()
                }
            }
            binding.btnWriteComplete -> {
                clickWriteComplete()
            }
        }
    }
}