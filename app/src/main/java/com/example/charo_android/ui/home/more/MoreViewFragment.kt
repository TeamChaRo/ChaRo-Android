package com.example.charo_android.ui.home.more

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.charo_android.R
import com.example.charo_android.data.LocalMoreViewDataSource
import com.example.charo_android.databinding.FragmentMoreViewBinding
import com.example.charo_android.ui.home.HomeFragment


class MoreViewFragment : Fragment() {
    private var _binding: FragmentMoreViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var moreViewAdapter : MoreViewAdapter
    private var homeFragment = HomeFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoreViewBinding.inflate(inflater, container, false)
        moreViewAdapter = MoreViewAdapter()

        val root: View = binding.root

        initMoreView()
        initSpinner()
        clickBackButton()

        return binding.root
    }


    private fun initMoreView() {
        binding.recyclerviewMoreView.adapter = moreViewAdapter
        moreViewAdapter.moreData.addAll(LocalMoreViewDataSource().fetchData())
        moreViewAdapter.notifyDataSetChanged()
        Log.d("1111", "success")
    }

    private fun initSpinner(){
        val adapter = ArrayAdapter.createFromResource(requireContext(), R.array.search_spinner, android.R.layout.simple_spinner_item)
        binding.spinnerMoreView.adapter = adapter
    }

    private fun clickSpinner(){
        binding.spinnerMoreView.onItemSelectedListener = object: AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position){
                    0 -> {
                        binding.textMoreViewSpinner.text = "인기순"
                    }

                    1 -> {
                        binding.textMoreViewSpinner.text = "최신순"
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }


        }

    }

    private fun clickBackButton(){
        binding.imgBackHome.setOnClickListener {
            val fragmentManager = activity?.supportFragmentManager
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_main, homeFragment)
                ?.commit()


        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
