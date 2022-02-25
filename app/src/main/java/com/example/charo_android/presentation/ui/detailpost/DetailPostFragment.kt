package com.example.charo_android.presentation.ui.detailpost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentDetailPostBinding
import com.example.charo_android.presentation.ui.detailpost.adapter.DetailPostViewPagerAdapter
import com.example.charo_android.presentation.ui.detailpost.viewmodel.DetailPostViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailPostFragment : Fragment() {
    private var _binding: FragmentDetailPostBinding? = null
    private val binding get() = _binding ?: error("binding not initialized")
    private val viewModel: DetailPostViewModel by viewModel()
    private var imageList = mutableListOf<String>()
    private lateinit var viewPagerAdapter: DetailPostViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_detail_post, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDetailPostData(0)
        initViewPager()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initViewPager() {
        viewPagerAdapter = DetailPostViewPagerAdapter()
        viewModel.detailPost.observe(viewLifecycleOwner) {
            imageList = it.images.toMutableList()
        }
        viewPagerAdapter.itemList = imageList
        binding.vpPost.adapter = viewPagerAdapter
    }
}