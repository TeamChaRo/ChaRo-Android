package com.charo.android.presentation.ui.detailpost

import android.annotation.SuppressLint
import android.content.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PointF
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.charo.android.R
import com.charo.android.databinding.FragmentDetailPostBinding
import com.charo.android.domain.model.detailpost.DetailPost
import com.charo.android.hidden.Hidden
import com.charo.android.presentation.ui.detailpost.adapter.DetailPostViewPagerAdapter
import com.charo.android.presentation.ui.mypage.other.OtherMyPageActivity
import com.charo.android.presentation.ui.write.WriteSharedViewModel
import com.charo.android.presentation.util.Define
import com.charo.android.presentation.util.LoginUtil
import com.google.android.gms.tasks.Task
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ShortDynamicLink
import com.skt.Tmap.*
import com.skt.Tmap.TMapView.OnClickListenerCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class DetailPostFragment : Fragment() {
    private var _binding: FragmentDetailPostBinding? = null
    private val binding get() = _binding ?: error("binding not initialized")

    private val viewModel by sharedViewModel<WriteSharedViewModel>()
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
        viewModel.initData()
        initToolbar(getString(R.string.title_home_kor))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tMapView = TMapView(requireContext())
        viewModel.getDetailPostData()

        initMap()
        copyAddress()
        clickLike()
        clickSave()
        clickShare()
        clickAuthor()
        showLikeList()
        observe()
    }

    private fun initToolbar(title : String){
        val toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_1)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbarTitle.text = title

        viewModel.isAuthorFlag.observe(viewLifecycleOwner){
            setHasOptionsMenu(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val menuInflater: MenuInflater = MenuInflater(requireContext())
        menuInflater.inflate(R.menu.detail_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initMap() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Main) {
                initTMap(tMapView)
            }

            viewModel.courseDetail.observe(viewLifecycleOwner) {
                if (it != null) {
                    drawPath(tMapView, it)
                }
            }
        }
    }

    private fun initViewPager(imageList: List<String>) {
        viewPagerAdapter = DetailPostViewPagerAdapter {
            viewModel.imageIndex = it
            parentFragmentManager.beginTransaction()
                .replace(R.id.write_share_layout, DetailPostImageFragment())
                .addToBackStack(null)
                .commit()
        }
        viewPagerAdapter.replaceItem(imageList)
        binding.vpPost.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Timber.e("Selected_Page $position")
                binding.tvPostOrder.text =
                    "${position + 1}/${viewPagerAdapter.itemCount}"
            }
        })
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

                    // 지도 중앙 맞춰주기
                    val info: TMapInfo =
                        tMapView.getDisplayTMapInfo(pointList as java.util.ArrayList<TMapPoint>?)
                    tMapView.setCenterPoint(info.tMapPoint.longitude, info.tMapPoint.latitude)
                    tMapView.zoomLevel = info.tMapZoomLevel
                }
            }.onFailure {
                // 실패 시 액티비티 종료 -> 추후엔 종료 말고 뭔가 다른 액션이 있었으면 좋겠다고 생각은 함(다이얼로그라던가 ...)
                requireActivity().finish()
                Timber.e("mlog: DetailPostFragment::Path 그리기 ${it.message.toString()}")
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
            if (viewModel.userEmail != "@") {
                // TODO: WriteSharedViewModel에 postLike 구현필요
                viewModel.postLike()
            } else {
                LoginUtil.loginPrompt(requireContext())
            }
        }
    }

    private fun clickSave() {
        binding.imgDetailSave.setOnClickListener {
            if (viewModel.userEmail != "@") {
                // TODO: WriteSharedViewModel에 postSave 구현필요
                viewModel.postSave()
            } else {
                LoginUtil.loginPrompt(requireContext())
            }
        }
    }

    private fun clickShare() {
        binding.imgDetailShare.setOnClickListener {
            val imageUri = when {
                viewModel.imageStringViewPager.value.isNullOrEmpty() -> {
                    //TODO: default image : 그래픽 이미지 (디자인 작업 후)
                    ""
                }
                else -> {
                    viewModel.imageStringViewPager.value!![0]
                }
            }
            // TODO: 나중에 postId 바꾸기
//            createDynamicLink((activity as DetailPostActivity).postId, (activity as DetailPostActivity).title, imageUri)
            createDynamicLink(viewModel.postId, viewModel.title.value.toString(), imageUri)
        }
    }

    private fun createDynamicLink(postId: Int, title: String, imageUri: String) {

        val link: String = Define().DEEP_LINK + "/" + Define().DYNAMIC_SEGMENT + "?postId=" + postId
        Timber.e("createDynamicLink() link: $link")
        Timber.e("createDynamicLink() imageUri: $imageUri")

        val uri = Uri.parse(link)
        FirebaseDynamicLinks.getInstance().createDynamicLink()
            .setLink(uri)
            .setDomainUriPrefix("https://charo.page.link")
            .setAndroidParameters(
                DynamicLink.AndroidParameters.Builder(requireActivity().packageName)
                    .setMinimumVersion(90)
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
                    .setTitle(title)
                    .setImageUrl(Uri.parse(imageUri))
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

    private fun clickMap() {
        val transaction = parentFragmentManager.beginTransaction()
        // TODO: Fragment Transaction 수정요망(id 부분)
        transaction.replace(R.id.write_share_layout, DetailPostMapFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun clickAuthor() {
        binding.imgAuthor.setOnClickListener {
            val intent = Intent(requireContext(), OtherMyPageActivity::class.java)
            intent.putExtra("userEmail", viewModel.authorEmail.value)
            startActivity(intent)
        }
        binding.tvAuthorNickname.setOnClickListener {
            val intent = Intent(requireContext(), OtherMyPageActivity::class.java)
            intent.putExtra("userEmail", viewModel.authorEmail.value)
            startActivity(intent)
        }
    }

    private fun showLikeList() {
        binding.tvDetailLike.setOnClickListener {
            if (childFragmentManager.findFragmentByTag("Dialog") == null) {
                DetailPostLikeListFragment().show(childFragmentManager, "Dialog")
            }
        }
    }

    private fun observe() {
        viewModel.imageStringViewPager.observe(viewLifecycleOwner) {
            initViewPager(it)
        }

        viewModel.deleteSuccess.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "게시물이 삭제되었습니다", Toast.LENGTH_SHORT).show()
                requireActivity().finish()
            } else {
                Toast.makeText(requireContext(), "다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }
}