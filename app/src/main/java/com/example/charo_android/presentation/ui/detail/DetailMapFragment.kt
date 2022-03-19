package com.example.charo_android.presentation.ui.detail

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentDetailMapBinding
import com.example.charo_android.hidden.Hidden
import com.skt.Tmap.*

class DetailMapFragment : Fragment() {
    private var _binding: FragmentDetailMapBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by activityViewModels()
    private val pointList = arrayListOf<TMapPoint>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailMapBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tMapView = TMapView(requireContext())
        tMapView.setSKTMapApiKey(Hidden().tMapApiKey)
        binding.clDetailMapview.addView(tMapView)

        if(viewModel.detailData.value != null) {
            addList(tMapView)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addList(tMapView: TMapView) {
        viewModel.detailData.value!!.data.course.forEach {
            pointList.add(TMapPoint(it.latitude.toDouble(), it.longitude.toDouble()))
        }
//        setCenter(tMapView)
        mark(tMapView)
    }

    private fun setCenter(tMapView: TMapView) {
        Log.d("DetailMapFragment", "setCenter Called")
        val info: TMapInfo = tMapView.getDisplayTMapInfo(pointList)
        tMapView.setCenterPoint(info.tMapPoint.longitude, info.tMapPoint.latitude)
        tMapView.zoomLevel = info.tMapZoomLevel
    }

    private fun mark(tMapView: TMapView) {
        for (i in pointList.indices) {
            val marker = TMapMarkerItem()
            var bitmap: Bitmap = when (i) {
                0 -> BitmapFactory.decodeResource(resources, R.drawable.ic_route_start)
                pointList.size - 1 -> BitmapFactory.decodeResource(
                    resources,
                    R.drawable.ic_route_end
                )
                else -> BitmapFactory.decodeResource(resources, R.drawable.ic_route_waypoint)
            }
            marker.icon = bitmap
            marker.setPosition(0.5F, 1.0F)
            marker.tMapPoint = pointList[i]
            marker.name = "marker_location_flag_$i"
            tMapView.addMarkerItem(marker.name, marker)
        }
        findPath(tMapView)
    }

    private fun findPath(tMapView: TMapView) {
        val thread = Thread() {
            try {
                for (i in 0 until pointList.size - 1) {
                    val from = pointList[i]
                    val to = pointList[i + 1]
                    val tMapPolyLine: TMapPolyLine = TMapData().findPathData(from, to)
                    tMapPolyLine.lineWidth = 3F
                    tMapPolyLine.outLineColor = ContextCompat.getColor(requireContext(), R.color.blue_main_0f6fff)
                    tMapPolyLine.lineColor = ContextCompat.getColor(requireContext(), R.color.blue_main_0f6fff)
                    tMapView.addTMapPolyLine("tMapPolyLine$i", tMapPolyLine)
                }
                setCenter(tMapView)
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
    }
}