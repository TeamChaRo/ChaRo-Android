package com.example.charo_android.ui.home.more

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.charo_android.R
import com.example.charo_android.data.LocalHomeThemeDataSource
import com.example.charo_android.data.LocalMoreThemeDataSource
import com.example.charo_android.databinding.FragmentMoreThemeContentViewBinding
import com.example.charo_android.databinding.FragmentMoreViewBinding


class MoreThemeContentViewFragment : Fragment() {
    private var _binding: FragmentMoreThemeContentViewBinding? = null
    private val binding get() = _binding!!
    private val moreThemeContentViewAdapter = MoreThemeContentViewAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoreThemeContentViewBinding.inflate(inflater, container, false)
        val root: View = binding.root


        initMoreThemeView()
        initSpinner()


        return root
    }


    private fun initMoreThemeView(){
        binding.recyclerviewMoreTheme.adapter = moreThemeContentViewAdapter
        moreThemeContentViewAdapter.moreThemeData.addAll(LocalMoreThemeDataSource().fetchData())
        moreThemeContentViewAdapter.notifyDataSetChanged()
    }

    private fun initSpinner(){
        val adapter = ArrayAdapter.createFromResource(requireContext(), R.array.search_spinner, android.R.layout.simple_spinner_item)
        binding.spinnerMoreTheme.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}