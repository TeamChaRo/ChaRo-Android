package com.example.charo_android.presentation.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)

        val postId = (activity as DetailActivity).postId
        val title = (activity as DetailActivity).title
        val date = (activity as DetailActivity).date
        Log.d("postId", postId.toString())
        Log.d("title", title)
        Log.d("date", date)

        return binding.root
    }
}