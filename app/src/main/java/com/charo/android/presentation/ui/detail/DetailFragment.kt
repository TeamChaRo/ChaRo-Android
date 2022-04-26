package com.charo.android.presentation.ui.detail

import android.annotation.SuppressLint
import android.content.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.charo.android.R
import com.charo.android.data.model.detailold.RequestDetailDeleteData
import com.charo.android.databinding.FragmentDetailBinding
import com.charo.android.hidden.Hidden
import com.charo.android.presentation.ui.main.MainActivity
import com.charo.android.presentation.util.CustomDialog
import com.charo.android.presentation.util.Define
import com.charo.android.presentation.util.enqueueUtil
import com.google.android.gms.tasks.Task
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ShortDynamicLink
import com.skt.Tmap.*
import timber.log.Timber

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by activityViewModels()
    private lateinit var viewPagerAdapter: DetailViewpagerAdapter
    private var isAuthor: Boolean = false

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
                imageList.addAll(viewModel.detailData.value!!.data.images)
                intent.putExtra("imageList", imageList)
            })
            intent.putExtra("itemPosition", it)
            startActivity(intent)
        }

        // ViewModel LiveData
        viewModel.setPostId(postId)
        if (viewModel.detailData.value == null) {
            Timber.d("viewModel getData() execute")
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
            Timber.d("detail observed")
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

                isAuthor = viewModel.detailData.value?.data?.isAuthor == true
                when (viewModel.detailData.value?.data?.isAuthor) {
                    true -> {
                        binding.apply {
                            clDetailTopPartMine.visibility = View.VISIBLE
                            clDetailTopPart.visibility = View.INVISIBLE
                        }
                    }
                    else -> {
                        binding.apply {
                            clDetailTopPartMine.visibility = View.INVISIBLE
                            clDetailTopPart.visibility = View.VISIBLE
                        }
                    }
                }
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
            requireActivity().onBackPressed()
        }
        binding.imgDetailIconBackMine.setOnClickListener {
            requireActivity().onBackPressed()
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
            createDynamicLink((activity as DetailActivity).postId, (activity as DetailActivity).title, imageUrl)
        }

        // 지도 클릭 이벤트
        binding.clDetailMapviewTouch.setOnClickListener {
            (context as DetailActivity).openFragment(1)
        }

        // n명이 좋아해요 클릭 이벤트
        binding.tvDetailLike.setOnClickListener {
            if (isAuthor) {
                openBottomSheetDialog()
            }
        }

        // 마이페이지 이동
        binding.imgDetailWriterImage.setOnClickListener {
            goMyPage()
        }
        binding.tvDetailWriterName.setOnClickListener {
            goMyPage()
        }

        // 메뉴 생성
        binding.imgDetailMoreMine.setOnClickListener {
            popUpMenu()
        }
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    private fun initViewPager(imgList: List<String>) {
        // viewPager registerOnPageChangeCallback
        binding.viewpagerDetailImage.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Timber.e("Selected_Page  ${position.toString()}")
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

    private fun createDynamicLink(postId : Int, title : String, imageUrl : String) {

        val link: String = Define().DEEP_LINK + "/" + Define().DYNAMIC_SEGMENT + "?postId=" + postId
        Timber.e("createDynamicLink() uriPath: $link")

        val uri = Uri.parse(link)
        FirebaseDynamicLinks.getInstance().createDynamicLink()
            .setLink(uri)
            .setDomainUriPrefix("https://charo.page.link")
            .setAndroidParameters(
                DynamicLink.AndroidParameters.Builder(requireActivity().packageName)
                    .setMinimumVersion(100)
                    .build()
            )
            .setGoogleAnalyticsParameters(
                DynamicLink.GoogleAnalyticsParameters.Builder()
                    .setSource("share")
                    .setMedium(title)
                    .setCampaign("detailPost")
                    .build()
            )
            .setSocialMetaTagParameters(
                DynamicLink.SocialMetaTagParameters.Builder()
                    .setTitle("Charo")
                    .setDescription(title)
                    .setImageUrl(Uri.parse(imageUrl))
                    .build()
            )
            .buildShortDynamicLink()
            .addOnCompleteListener() { task: Task<ShortDynamicLink> ->
                if (task.isSuccessful) {
                    val shortLink = task.result.shortLink
                    try {
                        if (shortLink != null) {
                            val sendIntent = Intent()
                            sendIntent.action = Intent.ACTION_SEND
                            sendIntent.putExtra(Intent.EXTRA_TEXT, shortLink.toString())
                            sendIntent.type = "text/plain"
                            startActivity(Intent.createChooser(sendIntent, "sharePost"))
                        }
                    } catch (ignored: ActivityNotFoundException) {
                        ignored.printStackTrace()
                    }
                } else {
                    Timber.e(task.exception.toString())
                }
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
                Timber.d("latitude  ${it.latitude}")
                Timber.d("longitude  ${it.longitude}")
                pointList.add(TMapPoint(it.latitude.toDouble(), it.longitude.toDouble()))
            }
        }
        mark(tMapView)
    }

    private fun setCenter(tMapView: TMapView) {
        val info: TMapInfo = tMapView.getDisplayTMapInfo(pointList)
        Timber.d("info.tMapPoint.longitude  ${info.tMapPoint.longitude.toString()}")
        Timber.d("info.tMapPoint.latitude  ${info.tMapPoint.latitude.toString()}")
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
            marker.name = "marker_location_flag_$i"
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
                        ContextCompat.getColor(requireContext(), R.color.blue_main_0f6fff)
                    tMapPolyLine.lineColor =
                        ContextCompat.getColor(requireContext(), R.color.blue_main_0f6fff)
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

    private fun goMyPage() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        val userEmail: String
        val nickname: String
        val isMyPage: Boolean
        Timber.d("author  ${viewModel.detailData.value?.data?.author.toString()}")
        if (viewModel.detailData.value?.data?.author == Hidden.nickName) {
            Timber.d("DetailFragment true")
            isMyPage = true
            userEmail = Hidden.userId
            nickname = Hidden.nickName
            intent.putExtra("nickname", nickname)
        } else {
            Timber.d("DetailFragment false")
            isMyPage = false
            userEmail = Hidden.otherUserEmail
            nickname = Hidden.otherNickname
            intent.putExtra("otherUserEmail", userEmail)
            intent.putExtra("otherUserNickname", nickname)
        }
        intent.putExtra("isMyPage", isMyPage)
        intent.putExtra("isFromOtherPage", true)
        startActivity(intent)
    }

    private fun popUpMenu() {
        PopupMenu(requireContext(), binding.imgDetailMoreMine).apply {
            setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener {
                when (it.itemId) {
                    R.id.detail_menu_edit -> {
                        Timber.d("edit clicked")
                        true
                    }
                    R.id.detail_menu_delete -> {
                        Timber.d("delete clicked")
                        deletePost()
                        true
                    }
                    else -> false
                }
            })
            inflate(R.menu.detail_menu)
            show()
        }
    }

    private fun deletePost() {
        val dialog = CustomDialog(requireActivity())
        dialog.showDialog(R.layout.dialog_detail_delete)
        dialog.setOnClickedListener(object : CustomDialog.ButtonClickListener {
            override fun onClicked(num: Int) {
                if (num == 1) {
                    val postId: Int = viewModel.postId.value!!
                    val images: MutableList<String> = viewModel.detailData.value?.data?.images!!
                    val call = com.charo.android.data.api.ApiService.detailViewService.deletePost(
                        postId,
                        RequestDetailDeleteData(images)
                    )
                    call.enqueueUtil(
                        onSuccess = {
                            Timber.d("deletePost execute")
                            requireActivity().finish()
                        }
                    )
                }
            }
        })
    }
}