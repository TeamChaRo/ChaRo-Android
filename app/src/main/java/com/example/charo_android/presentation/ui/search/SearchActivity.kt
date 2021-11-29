package com.example.charo_android.presentation.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import androidx.lifecycle.Observer
import com.example.charo_android.presentation.ui.main.MainActivity
import com.example.charo_android.R
import com.example.charo_android.data.api.ApiService
import com.example.charo_android.data.model.request.search.RequestSearchViewData
import com.example.charo_android.data.model.response.search.ResponseSearchViewData
import com.example.charo_android.databinding.ActivitySearchBinding
import com.example.charo_android.presentation.base.BaseActivity
import com.example.charo_android.presentation.ui.search.viewmodel.SearchViewModel
import com.example.charo_android.presentation.ui.setting.SettingProfileUpdateFragment
import com.example.charo_android.presentation.util.LocationUtil
import com.example.charo_android.presentation.util.ThemeUtil
import com.example.charo_android.presentation.util.changeFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {
    private lateinit var userId: String
    private lateinit var nickName: String
    private val themeUtil = ThemeUtil()
    private val locationUtil = LocationUtil()
    private val searchViewModel: SearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        nickName = intent.getStringExtra("nickName").toString()
        userId = intent.getStringExtra("userId").toString()
        Log.d("nice", userId)
        Log.d("nickName", nickName)

        changeFragment(R.id.fragment_container_search, SearchFragment())
    }


   /* fun initToolBar() {
        val toolbar = binding.toolbarSearch
        setSupportActionBar(toolbar)
    } */

    }
