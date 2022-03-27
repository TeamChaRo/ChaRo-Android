package com.example.charo_android.presentation.ui.mypage.other

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import com.example.charo_android.R
import com.example.charo_android.databinding.ActivityOtherMyPageBinding
import com.example.charo_android.presentation.ui.detailpost.DetailPostActivity
import com.example.charo_android.presentation.ui.follow.FollowActivity
import com.example.charo_android.presentation.ui.mypage.adapter.PostAdapter
import com.example.charo_android.presentation.ui.mypage.postlist.WrittenPostFragment
import com.example.charo_android.presentation.ui.mypage.viewmodel.OtherMyPageViewModel
import com.example.charo_android.presentation.util.SharedInformation
import org.koin.androidx.viewmodel.ext.android.viewModel

class OtherMyPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtherMyPageBinding
    private val adapter = PostAdapter {
        val intent = Intent(this, DetailPostActivity::class.java)
        intent.putExtra("postId", it.postId)
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

        // TODO: 추후 email 넣는 방식 수정
        viewModel.getLikePost()
        viewModel.getNewPost()
    }

    private fun initRecyclerView() {
        binding.rvPostList.adapter = adapter
        viewModel.writtenLikePostList.observe(this) {
            adapter.replaceItem(it)
        }
    }

    private fun initSpinner() {
        // spinner 자체 set up
        val filter = resources.getStringArray(R.array.charo_filter)
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
                        Log.d("mlog: OtherMyPageActivity::spinner handler", "onItemSelected - 0")
                        sort = WrittenPostFragment.LIKE
                    } else {
                        Log.d("mlog: OtherMyPageActivity::spinner handler", "onItemSelected - 1")
                        sort = WrittenPostFragment.NEW
                    }
                    changeRecyclerViewItemList(sort)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.d("mlog: OtherMyPageActivity::spinner handler", "onNothingSelected")
                }
            }
    }

    private fun changeRecyclerViewItemList(sort: Int) {
        when (sort) {
            LIKE -> {
                viewModel.writtenLikePostList.observe(this) {
                    adapter.apply {
                        this.replaceItem(it)
                    }
                }
            }
            NEW -> {
                viewModel.writtenNewPostList.observe(this) {
                    adapter.apply {
                        this.replaceItem(it)
                    }
                }
            }
        }
    }

    // TODO: 서버에서 가져오는 코드까지는 작성하였으나 실제로 데이터를 가져온 후에 뷰를 업데이트하는 코드 작성 필요함
    private fun endlessScroll() {
        binding.nsvPostList.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, oldScrollY ->
            if (scrollY >= v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight && scrollY > oldScrollY) {
                Log.d("mlog: OtherMyPageActivity::endlessScroll", "NestedScrollView 최하단 도달")
                when (sort) {
                    LIKE -> {
                        // 서버에서 더 가져오는 로직
                        Log.d("mlog: OtherMyPageActivity::endlessScroll", "인기순 작성한 게시글 더 불러오기")
                        viewModel.getMoreWrittenLikePost()
                    }
                    NEW -> {
                        // 서버에서 더 가져오는 로직
                        Log.d("mlog: OtherMyPageActivity::endlessScroll", "최신순 작성한 게시글 더 불러오기")
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
            viewModel.postFollow()
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

    companion object {
        const val LIKE = 0
        const val NEW = 1
    }
}