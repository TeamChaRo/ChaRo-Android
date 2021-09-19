package com.example.charo_android.presentation.ui.charo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.charo_android.R
import com.example.charo_android.hidden.Hidden
import com.example.charo_android.databinding.FragmentSaveBinding

class SaveFragment : Fragment() {
    private val saveViewModel: CharoViewModel by activityViewModels()
    private var _binding: FragmentSaveBinding? = null
    private val binding get() = _binding!!
    private lateinit var charoAdapter: CharoAdapter
    var itemLastSize = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        charoAdapter = CharoAdapter(Hidden.userId)
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_save, container, false)
        val root: View = binding.root

        Log.d("SaveFragment", "created!")

        binding.recyclerviewMyCharo.adapter = charoAdapter
        setUpSpinner()
        setupSpinnerHandler()

        saveViewModel.isServerConnection.observe(viewLifecycleOwner, {
            if (charoAdapter.itemList.isNotEmpty()) {
                charoAdapter.removeLoading()
            }
        })

        saveViewModel.savedMoreLikeData.observe(viewLifecycleOwner, {
            if (saveViewModel.savedLikeData.value?.drive != null && saveViewModel.savedMoreLikeData.value?.drive != null) {
                charoAdapter.itemList.addAll(saveViewModel.savedMoreLikeData.value?.drive!!)
                charoAdapter.notifyItemRangeInserted(
                    itemLastSize,
                    saveViewModel.savedMoreLikeData.value?.drive!!.size
                )
            }
        })

        saveViewModel.savedMoreNewData.observe(viewLifecycleOwner, {
            if (saveViewModel.savedNewData.value?.drive != null && saveViewModel.savedMoreLikeData.value?.drive != null) {
                charoAdapter.itemList.addAll(saveViewModel.savedMoreNewData.value?.drive!!)
                charoAdapter.notifyItemRangeInserted(
                    itemLastSize,
                    saveViewModel.savedMoreNewData.value?.drive!!.size
                )
            }
        })

        // Inflate the layout for this fragment
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpSpinner() {
        val filter = resources.getStringArray(R.array.charo_filter)
        val adapter = ArrayAdapter(requireActivity(), R.layout.item_charo_spinner, filter)
        adapter.setDropDownViewResource(R.layout.item_charo_spinner)
        binding.spinnerMyCharoFilter.adapter = adapter
    }

    private fun setupSpinnerHandler() {
        binding.spinnerMyCharoFilter.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position == 0) {
                        if (charoAdapter.spinnerPosition != 0) {
                            charoAdapter.itemList.clear()
                            charoAdapter.spinnerPosition = 0
                        }

                        saveViewModel.savedLikeData.observe(viewLifecycleOwner, {
                            if (saveViewModel.savedLikeData.value?.drive != null) {
                                charoAdapter.itemList.addAll(saveViewModel.savedLikeData.value?.drive!!)
                                charoAdapter.notifyDataSetChanged()
                                itemLastSize = charoAdapter.itemList.size
                            }
                        })
                    } else {
                        if (charoAdapter.spinnerPosition == 0) {
                            charoAdapter.itemList.clear()
                            charoAdapter.spinnerPosition = 1
                        }

                        if (saveViewModel.writtenNewData.value == null) {
                            saveViewModel.getInitNewData()
                        }

                        saveViewModel.savedNewData.observe(viewLifecycleOwner, {
                            if (saveViewModel.savedNewData.value?.drive != null) {
                                charoAdapter.itemList.addAll(saveViewModel.savedNewData.value?.drive!!)
                                charoAdapter.notifyDataSetChanged()
                                itemLastSize = charoAdapter.itemList.size
                            }
                        })
                    }
                    infiniteScrolling(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
    }

    private fun infiniteScrolling(spinnerPosition: Int) {
        binding.nscvMyCharo.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (v.getChildAt(v.childCount - 1) != null) {
                if ((scrollY >= (v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight)) &&
                    scrollY > oldScrollY
                ) {
                    Log.d("무한스크롤 최하단 도달", "도달완")
                    if (saveViewModel.isServerConnection.value == false && charoAdapter.itemList.isNotEmpty()) {
                        charoAdapter.addLoading()
                        when (spinnerPosition) {
                            0 -> {
                                saveViewModel.getMoreSavedLikeData()
                            }
                            else -> {
                                saveViewModel.getMoreSavedNewData()
                            }
                        }
                    }
                }
            }
        }))
    }
}