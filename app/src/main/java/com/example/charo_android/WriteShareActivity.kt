package com.example.charo_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.charo_android.R
import androidx.fragment.app.Fragment
import com.example.charo_android.databinding.ActivityWriteShareBinding
import com.example.charo_android.ui.home.replaceFragment

import com.example.charo_android.WriteFragment

class WriteShareActivity : AppCompatActivity() {
    private lateinit var writeFragment : WriteFragment

    private lateinit var userId: String
    private lateinit var nickName: String
    private lateinit var binding: ActivityWriteShareBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteShareBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = intent.getStringExtra("userId").toString()
        nickName = intent.getStringExtra("nickName").toString()

        writeFragment = WriteFragment()

        //writeFragment에서는 뒤로가기 안되게 하기 위해 .addToBackStack 제외
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.write_share_layout, writeFragment)
            .commit()

    }

    fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.write_share_layout, fragment, tag)
            .addToBackStack(tag)
            .commit()

    }

    override fun onBackPressed(){
        supportFragmentManager.popBackStack();
    }

}