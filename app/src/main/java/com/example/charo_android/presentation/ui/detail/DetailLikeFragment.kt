package com.example.charo_android.presentation.ui.detail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.charo_android.data.model.detail.Data
import com.example.charo_android.databinding.DialogDetailLikeBinding
import com.example.charo_android.presentation.ui.main.MainActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DetailLikeFragment : BottomSheetDialogFragment() {
    private var _binding: DialogDetailLikeBinding? = null
    private val binding get() = _binding!!
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
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDialog()
        initRecyclerView()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun initDialog() {
        binding.clDetailDialog.layoutParams.height =
            requireActivity().windowManager.currentWindowMetrics.bounds.height()
        (dialog as BottomSheetDialog).behavior.apply {
            isFitToContents = false
            state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }
    }

    private fun initRecyclerView() {
        adapter.itemList.addAll(
            listOf(
                Data("", true, "한승현", "email"),
                Data("", true, "한진희", "email"),
                Data("", true, "곽호택", "email"),
                Data("", false, "장혜령", "email"),
                Data("", false, "박익범", "email"),
                Data("", false, "이지원", "email"),
                Data("", false, "최인정", "email"),
                Data("", false, "오예원", "email"),
                Data("", false, "황지은", "email"),
                Data("", false, "배희진", "email"),
                Data("", false, "고다빈", "email"),
                Data("", false, "이성현", "email"),
                Data("", false, "고수현", "email"),
            )
        )
        binding.rcvDetailDialog.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}