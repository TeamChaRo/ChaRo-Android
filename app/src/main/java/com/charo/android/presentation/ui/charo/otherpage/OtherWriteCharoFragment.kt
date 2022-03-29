package com.charo.android.presentation.ui.charo.otherpage

import android.annotation.SuppressLint
import android.content.Intent
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
import com.example.charo_android.databinding.FragmentOtherWriteCharoBinding
import com.example.charo_android.hidden.Hidden
import com.example.charo_android.presentation.ui.charo.viewmodel.CharoViewModel
import com.example.charo_android.presentation.ui.charo.adapter.CharoAdapter
import com.example.charo_android.presentation.ui.detail.DetailActivity

class OtherWriteCharoFragment : Fragment() {
    private var _binding: FragmentOtherWriteCharoBinding? = null
    private val binding get() = _binding!!
    private val charoViewModel: CharoViewModel by activityViewModels()
    private lateinit var charoAdapter: CharoAdapter
    var itemLastSize = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_other_write_charo, container, false)

        charoAdapter = CharoAdapter(Hidden.userId) {
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("postId", it!!.postId)
            intent.putExtra("title", it.title)
            intent.putExtra("date", "${it.year}년 ${it.month}월 ${it.day}일")
            intent.putExtra("imageUrl", it.image)
            intent.putExtra("region", it.region)
            startActivity(intent)
        }

        binding.recyclerviewOtherCharo.adapter = charoAdapter
        setUpSpinner()
        setupSpinnerHandler()

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setUpSpinner() {
        val filter = resources.getStringArray(R.array.charo_filter)
        val adapter = ArrayAdapter(requireActivity(), R.layout.item_charo_spinner, filter)
        adapter.setDropDownViewResource(R.layout.item_charo_spinner)
        binding.spinnerOtherCharoFilter.adapter = adapter
    }

    private fun setupSpinnerHandler() {
        binding.spinnerOtherCharoFilter.onItemSelectedListener =
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

                        charoViewModel.otherWrittenLikeData.observe(viewLifecycleOwner, {
                            if(charoViewModel.otherWrittenLikeData.value?.drive != null) {
                                charoAdapter.itemList.addAll(charoViewModel.otherWrittenLikeData.value?.drive!!)
                                charoAdapter.notifyDataSetChanged()
                                itemLastSize = charoAdapter.itemList.size
                            }
                        })
                    } else {
                        if (charoAdapter.spinnerPosition == 0) {
                            charoAdapter.itemList.clear()
                            charoAdapter.spinnerPosition = 1
                        }

                        if (charoViewModel.otherWrittenNewData.value == null) {
                            charoViewModel.getInitOtherNewData(Hidden.otherUserEmail)
                        }

                        charoViewModel.otherWrittenNewData.observe(viewLifecycleOwner, {
                            if (charoViewModel.otherWrittenNewData.value?.drive != null) {
                                charoAdapter.itemList.addAll(charoViewModel.otherWrittenNewData.value?.drive!!)
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
        binding.nscvOtherCharo.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (v.getChildAt(v.childCount - 1) != null) {
                if ((scrollY >= (v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight)) &&
                    scrollY > oldScrollY
                ) {
                    Log.d("무한스크롤 최하단 도달", "도달완")
                    if (charoViewModel.isServerConnection.value == false && charoAdapter.itemList.isNotEmpty()) {
                        charoAdapter.addLoading()
                        when (spinnerPosition) {
                            0 -> {
                                charoViewModel.getMoreOtherWrittenLikeData(Hidden.otherUserEmail)
                            }
                            else -> {
                                charoViewModel.getMoreOtherWrittenNewData(Hidden.otherUserEmail)
                            }
                        }
                        charoAdapter.removeLoading()
                    }
                }
            }
        }))
    }
}