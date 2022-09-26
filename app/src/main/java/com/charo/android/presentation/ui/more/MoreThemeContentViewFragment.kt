package com.charo.android.presentation.ui.more

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.charo.android.R
import com.charo.android.data.model.request.home.RequestHomeLikeData
import com.charo.android.databinding.FragmentMoreThemeContentViewBinding
import com.charo.android.presentation.base.BaseFragment
import com.charo.android.presentation.ui.main.MainActivity
import com.charo.android.presentation.ui.main.SharedViewModel
import com.charo.android.presentation.ui.more.adapter.MoreThemeContentAdapter
import com.charo.android.presentation.ui.more.viewmodel.MoreViewViewModel
import com.charo.android.presentation.ui.write.WriteShareActivity
import com.charo.android.presentation.util.SharedInformation
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class MoreThemeContentViewFragment(val userId: String, val identifier: String, val value: String) :
    BaseFragment<FragmentMoreThemeContentViewBinding>(R.layout.fragment_more_theme_content_view),
    SwipeRefreshLayout.OnRefreshListener {
    private val moreViewModel: MoreViewViewModel by viewModel()
    private lateinit var moreThemeContentAdapter: MoreThemeContentAdapter
    private val sharedViewModel: SharedViewModel by sharedViewModel()
    var link = DataToMoreThemeLike()

    var currentSpinnerPosition = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinner()
        clickSpinner()

        binding.srThemeList.setColorSchemeResources(R.color.blue_main_0f6fff)
        binding.srEmptyList.setColorSchemeResources(R.color.blue_main_0f6fff)
        binding.srThemeList.setOnRefreshListener(this)
        binding.srEmptyList.setOnRefreshListener(this)
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
                    currentSpinnerPosition = position
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
        moreViewModel.getMoreView(userId, identifier, value)
        moreThemeContentAdapter = MoreThemeContentAdapter({
            val intent = Intent(requireContext(), WriteShareActivity::class.java)
            intent.apply {
                putExtra("userId", userId)
                putExtra("destination", "detail")
                putExtra("from", "MoreView")
                putExtra("postId", it.morePostId)
            }
            (requireActivity() as MainActivity).moreResultLauncher.launch(intent)
        }, { postId, updateLike ->

            moreViewModel._drive.value = moreViewModel.setLike(postId, updateLike)
            moreThemeContentAdapter.setHomeTrendDrive(moreViewModel._drive.value)

        }, link, userId)
        binding.recyclerviewMoreTheme.adapter = moreThemeContentAdapter

        moreViewModel.drive.flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
            .onEach {
//                Timber.e("flowWithLifecycle $it")
                binding.srThemeList.isRefreshing = false
                binding.srEmptyList.isRefreshing = false

//                moreViewModel.getMoreView(userId, identifier, value)
                moreThemeContentAdapter.setHomeTrendDrive(moreViewModel._drive.value)

                if (it.isEmpty()) {
                    binding.clEmptyList.visibility = View.VISIBLE
                    binding.clThemeList.visibility = View.GONE
                    binding.srEmptyList.visibility = View.VISIBLE
                    binding.srThemeList.visibility = View.GONE
                } else {
                    binding.clEmptyList.visibility = View.GONE
                    binding.clThemeList.visibility = View.VISIBLE
                    binding.srEmptyList.visibility = View.GONE
                    binding.srThemeList.visibility = View.VISIBLE
                }
            }.launchIn(lifecycleScope)
    }


    private fun initMoreThemeNewView() {
        Timber.d("데이터 받아오는거 확인 $value")
        moreViewModel.getMoreNewView(userId, identifier, value)
        moreThemeContentAdapter = MoreThemeContentAdapter({
            val intent = Intent(requireContext(), WriteShareActivity::class.java)
            intent.apply {
                putExtra("userId", userId)
                putExtra("destination", "detail")
                putExtra("from", "MoreView")
                putExtra("postId", it.morePostId)
            }
            (requireActivity() as MainActivity).moreResultLauncher.launch(intent)
        }, { postId, updateLike ->
            moreViewModel.newDrive.value = moreViewModel.setLike(postId, updateLike)
            moreThemeContentAdapter.setHomeTrendDrive(moreViewModel.newDrive.value)

            moreViewModel.setLike(postId, updateLike)
        }, link, userId)

        binding.recyclerviewMoreTheme.adapter = moreThemeContentAdapter

        moreViewModel.newDrive.flowWithLifecycle(lifecycle,Lifecycle.State.RESUMED)
            .onEach{
            binding.srThemeList.isRefreshing = false
            binding.srEmptyList.isRefreshing = false

//            moreViewModel.getMoreNewView(userId, identifier, value)
            moreThemeContentAdapter.setHomeTrendDrive(moreViewModel.newDrive.value)

            if (it.isEmpty()) {
                binding.clEmptyList.visibility = View.VISIBLE
                binding.clThemeList.visibility = View.GONE
                binding.srEmptyList.visibility = View.VISIBLE
                binding.srThemeList.visibility = View.GONE
            } else {
                binding.clEmptyList.visibility = View.GONE
                binding.clThemeList.visibility = View.VISIBLE
                binding.srEmptyList.visibility = View.GONE
                binding.srThemeList.visibility = View.VISIBLE
            }
        }.launchIn(lifecycleScope)
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

    override fun onRefresh() {
        Timber.d("moreThemeView onRefresh >>>>")
        if (currentSpinnerPosition == 0) {
            initMoreThemeView()
        } else {
            initMoreThemeNewView()
        }
    }

    override fun onResume() {
        Timber.d("moreThemeView onResume >>>>")
        super.onResume()
        if (currentSpinnerPosition == 0) {
            initMoreThemeView()
        } else {
            initMoreThemeNewView()
        }
    }
}