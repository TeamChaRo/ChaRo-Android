package com.example.charo_android.presentation.ui.newcharo.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentSavedPostBinding
import com.example.charo_android.presentation.ui.newcharo.adapter.PostAdapter

class SavedPostFragment : Fragment() {
    private var _binding: FragmentSavedPostBinding? = null
    val binding get() = _binding ?: error("binding not initiated")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_saved_post, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinner()
        initRecyclerView()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initSpinner() {
        // spinner 자체 set up
        val filter = resources.getStringArray(R.array.charo_filter)
        val adapter = ArrayAdapter(requireActivity(), R.layout.item_charo_spinner, filter)
        adapter.setDropDownViewResource(R.layout.item_charo_spinner)
        binding.spinnerPostList.adapter = adapter

        // spinner handler
        binding.spinnerPostList.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position == 0) {
                        Log.d("mlog: WrittenPostFragment::spinner handler", "onItemSelected - 0")
                    } else {
                        Log.d("mlog: WrittenPostFragment::spinner handler", "onItemSelected - 1")
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.d("mlog: WrittenPostFragment::spinner handler", "onNothingSelected")
                }
            }
    }

    private fun initRecyclerView() {
        val postAdapter = PostAdapter()
        binding.rvPostList.adapter = postAdapter
    }
}