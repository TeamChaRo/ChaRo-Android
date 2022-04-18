package com.charo.android.presentation.ui.charo.mypage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.charo.android.R
import com.charo.android.databinding.FragmentSaveBinding
import com.charo.android.hidden.Hidden
import com.charo.android.presentation.ui.charo.adapter.CharoAdapter
import com.charo.android.presentation.ui.charo.viewmodel.CharoViewModel
import com.charo.android.presentation.ui.detail.DetailActivity
import timber.log.Timber

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
        charoAdapter = CharoAdapter(Hidden.userId) {
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("postId", it!!.postId)
            intent.putExtra("title", it.title)
            intent.putExtra("date", "${it.year}년 ${it.month}월 ${it.day}일")
            intent.putExtra("imageUrl", it.image)
            intent.putExtra("region", it.region)
            startActivity(intent)
        }
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_save, container, false)
        val root: View = binding.root

        Timber.d("SaveFragment created!")
        binding.recyclerviewMyCharo.adapter = charoAdapter
        setUpSpinner()
        setupSpinnerHandler()
        removeLoading()
        getMoreLikeData()
        getMoreNewData()
        return root
    }

    override fun onResume() {
        super.onResume()
        charoAdapter.itemList.clear()
        saveViewModel.clearPostData()
        setUpSpinner()
        setupSpinnerHandler()
        removeLoading()
        getMoreLikeData()
        getMoreNewData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpSpinner() {
        val filter = resources.getStringArray(R.array.my_page_filter)
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
                                if(charoAdapter.itemList.isEmpty()) {
                                    charoAdapter.itemList.addAll(saveViewModel.savedLikeData.value?.drive!!)
                                    charoAdapter.notifyDataSetChanged()
                                    itemLastSize = charoAdapter.itemList.size
                                }
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
                                if(charoAdapter.itemList.isEmpty()) {
                                    charoAdapter.itemList.addAll(saveViewModel.savedNewData.value?.drive!!)
                                    charoAdapter.notifyDataSetChanged()
                                    itemLastSize = charoAdapter.itemList.size
                                }
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
                    Timber.d("무한스크롤 최하단 도달 완료")
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

    private fun removeLoading() {
        saveViewModel.isServerConnection.observe(viewLifecycleOwner, {
            if (charoAdapter.itemList.isNotEmpty()) {
                charoAdapter.removeLoading()
            }
        })
    }

    private fun getMoreLikeData() {
        saveViewModel.savedMoreLikeData.observe(viewLifecycleOwner, {
            if (saveViewModel.savedLikeData.value?.drive != null && saveViewModel.savedMoreLikeData.value?.drive != null) {
                charoAdapter.itemList.addAll(saveViewModel.savedMoreLikeData.value?.drive!!)
                charoAdapter.notifyItemRangeInserted(
                    itemLastSize,
                    saveViewModel.savedMoreLikeData.value?.drive!!.size
                )
            }
        })
    }

    private fun getMoreNewData() {
        saveViewModel.savedMoreNewData.observe(viewLifecycleOwner, {
            if (saveViewModel.savedNewData.value?.drive != null && saveViewModel.savedMoreLikeData.value?.drive != null) {
                charoAdapter.itemList.addAll(saveViewModel.savedMoreNewData.value?.drive!!)
                charoAdapter.notifyItemRangeInserted(
                    itemLastSize,
                    saveViewModel.savedMoreNewData.value?.drive!!.size
                )
            }
        })
    }
}