package com.charo.android.presentation.ui.follow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.charo.android.R
import com.charo.android.databinding.FragmentFollowingBinding
import com.charo.android.presentation.ui.follow.adapter.FollowAdapter
import com.charo.android.presentation.ui.follow.viewmodel.FollowViewModel
import com.charo.android.presentation.ui.mypage.other.OtherMyPageActivity
import com.charo.android.presentation.util.LoginUtil
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding ?: error("binding not initialized")
    private lateinit var adapter: FollowAdapter
    private val viewModel by sharedViewModel<FollowViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_following, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        refreshRecyclerView()
        observeLiveData()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initRecyclerView() {
        adapter = FollowAdapter({
            val intent = Intent(requireContext(), OtherMyPageActivity::class.java)
            intent.putExtra("userEmail", it.userEmail)
            startActivity(intent)
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

    private fun refreshRecyclerView() {
        binding.srlFollowing.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.blue_main_0f6fff))
        binding.srlFollowing.setOnRefreshListener {
            viewModel.getFollowList()
            binding.srlFollowing.isRefreshing = false
        }
    }

    private fun observeLiveData() {
        viewModel.following.observe(viewLifecycleOwner) {
            adapter.replaceItem(it)
        }
    }
}