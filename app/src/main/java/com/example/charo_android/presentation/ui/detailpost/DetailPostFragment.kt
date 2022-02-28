package com.example.charo_android.presentation.ui.detailpost

import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentDetailPostBinding
import com.example.charo_android.hidden.Hidden
import com.example.charo_android.presentation.ui.detailpost.adapter.DetailPostViewPagerAdapter
import com.example.charo_android.presentation.ui.detailpost.viewmodel.DetailPostViewModel
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPOIItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import com.skt.Tmap.TMapView.OnClickListenerCallback
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailPostFragment : Fragment() {
    private var _binding: FragmentDetailPostBinding? = null
    private val binding get() = _binding ?: error("binding not initialized")
    private val viewModel: DetailPostViewModel by viewModel()
    private lateinit var viewPagerAdapter: DetailPostViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_detail_post, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDetailPostData(0)
        initViewPager()
        initTMap()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initViewPager() {
        viewPagerAdapter = DetailPostViewPagerAdapter()
        viewModel.detailPost.observe(viewLifecycleOwner) {
            viewPagerAdapter.replaceItem(it.images)
        }
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