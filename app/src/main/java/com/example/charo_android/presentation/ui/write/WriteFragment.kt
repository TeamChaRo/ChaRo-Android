package com.example.charo_android.presentation.ui.write

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentWriteBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class WriteFragment : Fragment() {

    private lateinit var writeViewModel: WriteViewModel
    private var _binding: FragmentWriteBinding? = null

    private lateinit var writeAdapter: WriteAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        writeViewModel = ViewModelProvider(this).get(WriteViewModel::class.java)

        _binding = FragmentWriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textWrite
//        writeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        // 1. 우리가 사용할 어뎁터의 초기 값을 넣어준다
        writeAdapter = WriteAdapter()

        // 2. RecyclerView 에 어뎁터를 우리가 만든 어뎁터로 만들기
        binding.recyclerviewWriteImg.adapter = writeAdapter

        binding.imgWriteAddImg.setOnClickListener {
            var list = listOf<Uri>()
        }

        //이미지 업로드 리스트 부분
//        writeAdapter.imgList.addAll(
//            listOf<WriteImgInfo>(
//                WriteImgInfo(
//                    imgUri = "",
//                )
//            )
//        )
        writeAdapter.notifyDataSetChanged()

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
            MaterialAlertDialogBuilder(it.context)
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
            MaterialAlertDialogBuilder(it.context)
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
            MaterialAlertDialogBuilder(it.context)
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


        //startActivityForResult() - 화면전환 + 결과값 반환 로 변경 필요
        binding.btnWriteBottomNext.setOnClickListener {
            startActivityWriteMap()

        }

        binding.clWritePhoto.setOnClickListener {
            // addImage()
            openGallery()
        }

        return root
    }

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
    fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent, 1)   //OPEN_GALLERY
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == 1) {    //OPEN_GALLERY
//                var currentImageUrl: Uri? = data?.data
//
//                try {
//                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, currentImageUrl)
//                    binding.imgWritePhoto.setImageBitmap(bitmap)
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//            }
//        } else {
//            Log.d("ActivityResult", "something wrong")
//        }
    }

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
//        val intent = Intent(this@WriteActivity, WriteMapActivity::class.java)
//        intent.putExtra("theme11", binding.btnWriteTheme1.text.toString())
//            .putExtra("theme22", binding.btnWriteTheme2.text.toString())
//        // startActivityForResult(intent, 100) //REQUEST_CODE
//        startActivity(intent)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 100) { //REQUEST_CODE
//            if (resultCode == Activity.RESULT_OK) {
//                val theme1 = data!!.getStringExtra("theme1")
//                val theme2 = data.getStringExtra("theme2")
//                Toast.makeText(this, theme1+theme2 ,Toast.LENGTH_LONG).show()
//                binding.btnWriteTheme1.text = theme1
//                binding.btnWriteTheme2.text = theme2
//            }
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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