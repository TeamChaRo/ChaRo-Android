package com.example.charo_android.ui.charo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.charo_android.databinding.FragmentCharoBinding

class CharoFragment : Fragment() {

    private lateinit var charoViewModel: CharoViewModel
    private var _binding: FragmentCharoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        charoViewModel =
            ViewModelProvider(this).get(CharoViewModel::class.java)

        _binding = FragmentCharoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textCharo
        charoViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}