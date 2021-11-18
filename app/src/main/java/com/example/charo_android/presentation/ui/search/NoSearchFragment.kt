package com.example.charo_android.presentation.ui.search
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentNoSearchBinding
import com.example.charo_android.presentation.base.BaseFragment


class NoSearchFragment : BaseFragment<FragmentNoSearchBinding>(R.layout.fragment_no_search) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backSearch()

    }


    private fun backSearch(){
        binding.imgBackHome.setOnClickListener {
            val intent = Intent(requireActivity(), SearchActivity::class.java)
            startActivity(intent)
        }


    }

}