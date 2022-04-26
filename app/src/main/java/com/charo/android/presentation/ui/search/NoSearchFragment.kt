package com.charo.android.presentation.ui.search
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.charo.android.R
import com.charo.android.databinding.FragmentNoSearchBinding
import com.charo.android.presentation.base.BaseFragment
import com.charo.android.presentation.ui.main.MainActivity
import com.charo.android.presentation.util.SharedInformation


class NoSearchFragment : BaseFragment<FragmentNoSearchBinding>(R.layout.fragment_no_search) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backSearch()
        cancelSearch()
        writeDriveCourse()
    }

    //검색으로 돌아가기
    private fun backSearch(){
        binding.imgBackHome.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.apply {
                replace(
                    R.id.fragment_container_search,
                    SearchFragment()
                )
                commit()
            }
        }
    }

    //메인으로 돌아가기
    private fun cancelSearch(){
        binding.imgCancelSearch.setOnClickListener {
            requireActivity().finish()
        }
    }

    //드라이브 코스 작성하기
    private fun writeDriveCourse(){
        binding.imgNoSearchClick.setOnClickListener {
            SharedInformation.searchWrite = 1
            requireActivity().finish()
        }

    }

}