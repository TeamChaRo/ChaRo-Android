package com.charo.android.presentation.ui.mypage.guest

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.charo.android.R
import com.charo.android.databinding.FragmentGuestTopBinding
import com.charo.android.presentation.ui.setting.SettingActivity
import com.charo.android.presentation.ui.signin.SocialSignInActivity
import com.charo.android.presentation.util.LoginUtil
import com.charo.android.presentation.util.SharedInformation
import timber.log.Timber


class GuestTopFragment : Fragment() {
    private var _binding: FragmentGuestTopBinding? = null
    private val binding get() = _binding ?: error("binding not initialized")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_guest_top, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickSetting()
        clickLogin()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun clickSetting() {
        binding.imgProfileSetting.setOnClickListener {
            val userEmail = SharedInformation.getEmail(requireActivity())
            if (userEmail == "@") {
                LoginUtil.loginPrompt(requireActivity())
            } else {
                val intent = Intent(requireContext(), SettingActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun clickLogin() {
        binding.tvProfileNickname.setOnClickListener {
            val intent = Intent(requireContext(), SocialSignInActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        binding.imgLogin.setOnClickListener {
            val intent = Intent(requireContext(), SocialSignInActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}