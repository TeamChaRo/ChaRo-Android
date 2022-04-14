package com.charo.android.presentation.ui.mypage.postlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.charo.android.R
import com.charo.android.data.model.mypage.Post
import com.charo.android.databinding.FragmentWrittenPostBinding
import com.charo.android.presentation.ui.detailpost.DetailPostActivity
import com.charo.android.presentation.ui.mypage.adapter.PostAdapter
import com.charo.android.presentation.ui.mypage.viewmodel.MyPageViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class WrittenPostFragment : Fragment() {
    private val TAG = "mlog: WrittenPostFragment::"
    private var _binding: FragmentWrittenPostBinding? = null
    val binding get() = _binding ?: error("binding not initialized")
    val viewModel: MyPageViewModel by sharedViewModel()
    private lateinit var writtenPostAdapter: PostAdapter
    private var sort = LIKE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_written_post,
                container,
                false
            )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPostListNotEmpty()
        endlessScroll()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun checkPostListNotEmpty() {
        viewModel.writtenLikePostList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                initSpinner()
                initRecyclerView(it)
            }
        }
    }

    private fun initSpinner() {
        // spinner 자체 set up
        val filter = resources.getStringArray(R.array.charo_filter)
        val adapter = ArrayAdapter(requireActivity(), R.layout.item_charo_spinner, filter)
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
                        Timber.d("mlog: WrittenPostFragment::spinner handler onItemSelected - 0")
                        sort = LIKE
                    } else {
                        Timber.d("mlog: WrittenPostFragment::spinner handler onItemSelected - 1")
                        sort = NEW
                    }
                    changeRecyclerViewItemList(sort)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Timber.d("mlog: WrittenPostFragment::spinner handler onNothingSelected")
                }
            }
    }

    private fun initRecyclerView(postList: MutableList<Post>) {
        writtenPostAdapter = PostAdapter {
            val intent = Intent(requireContext(), DetailPostActivity::class.java)
            intent.putExtra("postId", it.postId)
            startActivity(intent)
        }
        binding.rvPostList.adapter = writtenPostAdapter
        writtenPostAdapter.replaceItem(postList)
    }

    private fun changeRecyclerViewItemList(sort: Int) {
        when (sort) {
            LIKE -> {
                viewModel.writtenLikePostList.observe(viewLifecycleOwner) {
                    writtenPostAdapter.apply {
                        this.replaceItem(it)
                    }
                }
            }
            NEW -> {
                viewModel.writtenNewPostList.observe(viewLifecycleOwner) {
                    writtenPostAdapter.apply {
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
                Timber.d("mlog: WrittenPostFragment::endlessScroll NestedScrollView 최하단 도달")
                when (sort) {
                    LIKE -> {
                        // 서버에서 더 가져오는 로직
                        Timber.d("mlog: WrittenPostFragment::endlessScroll 인기순 작성한 게시글 더 불러오기")
                        viewModel.getMoreWrittenLikePost()
                    }
                    NEW -> {
                        // 서버에서 더 가져오는 로직
                        Timber.d("mlog: WrittenPostFragment::endlessScroll 최신순 작성한 게시글 더 불러오기")
                        viewModel.getMoreWrittenNewPost()
                    }
                }
            }
        })
    }

    companion object {
        const val LIKE = 0
        const val NEW = 1
    }
}