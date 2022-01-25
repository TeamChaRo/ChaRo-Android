package com.example.charo_android.presentation.ui.newcharo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentProfileBinding
import com.example.charo_android.presentation.ui.newcharo.mine.MyBottomFragment
import com.example.charo_android.presentation.ui.newcharo.mine.MyTopFragment

// 마이페이지 프래그먼트(본체)
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    val binding get() = _binding ?: error("binding not initiated")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_profile, container, false)
        initFragmentContainerView()
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initFragmentContainerView() {
        val transaction = childFragmentManager.beginTransaction()

        // TODO: 분기처리 필요
        // 내 마이페이지, 다른 유저의 마이페이지, 비로그인 유저의 마이페이지 3가지로 분기처리
        // TEST: 내 마이페이지로 일단 처리, 추후 분기처리할 것
        transaction.add(R.id.fcv_top, MyTopFragment())
            .add(R.id.fcv_bottom, MyBottomFragment())
            .commit()
    }
}