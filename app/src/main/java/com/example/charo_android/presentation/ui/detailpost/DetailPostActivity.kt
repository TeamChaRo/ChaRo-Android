package com.example.charo_android.presentation.ui.detailpost

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.charo_android.R
import com.example.charo_android.databinding.ActivityDetailPostBinding
import com.example.charo_android.presentation.ui.detailpost.viewmodel.DetailPostViewModel

class DetailPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPostBinding
    private val viewModel by viewModels<DetailPostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_post)

        initFragmentContainerView()
    }

    private fun initFragmentContainerView() {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.add(R.id.fcv_detail_post, DetailPostFragment())
            .addToBackStack(null)
            .commit()
    }
}