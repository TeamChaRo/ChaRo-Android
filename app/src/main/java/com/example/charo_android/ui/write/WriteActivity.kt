package com.example.charo_android.ui.write

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.charo_android.R
import com.example.charo_android.api.ApiService
import com.example.charo_android.api.RequestWriteData
import com.example.charo_android.api.ResponseWriteData
import com.example.charo_android.data.FormDataUtil
import com.example.charo_android.data.WriteImgInfo
import com.example.charo_android.databinding.ActivityWriteBinding
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
        arrayOf("선택안함", "특별시", "광역시", "경기도", "강원도", "충청남도", "충청북도", "경상북도", "경상남도", "전라북도", "전라남도")
    val itemSpecial = arrayOf("선택안함", "서울", "제주")
    val itemMetroPolitan = arrayOf("선택안함", "부산", "대구", "인천", "울산", "대전", "광주")
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. 우리가 사용할 어뎁터의 초기 값을 넣어준다
        writeAdapter = WriteAdapter()

        // 2. RecyclerView 에 어뎁터를 우리가 만든 어뎁터로 만들기
        binding.recyclerviewWriteImg.adapter = writeAdapter

        //이미지 추가 버튼
        binding.imgWriteAddImg.setOnClickListener {
            openGallery()
            //     writeAdapter.notifyItemInserted(0)
        }
        warningText(binding.etWriteTitle, 38)
        warningText(binding.etWriteParkReview, 23)
        warningText(binding.etWriteMyDrive, 280)


        //이미지 업로드 리스트 부분
