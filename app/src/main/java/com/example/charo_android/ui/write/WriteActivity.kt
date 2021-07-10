package com.example.charo_android.ui.write

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.charo_android.R
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

        //지역
        val ItemsDo = arrayOf("")


        // 테마
        val ItemsTheme = arrayOf("산", "바다", "호수","강","봄","여름","가을","겨울","해안도로","벚꽃","단풍","여유","스피드","야경","도심")


        binding.btnWriteTheme1.setOnClickListener {
            val checkedItem = 1
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.theme1))
                .setNeutralButton("취소") { dialog, which ->
                    binding.btnWriteTheme1.text = resources.getString(R.string.theme1)
                    it.isSelected = false
                }
                .setPositiveButton("확인") { dialog, which ->
                    if(binding.btnWriteTheme1.text.toString() == resources.getString(R.string.theme1)) {
                        it.isSelected = false
                    }
                    it.isSelected = true
                }
                // Single-choice items (initialized with checked item)
                .setSingleChoiceItems(ItemsTheme, checkedItem) { dialog, which ->
                    //which : index
                    //테마 고르면 텍스트 변경
                    binding.btnWriteTheme1.text = ItemsTheme[which]
                }
                .show()
        }
        binding.btnWriteTheme2.setOnClickListener {
            val checkedItem = 1
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.theme2))
                .setNeutralButton("취소") { dialog, which ->
                    binding.btnWriteTheme2.text = resources.getString(R.string.theme2)
                    it.isSelected = false
                }
                .setPositiveButton("확인") { dialog, which ->
                    if(binding.btnWriteTheme1.text.toString() == resources.getString(R.string.theme2)) {
                        it.isSelected = false
                    }
                    it.isSelected = true
                }
                // Single-choice items (initialized with checked item)
                .setSingleChoiceItems(ItemsTheme, checkedItem) { dialog, which ->
                    //which : index
                    //테마 고르면 텍스트 변경
                    binding.btnWriteTheme2.text = ItemsTheme[which]
                }
                .show()
        }
        binding.btnWriteTheme3.setOnClickListener {
            val checkedItem = 1
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.theme3))
                .setNeutralButton("취소") { dialog, which ->
                    binding.btnWriteTheme3.text = resources.getString(R.string.theme3)
                    it.isSelected = false
                }
                .setPositiveButton("확인") { dialog, which ->
                    if(binding.btnWriteTheme3.text.toString() == resources.getString(R.string.theme3)) {
                        it.isSelected = false
                    }
                    it.isSelected = true
                }
                // Single-choice items (initialized with checked item)
                .setSingleChoiceItems(ItemsTheme, checkedItem) { dialog, which ->
                    //which : index
                    //테마 고르면 텍스트 변경
                    binding.btnWriteTheme3.text = ItemsTheme[which]
                }
                .show()
        }

        //주차 - 둘 중 하나만 선택 가능하도록 하기


        //startActivityForResult() - 화면전환 + 결과값 반환 로 변경 필요
        binding.btnWriteBottomNext.setOnClickListener {
            startActivityWriteMap()

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
        binding.btnWriteCity.setOnClickListener {
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
  //  binding.btnWriteTheme1.text.toString()
    fun startActivityWriteMap() {
        val intent = Intent(this@WriteActivity, WriteMapActivity::class.java)
        intent.putExtra("theme11", binding.btnWriteTheme1.text.toString())
            .putExtra("theme22", binding.btnWriteTheme2.text.toString())
       // startActivityForResult(intent, 100) //REQUEST_CODE
       startActivity(intent)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 100) { //REQUEST_CODE
//            if (resultCode == Activity.RESULT_OK) {
//                val theme1 = data!!.getStringExtra("theme1")
//                val theme2 = data.getStringExtra("theme2")
//                Toast.makeText(this, theme1+theme2 ,Toast.LENGTH_LONG).show()
//                binding.btnWriteTheme1.text = theme1
//                binding.btnWriteTheme2.text = theme2
//            }
//        }
//    }
}