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
import com.charo.android.databinding.FragmentWrittenPostBinding
import com.charo.android.presentation.ui.mypage.adapter.PostAdapter
import com.charo.android.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.charo.android.presentation.ui.write.WriteShareActivity
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class WrittenPostFragment : Fragment() {
    private val TAG = "mlog: WrittenPostFragment::"
    private var _binding: FragmentWrittenPostBinding? = null
    val binding get() = _binding ?: error("binding not initialized")
    val viewModel: MyPageViewModel by sharedViewModel()
    private val writtenPostAdapter: PostAdapter = PostAdapter {
        val intent = Intent(requireContext(), WriteShareActivity::class.java)
        intent.putExtra("postId", it.postId)
        intent.putExtra("destination", "detail")
        startActivity(intent)
    }
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
        Timber.i("$TAG: 작성한 게시물 리스트 프래그먼트 생성")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinner()
        initRecyclerView()
        endlessScroll()
        observeData()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun observeData() {
        viewModel.writtenLikePostList.observe(viewLifecycleOwner) {
            if(sort == LIKE) {
                writtenPostAdapter.replaceItem(it.map { Post -> Post.copy() })
            }
        }
        viewModel.writtenNewPostList.observe(viewLifecycleOwner) {
            if(sort == NEW) {
                writtenPostAdapter.replaceItem(it.map { Post -> Post.copy() })
            }
        }
    }

    private fun initSpinner() {
        val filter = resources.getStringArray(R.array.my_page_filter)
        val adapter = ArrayAdapter(requireActivity(), R.layout.item_charo_spinner, filter)
        adapter.setDropDownViewResource(R.layout.item_charo_spinner)
        binding.spinnerPostList.adapter = adapter

        binding.spinnerPostList.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    sort = if (position == 0) {
                        LIKE
                    } else {
                        NEW
                    }
                    changeRecyclerViewItemList()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    sort = LIKE
                }
            }
    }

    private fun initRecyclerView() {
        binding.rvPostList.adapter = writtenPostAdapter
    }

    private fun changeRecyclerViewItemList() {
        when (sort) {
            LIKE -> {
                viewModel.writtenLikePostList.value?.let {
                    writtenPostAdapter.replaceItem(it.map { Post -> Post.copy() })
                }
            }
            NEW -> {
                viewModel.writtenNewPostList.value?.let {
                    writtenPostAdapter.replaceItem(it.map { Post -> Post.copy() })
                }
            }
        }
    }

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

    companion object {
        const val LIKE = 0
        const val NEW = 1
    }
}