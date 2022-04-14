package com.charo.android.presentation.ui.detailpost

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.charo.android.R
import com.charo.android.databinding.FragmentDetailPostMapBinding
import com.charo.android.domain.model.detailpost.DetailPost
import com.charo.android.hidden.Hidden
import com.charo.android.presentation.ui.detailpost.viewmodel.DetailPostViewModel
import com.skt.Tmap.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class DetailPostMapFragment : Fragment() {
    private var _binding: FragmentDetailPostMapBinding? = null
    private val binding get() = _binding ?: error("binding not initialized")
    private val viewModel by sharedViewModel<DetailPostViewModel>()
    private lateinit var tMapView: TMapView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_detail_post_map, container, false)
        tMapView = TMapView(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMap(tMapView)
    }

    private fun initMap(tMapView: TMapView) {
        tMapView.setSKTMapApiKey(Hidden().tMapApiKey)
        binding.clDetailPostMap.addView(tMapView)

        viewModel.detailPost.observe(viewLifecycleOwner) {
            drawPath(tMapView, it.course)
        }
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
                        name = "marker$i"
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
                Timber.e("mlog: DetailPostFragment::Path 그리기 ${it.message.toString()}")
            }
        }
    }
}