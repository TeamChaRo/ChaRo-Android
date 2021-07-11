package com.example.charo_android

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.charo_android.data.LocalDetailViewpagerImageDataSource
import com.example.charo_android.databinding.ActivityDetailBinding
import com.skt.Tmap.*

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var detailViewPagerAdapter = DetailViewpagerImageAdapter()
    private var inputLatiList = arrayListOf<String>("37.553664", "37.475475", "37.266636", "37.394780")
    private var inputLongList = arrayListOf<String>("126.967067", "126.615699", "126.999397", "127.108971")
    private var inputMapPointList = arrayListOf<TMapPoint>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // tMapView 생성부분
        val tMapView = TMapView(this)
        val tMapViewContainer = binding.clDetailMapview
        tMapView.setSKTMapApiKey("l7xx94a7679a3e1d41a782105327ae7af1cd")
        tMapViewContainer.addView(tMapView)

        Log.d("zoomLevel", tMapView.zoomLevel.toString())

        // 뷰페이저의 초기 데이터 삽입
        initDetailImageItem()
        // 드라이브 상세설명 글자수 표시
        showDriveCourseInformationTextLenght()
        // 좋아요 클릭 이벤트
        imgDetailHeartOnClickEvent()
        // 저장 클릭 이벤트
        imgDetailSaveOnClickEvent()
        // 주소 복사
        imgDetailCopyOnClickEvent()
        // T/F 따라서 속성 바뀌는지 test
        test()
        // 경로탐색 test
        findPathTest(tMapView)

        Log.d("zoomLevel", tMapView.zoomLevel.toString())

        binding.tvDetailTitle.text = intent.getStringExtra("title")
    }

    private fun test() {
        setAttributeByFlag(binding.imgDetailParkingYes, binding.tvDetailParkingYes, true)
        setAttributeByFlag(binding.imgDetailParkingNo, binding.tvDetailParkingNo, false)

        setAttributeByFlag(binding.imgDetailWarningHighway, binding.tvDetailWarningHighway, true)
        setAttributeByFlag(binding.imgDetailWarningMountain, binding.tvDetailWarningMountain, true)
        setAttributeByFlag(binding.imgDetailWarningBeginner, binding.tvDetailWarningBeginner, false)
        setAttributeByFlag(binding.imgDetailWarningCrowded, binding.tvDetailWarningCrowded, false)
    }

    private fun setAttributeByFlag(imageView: ImageView, textView: TextView, flag: Boolean) {
        if (flag) {
            imageView.isSelected = true
            textView.setTextColor(Color.parseColor("#0f6fff"))
        } else {
            imageView.isSelected = false
            textView.setTextColor(Color.parseColor("#889098"))
        }
    }

    private fun initDetailImageItem() {
        binding.viewpagerDetailImage.adapter = detailViewPagerAdapter
        detailViewPagerAdapter.imageList.addAll(LocalDetailViewpagerImageDataSource().fetchData())
        detailViewPagerAdapter.notifyDataSetChanged()
    }

    private fun showDriveCourseInformationTextLenght() {
        var text: String = binding.tvDetailInformationText.text.toString()
        var textLength: Int = text.length
        binding.tvDetailInformationTextLength.text = textLength.toString() + "/280자"
    }

    private fun imgDetailHeartOnClickEvent() {
        binding.imgDetailHeart.setOnClickListener() {
            val heart = binding.imgDetailHeart
            if (heart.isSelected) {
                Log.d("log", "heart is selected")
                var likeCount = Integer.parseInt(binding.tvDetailHeartCount.text.toString()) - 1
                binding.tvDetailHeartCount.text = likeCount.toString()
            } else {
                var likeCount = Integer.parseInt(binding.tvDetailHeartCount.text.toString()) + 1
                binding.tvDetailHeartCount.text = likeCount.toString()
            }
            heart.isSelected = !heart.isSelected
        }
    }

    private fun imgDetailSaveOnClickEvent() {
        binding.imgDetailSave.setOnClickListener() {
            val save = binding.imgDetailSave
            if (save.isSelected) {
                Log.d("log", "save is selected")
            }
            save.isSelected = !save.isSelected
        }
    }

    private fun imgDetailCopyOnClickEvent() {
        binding.imgDetailMapStartCopy.setOnClickListener() {
            copyMapAddress(binding.tvDetailMapStartAddress)
        }
        binding.imgDetailMapVia1Copy.setOnClickListener() {
            copyMapAddress(binding.tvDetailMapVia1Address)
        }
        binding.imgDetailMapVia2Copy.setOnClickListener() {
            copyMapAddress(binding.tvDetailMapVia2Address)
        }
        binding.imgDetailMapEndCopy.setOnClickListener() {
            copyMapAddress(binding.tvDetailMapEndAddress)
        }
    }

    private fun copyMapAddress(textView: TextView) {
        var clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val address: String = binding.tvDetailMapStartAddress.text.toString()
        val clip: ClipData = ClipData.newPlainText("Start Point Address", address)
        clipboardManager.setPrimaryClip(clip)
        Toast.makeText(this, "클립보드에 복사되었습니다.", Toast.LENGTH_LONG).show()
    }

    private fun findPathTest(tMapView: TMapView) {
        // 순서
        // 1. 좌표를 배열에 추가
        addMapPointToList()
        // 2. 경로 그리기
        findPath(tMapView)
        // 3. 마커 찍기
        mark(tMapView)
    }

    private fun addMapPointToList() {
        for (i in 0 until inputLatiList.size) {
            var inputLatitude: String = inputLatiList[i]
            var inputLongitude: String = inputLongList[i]
            if (inputLatitude != "" && inputLongitude != "") {
                var latitude: Double = inputLatitude.toDouble()
                var longitude: Double = inputLongitude.toDouble()
                var mapPoint = TMapPoint(latitude, longitude)
                inputMapPointList.add(mapPoint)
            }
        }
    }

    private fun findPath(tMapView: TMapView) {
        for (i in 0 until inputMapPointList.size - 1) {
            var flag: Boolean = true
            if (i != 0 && i % 2 == 0) {
                flag = !flag
            }
            drawPath(tMapView, inputMapPointList[i], inputMapPointList[i+1],i, flag)
        }
        Handler(Looper.getMainLooper()).postDelayed({
            val info: TMapInfo = tMapView.getDisplayTMapInfo(inputMapPointList)
            tMapView.setCenterPoint(info.tMapPoint.longitude, info.tMapPoint.latitude)
            tMapView.zoomLevel = info.tMapZoomLevel
        }, 500)
    }

    private fun drawPath(tMapView: TMapView, start: TMapPoint, end: TMapPoint,cnt: Int, flag: Boolean) {
        val thread: Thread = Thread() {
            try {
                val tMapPolyLine: TMapPolyLine = TMapData().findPathData(start, end)
                tMapPolyLine.lineWidth = 3F
                tMapPolyLine.lineColor = getColor(R.color.blue_main)
                tMapView.addTMapPolyLine("tMapPolyLine$cnt", tMapPolyLine)
            } catch(e:Exception) {
                e.printStackTrace()
            }
        }
        thread.start()

        try {
            thread.join()
        } catch(e: Exception) {
            e.printStackTrace()
        }

        if(!flag) {
            Thread.sleep(1000)
        }
    }

    private fun mark(tMapView: TMapView) {
        for (i in 0 until inputMapPointList.size) {
            val marker = TMapMarkerItem()
            val mapPoint = inputMapPointList[i]
            var bitmap: Bitmap = if(i == 0){
                BitmapFactory.decodeResource(resources, R.drawable.ic_route_start)
            } else if(i == inputMapPointList.size - 1) {
                BitmapFactory.decodeResource(resources, R.drawable.ic_route_end)
            } else {
                BitmapFactory.decodeResource(resources, R.drawable.ic_route_waypoint)
            }
//            val bitmap: Bitmap = BitmapFactory.decodeResourc
//            val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.poi_dot)
            marker.icon = bitmap
            marker.setPosition(0.5F, 1.0F)
            marker.tMapPoint = mapPoint
            marker.name = "marker$i"
            tMapView.addMarkerItem(marker.name, marker)
        }
    }
}