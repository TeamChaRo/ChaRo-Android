package com.charo.android.presentation.ui.charo.otherpage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.charo.android.R
import com.charo.android.databinding.FragmentOtherCharoBinding
import com.charo.android.presentation.ui.charo.follow.CharoListActivity
import com.charo.android.presentation.ui.charo.viewmodel.CharoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OtherCharoFragment : Fragment() {
    private val charoViewModel: CharoViewModel by viewModel()
    private var _binding: FragmentOtherCharoBinding? = null
    private val binding get() = _binding!!
    private lateinit var otherUserEmail: String
    private lateinit var otherUserNickname: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_other_charo, container, false)
        otherUserEmail = arguments?.getString("userId").toString()
        otherUserNickname = arguments?.getString("nickName").toString()
        getUserInfo()
        goFollowView(otherUserEmail, otherUserNickname)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun goFollowView(myPageEmail: String, nickname: String) {
        binding.clCharoFollow.setOnClickListener {
            val intent = Intent(requireActivity(), CharoListActivity::class.java)
            intent.putExtra("myPageEmail", myPageEmail)
            intent.putExtra("nickname", nickname)
            startActivity(intent)
        }
    }

    private fun getUserInfo() {
        charoViewModel.getInitOtherLikeData(otherUserEmail)
        charoViewModel.otherInformation.observe(viewLifecycleOwner) {

        }
    }
}