package com.example.charo_android.ui.write

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.charo_android.MainActivity
import com.example.charo_android.R
import com.example.charo_android.api.ApiService
import com.example.charo_android.data.FormDataUtil
import com.example.charo_android.data.WriteImgInfo
import com.example.charo_android.databinding.ActivityWriteBinding
import com.example.charo_android.hidden.Hidden
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class WriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWriteBinding
    private lateinit var writeAdapter: WriteAdapter

    val itemProvince =
        arrayOf("특별시", "광역시", "경기도", "강원도", "충청남도", "충청북도", "경상북도", "경상남도", "전라북도", "전라남도")
    val itemSpecial = arrayOf("서울", "제주")
    val itemMetroPolitan = arrayOf("부산", "대구", "인천", "울산", "대전", "광주")
    val itemGyounGi = arrayOf(
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
    val itemChoongNam = arrayOf(
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
    val itemChoongBuk =
        arrayOf("괴산", "단양", "보은", "영동", "옥천", "음성", "제천", "증평", "진천", "청주", "충주")
    val itemGyungBuk = arrayOf(
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
    val itemGyungNam = arrayOf(
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
    val itemJunBuk = arrayOf(
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
    val itemJunNam = arrayOf(
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

    private lateinit var userId: String
    private lateinit var nickName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("log_activity", "activity start")
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = intent.getStringExtra("userId").toString()
        nickName = intent.getStringExtra("nickName").toString()

        // 1. 우리가 사용할 어뎁터의 초기 값을 넣어준다
        writeAdapter = WriteAdapter()

        // 2. RecyclerView 에 어뎁터를 우리가 만든 어뎁터로 만들기
        binding.recyclerviewWriteImg.adapter = writeAdapter

        //이미지 추가 버튼
        binding.imgWriteAddImg.setOnClickListener {
            openGallery()
        }
        warningText(binding.etWriteTitle, 38)
        warningText(binding.etWriteParkReview, 23)
        warningText(binding.etWriteMyDrive, 280)

        //버튼 selected 상태 변화 함수
        setButtonClickEvent()

        // 지역(도 단위)
        binding.btnWriteRegion.setOnClickListener() {
            val checkedItem = 0
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.region))
                .setNeutralButton("취소") { dialog, which ->
                    binding.btnWriteRegion.text = resources.getString(R.string.region)
                    it.isSelected = false
                }
                .setPositiveButton("확인") { dialog, which ->
                    if (binding.btnWriteRegion.text.toString() == resources.getString(R.string.region)) {
                        it.isSelected = false
                    }
                    it.isSelected = true
                }
                // Single-choice items (initialized with checked item)
                .setSingleChoiceItems(itemProvince, checkedItem) { dialog, which ->
                    //which : index
                    //테마 고르면 텍스트 변경
                    binding.btnWriteRegion.text = itemProvince[which]
                }
                .show()
        }

        binding.btnWriteLocation.setOnClickListener() {
            val checkedItem = 0
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.city))
                .setNeutralButton("취소") { dialog, which ->
                    binding.btnWriteLocation.text = resources.getString(R.string.city)
                    it.isSelected = false
                }
                .setPositiveButton("확인") { dialog, which ->
                    if (binding.btnWriteLocation.text.toString() == resources.getString(R.string.city) ||
                        binding.btnWriteLocation.text.toString() == "선택안함"
                    ) {
                        it.isSelected = false
                    }
                    it.isSelected = true
                }
                // Single-choice items (initialized with checked item)
                .setSingleChoiceItems(
                    if (binding.btnWriteRegion.text == "특별시") itemSpecial
                    else if (binding.btnWriteRegion.text == "광역시") itemMetroPolitan
                    else if (binding.btnWriteRegion.text == "경기도") itemGyounGi
                    else if (binding.btnWriteRegion.text == "강원도") itemGangWon
                    else if (binding.btnWriteRegion.text == "충청남도") itemChoongNam
                    else if (binding.btnWriteRegion.text == "충청북도") itemChoongBuk
                    else if (binding.btnWriteRegion.text == "경상북도") itemGyungBuk
                    else if (binding.btnWriteRegion.text == "경상남도") itemGyungNam
                    else if (binding.btnWriteRegion.text == "전라북도") itemJunBuk
                    else itemJunNam, checkedItem
                ) { dialog, which ->
                    //which : index
                    //테마 고르면 텍스트 변경
                    if (binding.btnWriteRegion.text == "특별시") binding.btnWriteLocation.text =
                        itemSpecial[which]
                    else if (binding.btnWriteRegion.text == "광역시") binding.btnWriteLocation.text =
                        itemMetroPolitan[which]
                    else if (binding.btnWriteRegion.text == "경기도") binding.btnWriteLocation.text =
                        itemGyounGi[which]
                    else if (binding.btnWriteRegion.text == "강원도") binding.btnWriteLocation.text =
                        itemGangWon[which]
                    else if (binding.btnWriteRegion.text == "충청남도") binding.btnWriteLocation.text =
                        itemChoongNam[which]
                    else if (binding.btnWriteRegion.text == "충청북도") binding.btnWriteLocation.text =
                        itemChoongBuk[which]
                    else if (binding.btnWriteRegion.text == "경상북도") binding.btnWriteLocation.text =
                        itemGyungBuk[which]
                    else if (binding.btnWriteRegion.text == "경상남도") binding.btnWriteLocation.text =
                        itemGyungNam[which]
                    else if (binding.btnWriteRegion.text == "전라북도") binding.btnWriteLocation.text =
                        itemJunBuk[which]
                    else binding.btnWriteLocation.text = itemJunNam[which]
                }
                .show()
        }

        // 테마
        val ItemsTheme = arrayOf(
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

        binding.btnWriteTheme1.setOnClickListener {
            val checkedItem = 0
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.theme1))
                .setNeutralButton("취소") { dialog, which ->
                    binding.btnWriteTheme1.text = resources.getString(R.string.theme1)
                    it.isSelected = false
                }
                .setPositiveButton("확인") { dialog, which ->
                    if (binding.btnWriteTheme1.text.toString() == resources.getString(R.string.theme1)) {
                        it.isSelected = false
                    }
                    it.isSelected = true
                }
                // Single-choice items (initialized with checked item)
                .setSingleChoiceItems(ItemsTheme, checkedItem) { dialog, which ->
                    //which : index
                    //테마 고르면 텍스트 변경
                    binding.btnWriteTheme1.text = ItemsTheme[which]
                }
                .show()
        }
        binding.btnWriteTheme2.setOnClickListener {
            val checkedItem = 0
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.theme2))
                .setNeutralButton("취소") { dialog, which ->
                    binding.btnWriteTheme2.text = resources.getString(R.string.theme2)
                    it.isSelected = false
                }
                .setPositiveButton("확인") { dialog, which ->
                    if (binding.btnWriteTheme1.text.toString() == resources.getString(R.string.theme2)) {
                        it.isSelected = false
                    }
                    it.isSelected = true
                }
                // Single-choice items (initialized with checked item)
                .setSingleChoiceItems(ItemsTheme, checkedItem) { dialog, which ->
                    //which : index
                    //테마 고르면 텍스트 변경
                    binding.btnWriteTheme2.text = ItemsTheme[which]
                }
                .show()
        }
        binding.btnWriteTheme3.setOnClickListener {
            val checkedItem = 0
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.theme3))
                .setNeutralButton("취소") { dialog, which ->
                    binding.btnWriteTheme3.text = resources.getString(R.string.theme3)
                    it.isSelected = false
                }
                .setPositiveButton("확인") { dialog, which ->
                    if (binding.btnWriteTheme3.text.toString() == resources.getString(R.string.theme3)) {
                        it.isSelected = false
                    }
                    it.isSelected = true
                }
                // Single-choice items (initialized with checked item)
                .setSingleChoiceItems(ItemsTheme, checkedItem) { dialog, which ->
                    //which : index
                    //테마 고르면 텍스트 변경
                    binding.btnWriteTheme3.text = ItemsTheme[which]
                }
                .show()
        }

        //주차 - 둘 중 하나만 선택 가능하도록 하기
        binding.btnWriteParkYes.setOnClickListener() {
            binding.btnWriteParkYes.isSelected = true
            binding.btnWriteParkNo.isSelected = false
        }
        binding.btnWriteParkNo.setOnClickListener() {
            binding.btnWriteParkNo.isSelected = true
            binding.btnWriteParkYes.isSelected = false
        }

        binding.clWritePhoto.setOnClickListener {
            // addImage()
            openGallery()
        }
        binding.imgWriteBack.setOnClickListener {
            onBackPressed()
        }

        //서버 테스트
        binding.btnWriteBottomNext.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage("작성을 완료하고, 드라이브 경로를 이어 작성하시겠습니까? 이후 글 수정은 어렵습니다.")
                .setNeutralButton("아니오") { dialog, which ->
                }
                .setPositiveButton("예") { dialog, which ->
                    insertDataToCompanionObject()
                }
                .show()
