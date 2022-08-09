package com.charo.android.presentation.ui.more

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.charo.android.R
import com.charo.android.data.model.request.home.RequestHomeLikeData
import com.charo.android.databinding.FragmentMoreThemeContentViewBinding
import com.charo.android.presentation.base.BaseFragment
import com.charo.android.presentation.ui.more.adapter.MoreThemeContentAdapter
import com.charo.android.presentation.ui.more.viewmodel.MoreViewViewModel
import com.charo.android.presentation.ui.write.WriteShareActivity
import com.charo.android.presentation.util.SharedInformation
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class MoreThemeContentViewFragment(val userId: String, val identifier: String, val value: String) :
    BaseFragment<FragmentMoreThemeContentViewBinding>(R.layout.fragment_more_theme_content_view),
    SwipeRefreshLayout.OnRefreshListener {
    private val moreViewModel: MoreViewViewModel by viewModel()
    private lateinit var moreThemeContentAdapter: MoreThemeContentAdapter
    var link = DataToMoreThemeLike()

    var currentSpinnerPosition = 0

    private val moreResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                result.data?.let {
                    val updateLike = it.getIntExtra("updateLike", -1)
                    val postId = it.getIntExtra("postId", -1)
                    Timber.d("moreResultLauncher updateLike $updateLike postId $postId")

                    if(updateLike != -1 && postId != -1){
                        when(updateLike){
                            0 -> {
                                moreThemeContentAdapter.setLike(postId, false)
                            }
                            1 -> {
                                moreThemeContentAdapter.setLike(postId, true)
                            }
                        }
                    }
                }
            }
            Timber.d("moreResultLauncher result $result")
        }

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
            moreResultLauncher.launch(intent)
        }, link, userId)
        binding.recyclerviewMoreTheme.adapter = moreThemeContentAdapter

        moreViewModel.drive.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                binding.srThemeList.isRefreshing = false
                binding.srEmptyList.isRefreshing = false
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
                    moreThemeContentAdapter.setHomeTrendDrive(it)

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
            moreResultLauncher.launch(intent)
        }, link, userId)
        binding.recyclerviewMoreTheme.adapter = moreThemeContentAdapter
        moreViewModel.newDrive.observe(viewLifecycleOwner) {
            binding.srThemeList.isRefreshing = false
            binding.srEmptyList.isRefreshing = false

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

    override fun onRefresh() {
        Timber.d("moreThemeView onRefresh >>>>")
        if (currentSpinnerPosition == 0) {
            initMoreThemeView()
        } else {
            initMoreThemeNewView()
        }
    }
}