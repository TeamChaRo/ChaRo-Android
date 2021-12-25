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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_other_charo, container, false)
        charoViewModel.getInitOtherLikeData(Hidden.otherUserEmail)
        charoViewModel.otherInformation.observe(viewLifecycleOwner, {
            binding.otherPageData = charoViewModel
        })
        goFollowView(Hidden.otherUserEmail)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun goFollowView(myPageEmail: String) {
        val intent = Intent(requireActivity(), CharoListActivity::class.java)
        intent.putExtra("myPageEmail", myPageEmail)
        startActivity(intent)
    }
}