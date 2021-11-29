package com.example.charo_android.presentation.ui.more

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.setFragmentResultListener
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentMoreViewBinding
import com.example.charo_android.hidden.Hidden
import com.example.charo_android.presentation.base.BaseFragment
import com.example.charo_android.presentation.ui.home.HomeFragment
import com.example.charo_android.presentation.ui.home.viewmodel.HomeViewModel
import com.example.charo_android.presentation.ui.main.SharedViewModel
import com.example.charo_android.presentation.ui.more.adapter.MoreViewAdapter
import com.example.charo_android.presentation.ui.more.viewmodel.MoreViewViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MoreViewFragment : BaseFragment<FragmentMoreViewBinding>(R.layout.fragment_more_view) {

    private val sharedViewModel: SharedViewModel by sharedViewModel()
    private val moreViewModel: MoreViewViewModel by viewModel()
    private var homeFragment = HomeFragment()
    private lateinit var moreViewAdapter: MoreViewAdapter
    private lateinit var userId: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userId = arguments?.getString("userId").toString()
        moreViewAdapter = MoreViewAdapter(userId)
        Log.d("userIdeas", userId)

        initSpinner()
        clickBackButton(userId)
        clickSpinner()
    }


    fun moreViewLoadData() {
        if (sharedViewModel.num.value == 0) {
            moreViewModel.getMoreView("and@naver.com", "0", "")
            moreViewAdapter = MoreViewAdapter(Hidden.userId)
            binding.recyclerviewMoreView.adapter = moreViewAdapter
            moreViewModel.drive.observe(viewLifecycleOwner) {
                moreViewAdapter.setHomeTrendDrive(it)
            }

        } else if(sharedViewModel.num.value == 2) {
            moreViewModel.getMoreView(Hidden.userId, "2", "busan")
            sharedViewModel.getHomeTitle(Hidden.userId)
            moreViewAdapter = MoreViewAdapter(Hidden.userId)
            binding.textToolbarTitle.text = sharedViewModel.localThemeTitle.value
            binding.recyclerviewMoreView.adapter = moreViewAdapter
            moreViewModel.drive.observe(viewLifecycleOwner) {
                moreViewAdapter.setHomeTrendDrive(it)
            }
        } else{
            moreViewModel.getMoreView(Hidden.userId, "3", "")
            sharedViewModel.getHomeTitle(Hidden.userId)
            moreViewAdapter = MoreViewAdapter(Hidden.userId)
            binding.textToolbarTitle.text = sharedViewModel.customThemeTitle.value
            binding.recyclerviewMoreView.adapter = moreViewAdapter
            moreViewModel.drive.observe(viewLifecycleOwner) {
                moreViewAdapter.setHomeTrendDrive(it)
            }
        }
    }


    fun moreNewViewData() {

        if (sharedViewModel.num.value == 0) {
            moreViewModel.getMoreNewView("and@naver.com", "0", "")
            moreViewAdapter = MoreViewAdapter(Hidden.userId)
            binding.recyclerviewMoreView.adapter = moreViewAdapter
            moreViewModel.newDrive.observe(viewLifecycleOwner) {
                moreViewAdapter.setHomeTrendDrive(it)
            }

        } else if (sharedViewModel.num.value == 2) {
            moreViewModel.getMoreNewView(Hidden.userId, "2", "busan")
            moreViewAdapter = MoreViewAdapter(Hidden.userId)
            binding.recyclerviewMoreView.adapter = moreViewAdapter
            moreViewModel.newDrive.observe(viewLifecycleOwner) {
                moreViewAdapter.setHomeTrendDrive(it)
            }
        }  else {
            moreViewModel.getMoreNewView(Hidden.userId, "3", "")
            moreViewAdapter = MoreViewAdapter(Hidden.userId)
            binding.recyclerviewMoreView.adapter = moreViewAdapter
            moreViewModel.newDrive.observe(viewLifecycleOwner){
                moreViewAdapter.setHomeTrendDrive(it)
            }

        }
    }


    private fun initSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.search_spinner,
            R.layout.custom_spinner_item
        )
        binding.spinnerMoreView.adapter = adapter
    }


    private fun clickSpinner() {
        binding.spinnerMoreView.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position == 0) {
                        moreViewLoadData()

                    } else {

                        moreNewViewData()
                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }

    }


    private fun clickBackButton(userId: String) {
        binding.imgBackHome.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("userId", userId)
            homeFragment.arguments = bundle
            val fragmentManager = activity?.supportFragmentManager
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_main, homeFragment)
                ?.commit()
        }

    }
}



