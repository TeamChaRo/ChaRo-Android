package com.charo.android.presentation.ui.detailpost

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.charo.android.R
import com.charo.android.databinding.ActivityDetailPostBinding
import com.charo.android.presentation.ui.detailpost.viewmodel.DetailPostViewModel
import com.charo.android.presentation.util.SharedInformation
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = MenuInflater(this)
        menuInflater.inflate(R.menu.detail_menu, menu)

//        return super.onCreateOptionsMenu(menu);
        return true
    }

    private fun initFragmentContainerView() {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.add(R.id.fcv_detail_post, DetailPostFragment())
            .commit()
    }
}