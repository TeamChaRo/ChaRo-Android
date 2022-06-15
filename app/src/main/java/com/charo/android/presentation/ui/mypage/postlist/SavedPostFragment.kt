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
import com.charo.android.databinding.FragmentSavedPostBinding
import com.charo.android.presentation.ui.main.MainActivity
import com.charo.android.presentation.ui.mypage.adapter.PostAdapter
import com.charo.android.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.charo.android.presentation.ui.write.WriteShareActivity
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class SavedPostFragment : Fragment() {
    private var _binding: FragmentSavedPostBinding? = null
    val binding get() = _binding ?: error("binding not initiated")
    val viewModel: MyPageViewModel by sharedViewModel()
    private val savedPostAdapter: PostAdapter = PostAdapter {
        val intent = Intent(requireContext(), WriteShareActivity::class.java)
        intent.putExtra("postId", it.postId)
        intent.putExtra("destination", "detail")
        intent.putExtra("from", "MainActivity")
        (requireActivity() as MainActivity).myPageResultLauncher.launch(intent)
    }
    private var sort = LIKE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_saved_post, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinner()
        initRecyclerView()
        endlessScroll()
        observeLiveData()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initSpinner() {
        // spinner 자체 set up
        val filter = resources.getStringArray(R.array.my_page_filter)
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
        binding.rvPostList.adapter = savedPostAdapter
    }

    private fun changeRecyclerViewItemList() {
        when (sort) {
            LIKE -> {
                viewModel.savedLikePostList.value?.let {
                    savedPostAdapter.replaceItem(it.map { Post -> Post.copy() })
                }
            }
            NEW -> {
                viewModel.savedNewPostList.value?.let {
                    savedPostAdapter.replaceItem(it.map { Post -> Post.copy() })
                }
            }
        }
    }

    private fun endlessScroll() {
        binding.nsvPostList.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, oldScrollY ->
            if (scrollY >= v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight && scrollY > oldScrollY) {
                when (sort) {
                    LIKE -> {
                        viewModel.getMoreSavedLikePost()
                    }
                    NEW -> {
                        viewModel.getMoreSavedNewPost()
                    }
                }
            }
        })
    }

    private fun observeLiveData() {
        viewModel.savedLikePostList.observe(viewLifecycleOwner) {
            if(sort == LIKE) {
                savedPostAdapter.replaceItem(it.map { Post -> Post.copy() })
            }
        }
        viewModel.savedNewPostList.observe(viewLifecycleOwner) {
            if(sort == NEW) {
                savedPostAdapter.replaceItem(it.map { Post -> Post.copy() })
            }
        }
    }

    companion object {
        const val LIKE = 0
        const val NEW = 1
    }
}