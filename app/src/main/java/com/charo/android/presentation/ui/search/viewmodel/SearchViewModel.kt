package com.charo.android.presentation.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charo.android.data.model.request.home.RequestHomeLikeData
import com.charo.android.data.model.request.search.RequestSearchViewData
import com.charo.android.domain.model.search.SearchDrive
import com.charo.android.domain.usecase.home.PostRemoteHomeLikeUseCase
import com.charo.android.domain.usecase.search.GetRemoteSearchUseCase
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchViewModel(
    private val getRemoteSearchUseCase: GetRemoteSearchUseCase,
    private val postRemoteHomeLikeUseCase: PostRemoteHomeLikeUseCase
): ViewModel() {

    val city = MutableLiveData<String>()

    val province = MutableLiveData<String>()

    val theme = MutableLiveData<String>()

    val caution = MutableLiveData<String>()




    private var _search = MutableLiveData<List<SearchDrive>>()
    val search: LiveData<List<SearchDrive>>
        get() = _search


    //검색 좋아요(인기순)
    fun getSearchLike(requestSearchViewData: RequestSearchViewData) {
        viewModelScope.launch {
            runCatching { getRemoteSearchUseCase.execute(requestSearchViewData) }
                .onSuccess {
                    _search.value = it
                    Timber.d("search 서버 통신 성공!")
                    Timber.d("search_${search.value.toString()}")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("search 서버 통신 실패")
                }
        }
    }

    //검색 최신순
    fun getSearchNew(requestSearchViewData: RequestSearchViewData) {
        viewModelScope.launch {
            runCatching { getRemoteSearchUseCase.executeNew(requestSearchViewData) }
                .onSuccess {
                    _search.value = it
                    Timber.d("search 서버 통신 성공!")
                    Timber.d("search_${search.value.toString()}")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("search 서버 통신 실패")
                }
        }
    }


    //Post 좋아요
    fun postLike(requestHomeLikeData: RequestHomeLikeData) {
        viewModelScope.launch {
            runCatching { postRemoteHomeLikeUseCase.execute(requestHomeLikeData) }
                .onSuccess {
                    Timber.d("searchLike 서버 통신 성공!")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("searchLike 서버 통신 실패!")
                }
        }


    }
}