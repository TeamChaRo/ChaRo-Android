package com.charo.android.presentation.ui.detailpost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.charo.android.databinding.FragmentDetailPostImageBinding
import com.charo.android.presentation.ui.detailpost.adapter.DetailPostImageViewPagerAdapter
import com.charo.android.presentation.ui.detailpost.viewmodel.DetailPostViewModel
import com.charo.android.presentation.ui.write.WriteSharedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DetailPostImageFragment : Fragment() {
    private var _binding: FragmentDetailPostImageBinding? = null
    private val binding get() = _binding ?: error("binding not initialized")
    private lateinit var viewPagerAdapter: DetailPostImageViewPagerAdapter
    // TODO: Old ViewModel
//    private val viewModel by sharedViewModel<DetailPostViewModel>()
    // TODO: New ViewModel
    private val viewModel by sharedViewModel<WriteSharedViewModel>()

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
        // TODO: Old Codes
        // 굳이 observe 해야했나 ?
//        viewModel.detailPost.observe(viewLifecycleOwner) {
//            viewPagerAdapter.replaceItem(it.images)
//            binding.vpImg.setCurrentItem(viewModel.imageIndex, false)
//        }
        viewModel.imageStringViewPager.value?.let {
            viewPagerAdapter.replaceItem(it.toList())
        }
        binding.vpImg.setCurrentItem(viewModel.imageIndex, false)
        binding.vpImg.adapter = viewPagerAdapter
    }
}