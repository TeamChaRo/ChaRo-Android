package com.charo.android.presentation.ui.search

import android.os.Bundle
import android.view.MenuItem
import com.charo.android.R
import com.charo.android.databinding.ActivitySearchBinding
import com.charo.android.presentation.base.BaseActivity
import com.charo.android.presentation.util.changeFragment

class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        changeFragment(R.id.fragment_container_search, SearchFragment())

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
