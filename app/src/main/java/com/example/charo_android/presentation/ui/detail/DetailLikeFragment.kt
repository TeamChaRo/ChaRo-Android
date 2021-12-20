package com.example.charo_android.presentation.ui.detail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import com.example.charo_android.databinding.DialogDetailLikeBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DetailLikeFragment : BottomSheetDialogFragment() {
    private var _binding: DialogDetailLikeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by activityViewModels()
    private val adapter by lazy {
        DetailLikeAdapter() {
//            val intent = Intent(context, MainActivity::class.java)
//            startActivity(intent)
            Toast.makeText(requireContext(), "click", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogDetailLikeBinding.inflate(layoutInflater, container, false)

        if (viewModel.userData.value == null) {
            Log.d("viewModel.userData", "execute")
            viewModel.getLikes(viewModel.postId.value!!)
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDialog()
        initRecyclerView()
        viewModel.userData.observe(viewLifecycleOwner, {
            addRecyclerViewItem()
        })
        clickClose()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun initDialog() {
        binding.clDetailDialog.layoutParams.height =
            (requireActivity().windowManager.currentWindowMetrics.bounds.height() * 0.9).toInt()
        (dialog as BottomSheetDialog).behavior.apply {
            isFitToContents = false
            state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }
    }

    private fun initRecyclerView() {
        binding.rcvDetailDialog.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun addRecyclerViewItem() {
        if (viewModel.userData.value != null && adapter.itemList.isEmpty()) {
            adapter.itemList.addAll(viewModel.userData.value!!)
        }
        binding.rcvDetailDialog.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun clickClose() {
        binding.imgDetailDialogClose.setOnClickListener {
            dismiss()
        }
    }

    private fun changeTopColor() {
//        binding.nscvMyCharo.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//            if (v.getChildAt(v.childCount - 1) != null) {
//                if ((scrollY >= (v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight)) &&
//                    scrollY > oldScrollY
//                ) {
//                    Log.d("무한스크롤 최하단 도달", "도달완")
//                    if (myCharoViewModel.isServerConnection.value == false && charoAdapter.itemList.isNotEmpty()) {
//                        charoAdapter.addLoading()
//                        when (spinnerPosition) {
//                            0 -> {
//                                myCharoViewModel.getMoreWrittenLikeData()
//                            }
//                            else -> {
//                                myCharoViewModel.getMoreWrittenNewData()
//                            }
//                        }
//                    }
//                }
//            }
//        }))
        binding.nscvDetailLike.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener {v, scrollX, scrollY, oldScrollX, oldScrollY ->
            
        }))
    }
}