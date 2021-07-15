package com.example.charo_android.ui.search

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.charo_android.MainActivity
import com.example.charo_android.R
import com.example.charo_android.api.ApiService
import com.example.charo_android.api.RequestSearchViewData
import com.example.charo_android.api.ResponseSearchViewData
import com.example.charo_android.databinding.ActivitySearchBinding
import com.example.charo_android.ui.home.HomeFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.chrono.JapaneseEra.values

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    val itemTheme = arrayOf(
        "선택안함",
        "산",
        "바다",
        "호수",
        "강",
        "봄",
        "여름",
        "가을",
        "겨울",
        "해안도로",
        "벚꽃",
        "단풍",
        "여유",
        "스피드",
        "야경",
        "도심"
    )

    val itemCaution = arrayOf("선택안함", "고속도로", "산길포함", "초보힘듦", "사람많음")
    val itemProvince =
        arrayOf("선택안함", "특별시", "광역시", "경기도", "강원도", "충청남도", "충청북도", "경상북도", "경상남도", "전라북도", "전라남도")
    val itemSpecial = arrayOf("서울", "제주")
    val itemMetroPolitan = arrayOf("부산", "대구", "인천", "울산", "대전", "광주")
    val itemGyounGi = arrayOf(
        "선택안함",
        "가평",
        "고양",
        "과천",
        "광명",
        "광주",
        "구리",
        "군포",
        "김포",
        "남양주",
        "동두천",
        "부천",
        "성남",
        "수원",
        "시흥",
        "안산",
        "안성",
        "안양",
        "양주",
        "양평",
        "여주",
        "연천",
        "오산",
        "용인",
        "의왕",
        "의정부",
        "이천",
        "파주",
        "평택",
        "포천",
        "하남",
        "화성"
    )
    val itemGangWon = arrayOf(
        "선택안함",
        "고성",
        "강릉",
        "동해",
        "삼척",
        "속초",
        "양구",
        "양양",
        "영월",
        "인제",
        "정선",
        "철원",
        "춘천",
        "태백",
        "평창",
        "홍천",
        "화천",
        "횡성"
    )
    val itemChoongChungNam = arrayOf(
        "선택안함",
        "계룡",
        "공주",
        "금산",
        "논산",
        "당진",
        "보령",
        "부여",
        "서산",
        "서천",
        "아산",
        "예산",
        "천안",
        "청양",
        "태안",
        "홍성"
    )
    val itemChoongChungBuk =
        arrayOf("선택안함", "괴산", "단양", "보은", "영동", "옥천", "음성", "제천", "증평", "진천", "청주", "충주")
    val itemGyungSangBuk = arrayOf(
        "선택안함",
        "경산",
        "경주",
        "고령",
        "구미",
        "군위",
        "김천",
        "독도",
        "문경",
        "봉화",
        "상주",
        "성주",
        "안동",
        "영덕",
        "영양",
        "영주",
        "영천",
        "예천",
        "울릉",
        "울진",
        "의성",
        "청도",
        "청송",
        "칠곡",
        "포항"
    )
    val itemGyungSanNam = arrayOf(
        "선택안함",
        "거제",
        "거창",
        "고성",
        "김해",
        "남해",
        "밀양",
        "사천",
        "산청",
        "양산",
        "의령",
        "진주",
        "창녕",
        "창원",
        "통영",
        "하동",
        "함안",
        "함양",
        "합천"
    )
    val itemJungLaBuk = arrayOf(
        "선택안함",
        "고창",
        "군산",
        "김제",
        "남원",
        "무주",
        "부안",
        "순창",
        "완주",
        "익산",
        "임실",
        "장수",
        "전주",
        "정읍",
        "진안"
    )
    val itemJungLaNam = arrayOf(
        "선택안함",
        "강진",
        "고흥",
        "곡성",
        "광양",
        "구례",
        "나주",
        "담양",
        "목포",
        "무안",
        "보성",
        "순천",
        "신안",
        "여수",
        "영광",
        "영암",
        "완도",
        "장성",
        "장흥",
        "진도",
        "함평",
        "해남",
        "화순"
    )

    private lateinit var userId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = intent.getStringExtra("userId").toString()
        Log.d("nice",userId)
        clickSearch(userId)
        initToolBar()
        goHomeView(userId)
        selectTheme()
        selectCatution()
        selectarea()
    }


    fun clickSearch(userId : String) {
        binding.imgSearchStart.setOnClickListener {
            val userId = userId
            val province = binding.btnSearchArea1.text.toString()
            val region = if(binding.btnSearchArea2.text.toString() == "선택안함") {
                ""
            } else {
                binding.btnSearchArea2.text.toString()
            }
            val theme = if(binding.btnSearchTheme.text.toString() == "선택안함") {
                ""
            } else {
                binding.btnSearchTheme.text.toString()
            }
            val caution = if(binding.btnSearchCaution.text.toString() == "선택안함") {
                ""
            } else {
                binding.btnSearchCaution.text.toString()
            }

            Log.d("region", region)
            Log.d("theme", theme)
            Log.d("caution", caution)

            val requestSearchViewData = RequestSearchViewData(userId=userId, region= region, theme=theme, caution=caution)

            val call: Call<ResponseSearchViewData> =
                ApiService.searchViewService.postSearch(requestSearchViewData)
            call.enqueue(object : Callback<ResponseSearchViewData> {
                override fun onResponse(
                    call: Call<ResponseSearchViewData>,
                    response: Response<ResponseSearchViewData>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()?.data?.totalCourse
                        Log.d("data", data.toString())
                        if (data == 0) {
                            val intent = Intent(this@SearchActivity, NoSearchActivity::class.java)
                            startActivity(intent)

                        } else {
                            val intent = Intent(this@SearchActivity, ResultSearchActivity::class.java)
                            intent.apply {
                                putExtra("userId", userId)
                                putExtra("province", province)
                                putExtra("city", region)
                                putExtra("theme", theme)
                                putExtra("caution", caution)
                                startActivity(intent)
                            }
                        }

                    }
                }

                override fun onFailure(call: Call<ResponseSearchViewData>, t: Throwable) {

                }
            })

        }
    }


    fun selectTheme() {
        binding.btnSearchTheme.setOnClickListener {
            val searchItem = 0
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.main_charo_theme)
                .setNeutralButton("취소") { dialog, which ->
                    binding.btnSearchTheme.setText(resources.getString(R.string.main_charo_theme))
                    it.isSelected = false
                }
                .setPositiveButton("확인") { dialog, which ->
                    if (binding.btnSearchTheme.text.toString() == resources.getString(R.string.main_charo_theme)) {
                        it.isSelected = false
                    }
                    it.isSelected = true
                }
                .setSingleChoiceItems(itemTheme, searchItem) { dialog, which ->
                    binding.apply {
                        btnSearchTheme.setText(itemTheme[which])
                        btnSearchTheme.setTextColor(getColor(R.color.blue_main))
                        imgSearchStart.setBackgroundResource(R.drawable.ic_search_start_blue)
                        text_search_start.setTextColor(getColor(R.color.white))
                    }
                }.show()
        }


    }

    fun selectCatution() {
        binding.btnSearchCaution.setOnClickListener {
            val searchCautionItem = 0
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.caution)
                .setNeutralButton("취소") { dialog, which ->
                    binding.btnSearchCaution.setText(resources.getString(R.string.caution))
                    it.isSelected = false
                }
                .setPositiveButton("확인") { dialog, which ->
                    if (binding.btnSearchCaution.text.toString() == resources.getString(R.string.caution)) {
                        it.isSelected = false
                    }
                    it.isSelected = true
                }
                .setSingleChoiceItems(itemCaution, searchCautionItem) { dialog, which ->
                    binding.apply {
                        btnSearchCaution.setText(itemCaution[which])
                        btnSearchCaution.setTextColor(getColor(R.color.blue_main))
                        imgSearchStart.setBackgroundResource(R.drawable.ic_search_start_blue)
                        textSearchStart.setTextColor(getColor(R.color.white))
                    }
                }.show()
        }
    }

    fun selectarea() {
        binding.btnSearchArea1.setOnClickListener {
            val searchCautionItem = 0
            MaterialAlertDialogBuilder(this)
                .setTitle("지역")
                .setNeutralButton("취소") { dialog, which ->
                    binding.btnSearchArea1.setText("지역")
                    it.isSelected = false
                }
                .setPositiveButton("확인") { dialog, which ->
                    if (binding.btnSearchArea1.text.toString() == "지역") {
                        it.isSelected = false
                    }
                    it.isSelected = true
                }
                .setSingleChoiceItems(itemProvince, searchCautionItem) { dialog, which ->
                    binding.apply {
                        btnSearchArea1.setText(itemProvince[which])
                        btnSearchArea1.setTextColor(getColor(R.color.blue_main))
                        imgSearchStart.setBackgroundResource(R.drawable.ic_search_start_blue)
                        text_search_start.setTextColor(getColor(R.color.white))
                    }
                }.show()
        }

        binding.apply {
            btnSearchArea2.setOnClickListener {
                if (btnSearchArea1.text == "특별시") {
                    val searchAreaItem = 0
                    MaterialAlertDialogBuilder(this@SearchActivity)
                        .setTitle("지역")
                        .setNeutralButton("취소") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setPositiveButton("확인") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setSingleChoiceItems(itemSpecial, searchAreaItem) { dialog, which ->
                            binding.apply {
                                btnSearchArea2.setText(itemSpecial[which])
                                btnSearchArea2.setTextColor(getColor(R.color.blue_main))
                                imgSearchStart.setBackgroundResource(R.drawable.ic_search_start_blue)
                                text_search_start.setTextColor(getColor(R.color.white))
                            }
                        }.show()
                } else if (btnSearchArea1.text == "광역시") {
                    val searchAreaItem = 0
                    MaterialAlertDialogBuilder(this@SearchActivity)
                        .setTitle("지역")
                        .setNeutralButton("취소") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setPositiveButton("확인") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setSingleChoiceItems(itemMetroPolitan, searchAreaItem) { dialog, which ->
                            binding.apply {
                                btnSearchArea2.setText(itemMetroPolitan[which])
                                btnSearchArea2.setTextColor(getColor(R.color.blue_main))
                                imgSearchStart.setBackgroundResource(R.drawable.ic_search_start_blue)
                                text_search_start.setTextColor(getColor(R.color.white))
                            }
                        }.show()
                } else if (btnSearchArea1.text == "경기도") {
                    val searchAreaItem = 0
                    MaterialAlertDialogBuilder(this@SearchActivity)
                        .setTitle("지역")
                        .setNeutralButton("취소") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setPositiveButton("확인") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setSingleChoiceItems(itemGyounGi, searchAreaItem) { dialog, which ->
                            binding.apply {
                                btnSearchArea2.setText(itemGyounGi[which])
                                btnSearchArea2.setTextColor(getColor(R.color.blue_main))
                                imgSearchStart.setBackgroundResource(R.drawable.ic_search_start_blue)
                                text_search_start.setTextColor(getColor(R.color.white))
                            }
                        }.show()
                } else if (btnSearchArea1.text == "강원도") {
                    val searchAreaItem = 0
                    MaterialAlertDialogBuilder(this@SearchActivity)
                        .setTitle("지역")
                        .setNeutralButton("취소") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setPositiveButton("확인") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setSingleChoiceItems(itemGangWon, searchAreaItem) { dialog, which ->
                            binding.apply {
                                btnSearchArea2.setText(itemGangWon[which])
                                btnSearchArea2.setTextColor(getColor(R.color.blue_main))
                                imgSearchStart.setBackgroundResource(R.drawable.ic_search_start_blue)
                                text_search_start.setTextColor(getColor(R.color.white))
                            }
                        }.show()
                } else if (btnSearchArea1.text == "충청남도") {
                    val searchAreaItem = 0
                    MaterialAlertDialogBuilder(this@SearchActivity)
                        .setTitle("지역")
                        .setNeutralButton("취소") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setPositiveButton("확인") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setSingleChoiceItems(itemChoongChungNam, searchAreaItem) { dialog, which ->
                            binding.apply {
                                btnSearchArea2.setText(itemChoongChungNam[which])
                                btnSearchArea2.setTextColor(getColor(R.color.blue_main))
                                imgSearchStart.setBackgroundResource(R.drawable.ic_search_start_blue)
                                text_search_start.setTextColor(getColor(R.color.white))
                            }
                        }.show()
                } else if (btnSearchArea1.text == "충청북도") {
                    val searchAreaItem = 0
                    MaterialAlertDialogBuilder(this@SearchActivity)
                        .setTitle("지역")
                        .setNeutralButton("취소") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setPositiveButton("확인") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setSingleChoiceItems(itemChoongChungBuk, searchAreaItem) { dialog, which ->
                            binding.apply {
                                btnSearchArea2.setText(itemChoongChungBuk[which])
                                btnSearchArea2.setTextColor(getColor(R.color.blue_main))
                                imgSearchStart.setBackgroundResource(R.drawable.ic_search_start_blue)
                                text_search_start.setTextColor(getColor(R.color.white))

                            }
                        }.show()
                } else if (btnSearchArea1.text == "경상북도") {
                    val searchAreaItem = 0
                    MaterialAlertDialogBuilder(this@SearchActivity)
                        .setTitle("지역")
                        .setNeutralButton("취소") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setPositiveButton("확인") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setSingleChoiceItems(itemGyungSangBuk, searchAreaItem) { dialog, which ->
                            binding.apply {
                                btnSearchArea2.setText(itemGyungSangBuk[which])
                                btnSearchArea2.setTextColor(getColor(R.color.blue_main))
                                imgSearchStart.setBackgroundResource(R.drawable.ic_search_start_blue)
                                text_search_start.setTextColor(getColor(R.color.white))
                            }
                        }.show()
                } else if (btnSearchArea1.text == "경상남도") {
                    val searchAreaItem = 0
                    MaterialAlertDialogBuilder(this@SearchActivity)
                        .setTitle("지역")
                        .setNeutralButton("취소") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setPositiveButton("확인") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setSingleChoiceItems(itemGyungSanNam, searchAreaItem) { dialog, which ->
                            binding.apply {
                                btnSearchArea2.setText(itemGyungSanNam[which])
                                btnSearchArea2.setTextColor(getColor(R.color.blue_main))
                                imgSearchStart.setBackgroundResource(R.drawable.ic_search_start_blue)
                                text_search_start.setTextColor(getColor(R.color.white))
                            }
                        }.show()
                } else if (btnSearchArea1.text == "전라북도") {
                    val searchAreaItem = 0
                    MaterialAlertDialogBuilder(this@SearchActivity)
                        .setTitle("지역")
                        .setNeutralButton("취소") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setPositiveButton("확인") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setSingleChoiceItems(itemJungLaBuk, searchAreaItem) { dialog, which ->
                            binding.apply {
                                btnSearchArea2.setText(itemJungLaBuk[which])
                                btnSearchArea2.setTextColor(getColor(R.color.blue_main))
                                imgSearchStart.setBackgroundResource(R.drawable.ic_search_start_blue)
                                text_search_start.setTextColor(getColor(R.color.white))
                            }
                        }.show()
                } else if (btnSearchArea1.text == "전라남도") {
                    val searchAreaItem = 0
                    MaterialAlertDialogBuilder(this@SearchActivity)
                        .setTitle("지역")
                        .setNeutralButton("취소") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setPositiveButton("확인") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setSingleChoiceItems(itemJungLaNam, searchAreaItem) { dialog, which ->
                            binding.apply {
                                btnSearchArea2.setText(itemJungLaNam[which])
                                btnSearchArea2.setTextColor(getColor(R.color.blue_main))
                                imgSearchStart.setBackgroundResource(R.drawable.ic_search_start_blue)
                                text_search_start.setTextColor(getColor(R.color.white))
                            }
                        }.show()
                }
            }
        }


    }


    private fun initToolBar() {
        val toolbar = binding.toolbarSearch
        setSupportActionBar(toolbar)


    }

    private fun goHomeView(userId : String) {
        binding.imgSearchBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

    }

}