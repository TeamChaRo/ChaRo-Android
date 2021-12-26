package com.example.charo_android.presentation.ui.charo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentOtherCharoBinding
import com.example.charo_android.hidden.Hidden

class OtherCharoFragment : Fragment() {
    private val charoViewModel: CharoViewModel by activityViewModels()
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

        charoViewModel.getInitOtherLikeData(otherUserEmail)
        charoViewModel.otherInformation.observe(viewLifecycleOwner, {
            binding.otherPageData = charoViewModel
        })
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
}