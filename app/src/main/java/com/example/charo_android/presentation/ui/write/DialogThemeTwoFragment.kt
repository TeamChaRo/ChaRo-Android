package com.example.charo_android.presentation.ui.write

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.charo_android.databinding.FragmentDialogThemeOneBinding
import com.example.charo_android.databinding.FragmentDialogThemeTwoBinding

class DialogThemeTwoFragment : Fragment() {
    fun newInstance() : DialogThemeTwoFragment
    {
        val args = Bundle()
        val frag = DialogThemeTwoFragment()
        frag.arguments = args
        return frag
    }

    private lateinit var _binding: FragmentDialogThemeTwoBinding
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDialogThemeTwoBinding.inflate(layoutInflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}