package com.example.charo_android.presentation.ui.mypage.postlist

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import com.example.charo_android.R
import com.example.charo_android.data.model.mypage.Post
import com.example.charo_android.databinding.FragmentWrittenPostBinding
import com.example.charo_android.presentation.ui.mypage.adapter.PostAdapter
import com.example.charo_android.presentation.ui.mypage.viewmodel.MyPageViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

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
//        initSpinner()
//        initRecyclerView()
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
                        Log.d("mlog: WrittenPostFragment::spinner handler", "onItemSelected - 0")
                        sort = LIKE
                    } else {
                        Log.d("mlog: WrittenPostFragment::spinner handler", "onItemSelected - 1")
                        sort = NEW
                    }
                    changeRecyclerViewItemList(sort)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.d("mlog: WrittenPostFragment::spinner handler", "onNothingSelected")
                }
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecyclerView(postList: MutableList<Post>) {
        writtenPostAdapter = PostAdapter()
        binding.rvPostList.adapter = writtenPostAdapter
        writtenPostAdapter.itemList.addAll(postList)
        writtenPostAdapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun changeRecyclerViewItemList(sort: Int) {
        when (sort) {
            LIKE -> {
                viewModel.writtenLikePostList.observe(viewLifecycleOwner) {
                    writtenPostAdapter.apply {
                        this.itemList.clear()
                        this.itemList.addAll(it)
                        this.notifyDataSetChanged()
                    }
                }
            }
            NEW -> {
                viewModel.writtenNewPostList.observe(viewLifecycleOwner) {
                    writtenPostAdapter.apply {
                        this.itemList.clear()
                        this.itemList.addAll(it)
                        this.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    // TODO: 서버에서 가져오는 코드까지는 작성하였으나 실제로 데이터를 가져온 후에 뷰를 업데이트하는 코드 작성 필요함
    private fun endlessScroll() {
        binding.nsvPostList.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, oldScrollY ->
            if (scrollY >= v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight && scrollY > oldScrollY) {
                Log.d("mlog: WrittenPostFragment::endlessScroll", "NestedScrollView 최하단 도달")
                when (sort) {
                    LIKE -> {
                        // 서버에서 더 가져오는 로직
                        Log.d("mlog: WrittenPostFragment::endlessScroll", "인기순 작성한 게시글 더 불러오기")
                        viewModel.getMoreWrittenLikePost()
                    }
                    NEW -> {
                        // 서버에서 더 가져오는 로직
                        Log.d("mlog: WrittenPostFragment::endlessScroll", "최신순 작성한 게시글 더 불러오기")
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