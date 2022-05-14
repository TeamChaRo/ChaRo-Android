package com.charo.android.presentation.ui.search

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import com.charo.android.R
import com.charo.android.data.model.request.search.RequestSearchViewData
import com.charo.android.databinding.FragmentSearchBinding
import com.charo.android.presentation.base.BaseFragment
import com.charo.android.presentation.ui.search.viewmodel.SearchViewModel
import com.charo.android.presentation.util.LocationUtil
import com.charo.android.presentation.util.SharedInformation
import com.charo.android.presentation.util.ThemeUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val themeUtil = ThemeUtil()
    private val locationUtil = LocationUtil()
    private val searchViewModel: SearchViewModel by sharedViewModel()

    private var preCheckProvince = 0
    private var preCheckedRegion = 0
    private var searchCautionItem = 0
    private var searchItem = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickSearch()
        goHomeView()
        selectTheme()
        selectCaution()
        nickName()

        binding.btnSearchArea1.setOnClickListener { selectProvince(it) }
        binding.btnSearchArea2.setOnClickListener { selectRegion(it) }
    }
    private fun nickName(){
        val userNickName = SharedInformation.getNickName(requireActivity())
        binding.textUserId.text = "${userNickName}님의"
    }

    private fun isActiveSearch(boolean : Boolean){
        binding.apply {
            if(boolean){
                textSearchStart.setBackgroundResource(R.drawable.ic_search_start_blue)
                textSearchStart.setTextColor(getColor(requireActivity(),R.color.white))
                textSearchStart.setPadding(0, 0, 0, 20)
            } else {
                if(TextUtils.isEmpty(searchViewModel.province.value) && TextUtils.isEmpty(searchViewModel.city.value)
                    && TextUtils.isEmpty(searchViewModel.theme.value) && TextUtils.isEmpty(searchViewModel.caution.value)) {

                    textSearchStart.setBackgroundResource(R.drawable.ic_search_btn_white)
                    textSearchStart.setTextColor(
                        getColor(
                            requireActivity(),
                            R.color.blue_main_0f6fff
                        )
                    )
                    textSearchStart.setPadding(0, 0, 0, 20)
                }
            }
        }
    }

    private fun clickSearch() {
        val userEmail = SharedInformation.getEmail(requireActivity())
        binding.textSearchStart.setOnClickListener {
            searchViewModel.province.value = if (binding.btnSearchArea1.text.toString() == "선택안함" || binding.btnSearchArea1.text.toString() == "지역(도)") {
                ""
            } else {
                binding.btnSearchArea1.text.toString()
            }
            searchViewModel.city.value =
                if (binding.btnSearchArea2.text.toString() == "선택안함" || binding.btnSearchArea2.text.toString() == "지역(시)") {
                    ""
                } else {
                    binding.btnSearchArea2.text.toString()
                }
            searchViewModel.theme.value = if (binding.btnSearchTheme.text.toString() == "테마") {
                ""
            } else {
                binding.btnSearchTheme.text.toString()
            }
            searchViewModel.caution.value =
                if (binding.btnSearchCaution.text.toString() == "주의사항") {
                    ""
                } else {
                    binding.btnSearchCaution.text.toString()
                }

            if(TextUtils.isEmpty(searchViewModel.province.value) && TextUtils.isEmpty(searchViewModel.city.value)
                && TextUtils.isEmpty(searchViewModel.theme.value) && TextUtils.isEmpty(searchViewModel.caution.value)){
                Toast.makeText(requireContext(),"지역, 테마, 주의사항 중 하나 이상 선택해주세요.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(!TextUtils.isEmpty(searchViewModel.province.value) && TextUtils.isEmpty(searchViewModel.city.value)){
                Toast.makeText(requireContext(),"지역(시)를 선택해주세요.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val requestSearchViewData = RequestSearchViewData(
                region = searchViewModel.city.value.toString(),
                theme = themeUtil.themeMap.get(searchViewModel.theme.value.toString()) ?: "",
                warning = themeUtil.cautionMap.get(searchViewModel.caution.value.toString()) ?: "",
                userEmail = userEmail,
            )
            searchViewModel.getSearchLike(requestSearchViewData)
            searchViewModel.search.observe(viewLifecycleOwner) {
                if (it.count() == 0) {
                    val transaction = activity?.supportFragmentManager?.beginTransaction()
                    transaction?.apply {
                        replace(R.id.fragment_container_search, NoSearchFragment())
                        addToBackStack("")
                        commit()
                    }
                } else {
                    val transaction = activity?.supportFragmentManager?.beginTransaction()
                    transaction?.apply {
                        replace(
                            R.id.fragment_container_search,
                            ResultSearchFragment()
                        )
                        addToBackStack("")
                        commit()
                    }
                }
            }
        }
    }

    fun selectTheme() {
        binding.btnSearchTheme.setOnClickListener {
            MaterialAlertDialogBuilder(requireActivity(), R.style.Dialog)
                .setTitle(R.string.main_charo_theme)
                .setNeutralButton("취소") { dialog, which ->
                    binding.btnSearchTheme.setText(resources.getString(R.string.main_charo_theme))
                    searchItem = 0
                    it.isSelected = false
                    isActiveSearch(false)
                }
                .setPositiveButton("확인") { dialog, which ->
                    it.isSelected = true
                    binding.btnSearchTheme.text = themeUtil.itemTheme[searchItem]
                    isActiveSearch(true)
                    if (binding.btnSearchTheme.text.toString() == resources.getString(R.string.main_charo_theme)) {
                        it.isSelected = false
                        isActiveSearch(false)
                    }
                }
                .setSingleChoiceItems(themeUtil.itemTheme, searchItem) { dialog, which ->
                    searchItem = which
                }
                .setBackground(resources.getDrawable(R.drawable.background_radius_all_20))
                .show()
        }
    }

    fun selectCaution() {
        binding.btnSearchCaution.setOnClickListener {
            MaterialAlertDialogBuilder(requireActivity(), R.style.Dialog)
                .setTitle(R.string.caution)
                .setNeutralButton("취소") { dialog, which ->
                    binding.btnSearchCaution.setText(resources.getString(R.string.caution))
                    searchCautionItem = 0
                    it.isSelected = false
                    isActiveSearch(false)
                }
                .setPositiveButton("확인") { dialog, which ->
                    it.isSelected = true
                    binding.btnSearchCaution.text = themeUtil.itemCaution[searchCautionItem]
                    isActiveSearch(true)
                    if (binding.btnSearchCaution.text.toString() == resources.getString(R.string.caution)) {
                        it.isSelected = false
                        isActiveSearch(false)
                    }
                }
                .setSingleChoiceItems(
                    themeUtil.itemCaution,
                    searchCautionItem,
                ) { dialog, which ->
                    searchCautionItem = which
                }
                .setBackground(resources.getDrawable(R.drawable.background_radius_all_20))
                .show()
        }
    }

    //지역 (도 단위)
    private fun selectProvince(it: View) {
        MaterialAlertDialogBuilder(requireContext(), R.style.Dialog)
            .setTitle(R.string.area)
            .setNeutralButton(R.string.cancel) { dialog, which ->
                binding.btnSearchArea1.text = resources.getString(R.string.area_province)
                binding.btnSearchArea2.text = resources.getString(R.string.area_region)
                binding.btnSearchArea2.isSelected = false
                it.isSelected = false
                preCheckProvince = 0
                isActiveSearch(false)
            }
            .setPositiveButton(R.string.agreement) { dialog, which ->
                it.isSelected = true
                binding.btnSearchArea1.text = locationUtil.itemProvince[preCheckProvince]
                searchViewModel.province.value = binding.btnSearchArea1.text.toString()
                selectRegion(binding.btnSearchArea2)
            }
            .setSingleChoiceItems(locationUtil.itemProvince, preCheckProvince) { dialog, which ->
                //which : index
                preCheckProvince = which

                //이전 선택값과 다를 때
                if (locationUtil.itemProvince[which] != searchViewModel.province.value) {
                    binding.btnSearchArea2.text = resources.getString(R.string.area_region)
                    binding.btnSearchArea2.isSelected = false
                    searchViewModel.city.value = ""
                }
            }
            .setCancelable(false)
            .setBackground(resources.getDrawable(R.drawable.background_radius_all_20))
            .show()
    }

    //지역 (시 단위)
    private fun selectRegion(it: View) {
        MaterialAlertDialogBuilder(requireContext(), R.style.Dialog)
            .setTitle(R.string.area)
            .setNeutralButton(R.string.cancel) { dialog, which ->
                binding.btnSearchArea2.text = resources.getString(R.string.area_region)
                it.isSelected = false
                preCheckedRegion = 0
                searchViewModel.city.value = ""
                isActiveSearch(false)
            }
            .setPositiveButton(R.string.agreement) { dialog, which ->
                it.isSelected = true

                if (locationUtil.matchRegionToProvince[binding.btnSearchArea1.text] == null) {
                    binding.btnSearchArea2.text = resources.getString(R.string.area_region)
                    it.isSelected = false
                } else {
                    binding.btnSearchArea2.text =
                        locationUtil.matchRegionToProvince[binding.btnSearchArea1.text]?.get(
                            preCheckedRegion
                        )
                            ?: resources.getString(R.string.area_region)

                    searchViewModel.city.value = binding.btnSearchArea2.text.toString()
                }
                isActiveSearch(true)
            }
            .setSingleChoiceItems(
                locationUtil.matchRegionToProvince[binding.btnSearchArea1.text]
                    ?: arrayOf("도 단위를 선택해주세요."), preCheckedRegion
            ) { dialog, which ->
                //which : index
                preCheckedRegion = which
            }
            .setCancelable(false)
            .setBackground(resources.getDrawable(R.drawable.background_radius_all_20))
            .show()
    }

    fun goHomeView() {
        binding.imgSearchBack.setOnClickListener {
            requireActivity().finish()
        }

    }


}