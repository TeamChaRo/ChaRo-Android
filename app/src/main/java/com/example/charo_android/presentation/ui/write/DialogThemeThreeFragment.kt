package com.example.charo_android.presentation.ui.write

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.charo_android.databinding.FragmentDialogThemeOneBinding
import com.example.charo_android.databinding.FragmentDialogThemeThreeBinding
import com.example.charo_android.databinding.FragmentDialogThemeTwoBinding

class DialogThemeThreeFragment : Fragment() {
    fun newInstance() : DialogThemeThreeFragment
    {
        val args = Bundle()
        val frag = DialogThemeThreeFragment()
        frag.arguments = args
        return frag
    }

    private lateinit var _binding: FragmentDialogThemeThreeBinding
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDialogThemeThreeBinding.inflate(layoutInflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}