package com.example.charo_android.presentation.ui.detailpost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentDetailPostBinding
import com.example.charo_android.hidden.Hidden
import com.example.charo_android.presentation.ui.detail.DetailLikeFragment
import com.example.charo_android.presentation.ui.detailpost.adapter.DetailPostViewPagerAdapter
import com.skt.Tmap.TMapView
import android.graphics.PointF
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPOIItem

import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView.OnClickListenerCallback


class DetailPostFragment : Fragment() {
    private var _binding: FragmentDetailPostBinding? = null
    private val binding get() = _binding ?: error("binding not initialized")
    private lateinit var viewPagerAdapter: DetailPostViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_detail_post, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        initTMap()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initViewPager() {
        viewPagerAdapter = DetailPostViewPagerAdapter()
        binding.vpPost.adapter = viewPagerAdapter
    }

    private fun initTMap() {
        val tMapView = TMapView(requireContext())
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
}