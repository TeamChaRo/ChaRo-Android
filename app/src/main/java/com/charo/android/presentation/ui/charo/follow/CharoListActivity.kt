package com.charo.android.presentation.ui.charo.follow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.charo.android.R
import com.charo.android.databinding.ActivityCharoListBinding
import com.charo.android.hidden.Hidden

class CharoListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCharoListBinding
    var myPageEmail: String? = null
    var nickname: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharoListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myPageEmail = intent.getStringExtra("myPageEmail")
        nickname = intent.getStringExtra("nickname")
        openFragment()
    }

    private fun openFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.charo_list_fragment, CharoListFragment(Hidden.userId, Hidden.nickName))
            .commit()
    }
}