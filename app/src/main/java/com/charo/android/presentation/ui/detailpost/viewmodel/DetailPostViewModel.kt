package com.charo.android.presentation.ui.detailpost.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.charo_android.domain.model.detailpost.DetailPost
import com.example.charo_android.domain.model.detailpost.User
import com.example.charo_android.domain.usecase.detailpost.GetDetailPostLikeUserListUseCase
import com.example.charo_android.domain.usecase.detailpost.GetDetailPostUseCase
import com.example.charo_android.domain.usecase.follow.PostFollowUseCase
import com.example.charo_android.domain.usecase.interaction.PostLikeUseCase
import com.example.charo_android.domain.usecase.interaction.PostSaveUseCase
import kotlinx.coroutines.launch

class DetailPostViewModel(
    private val getDetailPostUseCase: GetDetailPostUseCase,
    private val postLikeUseCase: PostLikeUseCase,
    private val postSaveUseCase: PostSaveUseCase,
    private val getDetailPostLikeUserListUseCase: GetDetailPostLikeUserListUseCase,
    private val postFollowUseCase: PostFollowUseCase
) : ViewModel() {
    private var _userEmail: String = "and@naver.com"
    val userEmail get() = _userEmail

    var postId = -1

    private var _detailPost = MutableLiveData<DetailPost>()
    val detailPost: LiveData<DetailPost> get() = _detailPost

    private var _isFavorite = MutableLiveData<Int>()
    val isFavorite: LiveData<Int> get() = _isFavorite

    private var _isStored = MutableLiveData<Int>()
    val isStored: LiveData<Int> get() = _isStored

    private var _likesCount = MutableLiveData<Int>()
    val likesCount: LiveData<Int> get() = _likesCount

    private var _likeUserList = MutableLiveData<List<User>>()
    val likeUserList: LiveData<List<User>> get() = _likeUserList

    var imageIndex = 0

    fun setUserEmail(userEmail: String) {
        _userEmail = userEmail
    }

    fun getDetailPostData() {
        viewModelScope.launch {
            kotlin.runCatching {
                // TODO: 추후 postId 넣는 방식 수정요망
                getDetailPostUseCase(userEmail, postId)
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

    fun getDetailPostLikeUserListData() {
        viewModelScope.launch {
            kotlin.runCatching {
                getDetailPostLikeUserListUseCase(postId, userEmail)
            }.onSuccess {
                _likeUserList.value = it
            }.onFailure {
                Log.e("mlog: DetailPostViewModel::getDetailPostLikeUserListData()", it.message.toString())
            }
        }
    }

    fun postFollow(otherUserEmail: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                postFollowUseCase(userEmail, otherUserEmail)
            }.onFailure {
                Log.e("mlog: DetailPostViewModel::postFollow", it.message.toString())
            }
        }
    }
}