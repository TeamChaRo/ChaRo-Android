package com.example.charo_android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.charo_android.databinding.FragmentDialogThemeOneBinding

class DialogThemeOneFragment : Fragment() {

    private lateinit var _binding: FragmentDialogThemeOneBinding

    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDialogThemeOneBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

}