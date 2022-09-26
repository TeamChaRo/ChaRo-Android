package com.charo.android.presentation.ui.search

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
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
        initValue()

        binding.btnSearchArea1.setOnClickListener { selectProvince(it) }
        binding.btnSearchArea2.setOnClickListener { selectRegion(it) }
    }

    private fun nickName() {
        val userNickName = SharedInformation.getNickName(requireActivity())
        binding.textUserId.text = "${userNickName}님의"
    }

    //검색 활성화
    private fun isActiveSearch(boolean: Boolean) {
        binding.apply {
            if (boolean) {
                textSearchStart.isActivated = true
                textSearchStart.setTextColor(getColor(requireActivity(), R.color.white))
            } else {
                textSearchStart.isActivated = false
                textSearchStart.setTextColor(
                    getColor(
                        requireActivity(),
                        R.color.blue_main_0f6fff
                    )
                )
            }
        }
    }

    private fun clickSearch() {
        val userEmail = SharedInformation.getEmail(requireActivity())

        binding.textSearchStart.setOnClickListener {
            //모두 빈값이면 return
            if(binding.btnSearchArea1.text.toString() == getString(R.string.area_province)
                && binding.btnSearchArea2.text.toString() == getString(R.string.area_region)
                && binding.btnSearchTheme.text.toString() == getString(R.string.main_charo_theme)
                && binding.btnSearchCaution.text.toString() == getString(R.string.caution)){
                return@setOnClickListener
            }

            searchViewModel.province.value =
                if (binding.btnSearchArea1.text.toString() == getString(R.string.no_select) || binding.btnSearchArea1.text.toString() == getString(R.string.area_province)) {
                    ""
                } else {
                    binding.btnSearchArea1.text.toString()
                }
            searchViewModel.city.value =
                if (binding.btnSearchArea2.text.toString() == getString(R.string.no_select) || binding.btnSearchArea2.text.toString() == getString(R.string.area_region)) {
                    ""
                } else {
                    binding.btnSearchArea2.text.toString()
                }
            searchViewModel.theme.value =
                if (binding.btnSearchTheme.text.toString() == getString(R.string.no_select) || binding.btnSearchTheme.text.toString() == getString(R.string.main_charo_theme)) {
                    ""
                } else {
                    binding.btnSearchTheme.text.toString()
                }
            searchViewModel.caution.value =
                if (binding.btnSearchCaution.text.toString() == getString(R.string.no_select) || binding.btnSearchCaution.text.toString() == getString(R.string.caution)) {
                    ""
                } else {
                    binding.btnSearchCaution.text.toString()
                }

            if (!TextUtils.isEmpty(searchViewModel.province.value) && TextUtils.isEmpty(
                    searchViewModel.city.value
                )
            ) {
                Toast.makeText(requireContext(), "지역(시)를 선택해주세요.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val requestSearchViewData = RequestSearchViewData(
                region = searchViewModel.city.value.toString(),
                theme = themeUtil.themeMap.get(searchViewModel.theme.value.toString()) ?: "",
                warning = themeUtil.cautionMap.get(searchViewModel.caution.value.toString()) ?: "",
                userEmail = userEmail,
            )
            searchViewModel.getSearchLike(requestSearchViewData)
            searchViewModel.searchStatus.observe(viewLifecycleOwner) {
                Timber.d("searchViewModel.searchStatus $it")
                if(it != 2000) {
                    Toast.makeText(requireContext(), getString(R.string.server_error_general),Toast.LENGTH_LONG)
                        .show()
                }
            }
            searchViewModel.search.observe(viewLifecycleOwner) {
                Timber.d("searchViewModel.search $it")

                val transaction = activity?.supportFragmentManager?.beginTransaction()
                val fragment: Fragment
                transaction?.apply {
                    fragment = ResultSearchFragment()
                    replace(R.id.fragment_container_search, fragment)
                    addToBackStack("")
                    commit()
                }
            }
        }
    }

    fun selectTheme() {
        binding.btnSearchTheme.setOnClickListener {
            MaterialAlertDialogBuilder(requireActivity(), R.style.Dialog)
                .setTitle(R.string.main_charo_theme)
                .setNeutralButton(R.string.cancel) { _, _ ->
                    binding.btnSearchTheme.text = resources.getString(R.string.main_charo_theme)
                    searchItem = 0
                    it.isSelected = false

                    //시 없이 도만 선택했을 경우 비활
                    if(binding.btnSearchArea1.text.toString() != resources.getString(R.string.area_province)
                            && binding.btnSearchArea2.text.toString() == resources.getString(R.string.area_region)) {
                        isActiveSearch(false)
                    //모두 빈 값일 경우 비활
                    } else if (binding.btnSearchCaution.text.toString() == resources.getString(R.string.caution)
                        && binding.btnSearchArea2.text.toString() == resources.getString(R.string.area_region)) {
                        isActiveSearch(false)
                    } else {
                        isActiveSearch(true)
                    }
                }
                .setPositiveButton(R.string.agreement) { _, _ ->
                    it.isSelected = true
                    binding.btnSearchTheme.text = themeUtil.itemTheme[searchItem]
                    if (binding.btnSearchTheme.text.toString() == resources.getString(R.string.main_charo_theme)) {
                        it.isSelected = false
                    } else {
                        isActiveSearch(true)
                    }

                    //시 없이 도만 선택했을 경우 비활
                    if(binding.btnSearchArea1.text.toString() != resources.getString(R.string.area_province)
                        && binding.btnSearchArea2.text.toString() == resources.getString(R.string.area_region)) {
                        isActiveSearch(false)
                    }
                }
                .setSingleChoiceItems(themeUtil.itemTheme, searchItem) { _, which ->
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
                .setNeutralButton(R.string.cancel) { _, _ ->
                    binding.btnSearchCaution.text = resources.getString(R.string.caution)
                    searchCautionItem = 0
                    it.isSelected = false

                    //시 없이 도만 선택했을 경우 비활
                    if(binding.btnSearchArea1.text.toString() != resources.getString(R.string.area_province)
                        && binding.btnSearchArea2.text.toString() == resources.getString(R.string.area_region)) {
                        isActiveSearch(false)
                    //모두 빈 값일 경우 비활
                    } else if (binding.btnSearchArea2.text.toString() == resources.getString(R.string.area_region)
                        && binding.btnSearchTheme.text.toString() == resources.getString(R.string.main_charo_theme)) {
                        isActiveSearch(false)
                    } else {
                        isActiveSearch(true)
                    }
                }
                .setPositiveButton(R.string.agreement) { _, _ ->
                    it.isSelected = true
                    binding.btnSearchCaution.text = themeUtil.itemCaution[searchCautionItem]
                    if (binding.btnSearchCaution.text.toString() == resources.getString(R.string.caution)) {
                        it.isSelected = false
                    } else {
                        isActiveSearch(true)
                    }

                    //시 없이 도만 선택했을 경우 비활
                    if(binding.btnSearchArea1.text.toString() != resources.getString(R.string.area_province)
                        && binding.btnSearchArea2.text.toString() == resources.getString(R.string.area_region)) {
                        isActiveSearch(false)
                    }
                }
                .setSingleChoiceItems(
                    themeUtil.itemCaution,
                    searchCautionItem,
                ) { _, which ->
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
            .setNeutralButton(R.string.cancel) { _, _ ->
                binding.btnSearchArea1.text = resources.getString(R.string.area_province)
                binding.btnSearchArea2.text = resources.getString(R.string.area_region)
                binding.btnSearchArea2.isSelected = false
                it.isSelected = false
                preCheckProvince = 0

                if (binding.btnSearchCaution.text.toString() == resources.getString(R.string.caution)
                    && binding.btnSearchTheme.text.toString() == resources.getString(R.string.main_charo_theme)) {
                    isActiveSearch(false)
                } else {
                    isActiveSearch(true)
                }
            }
            .setPositiveButton(R.string.agreement) { _, _ ->
                it.isSelected = true
                binding.btnSearchArea1.text = locationUtil.itemProvince[preCheckProvince]
                searchViewModel.province.value = binding.btnSearchArea1.text.toString()

                if(binding.btnSearchArea1.text.toString() == resources.getString(R.string.no_select)) {
                    isActiveSearch(true)
                } else {
                    selectRegion(binding.btnSearchArea2)
                }
            }
            .setSingleChoiceItems(locationUtil.itemProvince, preCheckProvince) { _, which ->
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
            .setNeutralButton(R.string.cancel) { _, _ ->
                binding.btnSearchArea2.text = resources.getString(R.string.area_region)
                it.isSelected = false
                preCheckedRegion = 0
                searchViewModel.city.value = ""

                isActiveSearch(false)
            }
            .setPositiveButton(R.string.agreement) { _, _ ->
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
                    isActiveSearch(true)
                }
            }
            .setSingleChoiceItems(
                locationUtil.matchRegionToProvince[binding.btnSearchArea1.text]
                    ?: arrayOf("도 단위를 선택해주세요."), preCheckedRegion
            ) { _, which ->
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

    private fun initValue() {
        isActiveSearch(false)
        //지역 도 시
        if (!TextUtils.isEmpty(searchViewModel.province.value)) {
            binding.btnSearchArea1.text = searchViewModel.province.value
            binding.btnSearchArea1.isSelected = true
        }
        if (!TextUtils.isEmpty(searchViewModel.city.value)) {
            binding.btnSearchArea2.text = searchViewModel.city.value
            binding.btnSearchArea2.isSelected = true
            isActiveSearch(true)
        }

        //테마
        if (!TextUtils.isEmpty(searchViewModel.theme.value)) {
            binding.btnSearchTheme.text = searchViewModel.theme.value
            binding.btnSearchTheme.isSelected = true
            isActiveSearch(true)
        }

        //주의사항
        if (!TextUtils.isEmpty(searchViewModel.caution.value)) {
            binding.btnSearchCaution.text = searchViewModel.caution.value
            binding.btnSearchCaution.isSelected = true
            isActiveSearch(true)
        }
    }

}