package com.charo.android.presentation.ui.write

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.SystemClock
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.charo.android.BuildConfig.TMAP_API_KEY
import com.charo.android.R
import com.charo.android.data.api.ApiService
import com.charo.android.databinding.FragmentWriteMapBinding
import com.charo.android.presentation.util.CustomToast
import com.charo.android.presentation.util.Define
import com.charo.android.presentation.util.SharedInformation
import com.charo.android.presentation.util.enqueueUtil
import com.skt.Tmap.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class WriteMapFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = WriteMapFragment()
    }

    private lateinit var tMapView: TMapView

    private val sharedViewModel by sharedViewModel<WriteSharedViewModel>()

    //    좌표 배열
    var path = arrayListOf<TMapPoint>()
    var isDrawing = false
    private var mLastClickTime = 0L

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

        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Main) {
                tMapView = TMapView(context)
                tMapView.setSKTMapApiKey(TMAP_API_KEY)
                binding.clWriteTmapView.addView(tMapView)
            }

            if (sharedViewModel.editFlag.value == true && sharedViewModel.editMapFlag.value == true) {
                sharedViewModel.initEditMapData()
            }

            fillTextView(locationFlag, tMapView)
        }

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
        when (locationFlag) {
            Define().LOCATION_FLAG_MID_FRST -> { // 경유지1인 경우
                binding.etWriteMapMid1.visibility = View.VISIBLE
                binding.imgWriteMapDelete1.visibility = View.VISIBLE
                binding.imgWriteMapAddAdress.visibility = View.GONE
                Timber.tag("fillTextView()").i("locationFlag == 경유지1")
            }
            else -> {
                binding.imgWriteMapAddAdress.visibility = View.VISIBLE
                Timber.tag("fillTextView()").i("locationFlag != 경유지1")
            }
        }

        if (!TextUtils.isEmpty(sharedViewModel.startAddress.value)) {
            binding.imgWriteMapAddAdress.visibility = View.VISIBLE
            binding.imgWriteMapAddAdress.isEnabled = true
            Timber.tag("fillTextView()").i("출발지 채워져있음")
        } else {
            binding.imgWriteMapAddAdress.visibility = View.VISIBLE
            binding.imgWriteMapAddAdress.isEnabled = false
            Timber.tag("fillTextView()").i("출발지 비워져있음")
        }

        if (!TextUtils.isEmpty(sharedViewModel.midFrstAddress.value)) {
            binding.etWriteMapMid1.visibility = View.VISIBLE
            binding.imgWriteMapDelete1.visibility = View.VISIBLE
            binding.imgWriteMapAddAdress.visibility = View.GONE
            Timber.tag("fillTextView()").i("경유지 채워져있음")
        }

        if (!TextUtils.isEmpty(sharedViewModel.endAddress.value)) {
            binding.btnWriteComplete.visibility = View.VISIBLE
            Timber.tag("fillTextView()").i("도착지 채워져있음")
        }

        binding.etWriteMapStart.text = sharedViewModel.startAddress.value
        binding.etWriteMapMid1.text = sharedViewModel.midFrstAddress.value
        binding.etWriteMapEnd.text = sharedViewModel.endAddress.value

        binding.imgWriteMapAddAdress.setOnClickListener {
            clickAddAddress()
        }
        binding.imgWriteMapDelete1.setOnClickListener {
            clickWriteMapDelete1(tMapView)
        }

        drawPath(tMapView)
    }

    private fun clickAddAddress() {
        if (!TextUtils.isEmpty(sharedViewModel.startAddress.value) && !TextUtils.isEmpty(
                sharedViewModel.endAddress.value
            )
        ) {
            if (binding.etWriteMapMid1.visibility == View.GONE) {
                binding.etWriteMapMid1.visibility = View.VISIBLE
                binding.imgWriteMapDelete1.visibility = View.VISIBLE
                binding.imgWriteMapAddAdress.visibility = View.GONE

                binding.etWriteMapMid1.text = sharedViewModel.midFrstAddress.value
            }
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.txt_check_input_start_end),
                Toast.LENGTH_LONG
            ).show()
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
        drawPath(tMapView)
    }

    private fun drawPath(tMapView: TMapView) {
        isDrawing = false
        CoroutineScope(Dispatchers.Main).launch {
            kotlin.runCatching {
                // ArrayList<TMapPoint>로 변환
                val pointList = ArrayList<TMapPoint>()
                if (sharedViewModel.startLat.value != 0.0) {
                    pointList.add(
                        TMapPoint(
                            sharedViewModel.startLat.value!!,
                            sharedViewModel.startLong.value!!
                        )
                    )
                }
                if (sharedViewModel.midFrstLat.value != 0.0) {
                    pointList.add(
                        TMapPoint(
                            sharedViewModel.midFrstLat.value!!,
                            sharedViewModel.midFrstLong.value!!
                        )
                    )
                }
                if (sharedViewModel.endLat.value != 0.0) {
                    pointList.add(
                        TMapPoint(
                            sharedViewModel.endLat.value!!,
                            sharedViewModel.endLong.value!!
                        )
                    )
                }

                // Marker 생성
                val markerList = mutableListOf<TMapMarkerItem>()
                val polyLineList = mutableListOf<TMapPolyLine>()
                for (i in pointList.indices) {
                    val marker = TMapMarkerItem()
                    val bitmap: Bitmap = when (i) {
                        // 출발지
                        0 -> BitmapFactory.decodeResource(resources, R.drawable.ic_route_start)
                        // 도착지
                        pointList.size - 1 -> BitmapFactory.decodeResource(
                            resources,
                            R.drawable.ic_route_end
                        )
                        // 경유지
                        else -> BitmapFactory.decodeResource(
                            resources,
                            R.drawable.ic_route_waypoint
                        )
                    }
                    marker.apply {
                        icon = bitmap
                        setPosition(0.5F, 1.0F)
                        tMapPoint = pointList[i]
                        name = "marker_location_flag_$i"
                    }
                    markerList.add(marker)

                    // 경로 그리기
                    if (i != pointList.size - 1) {
                        val from = pointList[i]
                        val to = pointList[i + 1]
                        var tMapPolyLine: TMapPolyLine
                        withContext(Dispatchers.IO) {
                            tMapPolyLine = TMapData().findPathData(from, to)
                        }
                        tMapPolyLine.apply {
                            lineWidth = 3F
                            outLineColor =
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.blue_main_0f6fff
                                )
                            lineColor =
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.blue_main_0f6fff
                                )
                        }
                        polyLineList.add(tMapPolyLine)
                    }
                }

                //이전 경로 초기화 후 그리기
                tMapView.removeAllTMapPolyLine()
                tMapView.removeAllMarkerItem()
                for (i in markerList.indices) {
                    when (i) {
                        markerList.size - 1 -> {
                            tMapView.addMarkerItem("marker$i", markerList[i])
                        }
                        else -> {
                            tMapView.addMarkerItem("marker$i", markerList[i])
                            tMapView.addTMapPolyLine("polyLine$i", polyLineList[i])
                        }
                    }
                }

                // 지도 중앙 맞춰주기
                if (pointList.isEmpty()) {
                    tMapView.setCenterPoint(127.840076, 35.838083)
                    tMapView.zoomLevel = 6
                } else {
                    val info: TMapInfo =
                        tMapView.getDisplayTMapInfo(pointList)
                    tMapView.setCenterPoint(info.tMapPoint.longitude, info.tMapPoint.latitude)
                    tMapView.zoomLevel = info.tMapZoomLevel
                }
                isDrawing = true
            }.onFailure {
                Toast.makeText(requireContext(),getString(R.string.server_error_general),Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun blackOut() {
        val isShowBlackout =
            (sharedViewModel.startAddress.value == "" && sharedViewModel.midFrstAddress.value == "" && sharedViewModel.endAddress.value == "")
                    && sharedViewModel.editFlag.value != true

        if (isShowBlackout) {
            binding.grayBackgroundForToast.visibility = View.VISIBLE
            CustomToast.createToast(requireContext(), getString(R.string.noti_write_map))
                ?.show()

        } else {
            binding.grayBackgroundForToast.visibility = View.GONE
        }
    }

    private fun clickWriteComplete() {
        if ((binding.etWriteMapMid1.visibility == View.VISIBLE && TextUtils.isEmpty(
                sharedViewModel.midFrstAddress.value
            ))
        ) {
            Toast.makeText(context, "경유지를 입력해주세요!", Toast.LENGTH_LONG).show()
            return
        }

        if(!isDrawing){
            Toast.makeText(context, "경로를 그리는 중이에요. 잠시만 기다려주세요!", Toast.LENGTH_LONG).show()
            return
        }

        val course = ArrayList<MultipartBody.Part>()
        course.add(
            MultipartBody.Part.createFormData(
                "course[0][address]",
                sharedViewModel.startAddress.value.toString()
            )
        )
        course.add(
            MultipartBody.Part.createFormData(
                "course[0][longitude]",
                sharedViewModel.startLong.value.toString()
            )
        )
        course.add(
            MultipartBody.Part.createFormData(
                "course[0][latitude]",
                sharedViewModel.startLat.value.toString()
            )
        )

        //경유지 유
        if (sharedViewModel.midFrstAddress.value !== "") {
            course.add(
                MultipartBody.Part.createFormData(
                    "course[1][address]",
                    sharedViewModel.midFrstAddress.value.toString()
                )
            )
            course.add(
                MultipartBody.Part.createFormData(
                    "course[1][longitude]",
                    sharedViewModel.midFrstLong.value.toString()
                )
            )
            course.add(
                MultipartBody.Part.createFormData(
                    "course[1][latitude]",
                    sharedViewModel.midFrstLat.value.toString()
                )
            )

            course.add(
                MultipartBody.Part.createFormData(
                    "course[2][address]",
                    sharedViewModel.endAddress.value.toString()
                )
            )
            course.add(
                MultipartBody.Part.createFormData(
                    "course[2][longitude]",
                    sharedViewModel.endLong.value.toString()
                )
            )
            course.add(
                MultipartBody.Part.createFormData(
                    "course[2][latitude]",
                    sharedViewModel.endLat.value.toString()
                )
            )

            //경유지 무
        } else {
            course.add(
                MultipartBody.Part.createFormData(
                    "course[1][address]",
                    sharedViewModel.endAddress.value.toString()
                )
            )
            course.add(
                MultipartBody.Part.createFormData(
                    "course[1][longitude]",
                    sharedViewModel.endLong.value.toString()
                )
            )
            course.add(
                MultipartBody.Part.createFormData(
                    "course[1][latitude]",
                    sharedViewModel.endLat.value.toString()
                )
            )
        }

        sharedViewModel.course.value = course

        AlertDialog.Builder(requireContext(), R.style.Dialog)
            .setMessage(getString(R.string.noti_complete_write))
            .setNegativeButton(getString(R.string.word_no)) { dialog, which ->
            }
            .setPositiveButton(getString(R.string.word_yes)) { dialog, which ->
                serveWriteData()
            }
            .show()
    }

    private fun serveWriteData() {
        val param: HashMap<String, RequestBody> = HashMap()

        val sendTheme: HashMap<String, RequestBody> = HashMap()
        for (i in sharedViewModel.theme.value!!.indices) {
            sendTheme["theme[$i]"] =
                sharedViewModel.theme.value!![i].toRequestBody("multipart/form-data".toMediaTypeOrNull())
        }

        val sendWarning: HashMap<String, RequestBody> = HashMap()
        for (i in sharedViewModel.warningUI.value!!.indices) {
            sendWarning["warning[$i]"] =
                sharedViewModel.warningUI.value!![i].toRequestBody("multipart/form-data".toMediaTypeOrNull())
        }

        val userEmail = SharedInformation.getEmail(requireActivity())
        val userEmailRB: RequestBody = userEmail.toRequestBody("text/plain".toMediaTypeOrNull())
        val titleRB: RequestBody =
            sharedViewModel.title.value!!.toRequestBody("text/plain".toMediaTypeOrNull())
        val provinceRB: RequestBody =
            sharedViewModel.province.value!!.toRequestBody("text/plain".toMediaTypeOrNull())
        val regionRB: RequestBody =
            sharedViewModel.region.value!!.toRequestBody("text/plain".toMediaTypeOrNull())
        val parkingDescRB: RequestBody =
            sharedViewModel.parkingDesc.value!!.toRequestBody("text/plain".toMediaTypeOrNull())
        val courseDescRB: RequestBody =
            sharedViewModel.courseDesc.value!!.toRequestBody("text/plain".toMediaTypeOrNull())
        val isParkingRB: RequestBody = sharedViewModel.isParking.value!!.toString()
            .toRequestBody("text/plain".toMediaTypeOrNull())

        param["userEmail"] = userEmailRB
        param["title"] = titleRB
        param["province"] = provinceRB
        param["region"] = regionRB
        param["parkingDesc"] = parkingDescRB
        param["isParking"] = isParkingRB
        param["courseDesc"] = courseDescRB

        when (sharedViewModel.editFlag.value) {
            true -> {
                // 수정하기
                val postIdRB: RequestBody = sharedViewModel.postId.toString()
                    .toRequestBody("text/plain".toMediaTypeOrNull())
                param["postId"] = postIdRB
                val postId = sharedViewModel.postId
                val deleted = ArrayList<MultipartBody.Part>()
                val call = ApiService.writeViewService
                    .editPost(
                        sendWarning,
                        sendTheme,
                        sharedViewModel.course.value!!,
                        sharedViewModel.imageMultiPart.value!!,
                        param
                    )
                call.enqueueUtil(
                    onSuccess = {
                        Timber.i("수정하기 성공")
                        Timber.i("수정하기 결과: $it")

                        Toast.makeText(requireContext(), "게시물이 수정되었습니다", Toast.LENGTH_LONG)
                            .show()
                        requireActivity().finish()
                    },
                    onError = {
                        Timber.i("수정하기 failed")
                        Timber.i("수정하기 ${call.request().body.toString()}")
                        Timber.i("수정하기 $param")
                        Timber.i("수정하기 sendWarning $sendWarning")
                        Timber.i("수정하기 sendTheme $sendTheme")
                        Timber.i("수정하기 course $sendTheme")
                        Timber.i("수정하기 image ${sharedViewModel.imageMultiPart.value!!}")
                        Timber.i("수정하기")

                        Toast.makeText(context, R.string.server_error_general, Toast.LENGTH_LONG).show()
                    }
                )
            }
            else -> {
                // 작성하기
                val call = ApiService.writeViewService
                    .writePost(
                        sendWarning,
                        sendTheme,
                        sharedViewModel.course.value!!,
                        sharedViewModel.imageMultiPart.value!!,
                        param
                    )
                call.enqueueUtil(
                    onSuccess = {
                        Timber.d("serveWriteData success")
                        Timber.d("serveWriteData $it")

                        Toast.makeText(context, "게시물이 등록되었습니다.", Toast.LENGTH_LONG).show()
                        writeShareActivity!!.finish()
                    },
                    onError = {
                        Timber.d("serveWriteData failed")
                        Timber.d("serveWriteData ${call.request().body.toString()}")
                        Timber.d("serveWriteData $param")
                        Timber.d("serveWriteData  sendWarning $sendWarning")
                        Timber.d("serveWriteData sendTheme $sendTheme")
                        Timber.d("serveWriteData course $sendTheme")
                        Timber.d("serveWriteData image ${sharedViewModel.imageMultiPart.value!!}")

                        Toast.makeText(context, R.string.server_error_general, Toast.LENGTH_LONG).show()
                    },
                )
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
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
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.start),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
            binding.btnWriteComplete -> {
                //더블클릭 방지
                if (SystemClock.elapsedRealtime() - mLastClickTime > 800) {
                    clickWriteComplete()
                }
                mLastClickTime = SystemClock.elapsedRealtime()
            }
        }
    }
}