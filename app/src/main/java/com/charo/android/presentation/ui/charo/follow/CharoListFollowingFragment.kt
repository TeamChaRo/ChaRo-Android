package com.charo.android.presentation.ui.charo.follow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.charo.android.databinding.FragmentCharoListFollowingBinding
import com.charo.android.hidden.Hidden
import com.charo.android.presentation.ui.charo.adapter.CharoListFollowAdapter
import com.charo.android.presentation.ui.charo.viewmodel.CharoViewModel
import com.charo.android.presentation.ui.main.MainActivity
import timber.log.Timber

class CharoListFollowingFragment : Fragment() {
    private var _binding: FragmentCharoListFollowingBinding? = null
    private val binding get() = _binding!!
    private val charoViewModel: CharoViewModel by activityViewModels()
    private val followAdapter = CharoListFollowAdapter() {
        val intent = Intent(context, MainActivity::class.java)
        val userEmail: String = it.userEmail
        val nickname: String = it.nickname
        val isMyPage: Boolean
        when(it.userEmail) {
            Hidden.userId -> {
                isMyPage = true
                intent.putExtra("nickname", nickname)
            }
            else -> {
                isMyPage = false
                intent.putExtra("otherUserEmail", userEmail)
                intent.putExtra("otherUserNickname", nickname)
            }
        }
        intent.putExtra("isMyPage", isMyPage)
        intent.putExtra("isFromOtherPage", true)
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharoListFollowingBinding.inflate(layoutInflater, container, false)
        initRecyclerView()
        charoViewModel.followData.observe(viewLifecycleOwner, {
            followAdapter.itemList.addAll(it.following)
            Timber.d("itemList.size ${followAdapter.itemList.size.toString()}")
            followAdapter.notifyDataSetChanged()
        })
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initRecyclerView() {
        with(binding) {
            recyclerViewCharoListFollowing.adapter = followAdapter
            followAdapter.notifyDataSetChanged()
        }
    }
}