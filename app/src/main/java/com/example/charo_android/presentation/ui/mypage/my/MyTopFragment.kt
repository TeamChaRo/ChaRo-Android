package com.example.charo_android.presentation.ui.mypage.my

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentMyTopBinding
import com.example.charo_android.presentation.ui.follow.FollowActivity
import com.example.charo_android.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.example.charo_android.presentation.ui.setting.SettingActivity
import com.example.charo_android.presentation.util.SharedInformation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MyTopFragment : Fragment() {
    private var _binding: FragmentMyTopBinding? = null
    val binding get() = _binding ?: error("binding not initiated")
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
        showFollowList()
        clickSetting()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun showFollowList() {
        binding.clProfileFollow.setOnClickListener {
            val intent = Intent(requireContext(), FollowActivity::class.java)
            intent.putExtra("userEmail", SharedInformation.getEmail(requireContext()))
            intent.putExtra("nickname", viewModel.userInfo.value?.nickname)
            startActivity(intent)
        }
    }

    private fun clickSetting() {
        binding.imgProfileSetting.setOnClickListener {
            val intent = Intent(requireContext(), SettingActivity::class.java)
            startActivity(intent)
        }
    }
}