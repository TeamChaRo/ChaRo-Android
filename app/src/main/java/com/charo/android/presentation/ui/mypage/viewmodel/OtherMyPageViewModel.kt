package com.charo.android.presentation.ui.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.charo_android.data.model.mypage.Post
import com.example.charo_android.data.model.mypage.UserInformation
import com.example.charo_android.domain.usecase.follow.PostFollowUseCase
import com.example.charo_android.domain.usecase.mypage.*
import kotlinx.coroutines.launch

class OtherMyPageViewModel(
    private val getRemoteLikePostUseCase: GetRemoteLikePostUseCase,
    private val getRemoteNewPostUseCase: GetRemoteNewPostUseCase,
    private val getRemoteMoreWrittenLikePostUseCase: GetRemoteMoreWrittenLikePostUseCase,
    private val getRemoteMoreWrittenNewPostUseCase: GetRemoteMoreWrittenNewPostUseCase,
    private val postFollowUseCase: PostFollowUseCase
) : ViewModel() {
    private val TAG = "mlog: OtherPageViewModel::"
//    private val userEmail = "and@naver.com"

    private var _userEmail: String = "@"
    val userEmail get() = _userEmail

    private var _otherUserEmail: String = ""
    val otherUserEmail get() = _otherUserEmail

    // 유저 정보
    private var _userInfo = MutableLiveData<UserInformation>()
    val userInfo: LiveData<UserInformation> get() = _userInfo

    // 인기순 작성한 글 정보
    private var _writtenLikeLastId = -1
    private var _writtenLikeLastCount = -1
    private var _writtenLikePostList = MutableLiveData<MutableList<Post>>()
    val writtenLikePostList: LiveData<MutableList<Post>> get() = _writtenLikePostList

    // 최신순 작성한 글 정보
    private var _writtenNewLastId = -1
    private var _writtenNewPostList = MutableLiveData<MutableList<Post>>()
    val writtenNewPostList: LiveData<MutableList<Post>> get() = _writtenNewPostList

    // 마이페이지 주인 팔로우 여부
    private var _isFollow = MutableLiveData<Boolean>(false)
    val isFollow: LiveData<Boolean> get() = _isFollow

    fun setUserEmail(userEmail: String) {
        _userEmail = userEmail
    }

    fun setOtherUserEmail(otherUserEmail: String) {
        _otherUserEmail = otherUserEmail
    }

    fun getLikePost() {
        viewModelScope.launch {
            kotlin.runCatching {
                getRemoteLikePostUseCase(otherUserEmail)
            }.onSuccess {
                _userInfo.value = it.userInformation
                _writtenLikeLastId = it.writtenPost.lastId
                _writtenLikeLastCount = it.writtenPost.lastCount
                _writtenLikePostList.value = it.writtenPost.drive
            }.onFailure {
                Log.d(TAG + "getLikePost()", it.message.toString())
            }
        }
    }

    fun getNewPost() {
        viewModelScope.launch {
            kotlin.runCatching {
                getRemoteNewPostUseCase(otherUserEmail)
            }.onSuccess {
                _userInfo.value = it.userInformation
                _writtenNewLastId = it.writtenPost.lastId
                _writtenNewPostList.value = it.writtenPost.drive
            }.onFailure {
                Log.d(TAG + "getNewPost()", it.message.toString())
            }
        }
    }

    fun getMoreWrittenLikePost() {
        viewModelScope.launch {
            kotlin.runCatching {
                getRemoteMoreWrittenLikePostUseCase(
                    otherUserEmail,
                    _writtenLikeLastId,
                    _writtenLikeLastCount
                )
            }.onSuccess {
                _writtenLikeLastId = it.lastId
                _writtenLikeLastCount = it.lastCount
                _writtenLikePostList.value?.addAll(it.drive)
            }.onFailure {
                Log.d(TAG + "getMoreWrittenLikePost()", it.message.toString())
            }
        }
    }

    fun getMoreWrittenNewPost() {
        viewModelScope.launch {
            kotlin.runCatching {
                getRemoteMoreWrittenNewPostUseCase(otherUserEmail, _writtenNewLastId)
            }.onSuccess {
                _writtenNewLastId = it.lastId
                _writtenNewPostList.value?.addAll(it.drive)
            }.onFailure {
                Log.d(TAG + "getMoreWrittenNewPost()", it.message.toString())
            }
        }
    }

    fun postFollow() {
        viewModelScope.launch {
            kotlin.runCatching {
                postFollowUseCase(userEmail, otherUserEmail)
            }.onSuccess {
                _isFollow.value = it
            }.onFailure {
                Log.e(TAG + "postFollow()", it.message.toString())
            }
        }
    }
}