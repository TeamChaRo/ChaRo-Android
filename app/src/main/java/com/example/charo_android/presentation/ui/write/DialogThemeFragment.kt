package com.example.charo_android.presentation.ui.write

import android.app.ActionBar
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.charo_android.R
import com.example.charo_android.databinding.DialogThemeBinding
import com.example.charo_android.databinding.ItemChipThemeBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_chip_theme.view.*

class DialogThemeFragment : BottomSheetDialogFragment() {
    private var _binding: DialogThemeBinding? = null
    private val binding get() = _binding!!

    private val selectedThemeList: ArrayList<String> = ArrayList()
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

        clickTheme(binding.innerTheme.chipLayoutMountain, binding.innerTheme.chipTextMountain, binding.innerTheme.chipSelectMountain)
        clickTheme(binding.innerTheme.chipLayoutSea, binding.innerTheme.chipTextSea, binding.innerTheme.chipSelectSea)
        clickTheme(binding.innerTheme.chipLayoutLake, binding.innerTheme.chipTextLake, binding.innerTheme.chipSelectLake)
        clickTheme(binding.innerTheme.chipLayoutRiver, binding.innerTheme.chipTextRiver, binding.innerTheme.chipSelectRiver)
        clickTheme(binding.innerTheme.chipLayoutSpring, binding.innerTheme.chipTextSpring, binding.innerTheme.chipSelectSpring)
        clickTheme(binding.innerTheme.chipLayoutSummer, binding.innerTheme.chipTextSummer, binding.innerTheme.chipSelectSummer)
        clickTheme(binding.innerTheme.chipLayoutFall, binding.innerTheme.chipTextFall, binding.innerTheme.chipSelectFall)
        clickTheme(binding.innerTheme.chipLayoutWinter, binding.innerTheme.chipTextWinter, binding.innerTheme.chipSelectWinter)
        clickTheme(binding.innerTheme.chipLayoutSeaload, binding.innerTheme.chipTextSeaload, binding.innerTheme.chipSelectSeaload)
        clickTheme(binding.innerTheme.chipLayoutBlossom, binding.innerTheme.chipTextBlossom, binding.innerTheme.chipSelectBlossom)
        clickTheme(binding.innerTheme.chipLayoutLeaves, binding.innerTheme.chipTextLeaves, binding.innerTheme.chipSelectLeaves)
        clickTheme(binding.innerTheme.chipLayoutRelax, binding.innerTheme.chipTextRelax, binding.innerTheme.chipSelectRelax)
        clickTheme(binding.innerTheme.chipLayoutSpeed, binding.innerTheme.chipTextSpeed, binding.innerTheme.chipSelectSpeed)
        clickTheme(binding.innerTheme.chipLayoutNight, binding.innerTheme.chipTextNight, binding.innerTheme.chipSelectNight)
        clickTheme(binding.innerTheme.chipLayoutCity, binding.innerTheme.chipTextCity, binding.innerTheme.chipSelectCity)

    }

    private fun clickTheme(layout: ConstraintLayout, textView: TextView, seqView: TextView){
        layout.setOnClickListener{
            it.isSelected = !it.isSelected

            if(it.isSelected){
                selectedThemeList.add(textView.text as String)
            }else{
                selectedThemeList.remove(textView.text as String)
                seqView.visibility = View.GONE
            }
            for(i in 0 until selectedThemeList.count()){

                if(selectedThemeList[i] == textView.text.toString()){
                    val index = i + 1
                    seqView.text = index.toString()
                    seqView.visibility = View.VISIBLE
                }else{
                    seqView.visibility = View.GONE
                }
            }
        }
    }

    private fun initDialog() {
        (dialog as BottomSheetDialog).behavior.apply {
            isFitToContents = false
            state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }
    }


    val itemsTheme = arrayOf(
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


//    private var _chip: ItemChipThemeBinding? = null
//    private val chip get() = _chip!!

//    private fun setThemeData(){
//        _chip = ItemChipThemeBinding.inflate(layoutInflater, container, false)
//
//        var layoutList : ArrayList<LinearLayout> = ArrayList()
//        for(i in 0 until itemsTheme.count()){
//            if(i%3 == 0){
//                layoutList.add(LinearLayout(requireContext()))
//            }
//        }
//
//        var k = 0
//        for(i in 0 until layoutList.count()){
//            for(j in k until itemsTheme.count()-1){
//
//                if(chip.chipLayout.parent != null){
//                    (chip.chipLayout.parent as ViewGroup).removeView(chip.chipLayout)
//                }
//
//                layoutList[i].addView(new chip.chipLayout.apply {
//                    chip.chipText.text = itemsTheme[j]
//                })
//
//                if((j != 0) && (j % 3 == 2)){
//                    k=k+3
//                    break
//                }
//            }
//
//            //레이아웃에 리니어레이아웃 하나씩 add
//            layoutList[i].layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
//            binding.chipGroupTheme.addView(layoutList[i])
//        }
//    }
}