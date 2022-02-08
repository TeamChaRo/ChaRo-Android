package com.example.charo_android.presentation.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.charo_android.databinding.FragmentOnBoarding3Binding
import com.example.charo_android.presentation.ui.signin.SocialSignInActivity


class OnBoardingThreeFragment : Fragment() {
    private var _binding: FragmentOnBoarding3Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoarding3Binding.inflate(inflater, container, false)

        binding.btnOnboard.setOnClickListener {
            val intent = Intent(requireActivity(), SocialSignInActivity::class.java)
            startActivity(intent)
        }


        return binding.root
    }


}