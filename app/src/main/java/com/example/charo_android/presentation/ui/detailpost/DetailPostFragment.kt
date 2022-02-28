package com.example.charo_android.presentation.ui.detailpost

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
import com.skt.Tmap.*
import com.skt.Tmap.TMapView.OnClickListenerCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailPostFragment : Fragment() {
    private var _binding: FragmentDetailPostBinding? = null
    private val binding get() = _binding ?: error("binding not initialized")
    private val viewModel: DetailPostViewModel by viewModel()
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
        viewModel.getDetailPostData(0)
        initTMap(tMapView)
        viewModel.detailPost.observe(viewLifecycleOwner) {
            initViewPager(it.images)
            drawPath(tMapView, it.course)
        }
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
                Toast.makeText(requireContext(), "onPressUpEvent", Toast.LENGTH_SHORT).show()
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
                val pointList = ArrayList<TMapPoint>()

                // ArrayList<TMapPoint>로 변환
                course.forEach {
                    pointList.add(TMapPoint(it.latitude, it.longitude))
                }

                // Marker 생성
                for (i in pointList.indices) {
                    val marker = TMapMarkerItem()
                    val bitmap: Bitmap = when (i) {
                        0 -> BitmapFactory.decodeResource(resources, R.drawable.ic_route_start)
                        pointList.size - 1 -> BitmapFactory.decodeResource(
                            resources,
                            R.drawable.ic_route_end
                        )
                        else -> BitmapFactory.decodeResource(
                            resources,
                            R.drawable.ic_route_waypoint
                        )
                    }
                    marker.icon = bitmap
                    marker.setPosition(0.5F, 1.0F)
                    marker.tMapPoint = pointList[i]
                    marker.name = "marker$i"
                    tMapView.addMarkerItem(marker.name, marker)
                }

                // Path 그리기
                for (i in 0 until pointList.size - 1) {
                    val from = pointList[i]
                    val to = pointList[i + 1]
                    var tMapPolyLine: TMapPolyLine
                    withContext(Dispatchers.IO) {
                        tMapPolyLine = TMapData().findPathData(from, to)
                    }
                    tMapPolyLine.lineWidth = 3F
                    tMapPolyLine.outLineColor =
                        ContextCompat.getColor(requireContext(), R.color.blue_main_0f6fff)
                    tMapPolyLine.lineColor =
                        ContextCompat.getColor(requireContext(), R.color.blue_main_0f6fff)
                    tMapView.addTMapPolyLine("tMapPolyLine$i", tMapPolyLine)
                }

                // 지도 중앙 맞춰주기
                val info: TMapInfo = tMapView.getDisplayTMapInfo(pointList)
                tMapView.setCenterPoint(info.tMapPoint.longitude, info.tMapPoint.latitude)
                tMapView.zoomLevel = info.tMapZoomLevel
            }.onFailure {
                // 실패 시 액티비티 종료
                requireActivity().finish()
                Log.e("mlog: DetailPostFragment::Path 그리기", it.message.toString())
            }
        }
    }
}