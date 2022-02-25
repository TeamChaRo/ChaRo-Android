package com.example.charo_android.presentation.ui.detailpost.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.charo_android.domain.model.detailpost.DetailPost
import com.example.charo_android.domain.usecase.detailpost.GetRemoteDetailPostUseCase
import kotlinx.coroutines.launch

class DetailPostViewModel(
    private val getRemoteDetailPostUseCase: GetRemoteDetailPostUseCase
): ViewModel() {
    private val userEmail = "and@naver.com"
    private val postId = 8

    private var _detailPost = MutableLiveData<DetailPost>()
    val detailPost: LiveData<DetailPost> get() = _detailPost

    fun getDetailPostData(postId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                // TODO: 추후 postId 넣는 방식 수정요망
                getRemoteDetailPostUseCase.invoke(userEmail, this@DetailPostViewModel.postId)
            }.onSuccess {
                _detailPost.value = it
            }.onFailure {
                Log.e("mlog: DetailPostViewModel::getDetailPost()", it.message.toString())
            }
        }
    }
}