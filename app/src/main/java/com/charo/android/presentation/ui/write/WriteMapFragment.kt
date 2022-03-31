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
import com.charo.android.data.api.ApiService
import com.charo.android.databinding.FragmentWriteMapBinding
import com.charo.android.hidden.Hidden
import com.charo.android.presentation.util.CustomToast
import com.charo.android.presentation.util.Define
import com.charo.android.presentation.util.SharedInformation
import com.charo.android.presentation.util.enqueueUtil
import com.skt.Tmap.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
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
                binding.imgWriteMapAddAdress.visibility = View.GONE
            }
            else -> {
                binding.imgWriteMapAddAdress.visibility = View.VISIBLE
            }
        }

        if (!TextUtils.isEmpty(sharedViewModel.startAddress.value)) {
            binding.imgWriteMapAddAdress.visibility = View.VISIBLE
            binding.imgWriteMapAddAdress.isEnabled = true
        }else{
            binding.imgWriteMapAddAdress.visibility = View.VISIBLE
            binding.imgWriteMapAddAdress.isEnabled = false
        }

        if (!TextUtils.isEmpty(sharedViewModel.midFrstAddress.value)) {
            binding.etWriteMapMid1.visibility = View.VISIBLE
            binding.imgWriteMapDelete1.visibility = View.VISIBLE
            binding.imgWriteMapAddAdress.visibility = View.GONE
        }

        if (!TextUtils.isEmpty(sharedViewModel.endAddress.value)) {
            binding.btnWriteComplete.visibility = View.VISIBLE
        }

        binding.etWriteMapStart.text = sharedViewModel.startAddress.value
        binding.etWriteMapMid1.text = sharedViewModel.midFrstAddress.value
        binding.etWriteMapEnd.text = sharedViewModel.endAddress.value

        binding.imgWriteMapAddAdress.setOnClickListener{
            clickAddAddress()
        }
        binding.imgWriteMapDelete1.setOnClickListener {
            clickWriteMapDelete1(tMapView)
        }

        addList(tMapView)
    }

    private fun clickAddAddress(){
        if (!TextUtils.isEmpty(sharedViewModel.startAddress.value) && !TextUtils.isEmpty(sharedViewModel.endAddress.value)) {
            if (binding.etWriteMapMid1.visibility == View.GONE) {
                binding.etWriteMapMid1.visibility = View.VISIBLE
                binding.imgWriteMapDelete1.visibility = View.VISIBLE
                binding.imgWriteMapAddAdress.visibility = View.GONE

                binding.etWriteMapMid1.text = sharedViewModel.midFrstAddress.value
            }
        }else{
            Toast.makeText(requireContext(),getString(R.string.txt_check_input_start_end),Toast.LENGTH_LONG).show()
        }
    }

    private fun clickWriteMapDelete1(tMapView: TMapView) {
        binding.etWriteMapMid1.visibility = View.GONE
        binding.imgWriteMapDelete1.visibility = View.GONE
        binding.imgWriteMapAddAdress.visibility = View.VISIBLE
        binding.imgWriteMapAddAdress.isEnabled = true

        sharedViewModel.midFrstAddress.value = ""
        sharedViewModel.midFrstLat.value = 0.0
        sharedViewModel.midFrstLong.value = 0.0

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

    private fun blackOut(){
        val isShowBlackout = sharedViewModel.startAddress.value == "" && sharedViewModel.midFrstAddress.value == "" && sharedViewModel.endAddress.value == ""

        if(isShowBlackout){
            binding.grayBackgroundForToast.visibility = View.VISIBLE
            CustomToast.createToast(requireContext(), getString(R.string.noti_write_map))?.show()

        }else{
            binding.grayBackgroundForToast.visibility = View.GONE
        }
    }

    private fun clickWriteComplete() {
        if ((binding.etWriteMapMid1.visibility == View.VISIBLE && TextUtils.isEmpty(sharedViewModel.midFrstAddress.value))) {
            Toast.makeText(context, "경유지를 입력해주세요!", Toast.LENGTH_LONG).show()
            return
        }

        val course = ArrayList<MultipartBody.Part>()

        course.add(MultipartBody.Part.createFormData("course[0][address]",sharedViewModel.startAddress.value.toString()))
        course.add(MultipartBody.Part.createFormData("course[0][longitude]",sharedViewModel.startLong.value.toString()))
        course.add(MultipartBody.Part.createFormData("course[0][latitude]",sharedViewModel.startLat.value.toString()))

        //경유지 유
        if(sharedViewModel.midFrstAddress.value !== ""){
            course.add(MultipartBody.Part.createFormData("course[1][address]",sharedViewModel.midFrstAddress.value.toString()))
            course.add(MultipartBody.Part.createFormData("course[1][longitude]",sharedViewModel.midFrstLong.value.toString()))
            course.add(MultipartBody.Part.createFormData("course[1][latitude]",sharedViewModel.midFrstLat.value.toString()))

            course.add(MultipartBody.Part.createFormData("course[2][address]",sharedViewModel.endAddress.value.toString()))
            course.add(MultipartBody.Part.createFormData("course[2][longitude]",sharedViewModel.endLong.value.toString()))
            course.add(MultipartBody.Part.createFormData("course[2][latitude]",sharedViewModel.endLat.value.toString()))

            //경유지 무
        }else{
            course.add(MultipartBody.Part.createFormData("course[1][address]",sharedViewModel.endAddress.value.toString()))
            course.add(MultipartBody.Part.createFormData("course[1][longitude]",sharedViewModel.endLong.value.toString()))
            course.add(MultipartBody.Part.createFormData("course[1][latitude]",sharedViewModel.endLat.value.toString()))
        }

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

    private fun serveWriteData() {
        val param : HashMap<String, RequestBody> = HashMap()

        val sendTheme : HashMap<String,RequestBody> = HashMap()
        for (i in sharedViewModel.theme.value!!.indices) {
            sendTheme["theme[$i]"] = sharedViewModel.theme.value!![i].toRequestBody("multipart/form-data".toMediaTypeOrNull())
        }

        val sendWarning : HashMap<String,RequestBody> = HashMap()
        for (i in sharedViewModel.warningUI.value!!.indices) {
            sendWarning["warning[$i]"] = sharedViewModel.warningUI.value!![i].toRequestBody("multipart/form-data".toMediaTypeOrNull())
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

        val call = ApiService.writeViewService
            .writePost(sendWarning, sendTheme, sharedViewModel.course.value!!, sharedViewModel.imageMultiPart.value!!, param)
        call.enqueueUtil(
            onSuccess = {
                Log.d("serveWriteData", "success")
                Log.d("serveWriteData", it.toString())

                Toast.makeText(context,"게시물이 등록되었습니다.", Toast.LENGTH_LONG).show()
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