package com.example.charo_android.ui.write

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.viewpager2.widget.ViewPager2
import com.example.charo_android.*
import com.example.charo_android.data.WriteImgInfo
import com.example.charo_android.databinding.FragmentWriteBinding
import com.example.charo_android.hidden.Hidden
import com.example.charo_android.ui.DialogThemeViewPagerAdapter
import com.example.charo_android.ui.home.HomeFragment

import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class WriteFragment : Fragment() {

    companion object {
        fun newInstance() = WriteFragment()
    }
    // viewPager2 뷰 객체 초기화를 액티비티 lifecycle에 맞게 지연시킴
    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout

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

//            showDialogFragment()

            MaterialAlertDialogBuilder(requireContext(),R.style.Dialog)
                .setTitle("지역")
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
                .setBackground(resources.getDrawable(R.drawable.background_radius_all_20))
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


//
//        // 2. 초기화 지연시킨 viewPager2 객체를 여기서 초기화함
//        viewPager2 = bottomSheetView.findViewById(R.id.viewPager2)

        // 3. viewPager2 뷰 객체에 어댑터 적용하기
//        viewPager2.adapter = DialogThemeViewPagerAdapter(this.fragmentActivity)

//        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
//        STATE_COLLAPSED : height 만큼 보이게
//        STATE_EXPANDED : 가득 차게 처리
//        STATE_HIDDEN : 숨김 처리

//        val bottomSheetFragment = DialogThemeFragment()

//        dialogThemeFragment = DialogThemeFragment()
//        val bottomSheetDialog = BottomSheetDialog(requireContext())
//        bottomSheetDialog.setContentView(dialogThemeFragment!!.requireView())


        val bottomSheetView = layoutInflater.inflate(R.layout.dialog_theme, container, false)


        // 2. 초기화 지연시킨 viewPager2 객체를 여기서 초기화함
        viewPager2 = bottomSheetView.findViewById(R.id.viewPager2)
        tabLayout = bottomSheetView.findViewById(R.id.tabLayout)

        // 3. viewPager2 뷰 객체에 어댑터 적용하기
        val adapter = DialogThemeViewPagerAdapter(requireActivity())
        adapter.addFragment(DialogThemeOneFragment())
        adapter.addFragment(DialogThemeTwoFragment())
        adapter.addFragment(DialogThemeThreeFragment())

        viewPager2.adapter = adapter
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int){
                super.onPageSelected(position)
                Log.e("ViewPagerFragment", "Page ${position+1}")

                adapter.showFragment(position)
            }
        })

        TabLayoutMediator(tabLayout, viewPager2){tab, position ->
            tab.text = "테마 ${position+1}"
        }.attach()


        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(bottomSheetView)

        binding.btnWriteTheme1.setOnClickListener {
            val checkedItem = 0
//            val bottomSheetDialog = BottomSheetDialog(dialogThemeFragment!!.requireContext())
//            dialogThemeFragment.view?.let { it -> dialogThemeFragment.setContentView(it) }
            bottomSheetDialog.show()


//            bottomSheetFragment.show(supportFragmentManager,"BottomSheetDialog")

//            MaterialAlertDialogBuilder(this)
//                .setTitle(resources.getString(R.string.theme1))
//                .setNeutralButton("취소") { dialog, which ->
//                    binding.btnWriteTheme1.text = resources.getString(R.string.theme1)
//                    it.isSelected = false
//                }
//                .setPositiveButton("확인") { dialog, which ->
//                    if (binding.btnWriteTheme1.text.toString() == resources.getString(R.string.theme1)) {
//                        it.isSelected = false
//                    }
//                    it.isSelected = true
//                }
//                // Single-choice items (initialized with checked item)
//                .setSingleChoiceItems(ItemsTheme, checkedItem) { dialog, which ->
//                    //which : index
//                    //테마 고르면 텍스트 변경
//                    binding.btnWriteTheme1.text = ItemsTheme[which]
//                }
//                .show()
        }
        binding.btnWriteTheme2.setOnClickListener {
            val checkedItem = 0
//            MaterialAlertDialogBuilder(this)
//                .setTitle(resources.getString(R.string.theme2))
//                .setNeutralButton("취소") { dialog, which ->
//                    binding.btnWriteTheme2.text = resources.getString(R.string.theme2)
//                    it.isSelected = false
//                }
//                .setPositiveButton("확인") { dialog, which ->
//                    if (binding.btnWriteTheme1.text.toString() == resources.getString(R.string.theme2)) {
//                        it.isSelected = false
//                    }
//                    it.isSelected = true
//                }
//                // Single-choice items (initialized with checked item)
//                .setSingleChoiceItems(ItemsTheme, checkedItem) { dialog, which ->
//                    //which : index
//                    //테마 고르면 텍스트 변경
//                    binding.btnWriteTheme2.text = ItemsTheme[which]
//                }
//                .show()
        }
        binding.btnWriteTheme3.setOnClickListener {
            val checkedItem = 0
//            MaterialAlertDialogBuilder(this)
//                .setTitle(resources.getString(R.string.theme3))
//                .setNeutralButton("취소") { dialog, which ->
//                    binding.btnWriteTheme3.text = resources.getString(R.string.theme3)
//                    it.isSelected = false
//                }
//                .setPositiveButton("확인") { dialog, which ->
//                    if (binding.btnWriteTheme3.text.toString() == resources.getString(R.string.theme3)) {
//                        it.isSelected = false
//                    }
//                    it.isSelected = true
//                }
//                // Single-choice items (initialized with checked item)
//                .setSingleChoiceItems(ItemsTheme, checkedItem) { dialog, which ->
//                    //which : index
//                    //테마 고르면 텍스트 변경
//                    binding.btnWriteTheme3.text = ItemsTheme[which]
//                }
//                .show()
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
//            replaceFragment(homeFragment, userId, nickName)
            writeShareActivity?.finish()
        }

        //서버 테스트
        binding.btnWriteBottomNext.setOnClickListener {

//            mapBinding = ActivityWriteMapBinding.inflate(layoutInflater)
//            AlertDialog.Builder(this)
//                .setMessage("작성을 완료하고, 드라이브 경로를 이어 작성하시겠습니까? 이후 글 수정은 어렵습니다.")
//                .setNeutralButton("아니오") { dialog, which ->
//                }
//                .setPositiveButton("예") { dialog, which ->
//                    insertDataToCompanionObject()
//                }
//                .show()

            writeShareActivity!!.replaceFragment(WriteMapFragment.newInstance(), "writeMap");

        }


        return root
    }

    private fun showDialogFragment() {
//        ExampleDialogFragment().show(supportFragmentManager, "exampleDialog")
        ExampleDialogFragment().show(activity?.supportFragmentManager!!, "exampleDialog")
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
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                if (data?.clipData == null) {
                    Log.d("errorimg", "null")

                } else {
                    var clipData = data.clipData
                    when {
                        clipData?.itemCount!! > 6 -> {
//                            Toast.makeText(this, "사진은 6개까지 선택 가능", Toast.LENGTH_LONG).show()
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

//    fun startActivityWriteMap() {
//        val intent = Intent(this@WriteFragment, WriteMapActivity::class.java)
//        intent.putExtra("theme11", binding.btnWriteTheme1.text.toString())
//        intent.putExtra("theme22", binding.btnWriteTheme2.text.toString())
//        intent.putExtra("locationFlag", "0")
//        intent.putExtra("textview", "0")
//        intent.putExtra("userId", userId)
//        intent.putExtra("nickName", nickName)
//        startActivity(intent)
//    }

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

}

//            var file: File
//            var fileList = listOf<Any>()
//            var requestFile: RequestBody
//            file =
//                File(writeAdapter.imgList[0].imgUri.toString())
