package com.example.charo_android.presentation.ui.write

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.charo_android.data.WriteImgInfo
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentWriteBinding
import com.example.charo_android.hidden.Hidden

import com.google.android.material.dialog.MaterialAlertDialogBuilder
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import android.text.Editable
import android.text.TextWatcher
import androidx.core.view.isVisible


class WriteFragment : Fragment() {

    inner class BitmapRequestBody(private val bitmap: Bitmap) : RequestBody(){
        override fun contentType(): MediaType? = "image/jpeg".toMediaType()

        override fun writeTo(sink: BufferedSink) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, sink.outputStream())
        }

    }

    companion object {
        fun newInstance() = WriteFragment()
    }
    private var _binding: FragmentWriteBinding? = null
    private lateinit var writeAdapter: WriteAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val sharedViewModel: WriteSharedViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                WriteSharedViewModel() as T
        }
    }

    var writeShareActivity: WriteShareActivity? = null
    var dialogThemeFragment: DialogThemeFragment? = DialogThemeFragment()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        writeShareActivity = context as WriteShareActivity
//        dialogThemeFragment = context as DialogThemeFragment
    }

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        userId = sharedViewModel.userId.value.toString()
        nickName = sharedViewModel.nickName.value.toString()

        Log.d("uuuwrite", userId)
        Log.d("uuuwrite", nickName)

        // 1. 우리가 사용할 어뎁터의 초기 값을 넣어준다
        writeAdapter = WriteAdapter()

        // 2. RecyclerView 에 어뎁터를 우리가 만든 어뎁터로 만들기
        binding.recyclerviewWriteImg.adapter = writeAdapter

        getSharedViewModelData()
        observeThemeData()

        //이미지 추가 버튼
        binding.imgWriteAddImg.setOnClickListener {
            openGallery()
        }
        warningText(binding.etWriteTitle, binding.tvWarningTitle, 38)
        warningText(binding.etWriteParkReview, binding.tvWarningParkReview, 23)
        warningText(binding.etWriteMyDrive, binding.tvWarningMyDrive,280)

        //버튼 selected 상태 변화 함수
        setButtonClickEvent()

        // 지역(도 단위)
        binding.btnWriteRegion.setOnClickListener() {
            val checkedItem = 0

//            showDialogFragment()

            MaterialAlertDialogBuilder(requireContext(),R.style.Dialog)
                .setTitle("지역")
                .setNeutralButton("취소") { dialog, which ->
                    binding.btnWriteRegion.text = resources.getString(R.string.region)
                    binding.btnWriteLocation.text = resources.getString(R.string.city)
                    binding.btnWriteLocation.isSelected = false
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
                    binding.btnWriteRegion.text = itemProvince[which]
                    if(itemProvince[which] !=  sharedViewModel.province.value){
                        binding.btnWriteLocation.text = resources.getString(R.string.city)
                        binding.btnWriteLocation.isSelected = false
                    }

                    sharedViewModel.province.value = binding.btnWriteRegion.text.toString()

                }
                .setBackground(resources.getDrawable(R.drawable.background_radius_all_20))
                .show()
        }

        binding.btnWriteLocation.setOnClickListener() {
            val checkedItem = 0
            MaterialAlertDialogBuilder(requireContext(),R.style.Dialog)
                .setTitle("지역")
                .setNeutralButton("취소") { dialog, which ->
                    binding.btnWriteLocation.text = resources.getString(R.string.city)
                    it.isSelected = false
                }
                .setPositiveButton("확인") { dialog, which ->
                    if (binding.btnWriteLocation.text.toString() == resources.getString(R.string.city)
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
                    fun setRegionForProvince(province:Array<String>){
                        if(which == -1){
                            binding.btnWriteLocation.text = province[0]
                        }else{
                            binding.btnWriteLocation.text = province[which]
                        }
                    }

                    when(binding.btnWriteRegion.text){
                        "특별시" -> {
                            setRegionForProvince(itemSpecial)
                        }
                        "광역시" -> {
                            setRegionForProvince(itemMetroPolitan)
                        }
                        "경기도" -> {
                            setRegionForProvince(itemGyounGi)
                        }
                        "강원도" -> {
                            setRegionForProvince(itemGangWon)
                        }
                        "충청남도" -> {
                            setRegionForProvince(itemChoongNam)
                        }
                        "충청북도" -> {
                            setRegionForProvince(itemChoongBuk)
                        }
                        "경상북도" -> {
                            setRegionForProvince(itemGyungBuk)
                        }
                        "경상남도" -> {
                            setRegionForProvince(itemGyungNam)
                        }
                        "전라북도" -> {
                            setRegionForProvince(itemJunBuk)
                        }
                        else -> {
                            setRegionForProvince(itemJunNam)
                        }
                    }

                    sharedViewModel.region.value = binding.btnWriteLocation.text.toString()

                }
                .setBackground(resources.getDrawable(R.drawable.background_radius_all_20))
                .show()
        }

        binding.btnWriteTheme1.setOnClickListener {
            openBottomSheetDialog()
        }
        binding.btnWriteTheme2.setOnClickListener {
            openBottomSheetDialog()
        }
        binding.btnWriteTheme3.setOnClickListener {
            openBottomSheetDialog()
        }

        //주차 - 둘 중 하나만 선택 가능하도록 하기
        binding.btnWriteParkYes.setOnClickListener() {
            binding.btnWriteParkYes.isSelected = true
            binding.btnWriteParkNo.isSelected = false

            sharedViewModel.isParking.value = true
        }
        binding.btnWriteParkNo.setOnClickListener() {
            binding.btnWriteParkNo.isSelected = true
            binding.btnWriteParkYes.isSelected = false

            sharedViewModel.isParking.value = false
        }

        binding.clWritePhoto.setOnClickListener {
            openGallery()
        }
        binding.imgWriteBack.setOnClickListener {

            AlertDialog.Builder(requireContext())
                .setMessage("게시물 작성을 중단하시겠습니까?\n")
                .setNeutralButton("이어서 작성") { dialog, which ->
                }
                .setPositiveButton("작성중단") { dialog, which ->
                    writeShareActivity?.finish()
                }
                .show()
        }

        binding.btnWriteBottomNext.setOnClickListener {
            //주의사항
            val warningList: ArrayList<MultipartBody.Part> = ArrayList()

            if(binding.btnWriteCautionHighway.isSelected) {
                warningList.add(MultipartBody.Part.createFormData("warning","highway"))
            }
            if(binding.btnWriteCautionMoun.isSelected){
                warningList.add(MultipartBody.Part.createFormData("warning","mountainRoad"))
            }
            if(binding.btnWriteCautionDiffi.isSelected){
                warningList.add(MultipartBody.Part.createFormData("warning","diffRoad"))
            }
            if(binding.btnWriteCautionPeople.isSelected){
                warningList.add(MultipartBody.Part.createFormData("warning","hotPlace"))
            }
            sharedViewModel.warning.value = warningList

            //제목
            sharedViewModel.title.value = binding.etWriteTitle.text.toString()

            //주차 설명
            sharedViewModel.parkingDesc.value = binding.etWriteParkReview.text.toString()

            //코스 설명
            sharedViewModel.courseDesc.value = binding.etWriteMyDrive.text.toString()

            writeShareActivity!!.replaceFragment(WriteMapFragment.newInstance(), "writeMap");
        }

        return root
    }

    private fun observeThemeData(){
        //테마 변경 실시간 적용
        sharedViewModel.theme.observe(viewLifecycleOwner, Observer<ArrayList<String>> { newTheme ->
            val textView = arrayOfNulls<TextView>(3)
            var theme : Int

            for(i in 0 until 3){
                theme = resources.getIdentifier("btn_write_theme${i+1}","id", activity?.packageName)
                textView[i] = view?.findViewById(theme)

                //초기화   "테마${i+1}"
                textView[i]?.text = getString(resources.getIdentifier("theme${i+1}","string", activity?.packageName))
                textView[i]?.isSelected = false
            }

            for(i in 0 until newTheme.count()){
                //재설정
                textView[i]?.text = newTheme[i]
                textView[i]?.isSelected = true
            }
        })

    }

    private fun openBottomSheetDialog() {
        val bottomSheetDialogFragment = DialogThemeFragment()
        bottomSheetDialogFragment.show(childFragmentManager, bottomSheetDialogFragment.tag)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        sharedViewModel = ViewModelProvider(this).get(WriteSharedViewModel::class.java)
        // TODO: Use the ViewModel
    }


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
        var image = ArrayList<MultipartBody.Part>()
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                if (data?.clipData == null) {
                    Log.d("errorimg", "null")

                } else {
                    var clipData = data.clipData
                    var imageUriList = ArrayList<Uri>()
                    when {
                        clipData?.itemCount!! > 6 -> {
//                            Toast.makeText(this, "사진은 6개까지 선택 가능", Toast.LENGTH_LONG).show()
                        }
                        clipData.getItemCount() == 1 -> {
                            binding.clWritePhoto.visibility = View.GONE
                            imgPath = clipData.getItemAt(0).uri

                            //리사이클러뷰에 사진 추가
                            writeAdapter.imgList.add(
                                WriteImgInfo(
                                    imgUri = imgPath,
                                )
                            )
                            sharedViewModel.imageUriRecyclerView.value = writeAdapter.imgList


                            //UriList 에 추가
                            imageUriList.add(imgPath)


                            writeAdapter.notifyItemInserted(0)
                            writeAdapter.notifyDataSetChanged()

//                            //uri -> file -> multipartform
//                            for (index in 0..writeAdapter.imgList.size - 1) {
//                                val file = File(writeAdapter.imgList[index].imgUri.path)
//                                val MEDIA_TYPE_IMAGE = "image/*".toMediaTypeOrNull()
//                                val surveyBody = RequestBody.create(MEDIA_TYPE_IMAGE, file)
//                                image.add(MultipartBody.Part.createFormData("image", "file.png", surveyBody))
//                            }
//                            sharedViewModel.imageMultiPart.value = image
//
//                            Log.e("image",image.toString())
//                            Log.e("sharedViewModel.imageUriRecyclerView.value",sharedViewModel.imageUriRecyclerView.value.toString())
//                            Log.e("sharedViewModel.imageMultiPart.value",sharedViewModel.imageMultiPart.value.toString())
//                            Log.e("imgPath",imgPath.toString())

                            //uri -> Bitmap -> multipartform
                            val bitmap : Bitmap

                            if(Build.VERSION.SDK_INT < 28) {
                                bitmap = MediaStore.Images.Media.getBitmap(
                                    context?.contentResolver,
                                    imgPath
                                )
                            } else {
                                val source = ImageDecoder.createSource(requireContext().contentResolver, imgPath)
                                bitmap = ImageDecoder.decodeBitmap(source)
                            }

                            val imageRequestBody = bitmap?.let {
                                BitmapRequestBody(it)
                            }
                            val imageData: MultipartBody.Part =
                                MultipartBody.Part.createFormData("image", "image.jpeg", imageRequestBody)

                            image.add(imageData)
                            sharedViewModel.imageMultiPart.value = image

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
        } else {
            Log.d("ActivityResult", "something wrong")

        }
    }

    private fun warningText(edt: EditText, warningTxt: TextView, len: Int) {
        edt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun afterTextChanged(p0: Editable?) {
                edt.removeTextChangedListener(this)

                val textLength : Int = p0.toString().length
                if(len > 200)
                    binding.tvLenMyDrive.text = "${textLength}/${len}자"

                if(textLength > len) {
                    edt.setText(p0.toString().substring(0,textLength-1))
                    edt.setSelection(textLength-1)
                }
                edt.isSelected = textLength > len
                warningTxt.isVisible = textLength > len

                edt.addTextChangedListener(this)
            }
        })
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

