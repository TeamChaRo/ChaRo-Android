package com.example.charo_android.presentation.ui.more

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.charo_android.R
import com.example.charo_android.data.api.ApiService
import com.example.charo_android.data.model.response.more.ResponseMoreViewData
import com.example.charo_android.databinding.FragmentMoreThemeContentViewBinding
import com.example.charo_android.presentation.ui.more.adapter.MoreThemeContentViewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MoreThemeContentViewFragment(userId :String, identity :String, value :String
    ) : Fragment() {
    private var _binding: FragmentMoreThemeContentViewBinding? = null
    private val binding get() = _binding!!
    private val moreThemeContentViewAdapter = MoreThemeContentViewAdapter(userId)

    val userId = userId
    val identity = identity
    val value = value

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoreThemeContentViewBinding.inflate(inflater, container, false)
        val root: View = binding.root


        initSpinner()


        return root
    }




    private fun initSpinner(){
        val adapter = ArrayAdapter.createFromResource(requireContext(), R.array.search_spinner, R.layout.custom_spinner_item)
        binding.spinnerMoreTheme.adapter = adapter
    }












    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}