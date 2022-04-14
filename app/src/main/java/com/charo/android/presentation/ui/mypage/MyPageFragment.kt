package com.charo.android.presentation.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.charo.android.R
import com.charo.android.data.model.mypage.UserInformation
import com.charo.android.databinding.FragmentMyPageBinding
import com.charo.android.presentation.ui.mypage.guest.GuestBottomFragment
import com.charo.android.presentation.ui.mypage.guest.GuestTopFragment
import com.charo.android.presentation.ui.mypage.my.MyBottomFragment
import com.charo.android.presentation.ui.mypage.my.MyTopFragment
import com.charo.android.presentation.ui.mypage.viewmodel.MyPageViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

// 마이페이지 프래그먼트(본체)
class MyPageFragment : Fragment() {
    private var _binding: FragmentMyPageBinding? = null
    val binding get() = _binding ?: error("binding not initiated")
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
        if(viewModel.userEmail != "@") {
            // 내 마이페이지 보는 경우
            viewModel.getLikePost()
            viewModel.getNewPost()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initFragmentContainerView() {
        val transaction = childFragmentManager.beginTransaction()

        if(viewModel.userEmail == "@") {
            transaction.add(R.id.fcv_top, GuestTopFragment())
                .add(R.id.fcv_bottom, GuestBottomFragment())
                .commit()
        } else {
            transaction.add(R.id.fcv_top, MyTopFragment())
                .add(R.id.fcv_bottom, MyBottomFragment())
                .commit()
        }
    }

    class MyObserver: Observer<UserInformation> {
        override fun onChanged(t: UserInformation?) {
            Timber.d("mlog: ㅎㅇㅎㅇ ${t?.nickname.toString()}")
        }
    }
}