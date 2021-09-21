package com.example.charo_android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.charo_android.databinding.FragmentDialogThemeOneBinding
import com.example.charo_android.databinding.FragmentDialogThemeTwoBinding

class DialogThemeTwoFragment : Fragment() {
    private lateinit var _binding: FragmentDialogThemeTwoBinding

    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDialogThemeTwoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

}