//        writeAdapter.imgList.addAll(
//            listOf<WriteImgInfo>(
//                WriteImgInfo(
//                    imgUri = "",
//                )
//            )
//        )
        //   writeAdapter.notifyDataSetChanged()

        //버튼 selected 상태 변화 함수
        setButtonClickEvent()

        //지역
        val ItemsDo = arrayOf("")


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
            val checkedItem = 1
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
            val checkedItem = 1
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
            val checkedItem = 1
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


        binding.clWritePhoto.setOnClickListener {
            // addImage()
            openGallery()
        }

        //서버 테스트
        binding.btnWriteBottomNext.setOnClickListener {
            var file: File
            var fileList = listOf<Any>()
            var requestFile: RequestBody
//               for (i in 0 until writeAdapter.imgList.size){
//                 file = File(writeAdapter.imgList[i].imgUri.toString()) //[WriteImgInfo(imgUri=content:/com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F45/ORIGINAL/NONE/image%2Fjpeg/1578402431), WriteImgInfo(imgUri=content:/com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F46/ORIGINAL/NONE/image%2Fjpeg/765233817)]

            file =
                File(writeAdapter.imgList[0].imgUri.toString()) //[WriteImgInfo(imgUri=content:/com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F45/ORIGINAL/NONE/image%2Fjpeg/1578402431), WriteImgInfo(imgUri=content:/com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F46/ORIGINAL/NONE/image%2Fjpeg/765233817)]

//            requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
//            fileList += requestFile

            var warningList: List<Boolean>
            warningList = listOf<Boolean>(true, false, true, false)
            writePost(
                binding.etWriteTitle.text.toString(),
                "token",
                "도",
                "시",
                warningList,
                listOf(
                    binding.btnWriteTheme1.text.toString(),
                    binding.btnWriteTheme2.text.toString(),
                    binding.btnWriteTheme3.text.toString()
                ),
                false,
                binding.etWriteParkReview.text.toString(),
                binding.etWriteMyDrive.text.toString(),
                RequestWriteData.Course(listOf("123"), listOf("123"), listOf("123")),
                file
            )
//            requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
//            fileList += requestFile
//            //  }
//            val body = MultipartBody.Part.createFormData("image", "파일명", requestFile)

            Log.d("clip", fileList.toString())
            //   startActivityWriteMap()
//            val requestWriteData = RequestWriteData(
//                title = binding.etWriteTitle.text.toString(),
//                userId = "token",
//                province = binding.btnWriteCity.text.toString(),
//                region = binding.btnWriteRegion.text.toString(),
//                theme = listOf(
//                    binding.btnWriteTheme1.text.toString(),
//                    binding.btnWriteTheme2.text.toString(),
//                    binding.btnWriteTheme3.text.toString()
//                ),
//                isParking = false,
//                parkingDesc = binding.etWriteParkReview.text.toString(),
////                course = [[ "123", "123" ],[ "123", "123" ],[ "123", "123" ]],
////                "address": [ "123", "123" ],
////              "latitude": [ "123", "123" ],
////              "longtitude": [ "123", "123" ]
//                image = file
//            )


//            val call: Call<ResponseWriteData> =
//                ApiService.writeViewService.postWrite(
//                    title = binding.etWriteTitle.text.toString(),
//                    userId = "token",
//                    province = binding.btnWriteCity.text.toString(),
//                    region = binding.btnWriteRegion.text.toString(),
//                    theme = listOf(
//                        binding.btnWriteTheme1.text.toString(),
//                        binding.btnWriteTheme2.text.toString(),
//                        binding.btnWriteTheme3.text.toString()
//                    ),
//                    isParking = false,
//                    parkingDesc = binding.etWriteParkReview.text.toString(),
//                course = [[ "123", "123" ],[ "123", "123" ],[ "123", "123" ]],
//                "address": [ "123", "123" ],
//              "latitude": [ "123", "123" ],
//              "longtitude": [ "123", "123" ]
//                    image = file
            //   )

//            call.enqueue(object : Callback<ResponseWriteData> {
//                override fun onResponse(
//                    call: Call<ResponseWriteData>,
//                    response: Response<ResponseWriteData>
//                ) {
//                    if (response.isSuccessful) {
//                        val data = response.body()!!.title
//                        Log.d("server connect", "success" + data)
//
//
//                    } else {
//                        Log.d("server connect", "fail")
//                        Log.d("server connect", "${response.errorBody()}")
//                        Log.d("server connect", "${response.message()}")
//                        Log.d("server connect", "${response.code()}")
//                        Log.d("server connect", "${response.raw().request.url}")
//                        onBackPressed()
//                    }
//
//                }

//                override fun onFailure(call: Call<ResponseWriteData>, t: Throwable) {
//                    Log.d("server connect", "error:${t.message}")
//                    onBackPressed()
//                }
//            })


        }

    }

    fun writePost(
        title: String,
        userId: String,
        province: String,
        region: String,
        warning: List<Boolean>,
        theme: List<String>,
        isParking: Boolean,
        parkingDesc: String,
        courseDesc: String,
        course: RequestWriteData.Course,
        image: File
    ) {
        val ftitle = FormDataUtil.getBody("title", title)
        val fuserId = FormDataUtil.getBody("userId", userId)
        val fprovince = FormDataUtil.getBody("province", province)
        val fregion = FormDataUtil.getBody("region", region)
        val ftheme = FormDataUtil.getBody("theme", theme)
        val fwarning = FormDataUtil.getBody("warning", warning)
        val fisParking = FormDataUtil.getBody("isParking", isParking)
        val fparkingDesc = FormDataUtil.getBody("parkingDesc", parkingDesc)
        val fcourseDesc = FormDataUtil.getBody("courseDesc", courseDesc)
        val fcourse = FormDataUtil.getBody("course", course)
        val fimage = FormDataUtil.getImageBody("image", image)

        val call: Call<ResponseWriteData> =
            ApiService.writeViewService.postWrite(
                ftitle,
                fuserId,
                fprovince,
                fregion,
                fwarning,
                ftheme,
                fisParking,
                fparkingDesc,
                fcourseDesc,
                fcourse,
                fimage
            )

        call.enqueue(object : Callback<ResponseWriteData> {
            //     Log.d("server","왜 안 되냐구")
            override fun onResponse(
                call: Call<ResponseWriteData>,
                response: Response<ResponseWriteData>
            ) {
                if (response.isSuccessful) {
//                    val data = response.body()!!.title
                    Log.d("server connect", "success")


                } else {
                    Log.d("1server connect", "write fail")
                    Log.d("2server connect write fail", "${response.errorBody()}")
                    Log.d("3server connect", "write fail${response.message()}")
                    Log.d("4server connect", "write fail${response.code()}")
                    Log.d("5server connect", "write fail${response.raw().request.url}")
                    onBackPressed()
                }

            }

            override fun onFailure(call: Call<ResponseWriteData>, t: Throwable) {
                Log.d("6server connect", "error:${t.message}")
                onBackPressed()
            }
        })


//        ApiService.writeViewService.postWrite(
//                ftitle, fuserId, fprovince, fregion, fwarning, ftheme, fisParking, fparkingDesc, fcourseDesc, fcourse, fimage
//            ).apply{
//                if(this.resultCode == SUCCESS){
//
//                }
//            }
    }

    fun openGallery() {
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
            if (requestCode == 1) {    //GET_GALLERY_IMAGE
                // if (resultCode == Activity.RESULT_OK) {
                //list = List<Uri>()
                if (data?.clipData == null) {
                    Log.d("errorimg", "null")

                } else {
                    var clipData = data.clipData
                    when {
                        clipData?.itemCount!! > 6 -> {
                            Toast.makeText(this, "사진은 6개까지 선택 가능", Toast.LENGTH_LONG).show()
                        }
                        clipData.getItemCount() == 1 -> {
                            imgPath = clipData.getItemAt(0).uri
                            Log.d("clipda", imgPath.toString())
                            writeAdapter.imgList.addAll(
                                listOf<WriteImgInfo>(
                                    WriteImgInfo(
                                        imgUri = imgPath,
                                    )
                                )
                            )
                            writeAdapter.notifyItemInserted(0)
                            writeAdapter.notifyDataSetChanged()
                            //   list.add(imgPath)
                        }
                        //다중 선택 오류
                        clipData.itemCount in 2..5 -> {
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
                                // list.add(imgPath)
                            }
                            writeAdapter.imgList.addAll(imgmoreList)
                            writeAdapter.notifyItemInserted(0)
                            writeAdapter.notifyDataSetChanged()
                        }
                    }

                }
            }
            var currentImageUrl: Uri? = data?.data

            try {
                val bitmap =
                    MediaStore.Images.Media.getBitmap(contentResolver, currentImageUrl)
                binding.imgWritePhoto.setImageBitmap(bitmap)
                writeAdapter.notifyItemInserted(0)
                writeAdapter.notifyDataSetChanged()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            Log.d("ActivityResult", "something wrong")

        }
    }

    fun warningText(edt: EditText, i: Int) {
        if (edt.text.length >= (i - 2)) {
            edt.isSelected = true
        } else {
            edt.isSelected = false
        }
    }
