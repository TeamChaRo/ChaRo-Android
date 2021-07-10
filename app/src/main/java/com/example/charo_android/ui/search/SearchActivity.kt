package com.example.charo_android.ui.search

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.charo_android.MainActivity
import com.example.charo_android.R
import com.example.charo_android.databinding.ActivityMainBinding
import com.example.charo_android.databinding.ActivityMainBinding.inflate
import com.example.charo_android.databinding.ActivitySearchBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.time.chrono.JapaneseEra.values

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    val itemTheme = arrayOf(
        "산", "바다", "호수", "강", "봄", "여름", "가을", "겨울", "해안도로", "벚꽃", "단풍", "여유", "스피드", "야경", "도심"
    )

    val itemCaution = arrayOf("고속도로", "산길포함", "초보힘듦", "사람많음")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolBar()
        goHomeView()
        selectTheme()
        selectCatution()
        changeSearch()
    }


    fun selectTheme() {
        binding.btnSearchTheme.setOnClickListener {
            val searchItem = 1
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.main_charo_theme)
                .setNeutralButton("취소") { dialog, which ->
                    binding.btnSearchCaution.setText(resources.getString(R.string.main_charo_theme))
                    it.isSelected = false
                }
                .setPositiveButton("확인") { dialog, which ->
                    if (binding.btnSearchTheme.text.toString() == resources.getString(R.string.main_charo_theme)) {
                        it.isSelected = false
                    }
                    it.isSelected = true
                }
                .setSingleChoiceItems(itemTheme, searchItem) { dialog, which ->
                    binding.apply {
                        btnSearchTheme.setText(itemTheme[which])
                        btnSearchTheme.setTextColor(getColor(R.color.blue_main))
                        imgSearchStart.setBackgroundResource(R.drawable.ic_search_start_blue)
                        textSearchStart.setTextColor(getColor(R.color.white))
                    }
                }.show()
        }


    }

    fun selectCatution() {
        binding.btnSearchCaution.setOnClickListener {
            val searchCautionItem = 1
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.caution)
                .setNeutralButton("취소") { dialog, which ->
                    binding.btnSearchTheme.setText(resources.getString(R.string.caution))
                    it.isSelected = false
                }
                .setPositiveButton("확인") { dialog, which ->
                    if (binding.btnSearchCaution.text.toString() == resources.getString(R.string.caution)) {
                        it.isSelected = false
                    }
                    it.isSelected = true
                }
                .setSingleChoiceItems(itemCaution, searchCautionItem) { dialog, which ->
                    binding.apply {
                        btnSearchCaution.setText(itemCaution[which])
                        btnSearchCaution.setTextColor(getColor(R.color.blue_main))
                        imgSearchStart.setBackgroundResource(R.drawable.ic_search_start_blue)
                        textSearchStart.setTextColor(getColor(R.color.white))
                    }
                }.show()
        }


    }

    private fun changeSearch() {
        if (binding.btnSearchCaution.isSelected == true or binding.btnSearchTheme.isSelected == true){
            binding.apply{

            }
        }
    }


    private fun initToolBar() {
        val toolbar = binding.toolbarSearch
        setSupportActionBar(toolbar)


    }

    private fun goHomeView() {
        binding.imgSearchBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

}