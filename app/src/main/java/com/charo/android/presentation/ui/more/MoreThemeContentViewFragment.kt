package com.charo.android.presentation.ui.more

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.charo_android.R
import com.example.charo_android.data.model.request.home.RequestHomeLikeData
import com.example.charo_android.databinding.FragmentMoreThemeContentViewBinding
import com.example.charo_android.hidden.Hidden
import com.example.charo_android.presentation.base.BaseFragment
import com.example.charo_android.presentation.ui.main.SharedViewModel
import com.example.charo_android.presentation.ui.more.adapter.MoreThemeContentAdapter
import com.example.charo_android.presentation.ui.more.adapter.MoreViewAdapter
import com.example.charo_android.presentation.ui.more.viewmodel.MoreViewViewModel
import com.example.charo_android.presentation.util.SharedInformation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MoreThemeContentViewFragment(val userId: String, val identifier: String, val value: String) :
    BaseFragment<FragmentMoreThemeContentViewBinding>(R.layout.fragment_more_theme_content_view) {
    private val moreViewModel: MoreViewViewModel by viewModel()
    private lateinit var moreThemeContentAdapter: MoreThemeContentAdapter
    private val sharedViewModel: SharedViewModel by sharedViewModel()
    var link = DataToMoreThemeLike()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinner()
        clickSpinner()

    }

    private fun clickSpinner() {
        binding.spinnerMoreTheme.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position == 0) {
                        initMoreThemeView()

                    } else {

                        initMoreThemeNewView()
                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }

    }

    private fun initMoreThemeView(){
        moreViewModel.getMoreView(userId, identifier, value)
        moreThemeContentAdapter = MoreThemeContentAdapter(link)
        binding.recyclerviewMoreTheme.adapter = moreThemeContentAdapter
        moreViewModel.drive.observe(viewLifecycleOwner) {
            moreThemeContentAdapter.setHomeTrendDrive(it)
        }
    }

    private fun initMoreThemeNewView(){
            moreViewModel.getMoreNewView(userId, identifier, value)
        moreThemeContentAdapter = MoreThemeContentAdapter(link)
            binding.recyclerviewMoreTheme.adapter = moreThemeContentAdapter
            moreViewModel.newDrive.observe(viewLifecycleOwner) {
                moreThemeContentAdapter.setHomeTrendDrive(it)
            }
    }

    private fun initSpinner(){
        val adapter = ArrayAdapter.createFromResource(requireContext(), R.array.search_spinner, R.layout.custom_spinner_item)
        binding.spinnerMoreTheme.adapter = adapter
    }


    inner class DataToMoreThemeLike(){
        fun getPostId(postId : Int){
            val userEmail = SharedInformation.getEmail(requireActivity())
            sharedViewModel.postId.value = postId
            sharedViewModel.postId.observe(viewLifecycleOwner){
                moreViewModel.postLike(RequestHomeLikeData("and@naver.com",it))
            }
        }

    }
}