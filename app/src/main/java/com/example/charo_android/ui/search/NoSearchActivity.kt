package com.example.charo_android.ui.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.charo_android.databinding.ActivityNoSearchBinding
//import com.example.charo_android.ui.write.WriteActivity
import com.example.charo_android.ui.write.WriteShareActivity

class NoSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoSearchBinding
    private lateinit var userId : String
    private lateinit var nickName : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userId = intent.getStringExtra("userId").toString()
        nickName = intent.getStringExtra("nickName").toString()
        clickBackButton(userId)
        clickWriteButton()
    }


    private fun clickBackButton(userId: String) {
        binding.imgBackHome.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            intent.apply{
                putExtra("userId", userId)
                putExtra("nickName", nickName)
                startActivity(intent)
            }
        }

    }

    private fun clickWriteButton(){
        binding.imgNoSearchClick.setOnClickListener {
            val intent = Intent(this, WriteShareActivity::class.java)
            startActivity(intent)
        }

    }
}