//        startActivityWriteMap()
//        writeShareActivity?.replaceFragment(WriteMapFragment, "writeMap");

    }

    //돌아왔을 때 값 유지
    fun getSharedViewModelData() {
        //사진
        if (sharedViewModel.imageMultiPart.value != null) {
            binding.clWritePhoto.visibility = View.GONE

            var imgmoreList = mutableListOf<WriteImgInfo>()

            sharedViewModel.imageUriRecyclerView.value?.forEach { imageUri ->
                writeAdapter.imgList = sharedViewModel.imageUriRecyclerView.value!!
            }
            Log.e("imgmoreList", imgmoreList.toString())
            writeAdapter.notifyItemInserted(0)
            writeAdapter.notifyDataSetChanged()

        }

        //지역 도 시
        if(sharedViewModel.province.value != ""){
            binding.btnWriteRegion.text = sharedViewModel.province.value
            binding.btnWriteRegion.isSelected = true
        }
        if(sharedViewModel.region.value != "") {
            binding.btnWriteLocation.text = sharedViewModel.region.value
            binding.btnWriteLocation.isSelected = true
        }

        //주차 있없
        if(sharedViewModel.isParking.value == true){
            binding.btnWriteParkYes.isSelected = true
            binding.btnWriteParkNo.isSelected = false

        }else if(sharedViewModel.isParking.value == false){
            binding.btnWriteParkYes.isSelected = false
            binding.btnWriteParkNo.isSelected = true
        }

        //주의사항
//        sharedViewModel.warning.value?.forEach { warning ->
//            when (warning) {
//                ("warning","highway") -> {
//                    binding.btnWriteCautionHighway.isSelected = true
//                }
//                "mountainRoad" -> {
//                    binding.btnWriteCautionMoun.isSelected = true
//                }
//                "" -> {
//                    binding.btnWriteCautionDiffi.isSelected = true
//                }
//                "p" -> {
//                    binding.btnWriteCautionPeople.isSelected = true
//                }
//            }
//        }
    }

}
