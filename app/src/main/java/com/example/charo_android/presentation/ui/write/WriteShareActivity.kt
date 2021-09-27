package com.example.charo_android.presentation.ui.write

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.charo_android.R
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.charo_android.databinding.ActivityWriteShareBinding

class WriteShareActivity : AppCompatActivity() {
    private lateinit var writeFragment : WriteFragment

    private lateinit var userId: String
    private lateinit var nickName: String
    private lateinit var binding: ActivityWriteShareBinding

    private lateinit var sharedViewModel: WriteSharedViewModel
//    private val sharedViewModel: WriteSharedViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteShareBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedViewModel = ViewModelProvider(this@WriteShareActivity).get(WriteSharedViewModel::class.java)

        userId = intent.getStringExtra("userId").toString()
        nickName = intent.getStringExtra("nickName").toString()

//        sharedViewModel.userId.value = "진희"
//        sharedViewModel.nickName.value = "지니"

        Log.d("uuuuuu", userId)
        Log.d("uuuuuu", sharedViewModel.userId.value.toString())

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
        sharedViewModel.latitude.value = 0.0
        sharedViewModel.longitude.value = 0.0
        sharedViewModel.locationName.value = ""
        sharedViewModel.locationAddress.value = ""
    }

}