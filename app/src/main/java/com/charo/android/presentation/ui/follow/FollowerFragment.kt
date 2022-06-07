package com.charo.android.presentation.ui.follow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.charo.android.R
import com.charo.android.databinding.FragmentFollowerBinding
import com.charo.android.presentation.ui.follow.adapter.FollowAdapter
import com.charo.android.presentation.ui.follow.viewmodel.FollowViewModel
import com.charo.android.presentation.ui.mypage.other.OtherMyPageActivity
import com.charo.android.presentation.util.LoginUtil
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FollowerFragment : Fragment() {
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding ?: error("binding not initialized")
    private lateinit var adapter: FollowAdapter
    private val viewModel by sharedViewModel<FollowViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_follower, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeLiveData()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initRecyclerView() {
        adapter = FollowAdapter({
            val intent = Intent(requireContext(), OtherMyPageActivity::class.java).apply {
                putExtra("userEmail", it.userEmail)
                putExtra("from", "FollowActivity")
            }

            (requireActivity() as FollowActivity).followResultLauncher.launch(intent)
        }, {
            if (viewModel.userEmail != "@") {
                viewModel.postFollow(it.userEmail)
            } else {
                LoginUtil.loginPrompt(requireContext())
            }
        }, viewModel.userEmail
        )
        binding.rv.adapter = adapter
    }

    private fun observeLiveData() {
        viewModel.follower.observe(viewLifecycleOwner) {
            adapter.replaceItem(it)
        }
    }
}