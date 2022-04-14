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
import com.charo.android.databinding.FragmentMyCharoBinding
import com.charo.android.hidden.Hidden
import com.charo.android.presentation.ui.charo.adapter.CharoAdapter
import com.charo.android.presentation.ui.charo.viewmodel.CharoViewModel
import com.charo.android.presentation.ui.detail.DetailActivity
import timber.log.Timber

class MyCharoFragment : Fragment() {
    private val myCharoViewModel: CharoViewModel by activityViewModels()
    private var _binding: FragmentMyCharoBinding? = null
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
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_charo, container, false)
        val root: View = binding.root

        Timber.d("MyCharoFragment created!")

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
        myCharoViewModel.clearPostData()
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

                        if (myCharoViewModel.writtenLikeData.value == null) {
                            myCharoViewModel.getInitLikeData()
                        }

                        myCharoViewModel.writtenLikeData.observe(viewLifecycleOwner, {
                            if (myCharoViewModel.writtenLikeData.value?.drive != null) {
                                if(charoAdapter.itemList.isEmpty()) {
                                    charoAdapter.itemList.addAll(myCharoViewModel.writtenLikeData.value?.drive!!)
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

                        if (myCharoViewModel.writtenNewData.value == null) {
                            myCharoViewModel.getInitNewData()
                        }

                        myCharoViewModel.writtenNewData.observe(viewLifecycleOwner, {
                            if (myCharoViewModel.writtenNewData.value?.drive != null) {
                                if(charoAdapter.itemList.isEmpty()) {
                                    charoAdapter.itemList.addAll(myCharoViewModel.writtenNewData.value?.drive!!)
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
                    if (myCharoViewModel.isServerConnection.value == false && charoAdapter.itemList.isNotEmpty()) {
                        charoAdapter.addLoading()
                        when (spinnerPosition) {
                            0 -> {
                                myCharoViewModel.getMoreWrittenLikeData()
                            }
                            else -> {
                                myCharoViewModel.getMoreWrittenNewData()
                            }
                        }
                        charoAdapter.removeLoading()
                    }
                }
            }
        }))
    }

    private fun removeLoading() {
        myCharoViewModel.isServerConnection.observe(viewLifecycleOwner, {
            if (charoAdapter.itemList.isNotEmpty()) {
                charoAdapter.removeLoading()
            }
        })
    }

    private fun getMoreLikeData() {
        myCharoViewModel.writtenMoreLikeData.observe(viewLifecycleOwner, {
            if (myCharoViewModel.writtenLikeData.value?.drive != null && myCharoViewModel.writtenMoreLikeData.value?.drive != null) {
                charoAdapter.itemList.addAll(myCharoViewModel.writtenMoreLikeData.value?.drive!!)
                charoAdapter.notifyItemRangeInserted(
                    itemLastSize,
                    myCharoViewModel.writtenMoreLikeData.value?.drive!!.size
                )
            }
        })
    }

    private fun getMoreNewData() {
        myCharoViewModel.writtenMoreNewData.observe(viewLifecycleOwner, {
            if (myCharoViewModel.writtenNewData.value?.drive != null && myCharoViewModel.writtenMoreNewData.value?.drive != null) {
                charoAdapter.itemList.addAll(myCharoViewModel.writtenMoreNewData.value?.drive!!)
                charoAdapter.notifyItemRangeInserted(
                    itemLastSize,
                    myCharoViewModel.writtenMoreNewData.value?.drive!!.size
                )
            }
        })
    }
}