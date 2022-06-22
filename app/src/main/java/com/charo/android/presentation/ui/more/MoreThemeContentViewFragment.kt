package com.charo.android.presentation.ui.more

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.charo.android.R
import com.charo.android.data.model.request.home.RequestHomeLikeData
import com.charo.android.databinding.FragmentMoreThemeContentViewBinding
import com.charo.android.presentation.base.BaseFragment
import com.charo.android.presentation.ui.more.adapter.MoreThemeContentAdapter
import com.charo.android.presentation.ui.more.viewmodel.MoreViewViewModel
import com.charo.android.presentation.util.SharedInformation
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class MoreThemeContentViewFragment(val userId: String, val identifier: String, val value: String) :
    BaseFragment<FragmentMoreThemeContentViewBinding>(R.layout.fragment_more_theme_content_view) {
    private val moreViewModel: MoreViewViewModel by viewModel()
    private lateinit var moreThemeContentAdapter: MoreThemeContentAdapter
    var link = DataToMoreThemeLike()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinner()
        clickSpinner()


    }

    private fun clickSpinner() {
        //default
        initMoreThemeView()

        //click
        binding.spinnerMoreTheme.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

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

    private fun initMoreThemeView() {
        Timber.d("데이터 받아오는거 확인 $value")
        moreViewModel.getMoreView(userId, identifier, value)
        moreThemeContentAdapter = MoreThemeContentAdapter(link, userId)
        binding.recyclerviewMoreTheme.adapter = moreThemeContentAdapter
        moreViewModel.drive.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.clEmptyList.visibility = View.VISIBLE
                binding.clThemeList.visibility = View.GONE
            } else {
                binding.clEmptyList.visibility = View.GONE
                binding.clThemeList.visibility = View.VISIBLE
                moreThemeContentAdapter.setHomeTrendDrive(it)
            }
        }
    }

    private fun initMoreThemeNewView() {
        Timber.d("데이터 받아오는거 확인 $value")
        moreViewModel.getMoreNewView(userId, identifier, value)
        moreThemeContentAdapter = MoreThemeContentAdapter(link, userId)
        binding.recyclerviewMoreTheme.adapter = moreThemeContentAdapter
        moreViewModel.newDrive.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.clEmptyList.visibility = View.VISIBLE
                binding.clThemeList.visibility = View.GONE
            } else {
                binding.clEmptyList.visibility = View.GONE
                binding.clThemeList.visibility = View.VISIBLE
                moreThemeContentAdapter.setHomeTrendDrive(it)
            }
        }
    }

    private fun initSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.search_spinner,
            R.layout.custom_spinner_item
        )
        binding.spinnerMoreTheme.adapter = adapter
    }


    inner class DataToMoreThemeLike() {
        fun getPostId(postId: Int) {
            Timber.d("more PostId $postId")
            val userEmail = SharedInformation.getEmail(requireActivity())
            moreViewModel.postLike(RequestHomeLikeData(userEmail, postId))
        }

    }
}