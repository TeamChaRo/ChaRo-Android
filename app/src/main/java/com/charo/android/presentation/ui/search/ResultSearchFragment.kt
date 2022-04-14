package com.charo.android.presentation.ui.search

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.charo.android.R
import com.charo.android.data.model.request.home.RequestHomeLikeData
import com.charo.android.databinding.FragmentResultSearchBinding
import com.charo.android.presentation.base.BaseFragment
import com.charo.android.presentation.ui.search.viewmodel.SearchViewModel
import com.charo.android.presentation.util.SharedInformation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber


class ResultSearchFragment : BaseFragment<FragmentResultSearchBinding>(R.layout.fragment_result_search) {
    private val searchViewModel: SearchViewModel by sharedViewModel()
    private lateinit var resultSearchAdapter: ResultSearchAdapter
    var links = DataToSearchLike()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadSearchData()
        initSpinner()
        initResultSearchView()
        clickBackBtn()
        clickBackHome()
        Timber.d("searchViewModel ${searchViewModel.province.value.toString()}")
        Timber.d("searchViewModel ${searchViewModel.city.value.toString()}")
        Timber.d("searchViewModel ${searchViewModel.theme.value.toString()}")
        Timber.d("searchViewModel ${searchViewModel.caution.value.toString()}")
    }


    fun loadSearchData() {
        if (searchViewModel.province.value == "") {
            binding.chipResultSearch1.visibility = View.GONE
        } else {
            binding.chipResultSearch1.text = "${searchViewModel.province.value}"
        }
        if (searchViewModel.city.value == "") {
            binding.chipResultSearch2.visibility = View.GONE
        } else {
            binding.chipResultSearch2.text = "${searchViewModel.city.value}"
        }
        if (searchViewModel.theme.value == "") {
            binding.chipResultSearch3.visibility = View.GONE
        } else {
            binding.chipResultSearch3.text = "${searchViewModel.theme.value}"
        }

        if (searchViewModel.caution.value == "") {
            binding.chipResultSearch4.visibility = View.GONE
        } else {
            binding.chipResultSearch4.text = "${searchViewModel.caution.value}"+"x"

        }
    }

    private fun initResultSearchView() {
        resultSearchAdapter = ResultSearchAdapter(links)
        binding.recyclerviewResultSearch.adapter = resultSearchAdapter
        searchViewModel.search.observe(viewLifecycleOwner) {
            resultSearchAdapter.setSearchDrive(it)
            binding.textResultSearchCount.text = String.format(getString(R.string.main_charo_more_view_count), it.size)
        }

    }

    //뒤로가기
    private fun clickBackBtn(){
        binding.imgBackSearchView.bringToFront()
        binding.imgBackSearchView.isClickable = true
        binding.imgBackSearchView.setOnClickListener {
            Timber.d("resultSearch 뒤로가기 버튼 눌림")
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.apply {
                replace(
                    R.id.fragment_container_search,
                    SearchFragment()
                )
                commit()
            }
        }
    }

    //검색 종료(홈으로 이동)
    private fun clickBackHome(){
        binding.imgGoHomeView.setOnClickListener {
            requireActivity().finish()
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

    inner class DataToSearchLike(){
        fun getPostId(postId : Int){
            val userEmail = SharedInformation.getEmail(requireActivity())
                searchViewModel.postLike(RequestHomeLikeData(userEmail,postId))
        }

    }
}