//            writePost(
//                binding.etWriteTitle.text.toString(),
//                "jiwon0321",
//                binding.btnWriteRegion.text.toString(),
//                binding.btnWriteLocation.text.toString(),
//                listOf<Boolean>(
//                    binding.btnWriteCautionHighway.isSelected,
//                    binding.btnWriteCautionMoun.isSelected,
//                    binding.btnWriteCautionDiffi.isSelected,
//                    binding.btnWriteCautionPeople.isSelected
//                ),
//                listOf<String>(binding.btnWriteTheme1.text.toString()),
//                binding.btnWriteParkYes.isSelected,
//                binding.etWriteParkReview.text.toString(),
//                binding.etWriteMyDrive.text.toString(),
//                RequestWriteData.Course(listOf("123"), listOf("123"), listOf("123")),
//                File(writeAdapter.imgList[0].imgUri.toString())
//            )
//            var file: File
//            var fileList = listOf<Any>()
//            var requestFile: RequestBody
//            file =
//                File(writeAdapter.imgList[0].imgUri.toString()) //[WriteImgInfo(imgUri=content:/com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F45/ORIGINAL/NONE/image%2Fjpeg/1578402431), WriteImgInfo(imgUri=content:/com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F46/ORIGINAL/NONE/image%2Fjpeg/765233817)]
//            var warningList: List<Boolean>
//            warningList = listOf<Boolean>(true, false, true, false)
//            writePost(
//                binding.etWriteTitle.text.toString(),
//                "token",
//                "도",
//                "시",
//                warningList,
//                listOf(
//                    binding.btnWriteTheme1.text.toString(),
//                    binding.btnWriteTheme2.text.toString(),
//                    binding.btnWriteTheme3.text.toString()
//                ),
//                false,
//                binding.etWriteParkReview.text.toString(),
//                binding.etWriteMyDrive.text.toString(),
//                RequestWriteData.Course(listOf("123"), listOf("123"), listOf("123")),
//                file
//            )
//            Log.d("clip", fileList.toString())
//            val intent = Intent(this, WriteMapActivity::class.java)
//            startActivity(intent)
        }
    }

    // writePost는 현재 액티비티에서 할 일이 아님.
