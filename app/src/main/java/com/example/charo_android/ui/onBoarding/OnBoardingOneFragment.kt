package com.example.charo_android.ui.onBoarding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.charo_android.databinding.FragmentOnBoarding1Binding
import com.example.charo_android.ui.signin.SignInActivity


class OnBoardingOneFragment : Fragment() {
    private var _binding: FragmentOnBoarding1Binding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoarding1Binding.inflate(inflater, container, false)

        binding.btnSkips.setOnClickListener {
            val intent = Intent(requireActivity(), SignInActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }


}