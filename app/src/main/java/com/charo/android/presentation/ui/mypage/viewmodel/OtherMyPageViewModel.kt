package com.charo.android.presentation.ui.mypage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charo.android.data.model.mypage.Post
import com.charo.android.data.model.mypage.UserInformation
import com.charo.android.domain.usecase.follow.PostFollowUseCase
import com.charo.android.domain.usecase.mypage.*
import kotlinx.coroutines.launch
import timber.log.Timber

class OtherMyPageViewModel(
    private val getRemoteLikePostUseCase: GetRemoteLikePostUseCase,
    private val getRemoteNewPostUseCase: GetRemoteNewPostUseCase,
    private val getRemoteMoreWrittenLikePostUseCase: GetRemoteMoreWrittenLikePostUseCase,
    private val getRemoteMoreWrittenNewPostUseCase: GetRemoteMoreWrittenNewPostUseCase,
    private val postFollowUseCase: PostFollowUseCase,
    private val getRemoteFollowUseCase: GetRemoteFollowUseCase
) : ViewModel() {
    private val TAG = "mlog: OtherPageViewModel::"
//    private val userEmail = "and@naver.com"

    private var _userEmail = "@"
    val userEmail: String get() = _userEmail

    private var _otherUserEmail = ""
    val otherUserEmail: String get() = _otherUserEmail

    val isMine = MutableLiveData<Boolean>()

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
        if (_userEmail == _otherUserEmail) {
            isMine.value = true
        }
    }

    fun getLikePost() {
        viewModelScope.launch {
            kotlin.runCatching {
                getRemoteLikePostUseCase(otherUserEmail)
            }.onSuccess {
                it.let {
                    _userInfo.value = it.userInformation
                    _writtenLikeLastId = it.writtenPost.lastId
                    _writtenLikeLastCount = it.writtenPost.lastCount
                    _writtenLikePostList.value = it.writtenPost.drive
                }
            }.onFailure {
                Timber.d("$TAG getLikePost() ${it.message.toString()}")
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
                Timber.d("$TAG getNewPost() ${it.message.toString()}")
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
                _writtenLikePostList.value = _writtenLikePostList.value?.apply { addAll(it.drive) }
            }.onFailure {
                Timber.d("$TAG getMoreWrittenLikePost() ${it.message.toString()}")
            }
        }
    }

    fun getMoreWrittenNewPost() {
        viewModelScope.launch {
            kotlin.runCatching {
                getRemoteMoreWrittenNewPostUseCase(otherUserEmail, _writtenNewLastId)
            }.onSuccess {
                _writtenNewLastId = it.lastId
                _writtenNewPostList.value = _writtenNewPostList.value?.apply { addAll(it.drive) }
            }.onFailure {
                Timber.d("$TAG getMoreWrittenNewPost() ${it.message.toString()}")
            }
        }
    }

    fun postFollow() {
        viewModelScope.launch {
            kotlin.runCatching {
                postFollowUseCase(userEmail, otherUserEmail)
            }.onSuccess {
                _isFollow.value = it
                if(it) {
                    _userInfo.value = userInfo.value?.copy()?.apply {
                        this.follower++
                    }
                } else {
                    _userInfo.value = userInfo.value?.copy()?.apply {
                        this.follower--
                    }
                }
            }.onFailure {
                Timber.d("$TAG postFollow() ${it.message.toString()}")
            }
        }
    }

    fun getFollow() {
        viewModelScope.launch {
            kotlin.runCatching {
                getRemoteFollowUseCase(userEmail, otherUserEmail)
            }.onSuccess {
                _isFollow.value = it
            }.onFailure {
                Timber.i("$TAG getFollow() $it")
            }
        }
    }
}