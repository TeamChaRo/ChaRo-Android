package com.charo.android.presentation.ui.mypage.other

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import com.charo.android.R
import com.charo.android.databinding.ActivityOtherMyPageBinding
import com.charo.android.presentation.ui.follow.FollowActivity
import com.charo.android.presentation.ui.mypage.adapter.PostAdapter
import com.charo.android.presentation.ui.mypage.viewmodel.OtherMyPageViewModel
import com.charo.android.presentation.ui.write.WriteShareActivity
import com.charo.android.presentation.util.LoginUtil
import com.charo.android.presentation.util.SharedInformation
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class OtherMyPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtherMyPageBinding
    private val adapter = PostAdapter {
        val intent = Intent(this, WriteShareActivity::class.java)
        intent.putExtra("postId", it.postId)
        intent.putExtra("destination", "detail")
        startActivity(intent)
    }
    private val viewModel by viewModel<OtherMyPageViewModel>()
    private var sort = LIKE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_other_my_page)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.setUserEmail(SharedInformation.getEmail(this))
        viewModel.setOtherUserEmail(intent.getStringExtra("userEmail") ?: error(finish()))

        initRecyclerView()
        initSpinner()
        endlessScroll()
        clickBack()
        clickFollow()
        showFollowList()
        if (viewModel.userEmail != "@") {
            viewModel.getLikePost()
            viewModel.getNewPost()
            viewModel.getFollow()
        }
        observeLiveData()
    }

    override fun onRestart() {
        super.onRestart()
        if (viewModel.userEmail != "@") {
            viewModel.getFollow()
        }
    }

    private fun initRecyclerView() {
        binding.rvPostList.adapter = adapter
    }

    private fun initSpinner() {
        // spinner 자체 set up
        val filter = resources.getStringArray(R.array.my_page_filter)
        val adapter = ArrayAdapter(this, R.layout.item_charo_spinner, filter)
        adapter.setDropDownViewResource(R.layout.item_charo_spinner)
        binding.spinnerPostList.adapter = adapter

        // spinner handler
        binding.spinnerPostList.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position == 0) {
                        Timber.d("mlog: OtherMyPageActivity::spinner handler onItemSelected - 0")
                        sort = LIKE
                    } else {
                        Timber.d("mlog: OtherMyPageActivity::spinner handler onItemSelected - 1")
                        sort = NEW
                    }
                    changeRecyclerViewItemList(sort)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    sort = LIKE
                }
            }
    }

    private fun changeRecyclerViewItemList(sort: Int) {
        when (sort) {
            LIKE -> {
                viewModel.writtenLikePostList.value?.let {
                    adapter.replaceItem(it.map { Post -> Post.copy() })
                }
            }
            NEW -> {
                viewModel.writtenNewPostList.value?.let {
                    adapter.replaceItem(it.map { Post -> Post.copy() })
                }
            }
        }
    }

    // TODO: 서버에서 가져오는 코드까지는 작성하였으나 실제로 데이터를 가져온 후에 뷰를 업데이트하는 코드 작성 필요함
    private fun endlessScroll() {
        binding.nsvPostList.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, oldScrollY ->
            if (scrollY >= v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight && scrollY > oldScrollY) {
                when (sort) {
                    LIKE -> {
                        viewModel.getMoreWrittenLikePost()
                    }
                    NEW -> {
                        viewModel.getMoreWrittenNewPost()
                    }
                }
            }
        })
    }

    private fun clickBack() {
        binding.clBack.setOnClickListener {
            finish()
        }
    }

    private fun clickFollow() {
        binding.tvFollow.setOnClickListener {
            if (viewModel.userEmail != "@") {
                viewModel.postFollow()
            } else {
                LoginUtil.loginPrompt(this)
            }
        }
    }

    private fun showFollowList() {
        binding.clProfileFollow.setOnClickListener {
            val intent = Intent(this, FollowActivity::class.java)
            intent.putExtra("userEmail", viewModel.otherUserEmail)
            intent.putExtra("nickname", viewModel.userInfo.value?.nickname)
            startActivity(intent)
        }
    }

    private fun observeLiveData() {
        viewModel.writtenLikePostList.observe(this) {
            if(sort == LIKE) {
                adapter.replaceItem(it.map { Post -> Post.copy() })
            }
        }
        viewModel.writtenNewPostList.observe(this) {
            if(sort == NEW) {
                adapter.replaceItem(it.map { Post -> Post.copy() })
            }
        }
    }

    companion object {
        const val LIKE = 0
        const val NEW = 1
    }
}