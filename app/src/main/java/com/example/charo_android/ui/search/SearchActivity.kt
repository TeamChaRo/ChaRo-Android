package com.example.charo_android.ui.search

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.charo_android.R
import com.example.charo_android.databinding.ActivityMainBinding
import com.example.charo_android.databinding.ActivityMainBinding.inflate
import com.example.charo_android.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    val itemTheme = arrayOf("산", "바다", "호수","강","봄","여름","가을","겨울","해안도로","벚꽃","단풍","여유","스피드","야경","도심")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }


    private fun selectTheme(){
        binding.btnSearchTheme.setOnClickListener {

        }


    }

}