//    private fun writePost(
//        title: String,
//        userId: String,
//        province: String,
//        region: String,
//        warning: List<Boolean>,
//        theme: List<String>,
//        isParking: Boolean,
//        parkingDesc: String,
//        courseDesc: String,
//        course: RequestWriteData.Course,
//        image: File
//    ) {
//        val ftitle = FormDataUtil.getBody("title", title)
//        val fuserId = FormDataUtil.getBody("userId", userId)
//        val fprovince = FormDataUtil.getBody("province", province)
//        val fregion = FormDataUtil.getBody("region", region)
//        val ftheme = FormDataUtil.getBody("theme", theme)
//        val fwarning = FormDataUtil.getBody("warning", warning)
//        val fisParking = FormDataUtil.getBody("isParking", isParking)
//        val fparkingDesc = FormDataUtil.getBody("parkingDesc", parkingDesc)
//        val fcourseDesc = FormDataUtil.getBody("courseDesc", courseDesc)
//        val fcourse = FormDataUtil.getBody("course", course)
//        val fimage = FormDataUtil.getImageBody("image", image)
//
//        val imageFileList: MutableList<MultipartBody.Part> = mutableListOf()
//        for(img in images) {
//
//        }
//
//        val call: Call<ResponseWriteData> =
//            ApiService.writeViewService.postWrite(
//                ftitle,
//                fuserId,
//                fprovince,
//                fregion,
//                fwarning,
//                ftheme,
//                fisParking,
//                fparkingDesc,
//                fcourseDesc,
//                fcourse,
//                fimage
//            )
//
//        call.enqueue(object : Callback<ResponseWriteData> {
//            //     Log.d("server","왜 안 되냐구")
//            override fun onResponse(
//                call: Call<ResponseWriteData>,
//                response: Response<ResponseWriteData>
//            ) {
//                if (response.isSuccessful) {
////                    val data = response.body()!!.title
//                    Log.d("server connect", "success")
//
//
//                } else {
//                    Log.d("1server connect", "write fail")
//                    Log.d("2server connect write fail", "${response.errorBody()}")
//                    Log.d("3server connect", "write fail${response.message()}")
//                    Log.d("4server connect", "write fail${response.code()}")
//                    Log.d("5server connect", "write fail${response.raw().request.url}")
//                }
//
//            }
//
//            override fun onFailure(call: Call<ResponseWriteData>, t: Throwable) {
//                Log.d("6server connect", "error:${t.message}")
//                onBackPressed()
//            }
//        })
//    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)  //ACTION_PICK   //ACTION_GET_CONTENT
        intent.type = "image/*"
        //다중 선택 막아놓기
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)

        intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI //데이터를 Uri로 받는다
        startActivityForResult(intent, 1)   //1 OPEN_GALLERY //200 GET_GALLERY_IMAGE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var imgPath: Uri
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                if (data?.clipData == null) {
                    Log.d("errorimg", "null")

                } else {
                    var clipData = data.clipData
                    when {
                        clipData?.itemCount!! > 6 -> {
                            Toast.makeText(this, "사진은 6개까지 선택 가능", Toast.LENGTH_LONG).show()
                        }
                        clipData.getItemCount() == 1 -> {
                            binding.clWritePhoto.visibility = View.GONE
                            imgPath = clipData.getItemAt(0).uri
                            Log.d("clipda", imgPath.toString())
                            writeAdapter.imgList.add(
                                WriteImgInfo(
                                    imgUri = imgPath,
                                )
                            )
                            writeAdapter.notifyItemInserted(0)
                            writeAdapter.notifyDataSetChanged()
                        }
                        clipData.itemCount in 2..5 -> {
                            binding.clWritePhoto.visibility = View.GONE
                            var imgmoreList = mutableListOf<WriteImgInfo>()
                            for (i: Int in 0..clipData.itemCount) {
                                Log.d("clipData", clipData.getItemAt(i).uri.toString())
                                imgPath = clipData.getItemAt(i).uri
                                imgmoreList.add(
                                    0,
                                    WriteImgInfo(
                                        imgUri = imgPath,
                                    )
                                )
                            }
                            writeAdapter.imgList.addAll(imgmoreList)
                            writeAdapter.notifyItemInserted(0)
                            writeAdapter.notifyDataSetChanged()
                        }
                    }

                }
            }
            var currentImageUrl: Uri? = data?.data

