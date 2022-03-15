package com.example.charo_android.presentation.ui.detailpost.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.charo_android.domain.model.detailpost.DetailPost
import com.example.charo_android.domain.usecase.detailpost.GetRemoteDetailPostUseCase
import com.example.charo_android.domain.usecase.interaction.PostLikeUseCase
import com.example.charo_android.domain.usecase.interaction.PostSaveUseCase
import kotlinx.coroutines.launch

class DetailPostViewModel(
    private val getRemoteDetailPostUseCase: GetRemoteDetailPostUseCase,
    private val postLikeUseCase: PostLikeUseCase,
    private val postSaveUseCase: PostSaveUseCase
) : ViewModel() {
    private val userEmail = "and@naver.com"
    var postId = 8

    private var _detailPost = MutableLiveData<DetailPost>()
    val detailPost: LiveData<DetailPost> get() = _detailPost

    private var _isFavorite = MutableLiveData<Int>()
    val isFavorite: LiveData<Int> get() = _isFavorite

    private var _isStored = MutableLiveData<Int>()
    val isStored: LiveData<Int> get() = _isStored

    private var _likesCount = MutableLiveData<Int>()
    val likesCount: LiveData<Int> get() = _likesCount

    fun getDetailPostData() {
        viewModelScope.launch {
            kotlin.runCatching {
                // TODO: 추후 postId 넣는 방식 수정요망
                getRemoteDetailPostUseCase(userEmail, postId)
            }.onSuccess {
                _detailPost.value = it
                _isFavorite.value = it.isFavorite
                _isStored.value = it.isStored
                _likesCount.value = it.likesCount
            }.onFailure {
                Log.e("mlog: DetailPostViewModel::getDetailPost()", it.message.toString())
            }
        }
    }

    fun postLike() {
        viewModelScope.launch {
            kotlin.runCatching {
                // TODO: 추후 postId 넣는 방식 수정요망
                postLikeUseCase(userEmail, postId)
            }.onSuccess {
                if (it) {
                    _isFavorite.value = (isFavorite.value?.plus(1))?.rem(2)
                    updateLikesCount()
                }
            }.onFailure {
                Log.e("mlog: DetailPostViewModel::postLike()", it.message.toString())
            }
        }
    }

    fun postSave() {
        viewModelScope.launch {
            kotlin.runCatching {
                postSaveUseCase(userEmail, postId)
            }.onSuccess {
                if (it) {
                    _isStored.value = (isStored.value?.plus(1))?.rem(2)
                }
            }.onFailure {
                Log.e("mlog: DetailPostViewModel::postSave()", it.message.toString())
            }
        }
    }

    private fun updateLikesCount() {
        when(isFavorite.value) {
            0 -> _likesCount.value = likesCount.value?.minus(1)
            else -> _likesCount.value = likesCount.value?.plus(1)
        }
    }
}