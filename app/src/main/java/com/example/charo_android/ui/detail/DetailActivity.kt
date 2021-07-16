package com.example.charo_android.ui.detail

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.charo_android.R
import com.example.charo_android.api.ApiService
import com.example.charo_android.data.*
import com.example.charo_android.databinding.ActivityDetailBinding
import com.example.charo_android.hidden.Hidden
import com.example.charo_android.ui.write.WriteActivity
import com.example.charo_android.ui.write.WriteMapActivity
import com.skt.Tmap.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var detailViewPagerAdapter = DetailViewpagerImageAdapter()

    // test
    private var inputLatiList = arrayListOf<String>()
    private var inputLongList = arrayListOf<String>()
    private var inputMapPointList = arrayListOf<TMapPoint>()
    private var inputImageList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var userId: String = intent.getStringExtra("userId").toString()
        var postId: Int = intent.getIntExtra("postId", 0)
        Log.d("log", userId)
        Log.d("log", postId.toString())

        // tMapView 생성부분
        val tMapView = TMapView(this)
        val tMapViewContainer = binding.clDetailMapview
        tMapView.setSKTMapApiKey(Hidden().tMapApiKey)
        tMapViewContainer.addView(tMapView)

        init(userId, postId, tMapView)
        // 좋아요 클릭 이벤트
        imgDetailHeartOnClickEvent(userId, postId)
        // 저장 클릭 이벤트
        imgDetailSaveOnClickEvent(userId, postId)
        // 주소 복사
        imgDetailCopyOnClickEvent()
        // 뒤로가기 || 닫힘 버튼 클릭 이벤트
        imgDetailIconBackOnClickEvent()
        // 글 수정/삭제 이벤트
        imgDetailMoreMineOnClickEvent()
    }

    private fun init(userId: String, postId: Int, tMapView: TMapView) {
        val call: Call<ResponseDetailData> = ApiService.detailViewService.getDetail(userId, postId)

        call.enqueue(object : Callback<ResponseDetailData> {
            override fun onResponse(
                call: Call<ResponseDetailData>,
                response: Response<ResponseDetailData>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data
                    Log.d("server connect", "success")

                    // view에 binding
                    // image는 나중에
//                    val thread: Thread = Thread() {
//                        try {
                    val mContext: Context = applicationContext

                    Glide.with(mContext)
                        .load(data!![0].profileImage)
                        .override(29, 29)
                        .circleCrop()
                        .into(binding.imgDetailWriterImage)

                    for(i in data!![0].images.indices){
                        inputImageList.add(data!![0].images[i])
                    }

                    if(data!![0].isAuthor) {
                        binding.clDetailTopPartMine.visibility = View.VISIBLE
                        binding.clDetailTopPart.visibility = View.INVISIBLE
                    } else {
                        binding.clDetailTopPartMine.visibility = View.INVISIBLE
                        binding.clDetailTopPart.visibility = View.VISIBLE
                    }
                    binding.tvDetailTitle.text = data!![0].title
                    binding.tvDetailWriterName.text = data!![0].author
                    binding.tvDetailWriteDate.text =
                        "${data!![0].postingYear}년 ${data!![0].postingMonth}월 ${data!![0].postingDay}일"
                    binding.imgDetailSave.isSelected = data!![0].isStored
                    binding.imgDetailHeart.isSelected = data!![0].isFavorite
                    binding.tvDetailHeartCount.text = data!![0].likesCount.toString()
                    binding.tvDetailLocationBig.text = data!![0].province
                    binding.tvDetailLocationSmall.text = data!![0].city
                    if (data!![0].themes.size == 1) {
                        binding.imgDetailTheme2.visibility = View.INVISIBLE
                        binding.imgDetailTheme3.visibility = View.INVISIBLE
                        binding.tvDetailTheme1.text = data!![0].themes[0]
                    } else if (data!![0].themes.size == 2) {
                        binding.imgDetailTheme3.visibility = View.INVISIBLE
                        binding.imgDetailTheme2.visibility = View.VISIBLE
                        binding.tvDetailTheme1.text = data!![0].themes[0]
                        binding.tvDetailTheme2.text = data!![0].themes[1]
                    } else {
                        binding.imgDetailTheme3.visibility = View.VISIBLE
                        binding.imgDetailTheme2.visibility = View.VISIBLE
                        binding.tvDetailTheme1.text = data!![0].themes[0]
                        binding.tvDetailTheme2.text = data!![0].themes[1]
                        binding.tvDetailTheme3.text = data!![0].themes[2]
                    }
                    binding.tvDetailMapStartAddress.text = data!![0].source
                    binding.tvDetailMapEndAddress.text = data!![0].destination
                    if (data!![0].wayPoint[0] == "") {
                        binding.clDetailMapVia1.visibility = View.GONE
                        binding.clDetailMapVia2.visibility = View.GONE
                    } else if (data!![0].wayPoint[1] == "") {
                        binding.tvDetailMapVia1Address.text = data!![0].wayPoint[0]
                        binding.clDetailMapVia1.visibility = View.VISIBLE
                        binding.clDetailMapVia2.visibility = View.GONE
                    } else {
                        binding.tvDetailMapVia1Address.text = data!![0].wayPoint[0]
                        binding.tvDetailMapVia2Address.text = data!![0].wayPoint[1]
                        binding.clDetailMapVia1.visibility = View.VISIBLE
                        binding.clDetailMapVia2.visibility = View.VISIBLE
                    }
                    for (i in data!![0].latitude.indices) {
                        inputLatiList.add(data!![0].latitude[i])
                        inputLongList.add(data!![0].longtitude[i])

                        Log.d("위도", data!![0].latitude[i])
                        Log.d("경도", data!![0].longtitude[i])
                    }
                    if (data!![0].isParking) {
                        setAttributeByFlag(
                            binding.imgDetailParkingYes,
                            binding.tvDetailParkingYes,
                            true
                        )
                        setAttributeByFlag(
                            binding.imgDetailParkingNo,
                            binding.tvDetailParkingNo,
                            false
                        )
                    } else {
                        setAttributeByFlag(
                            binding.imgDetailParkingYes,
                            binding.tvDetailParkingYes,
                            false
                        )
                        setAttributeByFlag(
                            binding.imgDetailParkingNo,
                            binding.tvDetailParkingNo,
                            true
                        )
                    }
                    if (data!![0].parkingDesc != "") {
                        binding.tvDetailParkingInformation.text = data!![0].parkingDesc
                    }
                    setAttributeByFlag(
                        binding.imgDetailWarningHighway,
                        binding.tvDetailWarningHighway,
                        data!![0].warnings[0]
                    )
                    setAttributeByFlag(
                        binding.imgDetailWarningMountain,
                        binding.tvDetailWarningMountain,
                        data!![0].warnings[1]
                    )
                    setAttributeByFlag(
                        binding.imgDetailWarningBeginner,
                        binding.tvDetailWarningBeginner,
                        data!![0].warnings[2]
                    )
                    setAttributeByFlag(
                        binding.imgDetailWarningCrowded,
                        binding.tvDetailWarningCrowded,
                        data!![0].warnings[3]
                    )
                    binding.tvDetailInformationText.text = data!![0].courseDesc
//                        } catch(e: Exception){
//                            e.printStackTrace()
//                        }
//                    }
//                    thread.start()
//
//                    try {
//                        thread.join()
//                    } catch(e: Exception) {
//                        e.printStackTrace()
//                    }
//
//                    Thread.sleep(1000)
                } else {
                    Log.d("server connect", "fail")
                    Log.d("server connect", "${response.errorBody()}")
                    Log.d("server connect", "${response.message()}")
                    Log.d("server connect", "${response.code()}")
                    Log.d("server connect", "${response.raw().request.url}")
                    onBackPressed()
                }
                initDetailImageItem()
                showDriveCourseInformationTextLenght()
                findPathTest(tMapView)
            }

            override fun onFailure(call: Call<ResponseDetailData>, t: Throwable) {
                Log.d("server connect", "error:${t.message}")
                onBackPressed()
            }
        })
    }

    private fun setAttributeByFlag(imageView: ImageView, textView: TextView, flag: Boolean) {
        val mContext: Context = applicationContext
        if (flag) {
            imageView.isSelected = true
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.blue_main))

        } else {
            imageView.isSelected = false
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.gray4_sub))
        }
    }

    private fun initDetailImageItem() {
        binding.viewpagerDetailImage.adapter = detailViewPagerAdapter
//        detailViewPagerAdapter.imageList.addAll(LocalDetailViewpagerImageDataSource().fetchData())
//        DetailViewpagerImageInfo(image = R.drawable.mask_group)
        for(i in inputImageList.indices) {
            detailViewPagerAdapter.imageList.add(DetailViewpagerImageInfo(inputImageList[i]))
        }
        detailViewPagerAdapter.notifyDataSetChanged()
        Log.d("detailViewPagerAdapter.itemCount", detailViewPagerAdapter.itemCount.toString())
    }

    private fun showDriveCourseInformationTextLenght() {
        var text: String = binding.tvDetailInformationText.text.toString()
        var textLength: Int = text.length
        binding.tvDetailInformationTextLength.text = textLength.toString() + "/280자"
    }

    private fun imgDetailHeartOnClickEvent(userId: String, postId: Int) {
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

            val requestDetailLikeData = RequestDetailLikeData(
                userId = userId, postId = postId
            )

            val call: Call<ResponseDetailLikeData> = ApiService.detailViewLikeService
                .postDetailLike(requestDetailLikeData)

            call.enqueue(object : Callback<ResponseDetailLikeData>{
                override fun onResponse(
                    call: Call<ResponseDetailLikeData>,
                    response: Response<ResponseDetailLikeData>
                ) {
                    if(response.isSuccessful) {
                        Log.d("server connect", "success")
                    } else{
                        Log.d("server connect", "fail")
                        Log.d("server connect", "${response.errorBody()}")
                        Log.d("server connect", "${response.message()}")
                        Log.d("server connect", "${response.code()}")
                        Log.d("server connect", "${response.raw().request.url}")
                    }
                }

                override fun onFailure(call: Call<ResponseDetailLikeData>, t: Throwable) {
                    Log.d("server connect", "error:${t.message}")
                }
            })
        }
    }

    private fun imgDetailSaveOnClickEvent(userId: String, postId: Int) {
        binding.imgDetailSave.setOnClickListener() {
            val save = binding.imgDetailSave
            if (save.isSelected) {
                Log.d("log", "save is selected")
            }
            save.isSelected = !save.isSelected

            val requestDetailSaveData = RequestDetailSaveData(
                userId = userId, postId = postId
            )

            val call: Call<ResponseDetailSaveData> = ApiService.detailViewSaveService
                .postDetailSave(requestDetailSaveData)

            call.enqueue(object: Callback<ResponseDetailSaveData> {
                override fun onResponse(
                    call: Call<ResponseDetailSaveData>,
                    response: Response<ResponseDetailSaveData>
                ) {
                    if(response.isSuccessful){
                        Log.d("server connect", "success")
                    } else {
                        Log.d("server connect", "fail")
                        Log.d("server connect", "${response.errorBody()}")
                        Log.d("server connect", "${response.message()}")
                        Log.d("server connect", "${response.code()}")
                        Log.d("server connect", "${response.raw().request.url}")
                    }
                }

                override fun onFailure(call: Call<ResponseDetailSaveData>, t: Throwable) {
                    Log.d("server connect", "error:${t.message}")
                }
            })
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
        Log.d("log", "findPathTest")
        // 순서
        // 1. 좌표를 배열에 추가
        addMapPointToList()
        // 2. 경로 그리기
        findPath(tMapView)
        // 3. 마커 찍기
        mark(tMapView)
    }

    private fun addMapPointToList() {
        Log.d("log", "addMapPointToList")
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
        Log.d("log", "findPath")
        for (i in 0 until inputMapPointList.size - 1) {
            var flag: Boolean = true
            if (i != 0 && i % 2 == 0) {
                flag = !flag
            }
            drawPath(tMapView, inputMapPointList[i], inputMapPointList[i + 1], i, flag)
        }
        Handler(Looper.getMainLooper()).postDelayed({
            val info: TMapInfo = tMapView.getDisplayTMapInfo(inputMapPointList)
            tMapView.setCenterPoint(info.tMapPoint.longitude, info.tMapPoint.latitude)
            tMapView.zoomLevel = info.tMapZoomLevel
        }, 500)
    }

    private fun drawPath(
        tMapView: TMapView,
        start: TMapPoint,
        end: TMapPoint,
        cnt: Int,
        flag: Boolean
    ) {
        Log.d("log", "drawPath")
        val thread: Thread = Thread() {
            try {
                val tMapPolyLine: TMapPolyLine = TMapData().findPathData(start, end)
                tMapPolyLine.lineWidth = 3F
                tMapPolyLine.lineColor = getColor(R.color.blue_main)
                tMapView.addTMapPolyLine("tMapPolyLine$cnt", tMapPolyLine)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()

        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (!flag) {
            Thread.sleep(1000)
        }
    }

    private fun mark(tMapView: TMapView) {
        Log.d("log", "mark")
        for (i in 0 until inputMapPointList.size) {
            val marker = TMapMarkerItem()
            val mapPoint = inputMapPointList[i]
            var bitmap: Bitmap = if (i == 0) {
                BitmapFactory.decodeResource(resources, R.drawable.ic_route_start)
            } else if (i == inputMapPointList.size - 1) {
                BitmapFactory.decodeResource(resources, R.drawable.ic_route_end)
            } else {
                BitmapFactory.decodeResource(resources, R.drawable.ic_route_waypoint)
            }
            marker.icon = bitmap
            marker.setPosition(0.5F, 1.0F)
            marker.tMapPoint = mapPoint
            marker.name = "marker$i"
            tMapView.addMarkerItem(marker.name, marker)
        }
    }

    private fun imgDetailIconBackOnClickEvent() {
        binding.imgDetailIconBack1.setOnClickListener() {
            onBackPressed()
        }
        binding.imgDetailIconBackMine.setOnClickListener() {
            onBackPressed()
        }
    }

    private fun imgDetailMoreMineOnClickEvent() {
        binding.imgDetailMoreMine.setOnClickListener() {
            if(binding.clDetailMenu.visibility == View.VISIBLE) {
                binding.clDetailMenu.visibility = View.INVISIBLE
            } else if(binding.clDetailMenu.visibility == View.INVISIBLE) {
                binding.clDetailMenu.visibility = View.VISIBLE
                binding.clDetailMenu.bringToFront()
            }

            binding.tvDetailMenuBoardUpdate.setOnClickListener() {
                val intent = Intent(applicationContext, WriteActivity::class.java)
                ContextCompat.startActivity(applicationContext, intent, null)
            }
            binding.tvDetailMenuMapUpdate.setOnClickListener() {
                val intent = Intent(applicationContext, WriteMapActivity::class.java)
                ContextCompat.startActivity(applicationContext, intent, null)
            }
            binding.tvDetailMenuDelete.setOnClickListener() {
                Toast.makeText(applicationContext, "삭제되었습니다.", Toast.LENGTH_LONG).show()
                onBackPressed()
            }
        }
    }
}