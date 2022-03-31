package com.charo.android.presentation.ui.write

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
import com.charo.android.R
import com.charo.android.data.model.request.write.RequestWriteData
import com.charo.android.databinding.FragmentWriteMapBinding
import com.charo.android.hidden.Hidden
import com.charo.android.presentation.util.CustomToast
import com.charo.android.presentation.util.Define
import com.charo.android.presentation.util.SharedInformation
import com.charo.android.presentation.util.enqueueUtil
import com.google.gson.Gson
import com.skt.Tmap.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.lang.Exception
import kotlin.collections.ArrayList

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
            Define().LOCATION_FLAG_MID_FRST -> { // 경유지1인 경우
                binding.etWriteMapMid1.visibility = View.VISIBLE
                binding.imgWriteMapDelete1.visibility = View.VISIBLE
            }
            Define().LOCATION_FLAG_MID_SEC -> { // 경유지2인 경우
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
                Toast.makeText(requireContext(),getString(R.string.middle),Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(requireContext(),getString(R.string.txt_check_input_start_end),Toast.LENGTH_LONG).show()
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
            marker.name = "marker_location_flag_$i"
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
            CustomToast.createToast(requireContext(), getString(R.string.noti_write_map))?.show()

        }else{
            binding.grayBackgroundForToast.visibility = View.GONE
        }
    }

    private fun clickWriteComplete() {
        if ((binding.etWriteMapMid1.visibility == View.VISIBLE && !TextUtils.isEmpty(sharedViewModel.midFrstAddress.value)) ||
            binding.etWriteMapMid2.visibility == View.VISIBLE && !TextUtils.isEmpty(sharedViewModel.midSecAddress.value)
        ) {
            Toast.makeText(context, "경유지를 입력해주세요!", Toast.LENGTH_LONG).show()
            return
        }

        val course = ArrayList<RequestBody>()

        val startCourse : RequestWriteData.Course = RequestWriteData.Course(sharedViewModel.startAddress.value.toString(), sharedViewModel.startLong.value.toString(), sharedViewModel.startLat.value.toString())
        val midFrstCourse : RequestWriteData.Course = RequestWriteData.Course(sharedViewModel.midFrstAddress.value.toString(), sharedViewModel.midFrstLong.value.toString(), sharedViewModel.midFrstLat.value.toString())
        val midSecCourse : RequestWriteData.Course = RequestWriteData.Course(sharedViewModel.midSecAddress.value.toString(), sharedViewModel.midSecLong.value.toString(), sharedViewModel.midSecLat.value.toString())
        val endCourse : RequestWriteData.Course = RequestWriteData.Course(sharedViewModel.endAddress.value.toString(), sharedViewModel.endLong.value.toString(), sharedViewModel.endLat.value.toString())

        val startJson = Gson().toJson(startCourse)
        val startCourseRb: RequestBody = startJson.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val midFrstJson = Gson().toJson(midFrstCourse)
        val midFrstCourseRb: RequestBody = midFrstJson.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val midSecJson = Gson().toJson(midSecCourse)
        val midSecCourseRb: RequestBody = midSecJson.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val endJson = Gson().toJson(endCourse)
        val endCourseRb: RequestBody = endJson.toRequestBody("multipart/form-data".toMediaTypeOrNull())

        course.add(startCourseRb)
        if(sharedViewModel.midFrstAddress.value !== ""){
            course.add(midFrstCourseRb)
        }
        if(sharedViewModel.midSecAddress.value !== ""){
            course.add(midSecCourseRb)
        }
        course.add(endCourseRb)

        sharedViewModel.course.value = course

        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.noti_complete_write))
            .setNeutralButton(getString(R.string.word_no)) { dialog, which ->
            }
            .setPositiveButton(getString(R.string.word_yes)) { dialog, which ->
                serveWriteData()
            }
            .show()
    }

    fun serveWriteData() {
        val param : HashMap<String, RequestBody> = HashMap()

        val sendTheme : HashMap<String,RequestBody> = HashMap()
        for (i in sharedViewModel.theme.value!!.indices) {
            sendTheme["theme[$i]"] = sharedViewModel.theme.value!![i].toRequestBody("multipart/form-data".toMediaTypeOrNull())
        }

        val sendWarning : HashMap<String,RequestBody> = HashMap()
        for (i in sharedViewModel.warningUI.value!!.indices) {
            sendWarning["warning[$i]"] = sharedViewModel.warningUI.value!![i].toRequestBody("multipart/form-data".toMediaTypeOrNull())
        }

        val sendCourse : HashMap<String,RequestBody> = HashMap()
        for (i in sharedViewModel.course.value!!.indices) {
            sendCourse["course[$i]"] = sharedViewModel.course.value!![i]
        }

        val userEmail = SharedInformation.getEmail(requireActivity())
        val userEmailRB : RequestBody = userEmail.toRequestBody("text/plain".toMediaTypeOrNull())
        val titleRB : RequestBody = sharedViewModel.title.value!!.toRequestBody("text/plain".toMediaTypeOrNull())
        val provinceRB : RequestBody = sharedViewModel.province.value!!.toRequestBody("text/plain".toMediaTypeOrNull())
        val regionRB : RequestBody = sharedViewModel.region.value!!.toRequestBody("text/plain".toMediaTypeOrNull())
        val parkingDescRB : RequestBody = sharedViewModel.parkingDesc.value!!.toRequestBody("text/plain".toMediaTypeOrNull())
        val courseDescRB : RequestBody = sharedViewModel.courseDesc.value!!.toRequestBody("text/plain".toMediaTypeOrNull())
        val isParkingRB : RequestBody = sharedViewModel.isParking.value!!.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        param["userEmail"] = userEmailRB
        param["title"] = titleRB
        param["province"] = provinceRB
        param["region"] = regionRB
        param["parkingDesc"] = parkingDescRB
        param["isParking"] = isParkingRB
        param["courseDesc"] = courseDescRB

        val call = com.charo.android.data.api.ApiService.writeViewService
            .writePost(sendWarning, sendTheme, sendCourse, sharedViewModel.imageMultiPart.value!!, param)
        call.enqueueUtil(
            onSuccess = {
                Log.d("serveWriteData", "success")
                Log.d("serveWriteData", it.toString())

                writeShareActivity!!.finish()
            },
            onError = {
                Log.d("serveWriteData", "failed")
                Log.d("serveWriteData", call.request().body.toString())
                Log.d("serveWriteData",param.toString())
                Log.d("serveWriteData ", "sendWarning $sendWarning")
                Log.d("serveWriteData", "sendTheme $sendTheme")
                Log.d("serveWriteData","course $sendTheme")
                Log.d("serveWriteData","image ${sharedViewModel.imageMultiPart.value!!}")
            },
        )
    }

    override fun onClick(v: View?) {
        when(v){
            binding.imgWriteMapBack -> {
                writeShareActivity?.onBackPressed()
            }
            binding.etWriteMapStart -> {
                sharedViewModel.locationFlag.value = Define().LOCATION_FLAG_START
                writeShareActivity!!.replaceAddStackFragment(
                    WriteMapSearchFragment.newInstance(),
                    "writeMapSearch"
                )
            }
            binding.etWriteMapMid1 -> {
                sharedViewModel.locationFlag.value = Define().LOCATION_FLAG_MID_FRST
                writeShareActivity!!.replaceAddStackFragment(
                    WriteMapSearchFragment.newInstance(),
                    "writeMapSearch"
                )
            }
            binding.etWriteMapMid2 -> {
                sharedViewModel.locationFlag.value = Define().LOCATION_FLAG_MID_SEC
                writeShareActivity!!.replaceAddStackFragment(
                    WriteMapSearchFragment.newInstance(),
                    "writeMapSearch"
                )
            }
            binding.etWriteMapEnd -> {
                if (!TextUtils.isEmpty(sharedViewModel.startAddress.value)) {
                    sharedViewModel.locationFlag.value = Define().LOCATION_FLAG_END
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