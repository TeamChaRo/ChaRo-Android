package com.charo.android.presentation.ui.write

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.charo.android.R
import com.charo.android.databinding.DialogThemeBinding
import com.charo.android.presentation.util.ThemeUtil

import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DialogThemeFragment : BottomSheetDialogFragment() {
    private var _binding: DialogThemeBinding? = null
    private val binding get() = _binding!!

    private val selectedThemeList: ArrayList<String> = ArrayList()
    private val chipSeqMap : HashMap<String, TextView> = HashMap()  //"산", chipSelectMountain (selectSeqTextView)
    private val chipTextMap : HashMap<String, TextView> = HashMap()  //"산", chipTextMountain
    private val isSelectMap : HashMap<TextView, ConstraintLayout> = HashMap()

//    원래 진희코드
//    private val sharedViewModel: WriteSharedViewModel by activityViewModels {
//        object : ViewModelProvider.Factory {
//            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
//                WriteSharedViewModel() as T
//        }
//    }
    private val sharedViewModel by sharedViewModel<WriteSharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogThemeBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDialog()
        binding.ivCloseTheme.setOnClickListener {
            dismiss()
        }

        clickTheme(WriteThemeData(binding.innerTheme.chipLayoutMountain, binding.innerTheme.chipTextMountain, binding.innerTheme.chipSelectMountain))
        clickTheme(WriteThemeData(binding.innerTheme.chipLayoutSea, binding.innerTheme.chipTextSea, binding.innerTheme.chipSelectSea))
        clickTheme(WriteThemeData(binding.innerTheme.chipLayoutLake, binding.innerTheme.chipTextLake, binding.innerTheme.chipSelectLake))
        clickTheme(WriteThemeData(binding.innerTheme.chipLayoutRiver, binding.innerTheme.chipTextRiver, binding.innerTheme.chipSelectRiver))
        clickTheme(WriteThemeData(binding.innerTheme.chipLayoutSpring, binding.innerTheme.chipTextSpring, binding.innerTheme.chipSelectSpring))
        clickTheme(WriteThemeData(binding.innerTheme.chipLayoutSummer, binding.innerTheme.chipTextSummer, binding.innerTheme.chipSelectSummer))
        clickTheme(WriteThemeData(binding.innerTheme.chipLayoutFall, binding.innerTheme.chipTextFall, binding.innerTheme.chipSelectFall))
        clickTheme(WriteThemeData(binding.innerTheme.chipLayoutWinter, binding.innerTheme.chipTextWinter, binding.innerTheme.chipSelectWinter))
        clickTheme(WriteThemeData(binding.innerTheme.chipLayoutSeaload, binding.innerTheme.chipTextSeaload, binding.innerTheme.chipSelectSeaload))
        clickTheme(WriteThemeData(binding.innerTheme.chipLayoutBlossom, binding.innerTheme.chipTextBlossom, binding.innerTheme.chipSelectBlossom))
        clickTheme(WriteThemeData(binding.innerTheme.chipLayoutLeaves, binding.innerTheme.chipTextLeaves, binding.innerTheme.chipSelectLeaves))
        clickTheme(WriteThemeData(binding.innerTheme.chipLayoutRelax, binding.innerTheme.chipTextRelax, binding.innerTheme.chipSelectRelax))
        clickTheme(WriteThemeData(binding.innerTheme.chipLayoutSpeed, binding.innerTheme.chipTextSpeed, binding.innerTheme.chipSelectSpeed))
        clickTheme(WriteThemeData(binding.innerTheme.chipLayoutNight, binding.innerTheme.chipTextNight, binding.innerTheme.chipSelectNight))
        clickTheme(WriteThemeData(binding.innerTheme.chipLayoutCity, binding.innerTheme.chipTextCity, binding.innerTheme.chipSelectCity))
        clickTheme(WriteThemeData(binding.innerTheme.chipLayoutNoSelect, binding.innerTheme.chipTextNoSelect, binding.innerTheme.chipSelectNoSelect))

        setBeforeTheme(chipSeqMap)

    }

    private fun initDialog() {
        (dialog as BottomSheetDialog).behavior.apply {
            isFitToContents = false
        }
    }

    private fun btnComplete(){
        if(selectedThemeList.isNotEmpty()){
            binding.textDialogThemeComplete.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_main_0f6fff))

            binding.textDialogThemeComplete.setOnClickListener{
                sharedViewModel.theme.value = selectedThemeList
                dismiss()
            }
        }else{
            binding.textDialogThemeComplete.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray3_sub_acb5bd))
        }
    }

    //선택 예외처리
    private fun isPossibleSelect(themeData : WriteThemeData){
        //선택안함 선택 후 다른 테마 선택 불가
        val noSelect : String = getString(R.string.no_select)
        if(themeData.textView.text != noSelect && selectedThemeList.contains(ThemeUtil().themeMap[noSelect])){
            AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.txt_again_add_theme))
                .setPositiveButton(R.string.agreement) { dialog, which -> }
                .show()

            selectedThemeList.remove(ThemeUtil().themeMap[themeData.textView.text] as String)
            themeData.seqView.visibility = View.GONE
            themeData.layout.isSelected = false
            return
        }

        //최대 선택 가능 테마 3개
        if(selectedThemeList.size > 3){
            AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.txt_max_theme_cnt))
                .setPositiveButton(R.string.agreement) { dialog, which -> }
                .show()

            selectedThemeList.remove(ThemeUtil().themeMap[themeData.textView.text] as String)
            themeData.seqView.visibility = View.GONE
            themeData.layout.isSelected = false
        }
    }

    //선택한 테마 표시
    private fun clickTheme(themeData : WriteThemeData){
        chipTextMap[ThemeUtil().themeMap[themeData.textView.text] as String] = themeData.textView
        chipSeqMap[ThemeUtil().themeMap[themeData.textView.text] as String] = themeData.seqView
        isSelectMap[themeData.seqView] = themeData.layout

        themeData.layout.setOnClickListener{
            it.isSelected = !it.isSelected

            if(it.isSelected){
                selectedThemeList.add(ThemeUtil().themeMap[themeData.textView.text] as String)
                themeData.textView.isSelected = true
                isPossibleSelect(themeData)
            }else{
                selectedThemeList.remove(ThemeUtil().themeMap[themeData.textView.text] as String)
                themeData.seqView.visibility = View.GONE
                themeData.textView.isSelected = false
            }

            btnComplete()
            //테마 선택한 순서대로 숫자 설정
            setSelectSequence(chipSeqMap)
        }
    }

    private fun setSelectSequence(chipSeqMap : HashMap<String, TextView>){
        for(i in 0 until selectedThemeList.count()){
            val index = i + 1
            chipSeqMap[selectedThemeList[i]]?.text = index.toString()
            chipSeqMap[selectedThemeList[i]]?.visibility = View.VISIBLE
        }
    }

    private fun setBeforeTheme(chipSeqMap : HashMap<String, TextView>){
        //이전에 선택한 테마 유지
        for(i in 0 until (sharedViewModel.theme.value?.count() ?: 0)){
            val index = i + 1
            chipSeqMap[sharedViewModel.theme.value?.get(i)]?.text = index.toString()
            chipSeqMap[sharedViewModel.theme.value?.get(i)]?.visibility = View.VISIBLE

            selectedThemeList.add(sharedViewModel.theme.value?.get(i) as String)
            chipTextMap[sharedViewModel.theme.value?.get(i)]?.isSelected = true //TextView select
            isSelectMap[chipSeqMap[sharedViewModel.theme.value?.get(i)]]?.isSelected = true //layout select
        }
    }
}