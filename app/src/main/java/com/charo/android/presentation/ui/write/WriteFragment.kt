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
import android.os.SystemClock
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
import com.charo.android.presentation.util.SharedInformation
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
    private val TAG = "WriteFragment"

    companion object {
        fun newInstance() = WriteFragment()
    }

    private var _binding: FragmentWriteBinding? = null
    private val binding get() = _binding!!

    lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private var mLastClickTime = 0L

    private lateinit var writeAdapter: WriteAdapter

    //    ?????? ????????????
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
        initToolBar(getString(R.string.title_write_kor))

        writeAdapter = WriteAdapter() {
            //delete image
            writeAdapter.imgList.removeAt(it)
            writeAdapter.notifyDataSetChanged()

            // note(??????):
            // ViewModel ??? ????????? ???????????? ????????? ?????? ??????????????? ????????? ??????????????? ?????? ????????? ?????? ??? ????????????
            // ?????????, RecyclerView ?????? ???????????? ????????? ???????????? ??????????????? ???????????? '??????' ????????? ????????? ????????????
            // ????????? ???????????? ??????????????? ????????? ?????? ???????????? ??????
            // ?????????, ?????? ????????? ??????????????? ?????? ?????? ??????????????? ????????? ???????????????
//            sharedViewModel.imageMultiPart.value!!.removeAt(it)

            setPlusIconLoc()
        }
        binding.recyclerviewWriteImg.adapter = writeAdapter

        // ??????
        if (sharedViewModel.editFlag.value == true) {
            initEditData()
        }

        initListener()
        initWriteData()
        observeThemeData()

        galleryLauncher()

        //????????? ??????
        warningText(binding.etWriteTitle, binding.tvWarningTitle, 38)
        warningText(binding.etWriteParkReview, binding.tvWarningParkReview, 23)
        warningText(binding.etWriteMyDrive, binding.tvWarningMyDrive, 280)

        return root
    }

    @SuppressLint("SetTextI18n")
    private fun initEditData() {
        with(sharedViewModel) {
            // this = sharedViewModel
            // ?????? ????????? ????????? Data??? ?????? ViewModel??? ????????? ??? ????????? ViewModel??? ???????????? ???
            this.initEditData()

            // ?????? Fragment????????? ???????????? ?????? ??????(????????? DataBinding ?????????????????? ????????? ??? ??????????????? ???)
            // ?????? ?????????
            binding.toolbarTitle.text = "????????????"
            // ??? ??????
            binding.etWriteTitle.setText(this.title.value)
            // ???????????? ??????
            binding.etWriteParkReview.setText(this.parkingDesc.value)
            // ??????????????? ?????? isSelected??? ?????? ?????? ???????????? ????????? Fragment????????? ???????????? ???
            this.warnings.value?.let {
                binding.btnWriteCautionHighway.isSelected = it[0]
                binding.btnWriteCautionMoun.isSelected = it[1]
                binding.btnWriteCautionDiffi.isSelected = it[2]
                binding.btnWriteCautionPeople.isSelected = it[3]
            }
            // ?????? ??????
            binding.etWriteMyDrive.setText(this.courseDesc.value)
            // ?????? ?????? ?????????
            binding.tvLenMyDrive.text = "${this.courseDesc.value?.length}/280???"
        }
    }

    private fun initToolBar(title : String) {
        val toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_black)

        binding.toolbarTitle.text = title
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
                    binding.tvLenMyDrive.text = "${textLength}/${len}???"

                edt.isSelected = textLength > len
                warningTxt.isVisible = textLength > len

                if (textLength > len) {
                    edt.setText(p0.toString().substring(0, len - 1))
                    edt.setSelection(len - 1)
                }

                edt.addTextChangedListener(this)
            }
        })
    }

    //?????? (??? ??????)
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

                //?????? ???????????? ?????? ???
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

    //?????? (??? ??????)
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
                    ?: arrayOf("??? ????????? ??????????????????."), preCheckedRegion
            ) { dialog, which ->
                //which : index
                preCheckedRegion = which
            }
            .setCancelable(false)
            .setBackground(resources.getDrawable(R.drawable.background_radius_all_20))
            .show()
    }

    private fun observeThemeData() {
        //?????? ?????? ????????? ??????
        sharedViewModel.theme.observe(viewLifecycleOwner, Observer<ArrayList<String>> { newTheme ->
            val textView = arrayOfNulls<TextView>(3)
            var theme: Int

            for (i in 0 until 3) {
                theme =
                    resources.getIdentifier("btn_write_theme${i + 1}", "id", activity?.packageName)
                textView[i] = view?.findViewById(theme)

                //?????????   "??????${i+1}"
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
                //?????????
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

    // note(??????):
    // ????????? ?????????????????? ????????? '??????' ????????? ????????? ??? ??????????????? Uri ??? ??????????????? ????????????,
    // ?????? WriteImgInfo ?????? ???????????? ?????? ????????? ?????????? -> ??????
    // ???, ?????? DB??? ?????? ???????????? ?????? ?????? ??????????????? ?????? ?????? ????????? IO ???????????? ????????????, ???????????? ?????? ?????? ???
    // ????????? ?????? ?????? ??? ????????? ??????????????? ?????? ????????? ?????????????????? ???????????? ????????? ?????????
    // nextButtonToMap() ????????? ??? ??? ??? ???????????? ??? ??????
    // ????????? ?????? ????????? async ?????? ???????????? ?????? ?????????? ?????? ???????????? ???????????????
    // ???????????? WriteImgInfo ?????? -> WriteImgInfo ????????? ??? ??????
    private suspend fun convertImgToMultiPart(
        writeImgInfoList: MutableList<WriteImgInfo>,
    ): ArrayList<MultipartBody.Part> {
        val list = ArrayList<MultipartBody.Part>()
        writeImgInfoList.forEach { writeImgInfo ->
            when (writeImgInfo.isFromLocal) {
                // note(??????):
                // writeImgInfo ??? imgUri ??? ?????? ?????? ???????????? Uri ??? ??????
                true -> {
                    // note(??????):
                    // ?????? Log
                    Timber.tag("convertImgToMultiPart()").i("?????? ????????? convert ??????")
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
                    // note(??????):
                    // ?????? Log
                    Timber.tag("convertImgToMultiPart()::convert ??? List").i(list.toString())
                }
                // note(??????):
                // writeImgInfo ??? imgUri ??? ?????? DB??? Uri ??? ??????
                false -> {
                    // note(??????):
                    // ?????? Log
                    Timber.tag("convertImgToMultiPart()").i("????????? ????????? convert ??????")
                    // Uri -> String -> Bitmap -> MultiPartForm
                    // note(??????):
                    // ?????? ??????????????? ???????????? ???????????? ????????? ?????? ?????? ???????????? ?????????
                    // ???????????? ????????? ???????????? ????????? bitmap ?????? ???????????? ??? ????????? ?????? bitmap ??? ??????????????? ??????
                    // ????????? ?????? ????????? ????????? ????????? ????????? ?????? ?????????
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
                        // note(??????):
                        // ?????? Log
                        Timber.tag("convertImgToMultiPart()::convert ??? List").i(list.toString())
                    }.onFailure { error ->
                        Timber.tag("convertImgToMultiPart::?????? DB ????????? ????????? bitmap ????????? ??????")
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

                if (it.resultCode == Activity.RESULT_OK) {
                    if (it.data == null) {
                        Timber.d("error img data null")
                        Toast.makeText(context, "???????????? ?????? ???????????????.", Toast.LENGTH_LONG).show()
                        return@registerForActivityResult
                    }

                    val data = it.data!!.data
                    val clipData = it.data!!.clipData
                    Timber.d("WriteFragment img data : $data")
                    Timber.d("WriteFragment img clipData : $clipData")

                    if (data == null && clipData == null) {
                        Toast.makeText(context, "???????????? ?????? ???????????????.", Toast.LENGTH_LONG).show()
                        return@registerForActivityResult
                    }

                    val imgMoreList = mutableListOf<WriteImgInfo>()
                    if (clipData != null) {
                        if (clipData.itemCount > 6) {
                            Toast.makeText(context, "????????? 6????????? ?????? ???????????????.", Toast.LENGTH_LONG).show()
                            return@registerForActivityResult
                        } else {
                            if (clipData.itemCount in 1..6) {
                                for (i: Int in 0 until clipData.itemCount) {
                                    imgPath = clipData.getItemAt(i).uri

                                    val writeImgInfo =
                                        WriteImgInfo(imgUri = imgPath, isFromLocal = true)
                                    imgMoreList.add(writeImgInfo)
                                }
                            }
                        }
                    } else if (data != null){
                        imgMoreList.add(WriteImgInfo(imgUri = it.data!!.data!!, isFromLocal = true))
                    }

                    binding.clWritePhoto.visibility = View.GONE

                    val position = writeAdapter.itemCount
                    val newCount = position + imgMoreList.size
                    if (newCount > 6) {
                        Toast.makeText(
                            context,
                            "????????? 6????????? ?????? ???????????????. \n?????? ??????????????????.",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        writeAdapter.imgList.addAll(imgMoreList)
                        writeAdapter.notifyItemInserted(position)   //????????? ????????? ?????? ??????????????? set
                    }

                    setPlusIconLoc()
                }
            }
    }

    private fun openGallery() {
        if (writeAdapter.itemCount >= 6) {
            Toast.makeText(context, "????????? 6????????? ?????? ???????????????.", Toast.LENGTH_LONG).show()
            return
        }

        val intent = Intent(Intent.ACTION_GET_CONTENT) //ACTION_PICK   //ACTION_GET_CONTENT
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true) //?????? ?????? ??????
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        intent.setType("image/*")
        galleryLauncher.launch(intent)
    }

    private fun checkGalleryPermission() {
        var writePermission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        var readPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        // ?????? ????????? ??????
        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                && ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                    1)
            } else {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                    0)

                //SharedInformation ??????????????????
                SharedInformation.showForceRequestPermission(requireContext())
            }
        } else { // ?????? ??????
            openGallery()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            0 -> {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    SharedInformation.setPermissionNever(requireContext(), false)
                    Toast.makeText(requireActivity(), getString(R.string.txt_allow_permission), Toast.LENGTH_SHORT).show()
                    openGallery()
                } else {
                    //??????????????????
                    SharedInformation.setPermissionNever(requireContext(), true)
                }
            }
            1 -> {
                //??????
                SharedInformation.setPermissionNever(requireContext(), false)
                Toast.makeText(requireActivity(), getString(R.string.txt_need_permission), Toast.LENGTH_SHORT).show()
            }
        }
    }

    //????????? ?????????
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
        Timber.i("????????? ????????? ?????????: ${writeAdapter.imgList.size}")
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

        //add btn img ?????? ???????????? ?????? set
        val displayMetrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireContext().display!!.getRealMetrics(displayMetrics)
        } else {
            requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }

        val width = displayMetrics.widthPixels

        binding.imgWriteAddImg.layoutParams.width = (width/3.5).toInt()
        binding.imgWriteAddImg.layoutParams.height = (width/3.5).toInt()
        binding.imgWriteAddImg.requestLayout()
    }

    private fun nextButtonToMap() {
        //????????????
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

        //??????
        sharedViewModel.title.value = binding.etWriteTitle.text.toString()

        //?????? ??????
        sharedViewModel.parkingDesc.value = binding.etWriteParkReview.text.toString()

        //?????? ??????
        sharedViewModel.courseDesc.value = binding.etWriteMyDrive.text.toString()

        //???????????? ?????????
        sharedViewModel.imageUriRecyclerView.value = writeAdapter.imgList

        // note(??????):
        // RecyclerView Adapter itemList ??? Uri ???????????? ???????????? ????????? ??????
        CoroutineScope(Dispatchers.Main).launch {
            val multiPartJob = async { convertImgToMultiPart(writeAdapter.imgList) }
            val multiPartImageList: ArrayList<MultipartBody.Part> = multiPartJob.await()
            sharedViewModel.imageMultiPart.value = multiPartImageList
            checkDataSetEmpty(warningList)
        }
    }

    // ?????? ??????
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
            // note: ????????? ??? ?????? ????????? Toast ??? ???????????? ???????????????.
                var noData : String = ""
            if (TextUtils.isEmpty(sharedViewModel.title.value)) {
                Timber.tag("??? ??? ?????? ??????").i("?????? ??????")
                noData += "\n* ??????"
            }
            if (TextUtils.isEmpty(sharedViewModel.province.value)) {
                Timber.tag("??? ??? ?????? ??????").i("??????(???) ??????")
                noData += "\n* ??????(???)"
            }
            if (getString(R.string.no_select) != sharedViewModel.province.value
                && TextUtils.isEmpty(sharedViewModel.region.value)
            ) {
                Timber.tag("??? ??? ?????? ??????").i("??????(???)??? ????????? ??????(???) ??????")
                noData += "\n* ??????(???)"
            }
            if (TextUtils.isEmpty(sharedViewModel.courseDesc.value)) {
                Timber.tag("??? ??? ?????? ??????").i("?????? ????????? ??????")
                noData += "\n* ?????? ??????"
            }
            if (warningList.size == 0) {
                Timber.tag("??? ??? ?????? ??????").i("???????????? ??????")
            }
            if (sharedViewModel.imageMultiPart.value == null) {
                Timber.tag("??? ??? ?????? ??????").i("????????? ???????????? null")
                noData += "\n* ?????????"
            }
            if (sharedViewModel.imageMultiPart.value?.size == 0) {
                Timber.tag("??? ??? ?????? ??????").i("????????? ???????????? size 0")
                if (sharedViewModel.imageMultiPart.value != null) {
                    noData += "\n* ?????????"
                }
            }
            if (sharedViewModel.theme.value == null || sharedViewModel.theme.value?.size == 0) {
                Timber.tag("??? ??? ?????? ??????").i("?????? ??????")
                noData += "\n* ??????"
            }

            val coment = "???????????? ??????????????????.$noData"

            Toast.makeText(requireContext(), coment, Toast.LENGTH_LONG).show()
        } else {
            writeShareActivity!!.replaceAddStackFragment(
                WriteMapFragment.newInstance(),
                "writeMap"
            )
        }
    }

    //???????????? ??? ??? ??????
    private fun initWriteData() {
        //??????
        if (sharedViewModel.imageMultiPart.value != null) {
            binding.clWritePhoto.visibility = View.GONE

            val imgMoreList =
                sharedViewModel.imageUriRecyclerView.value ?: mutableListOf<WriteImgInfo>()

            imgMoreList.forEach { imageUri ->
                writeAdapter.imgList = imgMoreList
            }

            writeAdapter.notifyDataSetChanged()
            setPlusIconLoc()
        }

        //?????? ??? ???
        if (sharedViewModel.province.value != "") {
            binding.btnWriteProvince.text = sharedViewModel.province.value
            binding.btnWriteProvince.isSelected = true
        }
        if (sharedViewModel.region.value != "") {
            binding.btnWriteRegion.text = sharedViewModel.region.value
            binding.btnWriteRegion.isSelected = true
        }

        //?????? ??????
        if (sharedViewModel.isParking.value == true) {
            binding.btnWriteParkYes.isSelected = true
            binding.btnWriteParkNo.isSelected = false

        } else if (sharedViewModel.isParking.value == false) {
            binding.btnWriteParkYes.isSelected = false
            binding.btnWriteParkNo.isSelected = true
        }

        //????????????
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
            //????????? ?????????
            binding.imgWriteAddImg, binding.clWritePhoto -> {
                if(SystemClock.elapsedRealtime() - mLastClickTime > 800) {
                    checkGalleryPermission()
                }
                mLastClickTime = SystemClock.elapsedRealtime()
            }

            //????????????
            binding.btnWriteCautionHighway, binding.btnWriteCautionPeople, binding.btnWriteCautionDiffi, binding.btnWriteCautionMoun
            -> {
                v.isSelected = !v.isSelected
            }

            //??????
            binding.btnWriteTheme1, binding.btnWriteTheme2, binding.btnWriteTheme3 -> {
                openThemeDialog()
            }

            //?????? - ??? ??? ????????? ?????? ??????
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

            //?????? ??????
            binding.btnWriteBottomNext -> nextButtonToMap()

            //?????? ??????
            binding.btnWriteProvince -> selectProvince(v)
            binding.btnWriteRegion -> selectRegion(v)

        }
    }
}
