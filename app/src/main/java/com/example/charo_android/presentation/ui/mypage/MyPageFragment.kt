package com.example.charo_android.presentation.ui.mypage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentMyPageBinding
import com.example.charo_android.presentation.ui.mypage.mine.MyBottomFragment
import com.example.charo_android.presentation.ui.mypage.mine.MyTopFragment
import com.example.charo_android.presentation.ui.mypage.viewmodel.MyPageViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

// 마이페이지 프래그먼트(본체)
class MyPageFragment : Fragment() {
    private var _binding: FragmentMyPageBinding? = null
    val binding get() = _binding ?: error("binding not initiated")
//    val viewModel: MyPageViewModel by activityViewModels()
    val viewModel: MyPageViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_my_page, container, false)
        initFragmentContainerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 내 마이페이지 보는 경우
        viewModel.getUserInformation()
        viewModel.userInfo.observe(viewLifecycleOwner) {
            Log.d("mlog: MyPageFragment::onViewCreated", it.nickname)
        }
//        viewModel.getLikePost()
//        viewModel.getNewPost()
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