package com.charo.android.presentation.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.charo.android.R
import com.charo.android.data.model.request.home.RequestHomeLikeData
import com.charo.android.data.model.request.search.RequestSearchViewData
import com.charo.android.databinding.FragmentResultSearchBinding
import com.charo.android.presentation.base.BaseFragment
import com.charo.android.presentation.ui.search.viewmodel.SearchViewModel
import com.charo.android.presentation.util.SharedInformation
import com.charo.android.presentation.util.ThemeUtil
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber


class ResultSearchFragment : BaseFragment<FragmentResultSearchBinding>(R.layout.fragment_result_search) {
    private val searchViewModel: SearchViewModel by sharedViewModel()
    private val themeUtil = ThemeUtil()
    private lateinit var resultSearchAdapter: ResultSearchAdapter
    var links = DataToSearchLike()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()

        loadSearchData()
        initSpinner()
        initResultSearchView()
//        clickBackBtn()
        clickBackHome()
        clickSpinner()
        Timber.d("searchViewModel ${searchViewModel.province.value.toString()}")
        Timber.d("searchViewModel ${searchViewModel.city.value.toString()}")
        Timber.d("searchViewModel ${searchViewModel.theme.value.toString()}")
        Timber.d("searchViewModel ${searchViewModel.caution.value.toString()}")
    }

    private fun initToolbar(){
        val toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_1)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    //검색 종료(홈으로 이동)
    private fun clickBackHome(){
        binding.imgGoHomeView.setOnClickListener {
            requireActivity().finish()
        }
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
        val userId = SharedInformation.getEmail(requireActivity())
        resultSearchAdapter = ResultSearchAdapter(userId, links)
        binding.recyclerviewResultSearch.adapter = resultSearchAdapter
        searchViewModel.search.observe(viewLifecycleOwner) {
            resultSearchAdapter.setSearchDrive(it)
            binding.textResultSearchCount.text = String.format(getString(R.string.main_charo_more_view_count), it.size)
        }

    }

//    //뒤로가기
//    private fun clickBackBtn(){
//        binding.imgBackSearchView.bringToFront()
//        binding.imgBackSearchView.isClickable = true
//        binding.imgBackSearchView.setOnClickListener {
//            Timber.d("resultSearch 뒤로가기 버튼 눌림")
//            val transaction = activity?.supportFragmentManager?.beginTransaction()
//            transaction?.apply {
//                replace(
//                    R.id.fragment_container_search,
//                    SearchFragment()
//                )
//                commit()
//            }
//        }
//    }
//
//    //검색 종료(홈으로 이동)
//    private fun clickBackHome(){
//        binding.imgGoHomeView.setOnClickListener {
//            requireActivity().finish()
//        }
//    }


    private fun initSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.search_spinner,
            R.layout.custom_spinner_item
        )
        binding.spinnerResultSearch.adapter = adapter
    }
    //스피너 필터 클릭 이벤트
    private fun clickSpinner() {
        val userEmail = SharedInformation.getEmail(requireActivity())
        val requestSearchViewData = RequestSearchViewData(
            region = searchViewModel.city.value.toString(),
            theme = themeUtil.themeMap.get(searchViewModel.theme.value.toString()) ?: "",
            warning = themeUtil.cautionMap.get(searchViewModel.caution.value.toString()) ?: "",
            userEmail = userEmail,
        )
        binding.spinnerResultSearch.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position == 0) {
                        searchViewModel.getSearchLike(requestSearchViewData)

                    } else {
                        searchViewModel.getSearchNew(requestSearchViewData)
                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }

    }


    inner class DataToSearchLike(){
        fun getPostId(postId : Int){
            val userEmail = SharedInformation.getEmail(requireActivity())
                searchViewModel.postLike(RequestHomeLikeData(userEmail,postId))
        }

    }
}