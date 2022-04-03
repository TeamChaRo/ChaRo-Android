package com.charo.android.presentation.ui.search.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charo.android.data.model.request.home.RequestHomeLikeData
import com.charo.android.data.model.request.search.RequestSearchViewData
import com.charo.android.domain.model.StatusCode
import com.charo.android.domain.model.search.SearchDrive
import com.charo.android.domain.usecase.home.PostRemoteHomeLikeUseCase
import com.charo.android.domain.usecase.search.GetRemoteSearchUseCase

import kotlinx.coroutines.launch

class SearchViewModel(
    private val getRemoteSearchUseCase: GetRemoteSearchUseCase,
    private val postRemoteHomeLikeUseCase: PostRemoteHomeLikeUseCase
): ViewModel() {

    val city = MutableLiveData<String>()

    val province = MutableLiveData<String>()

    val theme = MutableLiveData<String>()

    val caution = MutableLiveData<String>()

    val userEmail = "and@naver.com"


    private var _search = MutableLiveData<List<SearchDrive>>()
    val search: LiveData<List<SearchDrive>>
        get() = _search


    fun getSearch(requestSearchViewData: RequestSearchViewData) {
        viewModelScope.launch {
            runCatching { getRemoteSearchUseCase.execute(requestSearchViewData) }
                .onSuccess {
                    _search.value = it
                    Log.d("search", "서버 통신 성공!")
                    Log.d("search", _search.value.toString())
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("search", "서버 통신 실패")
                }
        }
    }


    //Post 좋아요
    fun postLike(requestHomeLikeData: RequestHomeLikeData) {
        viewModelScope.launch {
            runCatching { postRemoteHomeLikeUseCase.execute(requestHomeLikeData) }
                .onSuccess {
                    Log.d("searchLike", "서버 통신 성공!")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("searchLike", "서버 통신 실패패!")
                }
        }


    }
}