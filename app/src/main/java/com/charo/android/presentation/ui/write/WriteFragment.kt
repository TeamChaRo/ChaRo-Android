package com.charo.android.presentation.ui.write

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.charo.android.R
import com.charo.android.data.model.write.WriteImgInfo
import com.charo.android.databinding.FragmentWriteBinding
import com.charo.android.presentation.util.Define
import com.charo.android.presentation.util.LocationUtil
import com.charo.android.presentation.util.ThemeUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import java.net.URL


class WriteFragment : Fragment(), View.OnClickListener {

    private val TAG = "승현"

    companion object {
        fun newInstance() = WriteFragment()
    }

    private var _binding: FragmentWriteBinding? = null
    private val binding get() = _binding!!

    lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    private lateinit var writeAdapter: WriteAdapter

    //    원래 진희코드
//    private val sharedViewModel: WriteSharedViewModel by activityViewModels {
//        object : ViewModelProvider.Factory {
//            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
//                WriteSharedViewModel() as T
//        }
//    }
    private val sharedViewModel by sharedViewModel<WriteSharedViewModel>()

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

        writeAdapter = WriteAdapter() {
            //delete image
            writeAdapter.imgList.removeAt(it)
            writeAdapter.notifyDataSetChanged()

            // note(승현):
            // ViewModel 에 이미지 멀티파트 리스트 값을 할당해주는 과정은 최종적으로 다음 버튼을 누를 때 이루어짐
            // 따라서, RecyclerView 에서 이미지를 아무리 추가하고 삭제한다고 하더라도 '다음' 버튼을 누르기 전까지는
            // 이미지 멀티파트 리스트에는 아무런 값이 들어있지 않음
            // 따라서, 아래 코드는 리팩토링을 거친 결과 불필요하게 되므로 주석처리함
//            sharedViewModel.imageMultiPart.value!!.removeAt(it)

            setPlusIconLoc()
        }
        binding.recyclerviewWriteImg.adapter = writeAdapter

        // 승현
        if (sharedViewModel.editFlag.value == true) {
            initEditData()
        }

        initListener()
        initWriteData()
        observeThemeData()

        galleryLauncher()

        //글자수 제한
        warningText(binding.etWriteTitle, binding.tvWarningTitle, 38)
        warningText(binding.etWriteParkReview, binding.tvWarningParkReview, 23)
        warningText(binding.etWriteMyDrive, binding.tvWarningMyDrive, 280)

