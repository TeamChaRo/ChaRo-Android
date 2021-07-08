package com.example.charo_android

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.charo_android.databinding.ActivityDetailViewBinding
import com.example.charo_android.databinding.ItemDetailImageBinding
import com.skt.Tmap.TMapView

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailViewBinding
    private var detailViewPagerAdapter = DetailViewpagerImageAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // tMapView 생성부분
        val tMapView = TMapView(this)
        val tMapViewContainer = binding.clDetailMapview
        tMapView.setSKTMapApiKey("")
        tMapViewContainer.addView(tMapView)

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
        if(flag) {
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
        var text : String = binding.tvDetailInformationText.text.toString()
        var textLength : Int = text.length
        binding.tvDetailInformationTextLength.text = textLength.toString()+"/280자"
    }

    private fun imgDetailHeartOnClickEvent() {
        binding.imgDetailHeart.setOnClickListener() {
            val heart = binding.imgDetailHeart
            if(heart.isSelected) {
                Log.d("log", "heart is selected")
                var likeCount = Integer.parseInt(binding.tvDetailHeartCount.text.toString()) - 1
                binding.tvDetailHeartCount.text = likeCount.toString()
            } else{
                var likeCount = Integer.parseInt(binding.tvDetailHeartCount.text.toString()) + 1
                binding.tvDetailHeartCount.text = likeCount.toString()
            }
            heart.isSelected = !heart.isSelected
        }
    }

    private fun imgDetailSaveOnClickEvent(){
        binding.imgDetailSave.setOnClickListener() {
            val save = binding.imgDetailSave
            if(save.isSelected){
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
    
    private fun copyMapAddress(textView: TextView){
        var clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val address:String = binding.tvDetailMapStartAddress.text.toString()
        val clip: ClipData = ClipData.newPlainText("Start Point Address", address)
        clipboardManager.setPrimaryClip(clip)
        Toast.makeText(this, "클립보드에 복사되었습니다.", Toast.LENGTH_LONG).show()
    }
}
