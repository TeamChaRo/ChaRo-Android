package com.example.charo_android.presentation.ui.charo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.charo_android.R
import com.example.charo_android.databinding.ActivityCharoListBinding
import com.example.charo_android.hidden.Hidden

class CharoListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCharoListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharoListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openFragment()
    }

    private fun openFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.charo_list_fragment, CharoListFragment(Hidden.userId, Hidden.nickName))
            .commit()
    }
}