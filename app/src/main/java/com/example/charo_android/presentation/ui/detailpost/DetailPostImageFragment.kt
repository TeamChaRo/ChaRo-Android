package com.example.charo_android.presentation.ui.detailpost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.charo_android.databinding.FragmentDetailPostImageBinding
import com.example.charo_android.presentation.ui.detailpost.adapter.DetailPostImageViewPagerAdapter
import com.example.charo_android.presentation.ui.detailpost.viewmodel.DetailPostViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DetailPostImageFragment : Fragment() {
    private var _binding: FragmentDetailPostImageBinding? = null
    private val binding get() = _binding ?: error("binding not initialized")
    private lateinit var viewPagerAdapter: DetailPostImageViewPagerAdapter
    private val viewModel by sharedViewModel<DetailPostViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailPostImageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initViewPager() {
        viewPagerAdapter = DetailPostImageViewPagerAdapter()
        viewModel.detailPost.observe(viewLifecycleOwner) {
            viewPagerAdapter.replaceItem(it.images)
            binding.vpImg.setCurrentItem(viewModel.imageIndex, false)
        }
        binding.vpImg.adapter = viewPagerAdapter
    }
}