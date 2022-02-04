package com.example.charo_android.presentation.ui.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.charo_android.databinding.DialogThemeBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogThemeFragment : BottomSheetDialogFragment() {
    private var _binding: DialogThemeBinding? = null
    private val binding get() = _binding!!

    private val selectedThemeList: ArrayList<String> = ArrayList()
    private val chipTextMap : HashMap<String, TextView> = HashMap()

    private val sharedViewModel: WriteSharedViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                WriteSharedViewModel() as T
        }
    }

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

        sharedViewModel.theme.value = selectedThemeList
    }

    //선택한 테마 표시
    private fun clickTheme(themeData : WriteThemeData){
        chipTextMap[themeData.textView.text as String] = themeData.seqView

        themeData.layout.setOnClickListener{
            it.isSelected = !it.isSelected

            if(it.isSelected){
                selectedThemeList.add(themeData.textView.text as String)
            }else{
                selectedThemeList.remove(themeData.textView.text as String)
                themeData.seqView.visibility = View.GONE
            }

            //테마 선택한 순서대로 숫자 설정
            setSelectSequence(chipTextMap)
        }
    }
    
    private fun setSelectSequence(chipTextMap : HashMap<String, TextView>){
        for(i in 0 until selectedThemeList.count()){
            val index = i + 1
            chipTextMap[selectedThemeList[i]]?.text = index.toString()
            chipTextMap[selectedThemeList[i]]?.visibility = View.VISIBLE
        }
    }

    private fun initDialog() {
        (dialog as BottomSheetDialog).behavior.apply {
            isFitToContents = false
            state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }
    }
}