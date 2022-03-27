package com.example.charo_android.presentation.ui.detailpost

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.charo_android.R
import com.example.charo_android.databinding.DialogDetailLikeBinding
import com.example.charo_android.domain.model.detailpost.User
import com.example.charo_android.presentation.ui.detailpost.adapter.DetailPostLikeListAdapter
import com.example.charo_android.presentation.ui.detailpost.viewmodel.DetailPostViewModel
import com.example.charo_android.presentation.ui.mypage.other.OtherMyPageActivity
import com.example.charo_android.presentation.util.LoginUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DetailPostLikeListFragment : BottomSheetDialogFragment() {
    private var _binding: DialogDetailLikeBinding? = null
    private val binding get() = _binding ?: error("binding not initialized")
    private lateinit var adapter: DetailPostLikeListAdapter
    private val viewModel by sharedViewModel<DetailPostViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.dialog_detail_like, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDialog()
        initRecyclerView()
        getDetailPostLikeUserList()
        updateRecyclerView()
        clickClose()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initDialog() {
        if (Build.VERSION.SDK_INT >= 30) {
            binding.clDetailDialog.layoutParams.height =
                requireActivity().windowManager.currentWindowMetrics.bounds.height() - 38
        } else {
            val view = requireActivity().window.decorView
            val insets = WindowInsetsCompat.toWindowInsetsCompat(view.rootWindowInsets, view)
                .getInsets(WindowInsetsCompat.Type.systemBars())
            binding.clDetailDialog.layoutParams.height =
                resources.displayMetrics.heightPixels - insets.bottom - insets.top - 38
        }

        (dialog as? BottomSheetDialog)?.behavior?.apply {
            isFitToContents = false
            expandedOffset = 38
            skipCollapsed = true
            state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }
    }

    private fun initRecyclerView() {
        adapter = DetailPostLikeListAdapter({
            val intent = Intent(requireContext(), OtherMyPageActivity::class.java)
            intent.putExtra("userEmail", it.userEmail)
            startActivity(intent)
        }, {
            if(viewModel.userEmail != "@") {
                viewModel.postFollow(it.userEmail)
            } else {
                LoginUtil.loginPrompt(requireContext())
            }
        })
        binding.rcvDetailDialog.adapter = adapter
    }

    private fun getDetailPostLikeUserList() {
        viewModel.getDetailPostLikeUserListData()
    }

    private fun updateRecyclerView() {
        viewModel.likeUserList.observe(viewLifecycleOwner) {
            adapter.replaceItem(it)
        }
    }

    private fun clickClose() {
        binding.imgDetailDialogClose.setOnClickListener {
            dismiss()
        }
    }
}