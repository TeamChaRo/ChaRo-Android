package com.charo.android.presentation.ui.more

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.charo.android.R
import com.charo.android.data.model.request.home.RequestHomeLikeData
import com.charo.android.databinding.FragmentMoreViewBinding
import com.charo.android.domain.model.more.MoreDrive
import com.charo.android.presentation.base.BaseFragment
import com.charo.android.presentation.ui.main.SharedViewModel
import com.charo.android.presentation.ui.more.adapter.MoreViewAdapter
import com.charo.android.presentation.ui.more.viewmodel.MoreViewViewModel
import com.charo.android.presentation.util.SharedInformation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class MoreViewFragment : BaseFragment<FragmentMoreViewBinding>(R.layout.fragment_more_view), SwipeRefreshLayout.OnRefreshListener {

    private val sharedViewModel: SharedViewModel by sharedViewModel()
    private val moreViewModel: MoreViewViewModel by viewModel()
    private lateinit var moreViewAdapter: MoreViewAdapter
    private lateinit var userId: String
    var links = DataToMoreLike()
    var currentSpinnerPosition = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        sharedViewModel.moreFragment.value = true

        userId = arguments?.getString("userId").toString()
        moreViewAdapter = MoreViewAdapter(userId, links)
        Timber.d("userIdeas $userId")

        binding.srMoreView.setOnRefreshListener(this)
        binding.srEmptyList.setOnRefreshListener(this)

        initSpinner()
//        clickBackButton(userId)
        clickSpinner()
        removeData()
    }

    private fun initToolbar() {
        val toolbar = binding.toolbarMore
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_1)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    fun moreViewLoadData() {
        val userEmail = SharedInformation.getEmail(requireActivity())
        if (sharedViewModel.num.value == 0) {
            moreViewModel.getMoreView(userEmail, "0", "")
            moreViewModel.getMoreViewLastId(userEmail, "0", "")
            moreViewAdapter = MoreViewAdapter(userEmail, links)
            binding.recyclerviewMoreView.adapter = moreViewAdapter

        } else if (sharedViewModel.num.value == 2) {
            moreViewModel.getMoreView(userEmail, "2", "busan")
            moreViewModel.getMoreViewLastId(userEmail, "2", "busan")
            sharedViewModel.getHomeTitle(userEmail)
            moreViewAdapter = MoreViewAdapter(userEmail, links)
            binding.textToolbarTitle.text = sharedViewModel.localThemeTitle.value
            binding.recyclerviewMoreView.adapter = moreViewAdapter
        } else {
            moreViewModel.getMoreView(userEmail, "3", "")
            moreViewModel.getMoreViewLastId(userEmail, "3", "")
            sharedViewModel.getHomeTitle(userEmail)
            moreViewAdapter = MoreViewAdapter(userEmail, links)
            binding.textToolbarTitle.text = sharedViewModel.customThemeTitle.value
            binding.recyclerviewMoreView.adapter = moreViewAdapter
        }

        moreViewModel.drive.observe(viewLifecycleOwner) {
            moreViewAdapter.setHomeTrendDrive(it as MutableList<MoreDrive>)
            emptyData(it)
        }
    }


    fun moreNewViewData() {
        val userEmail = SharedInformation.getEmail(requireActivity())
        if (sharedViewModel.num.value == 0) {
            moreViewModel.getMoreNewView(userEmail, "0", "")
            moreViewModel.getMoreNewViewLastId(userEmail, "0", "")
            moreViewAdapter = MoreViewAdapter(userEmail, links)
            binding.recyclerviewMoreView.adapter = moreViewAdapter

        } else if (sharedViewModel.num.value == 2) {
            moreViewModel.getMoreNewView(userEmail, "2", "busan")
            moreViewModel.getMoreNewViewLastId(userEmail, "2", "busan")
            moreViewAdapter = MoreViewAdapter(userEmail, links)
            binding.recyclerviewMoreView.adapter = moreViewAdapter
        } else {
            moreViewModel.getMoreNewView(userEmail, "3", "")
            moreViewModel.getMoreNewViewLastId(userEmail, "3", "")
            moreViewAdapter = MoreViewAdapter(userEmail, links)
            binding.recyclerviewMoreView.adapter = moreViewAdapter
        }

        moreViewModel.newDrive.observe(viewLifecycleOwner) {
            moreViewAdapter.setHomeTrendDrive(it as MutableList<MoreDrive>)
            emptyData(it)
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

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    currentSpinnerPosition = position
                    moreViewAdapter.removeHomeTrendDrive()
                    if (position == 0) {
                        if (moreViewAdapter.moreData.isEmpty()) {
                            moreViewLoadData()
                        }
                    } else {
                        if (moreViewAdapter.moreData.isEmpty()) {
                            moreNewViewData()
                        }
                    }
                    moreViewInfiniteScroll(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }

    }

    //????????? position??? ?????? ????????? ?????????
    private fun removeData() {
        moreViewModel.position.observe(viewLifecycleOwner) {
            Timber.d("??????????????? : ?????? ????????? ??????")
        }
    }


    //?????????
    inner class DataToMoreLike() {
        fun getPostId(postId: Int) {
            val userEmail = SharedInformation.getEmail(requireActivity())
            moreViewModel.postLike(RequestHomeLikeData(userEmail, postId))
        }
    }


    private fun moreViewInfiniteScroll(spinnerPosition: Int) {
        binding.recyclerviewMoreView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount - 1
                if (!binding.recyclerviewMoreView.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                    if (moreViewAdapter.moreData.isNotEmpty()) {
                        moreViewAdapter.addLoading()
                        when (spinnerPosition) {
                            0 -> {
                                moreViewAdapter.removeLoading()
                                moreViewInfiniteLoadData()
                            }
                            1 -> {
                                moreViewAdapter.removeLoading()
                                moreNewViewInfiniteLoadData()
                            }
                        }
                        moreViewAdapter.removeLoading()
                        Timber.d("infinite ?????? ????????? ???")
                    }
                }
            }
        })
    }

    private fun moreViewInfiniteLoadData() {
        val userEmail = SharedInformation.getEmail(requireActivity())
        Timber.d("userEmailss $userEmail")
        val lastId = moreViewModel.lastId.value!!.lastId
        val lastCount = moreViewModel.lastId.value!!.lastCount
        if (sharedViewModel.num.value == 0) {

            moreViewModel.getPreview(userEmail, "0", lastId, lastCount, "")

        } else if (sharedViewModel.num.value == 2) {
            moreViewModel.getPreview(userEmail, "2", lastId, lastCount, "busan")
        } else {
            moreViewModel.getPreview(userEmail, "3", lastId, lastCount, "")
        }
    }

    private fun moreNewViewInfiniteLoadData() {
        val userEmail = SharedInformation.getEmail(requireActivity())
        Timber.d("userEmailss $userEmail")
        val lastId = moreViewModel.lastId.value!!.lastId
        val lastCount = moreViewModel.lastId.value!!.lastCount
        if (sharedViewModel.num.value == 0) {
            moreViewModel.getNewPreview(userEmail, "0", lastId, "")

        } else if (sharedViewModel.num.value == 2) {
            moreViewModel.getNewPreview(userEmail, "2", lastId, "busan")

        } else {
            moreViewModel.getNewPreview(userEmail, "3", lastId, "")

        }
    }

    private fun emptyData(it : List<MoreDrive>) {
        binding.srMoreView.isRefreshing = false
        binding.srEmptyList.isRefreshing = false

        if(moreViewAdapter.itemCount == 0 && it.isEmpty()){
            binding.srMoreView.visibility = View.GONE
            binding.srEmptyList.visibility = View.VISIBLE
            binding.clMoreList.visibility = View.GONE
            binding.clEmptyList.visibility = View.VISIBLE
        } else {
            binding.srMoreView.visibility = View.VISIBLE
            binding.srEmptyList.visibility = View.GONE
            binding.clMoreList.visibility = View.VISIBLE
            binding.clEmptyList.visibility = View.GONE
        }
    }



    override fun onRefresh() {
        Timber.d("moreView onRefresh >>>>> ")
        moreViewAdapter.removeHomeTrendDrive()
        if (currentSpinnerPosition == 0) {
            if (moreViewAdapter.moreData.isEmpty()) {
                moreViewLoadData()
            }
        } else {
            if (moreViewAdapter.moreData.isEmpty()) {
                moreNewViewData()
            }
        }
        moreViewInfiniteScroll(currentSpinnerPosition)
    }
}


