package com.example.charo_android.ui.charo

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.charo_android.DetailViewpagerImageAdapter
import com.example.charo_android.R
import com.example.charo_android.data.LocalMyCharoDataSource
import com.example.charo_android.databinding.FragmentMyCharoBinding
import kotlinx.android.synthetic.main.activity_main.*

class MyCharoFragment : Fragment() {
    private var _binding: FragmentMyCharoBinding? = null
    private val binding get() = _binding!!
    private var myCharoAdapter = MyCharoAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyCharoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initMyCharoItem()
        setUpSpinner()

        // Inflate the layout for this fragment
        return root
    }

    private fun initMyCharoItem() {
        binding.recyclerviewMyCharo.adapter = myCharoAdapter
        myCharoAdapter.itemList.addAll(LocalMyCharoDataSource().fetchData())
        myCharoAdapter.notifyDataSetChanged()
    }
    
    private fun setUpSpinner() {
        val filter = resources.getStringArray(R.array.charo_filter)
        val adapter =
            activity?.let { ArrayAdapter(it, R.layout.support_simple_spinner_dropdown_item, filter) }
        binding.spinnerMyCharoFilter.adapter = adapter
    }
}