//
//    fun addImage(){
//
//        var writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//        var readPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//        if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED) {
//        // 권한 없어서 요청
//        ActivityCompat.requestPermissions(this,
//            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE), REQ_STORAGE_PERMISSION) }
//        else { // 권한 있음
//         var intent = Intent(Intent.ACTION_PICK)
//            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//            intent.type = "image/*"
//            startActivityForResult(intent, REQ_GALLERY)
//        }
//
//
//        //이미지 표현하기
//        REQ_GALLERY -> { data?.data?.let { it ->
//            showLoading()
//            imagePath = getRealPathFromURI(it)
//            GlideUtil.loadImage(activity = this@MealsCommentActivity,
//                requestOptions = RequestOptions(),
//                image = imagePath,
//                imageView = ivSelectImage,
//                requestListener = object : RequestListener<Drawable> {
//                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
//                        hideLoading()
//                        return false
//                    }
//
//                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
//                        hideLoading()
//                        return false
//                    }
//                })
//            }
//        }
//    }
//
//
//    }
//
//    //이미지 실제 경로 반환
//    fun getRealPathFromURI(uri: Uri): String { var buildName = Build.MANUFACTURER if (buildName.equals("Xiaomi")) { return uri.path } var columnIndex = 0 var proj = arrayOf(MediaStore.Images.Media.DATA) var cursor = contentResolver.query(uri, proj, null, null, null) if (cursor.moveToFirst()) { columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA) } return cursor.getString(columnIndex) }
//
//    //고용량 이미지 리사이즈
//    fun getResizePicture(imagePath: String): Bitmap { var options = BitmapFactory.Options() options.inJustDecodeBounds = true BitmapFactory.decodeFile(imagePath, options) var resize = 1000 var width = options.outWidth var height = options.outHeight var sampleSize = 1 while (true) { if (width / 2 < resize || height / 2 < resize) break width /= 2 height /= 2 sampleSize *= 2 } options.inSampleSize = sampleSize options.inJustDecodeBounds = false var resizeBitmap = BitmapFactory.decodeFile(imagePath, options) // 회전값 조정 var exit = ExifInterface(imagePath) var exifDegree = 0 exit?.let { var exifOrientation = it.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL) exifDegree = exifOreintationToDegrees(exifOrientation) } return roteateBitmap(resizeBitmap, exifDegree) }
//
//
//        //이미지 저장
//        fun saveBitmap(bitmap: Bitmap): String { var folderPath = Environment.getExternalStorageDirectory().absolutePath + "/path/" var fileName = "comment.jpeg" var imagePath = folderPath + fileName var folder = File(folderPath) if (!folder.isDirectory) folder.mkdirs() var out = FileOutputStream(folderPath + fileName) bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out) return imagePath }
//

    fun setButtonClickEvent() {

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
        binding.btnWriteCity.setOnClickListener {
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

    //  binding.btnWriteTheme1.text.toString()
    fun startActivityWriteMap() {
        val intent = Intent(this@WriteActivity, WriteMapActivity::class.java)
        intent.putExtra("theme11", binding.btnWriteTheme1.text.toString())
            .putExtra("theme22", binding.btnWriteTheme2.text.toString())
        // startActivityForResult(intent, 100) //REQUEST_CODE
        startActivity(intent)
    }

//

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 100) { //REQUEST_CODE
//            if (resultCode == Activity.RESULT_OK) {
//                val theme1 fun selectCity() {
////        binding.btnWriteCity.setOnClickListener {
////            val searchItem = 0
////            MaterialAlertDialogBuilder(this)
////                .setTitle(binding.btnWriteCity.text)
////                .setNeutralButton("취소") { dialog, which ->
////                    binding.btnWriteCity.setText(binding.btnWriteCity.text)
////                    it.isSelected = false
////                }
////                .setPositiveButton("확인") { dialog, which ->
////
////                    if (binding.btnWriteCity.text.toString() == "도") {
////                        it.isSelected = false
////                    }
////                    it.isSelected = true
////                }
////                // Single-choice items (initialized with checked item)
////                .setSingleChoiceItems(ItemsTheme, checkedItem) { dialog, which ->
////                    //which : index
////                    //테마 고르면 텍스트 변경
////                    binding.btnWriteCity.text = ItemsTheme[which]
////                }
////                .show()
////        }
////        }= data!!.getStringExtra("theme1")
//                val theme2 = data.getStringExtra("theme2")
//                Toast.makeText(this, theme1+theme2 ,Toast.LENGTH_LONG).show()
//                binding.btnWriteTheme1.text = theme1
//                binding.btnWriteTheme2.text = theme2
//            }
//        }
//    }
    }