package com.example.charo_android.presentation.ui.write

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
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

import com.google.android.material.dialog.MaterialAlertDialogBuilder
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.charo_android.presentation.util.Define
import com.example.charo_android.presentation.util.LocationUtil
import com.example.charo_android.presentation.util.ThemeUtil


class WriteFragment : Fragment() {

    companion object {
        fun newInstance() = WriteFragment()
    }
    private var _binding: FragmentWriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var writeAdapter: WriteAdapter
    private val sharedViewModel: WriteSharedViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                WriteSharedViewModel() as T
        }
    }

    var writeShareActivity: WriteShareActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        writeShareActivity = context as WriteShareActivity
    }

    private var locationUtil = LocationUtil()

    private var preCheckProvince = 0
    private var preCheckedRegion = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initToolBar()

        writeAdapter = WriteAdapter(){
            //delete image
            writeAdapter.imgList.removeAt(it)
            writeAdapter.notifyDataSetChanged()
        }
        binding.recyclerviewWriteImg.adapter = writeAdapter

        initWriteData()
        observeThemeData()

        //이미지 추가 버튼
        binding.imgWriteAddImg.setOnClickListener {
            openGallery()
        }

        //글자수 제한
        warningText(binding.etWriteTitle, binding.tvWarningTitle, 38)
        warningText(binding.etWriteParkReview, binding.tvWarningParkReview, 23)
        warningText(binding.etWriteMyDrive, binding.tvWarningMyDrive,280)

        //버튼 selected 상태 변화 함수
        setButtonClickEvent()

        // 지역(도 단위)
        binding.btnWriteRegion.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext(),R.style.Dialog)
                .setTitle(R.string.area)
                .setNeutralButton(R.string.cancel) { dialog, which ->
                    binding.btnWriteRegion.text = resources.getString(R.string.region)
                    binding.btnWriteLocation.text = resources.getString(R.string.city)
                    binding.btnWriteLocation.isSelected = false
                    it.isSelected = false
                    preCheckProvince = 0
                }
                .setPositiveButton(R.string.agreement) { dialog, which ->
                    it.isSelected = true
                    binding.btnWriteRegion.text = locationUtil.itemProvince[preCheckProvince]
                    sharedViewModel.province.value = binding.btnWriteRegion.text.toString()
                }
                .setSingleChoiceItems(locationUtil.itemProvince, preCheckProvince) { dialog, which ->
                    //which : index
                    preCheckProvince = which

                    //이전 선택값과 다를 때
                    if(locationUtil.itemProvince[which] !=  sharedViewModel.province.value){
                        binding.btnWriteLocation.text = resources.getString(R.string.city)
                        binding.btnWriteLocation.isSelected = false
                        sharedViewModel.region.value = ""
                    }
                }
                .setCancelable(false)
                .setBackground(resources.getDrawable(R.drawable.background_radius_all_20))
                .show()
        }

        binding.btnWriteLocation.setOnClickListener() {
            MaterialAlertDialogBuilder(requireContext(),R.style.Dialog)
                .setTitle(R.string.area)
                .setNeutralButton(R.string.cancel) { dialog, which ->
                    binding.btnWriteLocation.text = resources.getString(R.string.city)
                    it.isSelected = false
                    preCheckedRegion = 0
                    sharedViewModel.region.value = ""
                }
                .setPositiveButton(R.string.agreement) { dialog, which ->
                    it.isSelected = true
                    when(binding.btnWriteRegion.text){
                        "특별시" -> binding.btnWriteLocation.text = locationUtil.itemSpecial[preCheckedRegion]
                        "광역시" -> binding.btnWriteLocation.text = locationUtil.itemMetroPolitan[preCheckedRegion]
                        "경기도" -> binding.btnWriteLocation.text = locationUtil.itemGyounGi[preCheckedRegion]
                        "강원도" -> binding.btnWriteLocation.text = locationUtil.itemGangWon[preCheckedRegion]
                        "충청남도" -> binding.btnWriteLocation.text = locationUtil.itemChoongNam[preCheckedRegion]
                        "충청북도" -> binding.btnWriteLocation.text = locationUtil.itemChoongBuk[preCheckedRegion]
                        "경상북도" -> binding.btnWriteLocation.text = locationUtil.itemGyungBuk[preCheckedRegion]
                        "경상남도" -> binding.btnWriteLocation.text = locationUtil.itemGyungNam[preCheckedRegion]
                        "전라북도" -> binding.btnWriteLocation.text = locationUtil.itemJunBuk[preCheckedRegion]
                        "전라남도" -> binding.btnWriteLocation.text = locationUtil.itemJunNam[preCheckedRegion]
                        else -> {
                            binding.btnWriteLocation.text = resources.getString(R.string.city)
                            it.isSelected = false
                        }
                    }

                    sharedViewModel.region.value = binding.btnWriteLocation.text.toString()
                }
                .setSingleChoiceItems(
                    if (binding.btnWriteRegion.text == "특별시") locationUtil.itemSpecial
                    else if (binding.btnWriteRegion.text == "광역시") locationUtil.itemMetroPolitan
                    else if (binding.btnWriteRegion.text == "경기도") locationUtil.itemGyounGi
                    else if (binding.btnWriteRegion.text == "강원도") locationUtil.itemGangWon
                    else if (binding.btnWriteRegion.text == "충청남도") locationUtil.itemChoongNam
                    else if (binding.btnWriteRegion.text == "충청북도") locationUtil.itemChoongBuk
                    else if (binding.btnWriteRegion.text == "경상북도") locationUtil.itemGyungBuk
                    else if (binding.btnWriteRegion.text == "경상남도") locationUtil.itemGyungNam
                    else if (binding.btnWriteRegion.text == "전라북도") locationUtil.itemJunBuk
                    else if (binding.btnWriteRegion.text == "전라남도") locationUtil.itemJunNam
                    else arrayOf("도 단위를 선택해주세요.")
                    , preCheckedRegion
                ) { dialog, which ->
                    //which : index
                    preCheckedRegion = which
                }
                .setCancelable(false)
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

        //주차 - 둘 중 하나만 선택 가능
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

        // 다음 버튼
        binding.btnWriteBottomNext.setOnClickListener {
            //주의사항
            val warningList: ArrayList<MultipartBody.Part> = ArrayList()
            val warningUIList: ArrayList<String> = ArrayList()

            if(binding.btnWriteCautionHighway.isSelected) {
                warningList.add(MultipartBody.Part.createFormData("warning", Define().WARNING_HIGH_WAY))
                warningUIList.add(Define().WARNING_HIGH_WAY)
            }
            if(binding.btnWriteCautionMoun.isSelected){
                warningList.add(MultipartBody.Part.createFormData("warning", Define().WARNING_MOUNTAIN_ROAD))
                warningUIList.add(Define().WARNING_MOUNTAIN_ROAD)
            }
            if(binding.btnWriteCautionDiffi.isSelected){
                warningList.add(MultipartBody.Part.createFormData("warning", Define().WARNING_DIFF_ROAD))
                warningUIList.add(Define().WARNING_DIFF_ROAD)
            }
            if(binding.btnWriteCautionPeople.isSelected){
                warningList.add(MultipartBody.Part.createFormData("warning",Define().WARNING_HOT_PLACE))
                warningUIList.add(Define().WARNING_HOT_PLACE)
            }
            sharedViewModel.warning.value = warningList
            sharedViewModel.warningUI.value = warningUIList

            //제목
            sharedViewModel.title.value = binding.etWriteTitle.text.toString()

            //주차 설명
            sharedViewModel.parkingDesc.value = binding.etWriteParkReview.text.toString()

            //코스 설명
            sharedViewModel.courseDesc.value = binding.etWriteMyDrive.text.toString()

            Log.e("check","title ${sharedViewModel.title.value} \n" +
                    "courseDesc ${sharedViewModel.courseDesc.value} \n" +
                    "warningList ${warningList} \n imageMultiPart ${sharedViewModel.imageMultiPart.value} \n" +
                    "province ${sharedViewModel.province.value} \n region ${sharedViewModel.region.value} \n" +
                    "theme ${sharedViewModel.theme.value} ")
            //빈값 확인
            if(TextUtils.isEmpty(sharedViewModel.title.value)
                || TextUtils.isEmpty(sharedViewModel.province.value)
                || (getString(R.string.no_select) != sharedViewModel.province.value && TextUtils.isEmpty(sharedViewModel.region.value))
                || TextUtils.isEmpty(sharedViewModel.courseDesc.value) || warningList.size == 0
                || (sharedViewModel.imageMultiPart.value == null || sharedViewModel.imageMultiPart.value?.size == 0)
                || (sharedViewModel.theme.value == null || sharedViewModel.theme.value?.size == 0)){

                    Toast.makeText(requireContext(),getString(R.string.txt_check_all_input),Toast.LENGTH_LONG).show()
            }else{
                writeShareActivity!!.replaceAddStackFragment(WriteMapFragment.newInstance(), "writeMap");
            }
        }

        return root
    }

    private fun initToolBar() {
        val toolbar = binding.toolbarWrite
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_black)

        setHasOptionsMenu(true)
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
                textView[i]?.text = ThemeUtil().themeMap.entries.find { it.value == newTheme[i] }?.key
                textView[i]?.isSelected = true
            }
        })

    }

    private fun openBottomSheetDialog() {
        val bottomSheetDialogFragment = DialogThemeFragment()
        bottomSheetDialogFragment.show(childFragmentManager, bottomSheetDialogFragment.tag)
    }

    private fun openGallery() {
        if(writeAdapter.itemCount > 6){
            Toast.makeText(context, "사진은 6개까지 선택 가능합니다.", Toast.LENGTH_LONG).show()
            return
        }

        var writePermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
        var readPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
        // 권한 없어서 요청
        if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED) {
             ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 1000)

        } else { // 권한 있음
            val intent = Intent(Intent.ACTION_GET_CONTENT)  //ACTION_PICK   //ACTION_GET_CONTENT
            intent.type = "image/*"
            //다중 선택 가능
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)

            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI //데이터를 Uri로 받는다
            startActivityForResult(Intent.createChooser(intent, "Get Album"), 200)   //1 OPEN_GALLERY //200 GET_GALLERY_IMAGE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var imgPath: Uri
        var image = ArrayList<MultipartBody.Part>()
        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == 1) {
                if (data?.clipData == null) {
                    Log.d("error img", "null or not image")

                } else {
                    val clipData = data.clipData
                    when {
                        clipData?.itemCount!! > 6 -> {
                            Toast.makeText(context, "사진은 6개까지 선택 가능합니다.", Toast.LENGTH_LONG).show()
                        }
                        clipData.itemCount in 1..6 -> {
                            binding.clWritePhoto.visibility = View.GONE
                            val imgMoreList = mutableListOf<WriteImgInfo>()
                            for (i: Int in 0 until clipData.itemCount) {
                                imgPath = clipData.getItemAt(i).uri

                                //recyclerView 에 저장
                                imgMoreList.add(
                                    0,
                                    WriteImgInfo(
                                        imgUri = imgPath,
                                    )
                                )

                                //이미지 멀티파트로 저장
                                convertImgToMultiPart(imgPath, image)
                            }

                            val position = writeAdapter.itemCount
                            val newCount = position + imgMoreList.size
                            if(newCount > 6){
                                Toast.makeText(context, "사진은 6개까지 선택 가능합니다. \n다시 선택해주세요.", Toast.LENGTH_LONG).show()
                            }else{
                                writeAdapter.imgList.addAll(imgMoreList)
                                writeAdapter.notifyItemInserted(position)   //기존에 선택된 항목 뒤에서부터 set

                                sharedViewModel.imageMultiPart.value?.addAll(image)
                            }
                        }
                    }
                }
//            }
        } else {
            Log.d("ActivityResult", "something wrong")
        }
    }

    private fun convertImgToMultiPart(imgPath : Uri, list : ArrayList<MultipartBody.Part>){
        //uri -> Bitmap -> multipartform
        val bitmap : Bitmap = if(Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(
                context?.contentResolver,
                imgPath
            )
        } else {
            val source = ImageDecoder.createSource(requireContext().contentResolver, imgPath)
            ImageDecoder.decodeBitmap(source)
        }

        val imageRequestBody = BitmapRequestBody(bitmap)
        val imageData: MultipartBody.Part =
            MultipartBody.Part.createFormData("image", "image.jpeg", imageRequestBody)

        list.add(imageData)
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
    }

    //돌아왔을 때 값 유지
    fun initWriteData() {
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
        if(sharedViewModel.warningUI.value != null){
            for(warningData in sharedViewModel.warningUI.value!!){
                when(warningData){
                    Define().WARNING_HIGH_WAY -> binding.btnWriteCautionHighway.isSelected = true
                    Define().WARNING_DIFF_ROAD -> binding.btnWriteCautionDiffi.isSelected = true
                    Define().WARNING_MOUNTAIN_ROAD -> binding.btnWriteCautionMoun.isSelected = true
                    Define().WARNING_HOT_PLACE -> binding.btnWriteCautionPeople.isSelected = true
                }
            }
        }

    }

    //이미지 보내기
    inner class BitmapRequestBody(private val bitmap: Bitmap) : RequestBody(){
        override fun contentType(): MediaType = "image/jpeg".toMediaType()

        override fun writeTo(sink: BufferedSink) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, sink.outputStream())
        }

    }
}
