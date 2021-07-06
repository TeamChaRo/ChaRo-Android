package com.example.charo_android.ui.write

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.charo_android.R
import com.example.charo_android.databinding.ActivityMainBinding
import com.example.charo_android.databinding.ActivityWriteBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class WriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //버튼 selected 상태 변화 함수
        setButtonClickEvent()

        binding.btnWriteTheme1.setOnClickListener {
            val ItemsTheme1 = arrayOf("산", "바다", "호수","강","봄")
            val checkedItem = 1
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.theme1))
                .setNeutralButton("취소") { dialog, which ->
                    binding.btnWriteTheme1.setText(resources.getString(R.string.theme1))
                    it.isSelected = false
                }
                .setPositiveButton("확인") { dialog, which ->
                    if(binding.btnWriteTheme1.text.toString() == resources.getString(R.string.theme1)) {
                        it.isSelected = false
                    }
                    it.isSelected = true
                }
                // Single-choice items (initialized with checked item)
                .setSingleChoiceItems(ItemsTheme1, checkedItem) { dialog, which ->
                    //which : index
                    //테마 고르면 텍스트 변경
                    binding.btnWriteTheme1.setText(ItemsTheme1[which])
                }
                .show()
        }





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
        binding.btnWriteDo.setOnClickListener {
            it.isSelected = !it.isSelected
        }
        binding.btnWriteSi.setOnClickListener {
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




}