package com.charo.android.presentation.ui.detailpost

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.charo_android.R
import com.example.charo_android.databinding.ActivityDetailPostBinding
import com.example.charo_android.presentation.ui.detailpost.viewmodel.DetailPostViewModel
import com.example.charo_android.presentation.util.SharedInformation
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPostBinding
    private val viewModel by viewModel<DetailPostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_post)
        viewModel.postId = intent.getIntExtra("postId", -1)
        viewModel.setUserEmail(SharedInformation.getEmail(this))
        initFragmentContainerView()
    }

    private fun initFragmentContainerView() {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.add(R.id.fcv_detail_post, DetailPostFragment())
            .commit()
    }
}