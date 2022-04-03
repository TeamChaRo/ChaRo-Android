package com.charo.android.presentation.ui.search
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.charo.android.R
import com.charo.android.databinding.FragmentNoSearchBinding
import com.charo.android.presentation.base.BaseFragment


class NoSearchFragment : BaseFragment<FragmentNoSearchBinding>(R.layout.fragment_no_search) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backSearch()

    }


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

}