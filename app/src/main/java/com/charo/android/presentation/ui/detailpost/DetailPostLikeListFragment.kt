package com.charo.android.presentation.ui.detailpost

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.charo.android.R
import com.charo.android.databinding.DialogDetailLikeBinding
import com.charo.android.presentation.ui.detailpost.adapter.DetailPostLikeListAdapter
import com.charo.android.presentation.ui.mypage.other.OtherMyPageActivity
import com.charo.android.presentation.ui.write.WriteShareActivity
import com.charo.android.presentation.ui.write.WriteSharedViewModel
import com.charo.android.presentation.util.LoginUtil
import com.charo.android.presentation.util.SharedInformation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DetailPostLikeListFragment : BottomSheetDialogFragment() {
    private var _binding: DialogDetailLikeBinding? = null
    private val binding get() = _binding ?: error("binding not initialized")
    private lateinit var adapter: DetailPostLikeListAdapter

    // TODO: Old ViewModel
//    private val viewModel by sharedViewModel<DetailPostViewModel>()
    // TODO: New ViewModel
    private val viewModel by sharedViewModel<WriteSharedViewModel>()

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
        adapter = DetailPostLikeListAdapter(
            SharedInformation.getEmail(requireContext()), {
                val intent = Intent(requireContext(), OtherMyPageActivity::class.java).apply {
                    putExtra("userEmail", it.userEmail)
                    putExtra("from", "WriteShareActivity")
                }
                (requireActivity() as WriteShareActivity).followResultLauncher.launch(intent)
            }, {
                if (viewModel.userEmail != "@") {
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