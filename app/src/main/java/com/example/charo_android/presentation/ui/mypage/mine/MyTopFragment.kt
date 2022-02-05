package com.example.charo_android.presentation.ui.mypage.mine

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentMyTopBinding
import com.example.charo_android.presentation.ui.mypage.viewmodel.MyPageViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MyTopFragment : Fragment() {
    private var _binding: FragmentMyTopBinding? = null
    val binding get() = _binding ?: error("binding not initiated")
//    val viewModel by activityViewModels<MyPageViewModel>()
    val viewModel: MyPageViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_my_top, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.userInfo.observe(viewLifecycleOwner) {
            Log.d("mlog: MyTopFragment::onViewCreated", it.nickname)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}