//            try {
//                val bitmap =
//                    MediaStore.Images.Media.getBitmap(contentResolver, currentImageUrl)
//                binding.imgWritePhoto.setImageBitmap(bitmap)
//                writeAdapter.notifyItemInserted(0)
//                writeAdapter.notifyDataSetChanged()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
        } else {
            Log.d("ActivityResult", "something wrong")

        }
    }

    private fun warningText(edt: EditText, i: Int) {
        edt.isSelected = edt.text.length >= (i - 2)
    }

    private fun setButtonClickEvent() {

        binding.btnWriteCautionHighway.setOnClickListener {
            it.isSelected = !it.isSelected
        }
        binding.btnWriteCautionPeople.setOnClickListener {
            it.isSelected = !it.isSelected
        }
        binding.btnWriteCautionDiffi.setOnClickListener {
            it.isSelected = !it.isSelected
        }
        binding.btnWriteCautionMoun.setOnClickListener {
            it.isSelected = !it.isSelected
        }
        binding.btnWriteLocation.setOnClickListener {
            it.isSelected = !it.isSelected
        }
        binding.btnWriteRegion.setOnClickListener {
            it.isSelected = !it.isSelected
        }
        binding.btnWriteParkNo.setOnClickListener {
            it.isSelected = !it.isSelected
        }
        binding.btnWriteParkYes.setOnClickListener {
            it.isSelected = !it.isSelected
        }
        binding.btnWriteTheme1.setOnClickListener {
            it.isSelected = !it.isSelected
        }
        binding.btnWriteTheme2.setOnClickListener {
            it.isSelected = !it.isSelected
        }
        binding.btnWriteTheme3.setOnClickListener {
            it.isSelected = !it.isSelected
        }
    }

    fun startActivityWriteMap() {
        val intent = Intent(this@WriteActivity, WriteMapActivity::class.java)
        intent.putExtra("theme11", binding.btnWriteTheme1.text.toString())
        intent.putExtra("theme22", binding.btnWriteTheme2.text.toString())
        intent.putExtra("locationFlag", "0")
        intent.putExtra("textview", "0")
        intent.putExtra("userId", userId)
        intent.putExtra("nickName", nickName)
        startActivity(intent)
    }

    private fun insertDataToCompanionObject() {
        WriteData.courseDesc = binding.etWriteMyDrive.text.toString()
        WriteData.isParking = binding.btnWriteParkYes.isSelected
        WriteData.parkingDesc = binding.etWriteParkReview.text.toString()
        if(binding.btnWriteRegion.text != "도 단위")
            WriteData.province = binding.btnWriteRegion.text.toString()
        if(binding.btnWriteLocation.text != "시 단위")
            WriteData.province = binding.btnWriteLocation.text.toString()
        var themeList = mutableListOf<String>()
        if(binding.btnWriteTheme1.text != "테마1") {
            themeList.add(binding.btnWriteTheme1.text.toString())
        } else {
            themeList.add("")
        }
        if(binding.btnWriteTheme2.text != "테마2") {
            themeList.add(binding.btnWriteTheme2.text.toString())
        } else {
            themeList.add("")
        }
        if(binding.btnWriteTheme3.text != "테마3") {
            themeList.add(binding.btnWriteTheme3.text.toString())
        } else {
            themeList.add("")
        }
        WriteData.theme = themeList
        WriteData.title = binding.etWriteTitle.text.toString()
        WriteData.userId = Hidden.userId
        var warningList = mutableListOf<Boolean>()
        if(binding.btnWriteCautionHighway.isSelected){
            warningList.add(true)
        } else {
            warningList.add(false)
        }
        if(binding.btnWriteCautionMoun.isSelected){
            warningList.add(true)
        } else {
            warningList.add(false)
        }
        if(binding.btnWriteCautionDiffi.isSelected){
            warningList.add(true)
        } else {
            warningList.add(false)
        }
        if(binding.btnWriteCautionPeople.isSelected){
            warningList.add(true)
        } else {
            warningList.add(false)
        }
        WriteData.warning = warningList
        var fileList = mutableListOf<Uri>()
        for(i in writeAdapter.imgList.indices) {
            WriteData.fileList.add(writeAdapter.imgList[i].imgUri)
        }
        WriteData.fileList = fileList

        startActivityWriteMap()
    }
}

//            var file: File
//            var fileList = listOf<Any>()
//            var requestFile: RequestBody
//            file =
//                File(writeAdapter.imgList[0].imgUri.toString())