        return root
    }

    @SuppressLint("SetTextI18n")
    private fun initEditData() {
        with(sharedViewModel) {
            // this = sharedViewModel
            // 일부 변환이 필요한 Data의 경우 ViewModel이 관리할 수 있으면 ViewModel이 관리하게 함
            this.initEditData()

            // 직접 Fragment단에서 수정해야 하는 것들(양방향 DataBinding 미사용이므로 여기서 꼭 처리해줘야 함)
            // 툴바 타이틀
            binding.tvWriteToolbarTitle.text = "수정하기"
            // 글 제목
            binding.etWriteTitle.setText(this.title.value)
            // 주차공간 설명
            binding.etWriteParkReview.setText(this.parkingDesc.value)
            // 주의사항의 경우 isSelected를 통해 값을 관리하기 때문에 Fragment단에서 관리해야 함
            this.warnings.value?.let {
                binding.btnWriteCautionHighway.isSelected = it[0]
                binding.btnWriteCautionMoun.isSelected = it[1]
                binding.btnWriteCautionDiffi.isSelected = it[2]
                binding.btnWriteCautionPeople.isSelected = it[3]
            }
            // 코스 설명
            binding.etWriteMyDrive.setText(this.courseDesc.value)
            // 코스 설명 글자수
            binding.tvLenMyDrive.text = "${this.courseDesc.value?.length}/280자"
        }
    }

    private fun initToolBar() {
        val toolbar = binding.toolbarWrite
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_black)

        setHasOptionsMenu(true)
    }

    private fun initListener() {
        binding.imgWriteAddImg.setOnClickListener(this)
        binding.clWritePhoto.setOnClickListener(this)
        binding.btnWriteCautionHighway.setOnClickListener(this)
        binding.btnWriteCautionPeople.setOnClickListener(this)
        binding.btnWriteCautionDiffi.setOnClickListener(this)
        binding.btnWriteCautionMoun.setOnClickListener(this)
        binding.btnWriteRegion.setOnClickListener(this)
        binding.btnWriteProvince.setOnClickListener(this)
        binding.btnWriteParkNo.setOnClickListener(this)
        binding.btnWriteParkYes.setOnClickListener(this)
        binding.btnWriteTheme1.setOnClickListener(this)
        binding.btnWriteTheme2.setOnClickListener(this)
        binding.btnWriteTheme3.setOnClickListener(this)
        binding.btnWriteParkYes.setOnClickListener(this)
        binding.btnWriteParkNo.setOnClickListener(this)
        binding.btnWriteBottomNext.setOnClickListener(this)
        binding.btnWriteProvince.setOnClickListener(this)
        binding.btnWriteRegion.setOnClickListener(this)
    }

    private fun warningText(edt: EditText, warningTxt: TextView, len: Int) {
        edt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                edt.removeTextChangedListener(this)

                val textLength: Int = p0.toString().length
                if (len > 200)
                    binding.tvLenMyDrive.text = "${textLength}/${len}자"

                if (textLength > len) {
                    edt.setText(p0.toString().substring(0, textLength - 1))
                    edt.setSelection(textLength - 1)
                }
                edt.isSelected = textLength > len
                warningTxt.isVisible = textLength > len

                edt.addTextChangedListener(this)
            }
        })
    }

    //지역 (도 단위)
    private fun selectProvince(it: View) {
        MaterialAlertDialogBuilder(requireContext(), R.style.Dialog)
            .setTitle(R.string.area)
            .setNeutralButton(R.string.cancel) { dialog, which ->
                binding.btnWriteProvince.text = resources.getString(R.string.region)
                binding.btnWriteRegion.text = resources.getString(R.string.city)
                binding.btnWriteRegion.isSelected = false
                it.isSelected = false
                preCheckProvince = 0
            }
            .setPositiveButton(R.string.agreement) { dialog, which ->
                it.isSelected = true
                binding.btnWriteProvince.text = locationUtil.itemProvince[preCheckProvince]
                sharedViewModel.province.value = binding.btnWriteProvince.text.toString()
                selectRegion(binding.btnWriteRegion)
            }
            .setSingleChoiceItems(locationUtil.itemProvince, preCheckProvince) { dialog, which ->
                //which : index
                preCheckProvince = which

                //이전 선택값과 다를 때
                if (locationUtil.itemProvince[which] != sharedViewModel.province.value) {
                    binding.btnWriteRegion.text = resources.getString(R.string.city)
                    binding.btnWriteRegion.isSelected = false
                    sharedViewModel.region.value = ""
                }
            }
            .setCancelable(false)
            .setBackground(resources.getDrawable(R.drawable.background_radius_all_20))
            .show()
    }

    //지역 (시 단위)
    private fun selectRegion(it: View) {
        MaterialAlertDialogBuilder(requireContext(), R.style.Dialog)
            .setTitle(R.string.area)
            .setNeutralButton(R.string.cancel) { dialog, which ->
                binding.btnWriteRegion.text = resources.getString(R.string.city)
                it.isSelected = false
                preCheckedRegion = 0
                sharedViewModel.region.value = ""
            }
            .setPositiveButton(R.string.agreement) { dialog, which ->
                it.isSelected = true

                if (locationUtil.matchRegionToProvince[binding.btnWriteProvince.text] == null) {
                    binding.btnWriteRegion.text = resources.getString(R.string.city)
                    it.isSelected = false
                } else {
                    binding.btnWriteRegion.text =
                        locationUtil.matchRegionToProvince[binding.btnWriteProvince.text]?.get(
                            preCheckedRegion
                        )
                            ?: resources.getString(R.string.city)

                    sharedViewModel.region.value = binding.btnWriteRegion.text.toString()
                }
            }
            .setSingleChoiceItems(
                locationUtil.matchRegionToProvince[binding.btnWriteProvince.text]
                    ?: arrayOf("도 단위를 선택해주세요."), preCheckedRegion
            ) { dialog, which ->
                //which : index
                preCheckedRegion = which
            }
            .setCancelable(false)
            .setBackground(resources.getDrawable(R.drawable.background_radius_all_20))
            .show()
    }

    private fun observeThemeData() {
        //테마 변경 실시간 적용
        sharedViewModel.theme.observe(viewLifecycleOwner, Observer<ArrayList<String>> { newTheme ->
            val textView = arrayOfNulls<TextView>(3)
            var theme: Int

            for (i in 0 until 3) {
                theme =
                    resources.getIdentifier("btn_write_theme${i + 1}", "id", activity?.packageName)
                textView[i] = view?.findViewById(theme)

                //초기화   "테마${i+1}"
                textView[i]?.text = getString(
                    resources.getIdentifier(
                        "theme${i + 1}",
                        "string",
                        activity?.packageName
                    )
                )
                textView[i]?.isSelected = false
            }

            for (i in 0 until newTheme.count()) {
                //재설정
                textView[i]?.text =
                    ThemeUtil().themeMap.entries.find { it.value == newTheme[i] }?.key
                textView[i]?.isSelected = true
            }
        })

    }

    private fun openThemeDialog() {
        val bottomSheetDialogFragment = DialogThemeFragment()
        bottomSheetDialogFragment.show(childFragmentManager, bottomSheetDialogFragment.tag)
    }

    // note(승현):
    // 어차피 리팩토링으로 인해서 '다음' 버튼을 눌렀을 때 일괄적으로 Uri 를 멀티파트화 시킨다면,
    // 굳이 WriteImgInfo 객체 하나하나 받을 이유가 있을까? -> 없다
    // 또, 서버 DB에 직접 접근해야 하는 경우 네트워크를 타야 하기 때문에 IO 스레드를 이용하든, 코루틴을 쓰든 해야 함
    // 게다가 서버 갔다 온 이후에 멀티파트화 시킨 다음에 멀티파트화한 리스트를 가지고 있어야
    // nextButtonToMap() 메서드 내 빈 값 체크에서 안 걸림
    // 그러면 그냥 코루틴 async 써서 처리하면 되지 않을까? 하는 마음으로 리팩토링함
    // 파라미터 WriteImgInfo 객체 -> WriteImgInfo 리스트 로 변경
    private suspend fun convertImgToMultiPart(
        writeImgInfoList: MutableList<WriteImgInfo>,
    ): ArrayList<MultipartBody.Part> {
        val list = ArrayList<MultipartBody.Part>()
        writeImgInfoList.forEach { writeImgInfo ->
            when (writeImgInfo.isFromLocal) {
                // note(승현):
                // writeImgInfo 의 imgUri 가 기기 로컬 저장소의 Uri 인 경우
                true -> {
                    // note(승현):
                    // 단순 Log
                    Timber.tag("convertImgToMultiPart()").i("로컬 이미지 convert 수행")
                    //uri -> Bitmap -> multipartform
                    val bitmap: Bitmap = if (Build.VERSION.SDK_INT < 28) {
                        MediaStore.Images.Media.getBitmap(
                            context?.contentResolver,
                            writeImgInfo.imgUri
                        )
                    } else {
                        val source = ImageDecoder.createSource(
                            requireContext().contentResolver,
                            writeImgInfo.imgUri
                        )
                        ImageDecoder.decodeBitmap(source)
                    }

                    val imageRequestBody = BitmapRequestBody(bitmap)
                    val imageData: MultipartBody.Part =
                        MultipartBody.Part.createFormData(
                            "image",
                            "image.jpeg",
                            imageRequestBody
                        )

                    list.add(imageData)
                    // note(승현):
                    // 단순 Log
                    Timber.tag("convertImgToMultiPart()::convert 후 List").i(list.toString())
                }
                // note(승현):
                // writeImgInfo 의 imgUri 가 서버 DB의 Uri 인 경우
                false -> {
                    // note(승현):
                    // 단순 Log
                    Timber.tag("convertImgToMultiPart()").i("리모트 이미지 convert 수행")
                    // Uri -> String -> Bitmap -> MultiPartForm
                    // note(승현):
                    // 메인 스레드에서 네트워크 액세스를 시도할 경우 에러 발생하기 때문에
                    // 코루틴을 사용해 이미지를 가져와 bitmap 으로 변환시킨 후 성공할 경우 bitmap 을 멀티파트화 시킴
                    // 실패할 경우 어떻게 처리할 것인지 약간의 논의 필요함
                    kotlin.runCatching {
                        withContext(Dispatchers.IO) {
                            val url = URL(writeImgInfo.imgUri.toString())
                            BitmapFactory.decodeStream(url.openConnection().getInputStream())
                        }
                    }.onSuccess { bitmap ->
                        val imageRequestBody = BitmapRequestBody(bitmap)
                        val imageData: MultipartBody.Part =
                            MultipartBody.Part.createFormData(
                                "image",
                                "image.jpeg",
                                imageRequestBody
                            )

                        list.add(imageData)
                        // note(승현):
                        // 단순 Log
                        Timber.tag("convertImgToMultiPart()::convert 후 List").i(list.toString())
                    }.onFailure { error ->
                        Timber.tag("convertImgToMultiPart::서버 DB 이미지 가져와 bitmap 만들기 실패")
                            .e(error)
                    }
                }
            }
        }
        return list
    }

    private fun galleryLauncher() {
        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                var imgPath: Uri
                // note(승현):
                // image: ArrayList<MultipartBody.Part> 는 convertImgToMultiPart() 메서드를 실행할 때
                // 인자로 넣어주기 위해 존재하는 변수
                // convertImgToMultiPart() 메서드 호출지점을 galleryLauncher() 가 아닌
                // nextButtonToMap() 메서드로 이동시켰기 때문에 아래 변수는 이 스코프에서 사용하지 않는 변수가 됨
                // 따라서 주석처리
//                val image = ArrayList<MultipartBody.Part>()
                if (it.resultCode == Activity.RESULT_OK) {
                    if (it.data == null) {
                        Timber.d("error img data null")
                        return@registerForActivityResult
                    }

                    if (it.data == null || it.data?.clipData == null) {
                        Toast.makeText(context, "이미지만 선택 가능합니다.", Toast.LENGTH_LONG).show()
                        return@registerForActivityResult
                    }

                    val clipData = it.data!!.clipData
                    when {
                        clipData?.itemCount!! > 6 -> {
                            Toast.makeText(context, "사진은 6개까지 선택 가능합니다.", Toast.LENGTH_LONG).show()
                        }
                        clipData.itemCount in 1..6 -> {
                            binding.clWritePhoto.visibility = View.GONE
                            val imgMoreList = mutableListOf<WriteImgInfo>()
                            for (i: Int in 0 until clipData.itemCount) {
                                imgPath = clipData.getItemAt(i).uri

                                // note(승현):
                                // imgMoreList.add(0, writeImgInfo)로 인해 작성하기 UI 상에서의 이미지 순서와
                                // 상세보기 UI 상에서의 이미지 순서가 달라지게 됨
                                // 따라서, add(index, element) 메서드 대신 add(element) 메서드로 대체함
                                //recyclerView 에 저장
                                val writeImgInfo =
                                    WriteImgInfo(imgUri = imgPath, isFromLocal = true)
                                imgMoreList.add(
//                                    0,
                                    writeImgInfo
                                )

                                // note(승현):
                                // 기존의 코드는 갤러리에서 이미지를 가져왔을 때 콜백으로 convertImgToMultiPart() 메서드를 호출하는 방식
                                // 이는 기존 작성하기처럼 로컬 저장소에서 이미지를 가져오는 경우에는 문제없이 동작하나,
                                // 수정하기처럼 이미지를 서버 DB 에서 가져와야 하는 경우는 별도의 구현 처리를 해줘야 함
                                // 로컬 저장소와 서버 DB 에서 가져온 이미지 Uri 를 멀티파트화하는 코드를 별도로 분리해서 구현할 수 있으나
                                // (개인의견) 갤러리에서 이미지를 가져올 때마다 멀티파트화시키는 것보다는
                                // 다음 버튼을 누르는 순간 이미지를 멀티파트화시켜준다면 불필요한 convert 과정을 줄일 수 있을 것이라고 생각
                                // (핑계) 사실 별도로 구현할 경우 예외처리가 복잡하기도 할 것 같다.
                                // 따라서 nextButtonToMap() 메서드에서 convertImgToMultiPart() 메서드를 호출해
                                // 버튼을 눌렀을 때 RecyclerView Adapter 의 itemList 를 멀티파트화 시키는 방향으로 코드를 수정함
                                //이미지 멀티파트로 저장
//                                convertImgToMultiPart(writeImgInfo, image)
                            }

                            val position = writeAdapter.itemCount
                            val newCount = position + imgMoreList.size
                            if (newCount > 6) {
                                Toast.makeText(
                                    context,
                                    "사진은 6개까지 선택 가능합니다. \n다시 선택해주세요.",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                writeAdapter.imgList.addAll(imgMoreList)
                                writeAdapter.notifyItemInserted(position)   //기존에 선택된 항목 뒤에서부터 set

                                // note(승현):
                                // 아래 코드는 convertImgToMultiPart() 메서드를 통해 얻은 멀티파트 리스트를 ViewModel 에 담는 과정
                                // convertImgToMultiPart() 메서드의 호출위치를 바꿨기 때문에 아래 코드 주석처리함
//                                if (sharedViewModel.imageMultiPart.value == null) {
//                                    sharedViewModel.imageMultiPart.value = image
//                                } else {
//                                    sharedViewModel.imageMultiPart.value!!.addAll(image)
//                                }
                            }
                        }
                    }

                    setPlusIconLoc()
                }
            }
    }

    private fun openGallery() {

        var writePermission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        var readPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        // 권한 없어서 요청
        if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                1000
            )

        } else { // 권한 있음
            if (writeAdapter.itemCount >= 6) {
                Toast.makeText(context, "사진은 6개까지 선택 가능합니다.", Toast.LENGTH_LONG).show()
                return
            }

            val intent = Intent(Intent.ACTION_PICK)  //ACTION_PICK   //ACTION_GET_CONTENT
            intent.type = "image/*"
            //다중 선택 가능
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)

            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI //데이터를 Uri로 받는다
            galleryLauncher.launch(Intent.createChooser(intent, "Get Album"))
        }
    }

    //이미지 보내기
    inner class BitmapRequestBody(private val bitmap: Bitmap) : RequestBody() {
        override fun contentType(): MediaType = "image/jpeg".toMediaType()

        override fun writeTo(sink: BufferedSink) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, sink.outputStream())
        }

    }

    private fun setPlusIconLoc() {
        var rowSpec: GridLayout.Spec = GridLayout.spec(0)
        var columnSpec: GridLayout.Spec = GridLayout.spec(0)

        binding.clWritePhoto.visibility = View.GONE
        binding.gridImgPlus.visibility = View.VISIBLE
        when (writeAdapter.imgList.size) {
            0 -> {
                binding.clWritePhoto.visibility = View.VISIBLE
                binding.gridImgPlus.visibility = View.GONE
            }
            1 -> {
                rowSpec = GridLayout.spec(0)
                columnSpec = GridLayout.spec(0, GridLayout.CENTER, 1f)
            }
            2 -> {
                rowSpec = GridLayout.spec(0)
                columnSpec = GridLayout.spec(2)
            }
            3 -> {
                rowSpec = GridLayout.spec(1)
                columnSpec = GridLayout.spec(0)
            }
            4 -> {
                rowSpec = GridLayout.spec(1)
                columnSpec = GridLayout.spec(0, GridLayout.CENTER, 1f)
            }
            5 -> {
                rowSpec = GridLayout.spec(1)
                columnSpec = GridLayout.spec(2)
            }
            6 -> {
                binding.gridImgPlus.visibility = View.GONE
            }
        }

        if (binding.imgWriteAddImg.parent != null)
            (binding.imgWriteAddImg.parent as ViewGroup).removeView(binding.imgWriteAddImg)

        binding.gridImgPlus.addView(
            binding.imgWriteAddImg,
            GridLayout.LayoutParams(rowSpec, columnSpec)
        )

        //add btn img 크기 해상도에 맞게 set
        val displayMetrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireContext().display!!.getRealMetrics(displayMetrics)
        } else {
            requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }

        val width = displayMetrics.widthPixels

        binding.imgWriteAddImg.layoutParams.width = (width/3.5).toInt()
        binding.imgWriteAddImg.requestLayout()
    }

    private fun nextButtonToMap() {
        //주의사항
        val warningList: ArrayList<MultipartBody.Part> = ArrayList()
        val warningUIList: ArrayList<String> = ArrayList()

        if (binding.btnWriteCautionHighway.isSelected) {
            warningList.add(MultipartBody.Part.createFormData("warning", Define().WARNING_HIGH_WAY))
            warningUIList.add(Define().WARNING_HIGH_WAY)
        }
        if (binding.btnWriteCautionMoun.isSelected) {
            warningList.add(
                MultipartBody.Part.createFormData(
                    "warning",
                    Define().WARNING_MOUNTAIN_ROAD
                )
            )
            warningUIList.add(Define().WARNING_MOUNTAIN_ROAD)
        }
        if (binding.btnWriteCautionDiffi.isSelected) {
            warningList.add(
                MultipartBody.Part.createFormData(
                    "warning",
                    Define().WARNING_DIFF_ROAD
                )
            )
            warningUIList.add(Define().WARNING_DIFF_ROAD)
        }
        if (binding.btnWriteCautionPeople.isSelected) {
            warningList.add(
                MultipartBody.Part.createFormData(
                    "warning",
                    Define().WARNING_HOT_PLACE
                )
            )
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

        //보여지는 이미지
        sharedViewModel.imageUriRecyclerView.value = writeAdapter.imgList

        // note(승현):
        // RecyclerView Adapter itemList 의 Uri 리스트를 멀티파트 폼으로 변환
        CoroutineScope(Dispatchers.Main).launch {
            val multiPartJob = async { convertImgToMultiPart(writeAdapter.imgList) }
            val multiPartImageList: ArrayList<MultipartBody.Part> = multiPartJob.await()
            sharedViewModel.imageMultiPart.value = multiPartImageList
            checkDataSetEmpty(warningList)
        }
    }

    // 빈값 확인
    private fun checkDataSetEmpty(warningList: ArrayList<MultipartBody.Part>) {
        if (TextUtils.isEmpty(sharedViewModel.title.value)
            || TextUtils.isEmpty(sharedViewModel.province.value)
            || (getString(R.string.no_select) != sharedViewModel.province.value && TextUtils.isEmpty(
                sharedViewModel.region.value
            ))
            || TextUtils.isEmpty(sharedViewModel.courseDesc.value)
            || (sharedViewModel.imageMultiPart.value == null || sharedViewModel.imageMultiPart.value?.size == 0)
            || (sharedViewModel.theme.value == null || sharedViewModel.theme.value?.size == 0)
        ) {
            // note: 있어야 할 값이 없으면 Toast 를 띄워주는 조건문이다.
                var noData : String = ""
            if (TextUtils.isEmpty(sharedViewModel.title.value)) {
                Timber.tag("빈 값 있는 상황").i("제목 없음")
                noData += "\n* 제목"
            }
            if (TextUtils.isEmpty(sharedViewModel.province.value)) {
                Timber.tag("빈 값 있는 상황").i("지역(도) 없음")
                noData += "\n* 지역(도)"
            }
            if (getString(R.string.no_select) != sharedViewModel.province.value
                && TextUtils.isEmpty(sharedViewModel.region.value)
            ) {
                Timber.tag("빈 값 있는 상황").i("지역(도)는 있는데 지역(시) 없음")
                noData += "\n* 지역(시)"
            }
            if (TextUtils.isEmpty(sharedViewModel.courseDesc.value)) {
                Timber.tag("빈 값 있는 상황").i("코스 설명이 없음")
                noData += "\n* 코스 설명"
            }
            if (warningList.size == 0) {
                Timber.tag("빈 값 있는 상황").i("주의사항 없음")
            }
            if (sharedViewModel.imageMultiPart.value == null) {
                Timber.tag("빈 값 있는 상황").i("이미지 멀티파트 null")
                noData += "\n* 이미지"
            }
            if (sharedViewModel.imageMultiPart.value?.size == 0) {
                Timber.tag("빈 값 있는 상황").i("이미지 멀티파트 size 0")
                if (sharedViewModel.imageMultiPart.value != null) {
                    noData += "\n* 이미지"
                }
            }
            if (sharedViewModel.theme.value == null || sharedViewModel.theme.value?.size == 0) {
                Timber.tag("빈 값 있는 상황").i("테마 없음")
                noData += "\n* 테마"
            }

            val coment = "필수값을 입력해주세요.$noData"

            Toast.makeText(requireContext(), coment, Toast.LENGTH_LONG).show()
        } else {
            writeShareActivity!!.replaceAddStackFragment(
                WriteMapFragment.newInstance(),
                "writeMap"
            )
        }
    }

    //돌아왔을 때 값 유지
    private fun initWriteData() {
        //사진
        if (sharedViewModel.imageMultiPart.value != null) {
            binding.clWritePhoto.visibility = View.GONE

            val imgMoreList =
                sharedViewModel.imageUriRecyclerView.value ?: mutableListOf<WriteImgInfo>()

            imgMoreList.forEach { imageUri ->
                writeAdapter.imgList = imgMoreList
            }

            writeAdapter.notifyDataSetChanged()

        }

        //지역 도 시
        if (sharedViewModel.province.value != "") {
            binding.btnWriteProvince.text = sharedViewModel.province.value
            binding.btnWriteProvince.isSelected = true
        }
        if (sharedViewModel.region.value != "") {
            binding.btnWriteRegion.text = sharedViewModel.region.value
            binding.btnWriteRegion.isSelected = true
        }

        //주차 있없
        if (sharedViewModel.isParking.value == true) {
            binding.btnWriteParkYes.isSelected = true
            binding.btnWriteParkNo.isSelected = false

        } else if (sharedViewModel.isParking.value == false) {
            binding.btnWriteParkYes.isSelected = false
            binding.btnWriteParkNo.isSelected = true
        }

        //주의사항
        if (sharedViewModel.warningUI.value != null) {
            for (warningData in sharedViewModel.warningUI.value!!) {
                when (warningData) {
                    Define().WARNING_HIGH_WAY -> binding.btnWriteCautionHighway.isSelected =
                        true
                    Define().WARNING_DIFF_ROAD -> binding.btnWriteCautionDiffi.isSelected = true
                    Define().WARNING_MOUNTAIN_ROAD -> binding.btnWriteCautionMoun.isSelected =
                        true
                    Define().WARNING_HOT_PLACE -> binding.btnWriteCautionPeople.isSelected =
                        true
                }
            }
        }

    }

    override fun onClick(v: View?) {
        when (v) {
            //갤러리 이미지
            binding.imgWriteAddImg, binding.clWritePhoto -> {
                openGallery()
            }

            //주의사항
            binding.btnWriteCautionHighway, binding.btnWriteCautionPeople, binding.btnWriteCautionDiffi, binding.btnWriteCautionMoun
            -> {
                v.isSelected = !v.isSelected
            }

            //테마
            binding.btnWriteTheme1, binding.btnWriteTheme2, binding.btnWriteTheme3 -> {
                openThemeDialog()
            }

            //주차 - 둘 중 하나만 선택 가능
            binding.btnWriteParkYes -> {
                binding.btnWriteParkYes.isSelected = true
                binding.btnWriteParkNo.isSelected = false

                sharedViewModel.isParking.value = true
            }
            binding.btnWriteParkNo -> {
                binding.btnWriteParkNo.isSelected = true
                binding.btnWriteParkYes.isSelected = false

                sharedViewModel.isParking.value = false
            }

            //다음 버튼
            binding.btnWriteBottomNext -> nextButtonToMap()

            //지역 선택
            binding.btnWriteProvince -> selectProvince(v)
            binding.btnWriteRegion -> selectRegion(v)

        }
    }
}
