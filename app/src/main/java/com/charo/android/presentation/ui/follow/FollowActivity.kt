package com.charo.android.presentation.ui.follow

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.charo.android.R
import com.charo.android.data.model.mypage.User
import com.charo.android.databinding.ActivityFollowBinding
import com.charo.android.presentation.ui.follow.adapter.FollowViewPagerAdapter
import com.charo.android.presentation.ui.follow.viewmodel.FollowViewModel
import com.charo.android.presentation.ui.main.MainActivity
import com.charo.android.presentation.ui.mypage.other.OtherMyPageActivity
import com.charo.android.presentation.util.SharedInformation
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class FollowActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFollowBinding
    private val viewModel: FollowViewModel by viewModel()

    val followResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let {
                    viewModel.newUser = User(
                        it.getStringExtra("nickname") ?: "",
                        it.getStringExtra("userEmail") ?: "",
                        it.getStringExtra("image") ?: "",
                        it.getBooleanExtra("isFollow", false)
                    )
                    Timber.i(viewModel.newUser.toString())
                    viewModel.updateNewUser()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_follow)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.setUserEmail(SharedInformation.getEmail(this))
        viewModel.setMyPageEmail(intent.getStringExtra("userEmail") ?: error(finish()))
        viewModel.setNickname(intent.getStringExtra("nickname") ?: error(finish()))

        initViewPager()
        getFollowList()
        setOnSwipeRefreshLayoutListener()
        clickBack()
    }

    override fun onBackPressed() {
        when (intent.getStringExtra("from")) {
            "MainActivity" -> {
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("follower", viewModel.follower.value?.size ?: -1)
                    putExtra(
                        "following",
                        viewModel.following.value?.filter { it.isFollow }?.size ?: -1
                    )
                }
                setResult(RESULT_OK, intent)
            }
            "OtherMyPageActivity" -> {
                val intent = Intent(this, OtherMyPageActivity::class.java).apply {
                    putExtra("follower", viewModel.follower.value?.size ?: -1)
                    putExtra(
                        "following",
                        viewModel.following.value?.filter { it.isFollow }?.size ?: -1
                    )
                }
                setResult(RESULT_OK, intent)
            }
            else -> throw IllegalArgumentException()
        }
        super.onBackPressed()
    }

    private fun setOnSwipeRefreshLayoutListener() {
        binding.layoutSwipe.setColorSchemeResources(R.color.blue_main_0f6fff)
        binding.layoutSwipe.setOnRefreshListener {
            getFollowList()
            binding.layoutSwipe.isRefreshing = false
        }
    }

    private fun initViewPager() {
        // ViewPager 초기화
        val followViewPagerAdapter = FollowViewPagerAdapter(this)
        followViewPagerAdapter.fragmentList = listOf(FollowerFragment(), FollowingFragment())
        binding.vp.adapter = followViewPagerAdapter

        // TabLayout 초기화 및 ViewPager와 연결
        TabLayoutMediator(binding.tab, binding.vp) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()
    }

    private fun getFollowList() {
        viewModel.getFollowList()
    }

    private fun clickBack() {
        binding.imgBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        val tabTextList = listOf("팔로워", "팔로잉")
    }
}