package com.example.charo_android.presentation.ui.charo

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
import com.example.charo_android.data.api.hidden.Hidden
import com.example.charo_android.databinding.FragmentSaveBinding

class SaveFragment: Fragment() {
    private val saveViewModel: CharoViewModel by activityViewModels()
    private var _binding: FragmentSaveBinding? = null
    private val binding get() = _binding!!
    private lateinit var charoAdapter: CharoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        charoAdapter = CharoAdapter(Hidden.userId)
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_save, container, false)
        val root: View = binding.root

        Log.d("SaveFragment", "created!")

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
                        saveViewModel.savedPostSortedByPopular.observe(viewLifecycleOwner, {
                            if(saveViewModel.savedPostSortedByPopular.value?.drive != null) {
                                charoAdapter.itemList = saveViewModel.savedPostSortedByPopular.value?.drive!!
                                charoAdapter.notifyDataSetChanged()
                            }
                        })
                    } else {
                        binding.recyclerviewMyCharo.adapter = charoAdapter
                        if(saveViewModel.savedPostSortedByDate.value == null) {
                            saveViewModel.getServerDataSortedByDate()
                        }
                        saveViewModel.savedPostSortedByDate.observe(viewLifecycleOwner, {
                            if(saveViewModel.savedPostSortedByDate.value?.drive != null) {
                                charoAdapter.itemList = saveViewModel.savedPostSortedByDate.value?.drive!!
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