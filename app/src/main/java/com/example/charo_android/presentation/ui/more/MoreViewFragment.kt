package com.example.charo_android.presentation.ui.more

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.setFragmentResultListener
import com.example.charo_android.R
import com.example.charo_android.data.model.request.home.RequestHomeLikeData
import com.example.charo_android.databinding.FragmentMoreViewBinding
import com.example.charo_android.hidden.Hidden
import com.example.charo_android.presentation.base.BaseFragment
import com.example.charo_android.presentation.ui.home.HomeFragment
import com.example.charo_android.presentation.ui.home.viewmodel.HomeViewModel
import com.example.charo_android.presentation.ui.main.SharedViewModel
import com.example.charo_android.presentation.ui.more.adapter.MoreViewAdapter
import com.example.charo_android.presentation.ui.more.viewmodel.MoreViewViewModel
import com.example.charo_android.presentation.util.SharedInformation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MoreViewFragment : BaseFragment<FragmentMoreViewBinding>(R.layout.fragment_more_view) {

    private val sharedViewModel: SharedViewModel by sharedViewModel()
    private val moreViewModel: MoreViewViewModel by viewModel()
    private var homeFragment = HomeFragment()
    private lateinit var moreViewAdapter: MoreViewAdapter
    private lateinit var userId: String
    var links = DataToMoreLike()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userId = arguments?.getString("userId").toString()
        moreViewAdapter = MoreViewAdapter(userId, links)
        Log.d("userIdeas", userId)

        initSpinner()
        clickBackButton(userId)
        clickSpinner()
    }


    fun moreViewLoadData() {
        val userEmail = SharedInformation.getEmail(requireActivity())
        if (sharedViewModel.num.value == 0) {
            moreViewModel.getMoreView(userEmail, "0", "")
            moreViewAdapter = MoreViewAdapter(userEmail, links)
            binding.recyclerviewMoreView.adapter = moreViewAdapter
            moreViewModel.drive.observe(viewLifecycleOwner) {
                moreViewAdapter.setHomeTrendDrive(it)
            }

        } else if(sharedViewModel.num.value == 2) {
            moreViewModel.getMoreView(userEmail, "2", "busan")
            sharedViewModel.getHomeTitle(userEmail)
            moreViewAdapter = MoreViewAdapter(userEmail, links)
            binding.textToolbarTitle.text = sharedViewModel.localThemeTitle.value
            binding.recyclerviewMoreView.adapter = moreViewAdapter
            moreViewModel.drive.observe(viewLifecycleOwner) {
                moreViewAdapter.setHomeTrendDrive(it)
            }
        } else{
            moreViewModel.getMoreView(userEmail, "3", "")
            sharedViewModel.getHomeTitle(userEmail)
            moreViewAdapter = MoreViewAdapter(userEmail, links)
            binding.textToolbarTitle.text = sharedViewModel.customThemeTitle.value
            binding.recyclerviewMoreView.adapter = moreViewAdapter
            moreViewModel.drive.observe(viewLifecycleOwner) {
                moreViewAdapter.setHomeTrendDrive(it)
            }
        }
    }


    fun moreNewViewData() {
        val userEmail = SharedInformation.getEmail(requireActivity())
        if (sharedViewModel.num.value == 0) {
            moreViewModel.getMoreNewView("and@naver.com", "0", "")
            moreViewAdapter = MoreViewAdapter(userEmail, links)
            binding.recyclerviewMoreView.adapter = moreViewAdapter
            moreViewModel.newDrive.observe(viewLifecycleOwner) {
                moreViewAdapter.setHomeTrendDrive(it)
            }

        } else if (sharedViewModel.num.value == 2) {
            moreViewModel.getMoreNewView(userEmail, "2", "busan")
            moreViewAdapter = MoreViewAdapter(userEmail, links)
            binding.recyclerviewMoreView.adapter = moreViewAdapter
            moreViewModel.newDrive.observe(viewLifecycleOwner) {
                moreViewAdapter.setHomeTrendDrive(it)
            }
        }  else {
            moreViewModel.getMoreNewView(userEmail, "3", "")
            moreViewAdapter = MoreViewAdapter(userEmail, links)
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

    inner class DataToMoreLike(){

        fun getPostId(postId : Int){
            val userEmail = SharedInformation.getEmail(requireActivity())
            sharedViewModel.postId.value = postId
            sharedViewModel.postId.observe(viewLifecycleOwner){
                moreViewModel.postLike(RequestHomeLikeData(userEmail,it))
            }
        }

    }
}



