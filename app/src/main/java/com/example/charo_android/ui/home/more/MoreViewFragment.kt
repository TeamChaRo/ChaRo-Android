package com.example.charo_android.ui.home.more

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentHomeBinding
import com.example.charo_android.databinding.FragmentMoreViewBinding


class MoreViewFragment : Fragment() {
    private var _binding: FragmentMoreViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoreViewBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}