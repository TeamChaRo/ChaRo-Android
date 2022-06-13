package com.charo.android.presentation.ui.mypage.other

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import com.charo.android.R
import com.charo.android.databinding.ActivityOtherMyPageBinding
import com.charo.android.presentation.ui.follow.FollowActivity
import com.charo.android.presentation.ui.main.MainActivity
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
        intent.putExtra("from", "OtherMyPageActivity")
        myPageResultLauncher.launch(intent)
    }
    private val viewModel by viewModel<OtherMyPageViewModel>()
    private var sort = LIKE

    private val followResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let {
                    viewModel.follower = it.getIntExtra("follower", -1)
                    viewModel.following = it.getIntExtra("following", -1)

                    if(viewModel.follower != -1 && viewModel.following != -1) {
                        viewModel.updateFollow()
                    }
                }
            }
        }
    private val myPageResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                RESULT_OK -> {
                    result.data?.let {
                        viewModel.postId = it.getIntExtra("postId", 0)
                        viewModel.likesCount = it.getIntExtra("likesCount", 0)
                        viewModel.saveCountDiff = it.getIntExtra("saveCountDiff", 0)

                        if (viewModel.postId > 0) {
                            viewModel.updatePost()
                        }
                    }
                }
            }
        }

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
        fetchData()
        setOnSwipeRefreshLayoutListener()
        observeLiveData()
    }

    override fun onBackPressed() {
        if(intent.getStringExtra("from") == "FollowActivity") {
            val intent = Intent(this, FollowActivity::class.java).apply {
                putExtra("nickname", requireNotNull(viewModel.userInfo.value).nickname)
                putExtra("userEmail", viewModel.otherUserEmail)
                putExtra("image", requireNotNull(viewModel.userInfo.value).profileImage)
                putExtra("isFollow", requireNotNull(viewModel.isFollow).value)
            }
            setResult(RESULT_OK, intent)
        }
        super.onBackPressed()
    }

    private fun setOnSwipeRefreshLayoutListener() {
        binding.layoutSwipe.setColorSchemeResources(R.color.blue_main_0f6fff)
        binding.layoutSwipe.setOnRefreshListener {
            fetchData()
            binding.layoutSwipe.isRefreshing = false
        }
    }

    private fun fetchData() {
        if (viewModel.userEmail != "@") {
            viewModel.getLikePost()
            viewModel.getNewPost()
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
            onBackPressed()
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
            if(viewModel.userEmail == SharedInformation.getEmail(this)) {
                val intent = Intent(this, FollowActivity::class.java).apply {
                    putExtra("userEmail", viewModel.otherUserEmail)
                    putExtra("nickname", viewModel.userInfo.value?.nickname)
                    putExtra("from", "OtherMyPageActivity")
                }
                followResultLauncher.launch(intent)
            } else {
                val intent = Intent(this, FollowActivity::class.java)
                intent.putExtra("userEmail", viewModel.otherUserEmail)
                intent.putExtra("nickname", viewModel.userInfo.value?.nickname)
                startActivity(intent)
            }
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