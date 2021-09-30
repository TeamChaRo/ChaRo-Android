package com.example.charo_android.presentation.ui.detail

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentDetailBinding
import com.example.charo_android.hidden.Hidden
import com.skt.Tmap.*
import kotlinx.coroutines.Dispatchers

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by activityViewModels()
    private val viewPagerAdapter = DetailViewpagerAdapter()

    private val pointList = arrayListOf<TMapPoint>()
    private var detailActivity: DetailActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        detailActivity = context as DetailActivity
    }

    // Coroutine
//    private val mainDispatcher = Dispatchers.Main
//    private val ioDispatcher = Dispatchers.IO

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)

        val postId = (activity as DetailActivity).postId
        val title = (activity as DetailActivity).title
        val date = (activity as DetailActivity).date
        val imageUrl = (activity as DetailActivity).imageUrl
        val region = (activity as DetailActivity).region

        // tMapView 생성
        val tMapView = TMapView(requireContext())
        tMapView.setSKTMapApiKey(Hidden.tMapApiKey)
        tMapView.setUserScrollZoomEnable(true)
        binding.clDetailMapview.addView(tMapView)

        // ViewModel LiveData
        viewModel.getData(postId, title, date, region)
        viewModel.detailData.observe(viewLifecycleOwner, {
            Log.d("detail", "observed")
            binding.detailData = viewModel
            if (viewModel.detailData.value != null) {
                // Add Preview Image
                viewModel.addImageAtFront(imageUrl)
                // ViewPager
                initViewPager(viewModel.detailData.value!!.data.images)
                // tMapView
                addList(tMapView)
            }
        })

        // 좋아요 클릭 이벤트
        binding.imgDetailLike.setOnClickListener {
            clickLike(postId)
        }

        // 저장하기 클릭 이벤트
        binding.imgDetailSave.setOnClickListener {
            clickSave(postId)
        }

        // 뒤로가기 클릭 이벤트
        binding.imgDetailIconBack.setOnClickListener {
            detailActivity?.onBackPressed()
        }

        // 주소 복사 클릭 이벤트
        binding.imgDetailMapStartCopy.setOnClickListener {
            copyAddress(binding.tvDetailMapStartAddress)
        }
        binding.imgDetailMapViaCopy.setOnClickListener {
            copyAddress(binding.tvDetailMapViaAddress)
        }
        binding.imgDetailMapEndCopy.setOnClickListener {
            copyAddress(binding.tvDetailMapEndAddress)
        }

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initViewPager(imgList: List<String>) {
        binding.viewpagerDetailImage.adapter = viewPagerAdapter
        imgList.forEach {
            viewPagerAdapter.itemList.add(it)
        }
        viewPagerAdapter.notifyDataSetChanged()
    }

    private fun clickLike(postId: Int) {
        viewModel.postLike(postId)

        when (binding.imgDetailLike.isSelected) {
            false -> {
                binding.imgDetailLike.isSelected = true
                if (viewModel.detailData.value!!.data.isFavorite == 1) {
                    binding.tvDetailLike.text = getString(
                        R.string.like_count,
                        viewModel.detailData.value!!.data.likesCount
                    )
                } else {
                    binding.tvDetailLike.text = getString(
                        R.string.like_count,
                        viewModel.detailData.value!!.data.likesCount + 1
                    )
                }
            }
            true -> {
                binding.imgDetailLike.isSelected = false
                if (viewModel.detailData.value!!.data.isFavorite == 1) {
                    binding.tvDetailLike.text = getString(
                        R.string.like_count,
                        viewModel.detailData.value!!.data.likesCount - 1
                    )
                } else {
                    binding.tvDetailLike.text = getString(
                        R.string.like_count,
                        viewModel.detailData.value!!.data.likesCount
                    )
                }
            }
        }
    }

    private fun clickSave(postId: Int) {
        viewModel.postSave(postId)
        binding.imgDetailSave.isSelected = !binding.imgDetailSave.isSelected
    }

    private fun addList(tMapView: TMapView) {
        // 더미데이터 문
        viewModel.detailData.value!!.data.course.forEach {
            Log.d("latitude", it.latitude)
            Log.d("longitude", it.longitude)
            pointList.add(TMapPoint(it.latitude.toDouble(), it.longitude.toDouble()))
        }
        setCenter(tMapView)
        mark(tMapView)
    }

    private fun setCenter(tMapView: TMapView) {
        val info: TMapInfo = tMapView.getDisplayTMapInfo(pointList)
        Log.d("info.tMapPoint.longitude", info.tMapPoint.longitude.toString())
        Log.d("info.tMapPoint.latitude", info.tMapPoint.latitude.toString())
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
            marker.name = "marker$i"
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
                    tMapPolyLine.outLineColor = ContextCompat.getColor(requireContext(), R.color.blue_main)
                    tMapPolyLine.lineColor = ContextCompat.getColor(requireContext(), R.color.blue_main)
                    tMapView.addTMapPolyLine("tMapPolyLine$i", tMapPolyLine)
                }
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
    }

    private fun copyAddress(textView: TextView) {
        var clipboardManager = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val address: String = binding.tvDetailMapStartAddress.text.toString()
        val clip: ClipData = ClipData.newPlainText("Start Point Address", address)
        clipboardManager.setPrimaryClip(clip)
        Toast.makeText(requireContext(), "클립보드에 복사되었습니다.", Toast.LENGTH_LONG).show()
    }
}