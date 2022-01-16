package com.example.charo_android.presentation.ui.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat.getColor
import com.example.charo_android.R
import com.example.charo_android.data.model.request.search.RequestSearchViewData
import com.example.charo_android.databinding.FragmentSearchBinding
import com.example.charo_android.presentation.base.BaseFragment
import com.example.charo_android.presentation.ui.main.MainActivity
import com.example.charo_android.presentation.ui.search.viewmodel.SearchViewModel
import com.example.charo_android.presentation.util.LocationUtil
import com.example.charo_android.presentation.util.SharedInformation
import com.example.charo_android.presentation.util.ThemeUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val themeUtil = ThemeUtil()
    private val locationUtil = LocationUtil()
    private val searchViewModel: SearchViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickSearch()
        goHomeView()
        selectTheme()
        selectCatution()
        selectarea()
        binding.textUserId.text = "복덩이님의"
    }
    private fun nickName(){


    }

    private fun clickSearch() {
        val userEmail = SharedInformation.getEmail(requireActivity())
        binding.imgSearchStart.setOnClickListener {
            searchViewModel.province.value = if (binding.btnSearchArea1.text.toString() == "선택안함") {
                ""
            } else {
                binding.btnSearchArea1.text.toString()
            }
            searchViewModel.city.value =
                if (binding.btnSearchArea2.text.toString() == "선택안함" || binding.btnSearchArea2.text.toString() == "지역") {
                    ""
                } else {
                    binding.btnSearchArea2.text.toString()
                }
            searchViewModel.theme.value = if (binding.btnSearchTheme.text.toString() == "선택안함") {
                ""
            } else {
                binding.btnSearchTheme.text.toString()
            }
            searchViewModel.caution.value =
                if (binding.btnSearchCaution.text.toString() == "선택안함") {
                    ""
                } else {
                    binding.btnSearchCaution.text.toString()
                }
            Log.d("hu", searchViewModel.province.value.toString())
            Log.d("hu", searchViewModel.city.value.toString())
            Log.d("hu", searchViewModel.theme.value.toString())
            Log.d("hu", searchViewModel.caution.value.toString())

            val requestSearchViewData = RequestSearchViewData(
                region = searchViewModel.city.value.toString(),
                theme = themeUtil.themeMap.get(searchViewModel.theme.value.toString()) ?: "",
                warning = themeUtil.cautionMap.get(searchViewModel.caution.value.toString()) ?: "",
                userEmail = userEmail,
            )
            searchViewModel.getSearch(requestSearchViewData)
            searchViewModel.search.observe(viewLifecycleOwner) {
                if (it.count() == 0) {
                    val transaction = activity?.supportFragmentManager?.beginTransaction()
                    transaction?.apply {
                        replace(R.id.fragment_container_search, NoSearchFragment())
                        commit()

                    }
                } else {
                    val transaction = activity?.supportFragmentManager?.beginTransaction()
                    transaction?.apply {
                        replace(
                            R.id.fragment_container_search,
                            ResultSearchFragment()
                        )
                        commit()
                    }
                }
            }

        }
    }


    fun selectTheme() {
        binding.btnSearchTheme.setOnClickListener {
            val searchItem = 0
            MaterialAlertDialogBuilder(requireActivity())
                .setTitle(R.string.main_charo_theme)
                .setNeutralButton("취소") { dialog, which ->
                    binding.btnSearchTheme.setText(resources.getString(R.string.main_charo_theme))
                    it.isSelected = false
                }
                .setPositiveButton("확인") { dialog, which ->
                    if (binding.btnSearchTheme.text.toString() == resources.getString(R.string.main_charo_theme)) {
                        it.isSelected = false
                    }
                    it.isSelected = true
                }
                .setSingleChoiceItems(themeUtil.itemTheme, searchItem) { dialog, which ->
                    binding.apply {
                        btnSearchTheme.setText(themeUtil.itemTheme[which])
                        btnSearchTheme.setTextColor(getColor(requireActivity(),R.color.blue_main))
                        imgSearchStart.setBackgroundResource(R.drawable.ic_search_start_blue)
                        textSearchStart.setTextColor(getColor(requireActivity(),R.color.white))
                        textSearchStart.setPadding(0, 0, 0, 20)
                    }
                }.show()
        }


    }

    fun selectCatution() {
        binding.btnSearchCaution.setOnClickListener {
            val searchCautionItem = 0
            MaterialAlertDialogBuilder(requireActivity())
                .setTitle(R.string.caution)
                .setNeutralButton("취소") { dialog, which ->
                    binding.btnSearchCaution.setText(resources.getString(R.string.caution))
                    it.isSelected = false
                }
                .setPositiveButton("확인") { dialog, which ->
                    if (binding.btnSearchCaution.text.toString() == resources.getString(R.string.caution)) {
                        it.isSelected = false
                    }
                    it.isSelected = true
                }
                .setSingleChoiceItems(
                    themeUtil.itemCaution,
                    searchCautionItem
                ) { dialog, which ->
                    binding.apply {
                        btnSearchCaution.setText(themeUtil.itemCaution[which])
                        btnSearchCaution.setTextColor(getColor(requireActivity(),R.color.blue_main))
                        imgSearchStart.setBackgroundResource(R.drawable.ic_search_start_blue)
                        textSearchStart.setTextColor(getColor(requireActivity(),R.color.white))
                        textSearchStart.setPadding(0, 0, 0, 20)
                    }
                }.show()
        }
    }

    fun selectarea() {
        binding.btnSearchArea1.setOnClickListener {
            val searchCautionItem = 0
            MaterialAlertDialogBuilder(requireActivity())
                .setTitle("지역")
                .setNeutralButton("취소") { dialog, which ->
                    binding.btnSearchArea1.setText("지역")
                    it.isSelected = false
                }
                .setPositiveButton("확인") { dialog, which ->
                    if (binding.btnSearchArea1.text.toString() == "지역") {
                        it.isSelected = false
                    }
                    it.isSelected = true
                }
                .setSingleChoiceItems(
                    locationUtil.itemProvince,
                    searchCautionItem
                ) { dialog, which ->
                    binding.apply {
                        btnSearchArea1.setText(locationUtil.itemProvince[which])
                        btnSearchArea1.setTextColor(getColor(requireActivity(),R.color.blue_main))
                        imgSearchStart.setBackgroundResource(R.drawable.ic_search_start_blue)
                        textSearchStart.setTextColor(getColor(requireActivity(),R.color.white))
                        textSearchStart.setPadding(0, 0, 0, 20)
                    }
                }.show()
        }

        binding.apply {
            btnSearchArea2.setOnClickListener {
                if (btnSearchArea1.text == "특별시") {
                    val searchAreaItem = 0
                    MaterialAlertDialogBuilder(requireActivity())
                        .setTitle("지역")
                        .setNeutralButton("취소") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setPositiveButton("확인") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setSingleChoiceItems(
                            locationUtil.itemSpecial,
                            searchAreaItem
                        ) { dialog, which ->
                            binding.apply {
                                btnSearchArea2.setText(locationUtil.itemSpecial[which])
                                btnSearchArea2.setTextColor(getColor(requireActivity(),R.color.blue_main))
                                imgSearchStart.setBackgroundResource(R.drawable.ic_search_start_blue)
                                textSearchStart.setTextColor(getColor(requireActivity(),R.color.white))
                                textSearchStart.setPadding(0, 0, 0, 20)
                            }
                        }.show()
                } else if (btnSearchArea1.text == "광역시") {
                    val searchAreaItem = 0
                    MaterialAlertDialogBuilder(requireActivity())
                        .setTitle("지역")
                        .setNeutralButton("취소") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setPositiveButton("확인") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setSingleChoiceItems(
                            locationUtil.itemMetroPolitan,
                            searchAreaItem
                        ) { dialog, which ->
                            binding.apply {
                                btnSearchArea2.setText(locationUtil.itemMetroPolitan[which])
                                btnSearchArea2.setTextColor(getColor(requireActivity(),R.color.blue_main))
                                imgSearchStart.setBackgroundResource(R.drawable.ic_search_start_blue)
                                textSearchStart.setTextColor(getColor(requireActivity(),R.color.white))
                                textSearchStart.setPadding(0, 0, 0, 20)
                            }
                        }.show()
                } else if (btnSearchArea1.text == "경기도") {
                    val searchAreaItem = 0
                    MaterialAlertDialogBuilder(requireActivity())
                        .setTitle("지역")
                        .setNeutralButton("취소") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setPositiveButton("확인") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setSingleChoiceItems(
                            locationUtil.itemGyounGi,
                            searchAreaItem
                        ) { dialog, which ->
                            binding.apply {
                                btnSearchArea2.setText(locationUtil.itemGyounGi[which])
                                btnSearchArea2.setTextColor(getColor(requireActivity(),R.color.blue_main))
                                imgSearchStart.setBackgroundResource(R.drawable.ic_search_start_blue)
                                textSearchStart.setTextColor(getColor(requireActivity(),R.color.white))
                                textSearchStart.setPadding(0, 0, 0, 20)
                            }
                        }.show()
                } else if (btnSearchArea1.text == "강원도") {
                    val searchAreaItem = 0
                    MaterialAlertDialogBuilder(requireActivity())
                        .setTitle("지역")
                        .setNeutralButton("취소") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setPositiveButton("확인") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setSingleChoiceItems(
                            locationUtil.itemGangWon,
                            searchAreaItem
                        ) { dialog, which ->
                            binding.apply {
                                btnSearchArea2.setText(locationUtil.itemGangWon[which])
                                btnSearchArea2.setTextColor(getColor(requireActivity(),R.color.blue_main))
                                imgSearchStart.setBackgroundResource(R.drawable.ic_search_start_blue)
                                textSearchStart.setTextColor(getColor(requireActivity(),R.color.white))
                                textSearchStart.setPadding(0, 0, 0, 20)
                            }
                        }.show()
                } else if (btnSearchArea1.text == "충청남도") {
                    val searchAreaItem = 0
                    MaterialAlertDialogBuilder(requireActivity())
                        .setTitle("지역")
                        .setNeutralButton("취소") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setPositiveButton("확인") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setSingleChoiceItems(
                            locationUtil.itemChoongChungNam,
                            searchAreaItem
                        ) { dialog, which ->
                            binding.apply {
                                btnSearchArea2.setText(locationUtil.itemChoongChungNam[which])
                                btnSearchArea2.setTextColor(getColor(requireActivity(),R.color.blue_main))
                                imgSearchStart.setBackgroundResource(R.drawable.ic_search_start_blue)
                                textSearchStart.setTextColor(getColor(requireActivity(),R.color.white))
                                textSearchStart.setPadding(0, 0, 0, 20)
                            }
                        }.show()
                } else if (btnSearchArea1.text == "충청북도") {
                    val searchAreaItem = 0
                    MaterialAlertDialogBuilder(requireActivity())
                        .setTitle("지역")
                        .setNeutralButton("취소") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setPositiveButton("확인") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setSingleChoiceItems(
                            locationUtil.itemChoongChungBuk,
                            searchAreaItem
                        ) { dialog, which ->
                            binding.apply {
                                btnSearchArea2.setText(locationUtil.itemChoongChungBuk[which])
                                btnSearchArea2.setTextColor(getColor(requireActivity(),R.color.blue_main))
                                imgSearchStart.setBackgroundResource(R.drawable.ic_search_start_blue)
                                textSearchStart.setTextColor(getColor(requireActivity(),R.color.white))
                                textSearchStart.setPadding(0, 0, 0, 20)

                            }
                        }.show()
                } else if (btnSearchArea1.text == "경상북도") {
                    val searchAreaItem = 0
                    MaterialAlertDialogBuilder(requireActivity())
                        .setTitle("지역")
                        .setNeutralButton("취소") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setPositiveButton("확인") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setSingleChoiceItems(
                            locationUtil.itemGyungSangBuk,
                            searchAreaItem
                        ) { dialog, which ->
                            binding.apply {
                                btnSearchArea2.setText(locationUtil.itemGyungSangBuk[which])
                                btnSearchArea2.setTextColor(getColor(requireActivity(),R.color.blue_main))
                                imgSearchStart.setBackgroundResource(R.drawable.ic_search_start_blue)
                                textSearchStart.setTextColor(getColor(requireActivity(),R.color.white))
                                textSearchStart.setPadding(0, 0, 0, 20)
                            }
                        }.show()
                } else if (btnSearchArea1.text == "경상남도") {
                    val searchAreaItem = 0
                    MaterialAlertDialogBuilder(requireActivity())
                        .setTitle("지역")
                        .setNeutralButton("취소") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setPositiveButton("확인") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setSingleChoiceItems(
                            locationUtil.itemGyungSanNam,
                            searchAreaItem
                        ) { dialog, which ->
                            binding.apply {
                                btnSearchArea2.setText(locationUtil.itemGyungSanNam[which])
                                btnSearchArea2.setTextColor(getColor(requireActivity(),R.color.blue_main))
                                imgSearchStart.setBackgroundResource(R.drawable.ic_search_start_blue)
                                textSearchStart.setTextColor(getColor(requireActivity(),R.color.white))
                                textSearchStart.setPadding(0, 0, 0, 20)
                            }
                        }.show()
                } else if (btnSearchArea1.text == "전라북도") {
                    val searchAreaItem = 0
                    MaterialAlertDialogBuilder(requireActivity())
                        .setTitle("지역")
                        .setNeutralButton("취소") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setPositiveButton("확인") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setSingleChoiceItems(
                            locationUtil.itemJungLaBuk,
                            searchAreaItem
                        ) { dialog, which ->
                            binding.apply {
                                btnSearchArea2.setText(locationUtil.itemJungLaBuk[which])
                                btnSearchArea2.setTextColor(getColor(requireActivity(),R.color.blue_main))
                                imgSearchStart.setBackgroundResource(R.drawable.ic_search_start_blue)
                                textSearchStart.setTextColor(getColor(requireActivity(),R.color.white))
                                textSearchStart.setPadding(0, 0, 0, 20)
                            }
                        }.show()
                } else if (btnSearchArea1.text == "전라남도") {
                    val searchAreaItem = 0
                    MaterialAlertDialogBuilder(requireActivity())
                        .setTitle("지역")
                        .setNeutralButton("취소") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setPositiveButton("확인") { dialog, which ->
                            if (binding.btnSearchArea2.text.toString() == "지역") {
                                it.isSelected = false
                            }
                            it.isSelected = true
                        }
                        .setSingleChoiceItems(
                            locationUtil.itemJungLaNam,
                            searchAreaItem
                        ) { dialog, which ->
                            binding.apply {
                                btnSearchArea2.setText(locationUtil.itemJungLaNam[which])
                                btnSearchArea2.setTextColor(getColor(requireActivity(),R.color.blue_main))
                                imgSearchStart.setBackgroundResource(R.drawable.ic_search_start_blue)
                                textSearchStart.setTextColor(getColor(requireActivity(),R.color.white))
                                textSearchStart.setPadding(0, 0, 0, 20)
                            }
                        }.show()
                }
            }
        }


    }



    fun goHomeView() {
        binding.imgSearchBack.setOnClickListener {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
        }

    }


}