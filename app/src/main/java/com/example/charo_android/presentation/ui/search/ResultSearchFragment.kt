package com.example.charo_android.presentation.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentResultSearchBinding
import com.example.charo_android.presentation.base.BaseFragment
import com.example.charo_android.presentation.ui.search.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ResultSearchFragment : BaseFragment<FragmentResultSearchBinding>(R.layout.fragment_result_search) {
    private val searchViewModel: SearchViewModel by sharedViewModel()
    private lateinit var resultSearchAdapter: ResultSearchAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadSearchData()
        initSpinner()
        initResultSearchView()
    }


    fun loadSearchData() {
        Log.d("searchViewModel", searchViewModel.province.value.toString())
        Log.d("searchViewModel", searchViewModel.city.value.toString())
        Log.d("searchViewModel", searchViewModel.theme.value.toString())
        Log.d("searchViewModel", searchViewModel.caution.value.toString())

        if (searchViewModel.caution.value == "") {
            binding.chipResultSearch4.visibility = View.GONE
        } else {
            binding.chipResultSearch4.text = "${searchViewModel.caution.value}"+"x"
        }
    }

    private fun initResultSearchView() {
        resultSearchAdapter = ResultSearchAdapter()
        binding.recyclerviewResultSearch.adapter = resultSearchAdapter
        searchViewModel.search.observe(viewLifecycleOwner) {
            resultSearchAdapter.setSearchDrive(it)
        }
    }



    private fun initSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.search_spinner,
            R.layout.custom_spinner_item
        )
        binding.spinnerResultSearch.adapter = adapter
    }
}