package com.example.charo_android.presentation.ui.write

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.charo_android.databinding.DialogThemeBinding
import com.example.charo_android.databinding.FragmentDialogThemeOneBinding

class DialogThemeOneFragment : Fragment() {
    fun newInstance() : DialogThemeOneFragment
    {
        val args = Bundle()
        val frag = DialogThemeOneFragment()
        frag.arguments = args
        return frag
    }

    private lateinit var _binding: FragmentDialogThemeOneBinding
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDialogThemeOneBinding.inflate(layoutInflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}