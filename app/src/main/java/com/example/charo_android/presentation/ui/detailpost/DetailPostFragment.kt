package com.example.charo_android.presentation.ui.detailpost

import android.content.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentDetailPostBinding
import com.example.charo_android.domain.model.detailpost.DetailPost
import com.example.charo_android.hidden.Hidden
import com.example.charo_android.presentation.ui.detailpost.adapter.DetailPostViewPagerAdapter
import com.example.charo_android.presentation.ui.detailpost.viewmodel.DetailPostViewModel
import com.example.charo_android.presentation.ui.mypage.other.OtherMyPageActivity
import com.skt.Tmap.*
import com.skt.Tmap.TMapView.OnClickListenerCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DetailPostFragment : Fragment() {
    private var _binding: FragmentDetailPostBinding? = null
    private val binding get() = _binding ?: error("binding not initialized")
    private val viewModel by sharedViewModel<DetailPostViewModel>()
    private lateinit var viewPagerAdapter: DetailPostViewPagerAdapter

    private lateinit var tMapView: TMapView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_detail_post, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tMapView = TMapView(requireContext())
        viewModel.getDetailPostData()
        initTMap(tMapView)
        viewModel.detailPost.observe(viewLifecycleOwner) {
            initViewPager(it.images)
            drawPath(tMapView, it.course)
        }
        copyAddress()
        clickLike()
        clickSave()
        clickShare()
        goBack()
        clickAuthor()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initViewPager(imageList: List<String>) {
        viewPagerAdapter = DetailPostViewPagerAdapter()
        viewPagerAdapter.replaceItem(imageList)
        binding.vpPost.adapter = viewPagerAdapter
    }

    private fun initTMap(tMapView: TMapView) {
        tMapView.setSKTMapApiKey(Hidden().tMapApiKey)
        tMapView.setUserScrollZoomEnable(true)
        tMapView.setOnClickListenerCallBack(object : OnClickListenerCallback {
            override fun onPressUpEvent(
                markerlist: ArrayList<TMapMarkerItem>?,
                poilist: ArrayList<TMapPOIItem>?,
                point: TMapPoint?,
                pointf: PointF?
            ): Boolean {
                clickMap()
                return false
            }

            override fun onPressEvent(
                markerlist: ArrayList<TMapMarkerItem>?,
                poilist: ArrayList<TMapPOIItem>?,
                point: TMapPoint?,
                pointf: PointF?
            ): Boolean {
                return false
            }
        })
        binding.clPostMap.addView(tMapView)
    }

    private fun drawPath(tMapView: TMapView, course: List<DetailPost.Course>) {
        CoroutineScope(Dispatchers.Main).launch {
            kotlin.runCatching {
                // ArrayList<TMapPoint>로 변환
                val pointList = course.map {
                    TMapPoint(it.latitude, it.longitude)
                }

                // 지도 중앙 맞춰주기
                val info: TMapInfo =
                    tMapView.getDisplayTMapInfo(pointList as java.util.ArrayList<TMapPoint>?)
                tMapView.setCenterPoint(info.tMapPoint.longitude, info.tMapPoint.latitude)
                tMapView.zoomLevel = info.tMapZoomLevel

                // Marker 생성
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
                    tMapView.addMarkerItem(marker.name, marker)

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
                                ContextCompat.getColor(requireContext(), R.color.blue_main_0f6fff)
                            lineColor =
                                ContextCompat.getColor(requireContext(), R.color.blue_main_0f6fff)
                        }
                        tMapView.addTMapPolyLine(
                            "tMapPolyLine$i",
                            tMapPolyLine
                        )
                    }
                }
            }.onFailure {
                // 실패 시 액티비티 종료 -> 추후엔 종료 말고 뭔가 다른 액션이 있었으면 좋겠다고 생각은 함(다이얼로그라던가 ...)
                requireActivity().finish()
                Log.e("mlog: DetailPostFragment::Path 그리기", it.message.toString())
            }
        }
    }

    private fun copyAddress() {
        binding.clPostAddressCopyStart.setOnClickListener {
            copyAddressToClipboard(binding.tvPostAddressStart.text.toString())
        }
        binding.clPostAddressCopyVia.setOnClickListener {
            copyAddressToClipboard(binding.tvPostAddressVia.text.toString())
        }
        binding.clPostAddressCopyEnd.setOnClickListener {
            copyAddressToClipboard(binding.tvPostAddressEnd.text.toString())
        }
    }

    private fun copyAddressToClipboard(address: String) {
        val clipboardManager =
            requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("Charo Address", address)
        clipboardManager.setPrimaryClip(clip)
        Toast.makeText(requireContext(), "클립보드에 복사되었습니다.", Toast.LENGTH_LONG).show()
    }

    private fun clickLike() {
        binding.imgDetailLike.setOnClickListener {
            viewModel.postLike()
        }
    }

    private fun clickSave() {
        binding.imgDetailSave.setOnClickListener {
            viewModel.postSave()
        }
    }

    private fun goBack() {
        binding.clBack.setOnClickListener {
            requireActivity().finish()
        }
    }

    private fun clickShare() {
        binding.imgDetailShare.setOnClickListener {
            try {
                // TODO: 나중에 postId 바꾸기
//                val deepLink =
//                    "http://www.charo.com/detail/${(activity as DetailPostActivity).postId}" //딥링크
                val deepLink =
                    "http://www.charo.com/detail/8" //딥링크
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "*/*"
                intent.putExtra(Intent.EXTRA_TEXT, deepLink) // text는 공유하고 싶은 글자

                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

                val chooser = Intent.createChooser(intent, "공유하기")
                startActivity(chooser)

            } catch (ignored: ActivityNotFoundException) {
                Log.d("test", "ignored : $ignored")
            }
        }
    }

    private fun clickMap() {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fcv_detail_post, DetailPostMapFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun clickAuthor() {
        binding.imgAuthor.setOnClickListener {
            val intent = Intent(requireContext(), OtherMyPageActivity::class.java)
            intent.putExtra("userEmail", viewModel.detailPost.value?.authorEmail)
            startActivity(intent)
        }
        binding.tvAuthorNickname.setOnClickListener {
            val intent = Intent(requireContext(), OtherMyPageActivity::class.java)
            intent.putExtra("userEmail", viewModel.detailPost.value?.authorEmail)
            startActivity(intent)
        }
    }
}