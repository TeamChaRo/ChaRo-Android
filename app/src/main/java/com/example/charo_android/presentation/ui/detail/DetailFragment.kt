package com.example.charo_android.presentation.ui.detail

import android.annotation.SuppressLint
import android.content.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentDetailBinding
import com.example.charo_android.hidden.Hidden
import com.skt.Tmap.*

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by activityViewModels()

    //    private val viewPagerAdapter = DetailViewpagerAdapter()
    private lateinit var viewPagerAdapter: DetailViewpagerAdapter

    private val pointList = arrayListOf<TMapPoint>()
    private var detailActivity: DetailActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        detailActivity = context as DetailActivity
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)

        val postId = (activity as DetailActivity).postId
        val title = (activity as DetailActivity).title
        val date = (activity as DetailActivity).date
        val region = (activity as DetailActivity).region

        viewPagerAdapter = DetailViewpagerAdapter() {
            val intent = Intent(requireContext(), DetailImageActivity::class.java)
            val imageList: ArrayList<String> = ArrayList()
            viewModel.detailData.observe(viewLifecycleOwner, {
                viewModel.detailData.value!!.data.images.forEach {
                    imageList.add(it)
                }
                intent.putExtra("imageList", imageList)
            })
            startActivity(intent)
        }

        // ViewModel LiveData
        viewModel.setPostId(postId)
        if (viewModel.detailData.value == null) {
            Log.d("viewModel", "getData() execute")
            viewModel.getData(postId, title, date, region)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // tMapView 생성
        val tMapView = TMapView(requireContext())
        tMapView.setSKTMapApiKey(Hidden().tMapApiKey)
        tMapView.setUserScrollZoomEnable(true)
        binding.clDetailMapview.addView(tMapView)

        val imageUrl = (activity as DetailActivity).imageUrl
        viewModel.detailData.observe(viewLifecycleOwner, {
            Log.d("detail", "observed")
            binding.detailData = viewModel
            if (viewModel.detailData.value != null) {
                if (viewPagerAdapter.itemList.isEmpty()) {
                    // Add Preview Image
                    viewModel.addImageAtFront(imageUrl)
                }
                // ViewPager
                initViewPager(viewModel.detailData.value!!.data.images)
                // tMapView
                addList(tMapView)
            }
        })

        // 좋아요 클릭 이벤트
        binding.imgDetailLike.setOnClickListener {
            clickLike((activity as DetailActivity).postId)
        }

        // 저장하기 클릭 이벤트
        binding.imgDetailSave.setOnClickListener {
            clickSave((activity as DetailActivity).postId)
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

        //공유하기 클릭 이벤트
        binding.imgDetailShare.setOnClickListener {
            clickShare()
        }

        // 지도 클릭 이벤트
        binding.clDetailMapviewTouch.setOnClickListener {
            (context as DetailActivity).openFragment(1)
        }

        // n명이 좋아해요 클릭 이벤트
        binding.tvDetailLike.setOnClickListener {
            openBottomSheetDialog()
        }
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    private fun initViewPager(imgList: List<String>) {
        // viewPager registerOnPageChangeCallback
        binding.viewpagerDetailImage.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.e("Selected_Page", position.toString())
                binding.tvDetailViewpagerImage.text =
                    "${position + 1}/${viewPagerAdapter.itemList.size}"
            }
        })

        binding.viewpagerDetailImage.adapter = viewPagerAdapter
        viewPagerAdapter.itemList.clear()
        imgList.forEach {
            viewPagerAdapter.itemList.add(it)
        }
        viewPagerAdapter.notifyDataSetChanged()
        binding.tvDetailViewpagerImage.text = "1/${viewPagerAdapter.itemList.size}"
    }

    private fun clickShare(){
        try {
            val appDownload = "https://developer.android.com/training/sharing/" //앱 설치 페이지
            val intent = Intent(Intent.ACTION_SEND)
//            intent.type = "text/plain"
            intent.type = "*/*"
            intent.putExtra(Intent.EXTRA_TEXT, appDownload) // text는 공유하고 싶은 글자

            // (Optional) Here we're setting the title of the content
            intent.putExtra(Intent.EXTRA_TITLE, "게시물 공유하기")

            // (Optional) Here we're passing a content URI to an image to be displayed
            //data = contentUri
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

            val chooser = Intent.createChooser(intent, "공유하기")
            startActivity(chooser)

        } catch (ignored: ActivityNotFoundException) {
            Log.d("test", "ignored : $ignored")
        }
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
        if (pointList.isEmpty()) {
            viewModel.detailData.value!!.data.course.forEach {
                Log.d("latitude", it.latitude)
                Log.d("longitude", it.longitude)
                pointList.add(TMapPoint(it.latitude.toDouble(), it.longitude.toDouble()))
            }
        }
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
            val bitmap: Bitmap = when (i) {
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
        val thread = Thread {
            try {
                for (i in 0 until pointList.size - 1) {
                    val from = pointList[i]
                    val to = pointList[i + 1]
                    val tMapPolyLine: TMapPolyLine = TMapData().findPathData(from, to)
                    tMapPolyLine.lineWidth = 3F
                    tMapPolyLine.outLineColor =
                        ContextCompat.getColor(requireContext(), R.color.blue_main)
                    tMapPolyLine.lineColor =
                        ContextCompat.getColor(requireContext(), R.color.blue_main)
                    tMapView.addTMapPolyLine("tMapPolyLine$i", tMapPolyLine)
                }
                setCenter(tMapView)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
    }

    private fun copyAddress(textView: TextView) {
        val clipboardManager =
            requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val address: String = textView.text.toString()
        val clip: ClipData = ClipData.newPlainText("Start Point Address", address)
        clipboardManager.setPrimaryClip(clip)
        Toast.makeText(requireContext(), "클립보드에 복사되었습니다.", Toast.LENGTH_LONG).show()
    }

    private fun openBottomSheetDialog() {
        val bottomSheetDialogFragment = DetailLikeFragment()
        bottomSheetDialogFragment.show(childFragmentManager, bottomSheetDialogFragment.tag)
    }
}