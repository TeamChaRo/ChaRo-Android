package com.charo.android.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.charo.android.databinding.FragmentBannerAboutCharoBinding
import com.charo.android.domain.model.home.BannerAboutCharo

class BannerAboutCharoFragment(val item : BannerAboutCharo) : Fragment() {
    private var _binding: FragmentBannerAboutCharoBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBannerAboutCharoBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgViewpager.setImageResource(item.image)
        binding.banner = item
    }
}