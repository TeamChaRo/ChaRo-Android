package com.example.charo_android.ui.charo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.charo_android.R
import com.example.charo_android.data.LocalMyCharoDataSource
import com.example.charo_android.databinding.FragmentMyCharoBinding

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initMyCharoItem() {
        binding.recyclerviewMyCharo.adapter = myCharoAdapter
        myCharoAdapter.itemList.addAll(LocalMyCharoDataSource().fetchData())
        myCharoAdapter.notifyDataSetChanged()
    }
    
    private fun setUpSpinner() {
        val filter = resources.getStringArray(R.array.charo_filter)
        val adapter =
            activity?.let { ArrayAdapter(it, R.layout.item_charo_spinner, filter) }
        adapter?.setDropDownViewResource(R.layout.item_charo_spinner)
        binding.spinnerMyCharoFilter.adapter = adapter
    }
}