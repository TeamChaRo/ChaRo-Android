package com.example.charo_android.ui.charo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentMyCharoBinding
import com.example.charo_android.hidden.Hidden

class MyCharoFragment : Fragment() {
    private val myCharoViewModel: CharoViewModel by activityViewModels()
    private var _binding: FragmentMyCharoBinding? = null
    private val binding get() = _binding!!
    private lateinit var charoAdapter: CharoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        charoAdapter = CharoAdapter(Hidden.userId)
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_charo, container, false)
        val root: View = binding.root

        Log.d("MyCharoFragment", "created!")

        setUpSpinner()
        setupSpinnerHandler()

        // Inflate the layout for this fragment
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpSpinner() {
        val filter = resources.getStringArray(R.array.charo_filter)
        val adapter =
            activity?.let { ArrayAdapter(it, R.layout.item_charo_spinner, filter) }
        adapter?.setDropDownViewResource(R.layout.item_charo_spinner)
        binding.spinnerMyCharoFilter.adapter = adapter
    }

    private fun setupSpinnerHandler() {
        binding.spinnerMyCharoFilter.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position == 0) {
                        binding.recyclerviewMyCharo.adapter = charoAdapter
                        myCharoViewModel.writtenPostSortedByPopular.observe(viewLifecycleOwner, {
                            if(myCharoViewModel.writtenPostSortedByPopular.value?.drive != null) {
                                charoAdapter.itemList = myCharoViewModel.writtenPostSortedByPopular.value?.drive!!
                                charoAdapter.notifyDataSetChanged()
                            }
                        })
                    } else {
                        binding.recyclerviewMyCharo.adapter = charoAdapter
                        if(myCharoViewModel.writtenPostSortedByDate.value == null) {
                            myCharoViewModel.getServerDataSortedByDate()
                        }
                        myCharoViewModel.writtenPostSortedByDate.observe(viewLifecycleOwner, {
                            if(myCharoViewModel.writtenPostSortedByDate.value?.drive != null) {
                                charoAdapter.itemList = myCharoViewModel.writtenPostSortedByDate.value?.drive!!
                                charoAdapter.notifyDataSetChanged()
                            }
                        })
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    binding.recyclerviewMyCharo.adapter = charoAdapter
                }
            }
    }
}