package com.charo.android.presentation.ui.mypage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charo.android.data.model.mypage.Post
import com.charo.android.data.model.mypage.UserInformation
import com.charo.android.domain.usecase.mypage.*
import com.charo.android.presentation.util.SingleLiveEvent
import kotlinx.coroutines.async
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import timber.log.Timber

class MyPageViewModel(
    private val getRemoteLikePostUseCase: GetRemoteLikePostUseCase,
    private val getRemoteNewPostUseCase: GetRemoteNewPostUseCase,
    private val getRemoteMoreWrittenLikePostUseCase: GetRemoteMoreWrittenLikePostUseCase,
    private val getRemoteMoreWrittenNewPostUseCase: GetRemoteMoreWrittenNewPostUseCase,
    private val getRemoteMoreSavedLikePostUseCase: GetRemoteMoreSavedLikePostUseCase,
    private val getRemoteMoreSavedNewPostUseCase: GetRemoteMoreSavedNewPostUseCase
) : ViewModel() {
    private val TAG = "mlog: MyPageViewModel::"

    private var _userEmail: String = "@"
    val userEmail get() = _userEmail

    // 유저 정보
    private var _userInfo = MutableLiveData<UserInformation>()
    val userInfo: LiveData<UserInformation> get() = _userInfo

    // 인기순 작성한 글 정보
    private var _writtenLikeLastId = -1
    private var _writtenLikeLastCount = -1
    private var _writtenLikePostList = MutableLiveData<MutableList<Post>>()
    val writtenLikePostList: LiveData<MutableList<Post>> get() = _writtenLikePostList

    // 인기순 저장한 글 정보
    private var _savedLikeLastId = -1
    private var _savedLikeLastCount = -1
    private var _savedLikePostList = MutableLiveData<MutableList<Post>>()
    val savedLikePostList: LiveData<MutableList<Post>> get() = _savedLikePostList

    // 최신순 작성한 글 정보
    private var _writtenNewLastId = -1
    private var _writtenNewPostList = MutableLiveData<MutableList<Post>>()
    val writtenNewPostList: LiveData<MutableList<Post>> get() = _writtenNewPostList

    // 최신순 저장한 글 정보
    private var _savedNewLastId = -1
    private var _savedNewPostList = MutableLiveData<MutableList<Post>>()
    val savedNewPostList: LiveData<MutableList<Post>> get() = _savedNewPostList

    // 서버 에러 알림용 변수
    private var _serverError = SingleLiveEvent<Boolean>()
    val serverError: LiveData<Boolean> get() = _serverError

    fun setUserEmail(userEmail: String) {
        _userEmail = userEmail
    }

    fun getLikePost() {
        viewModelScope.launch {
            kotlin.runCatching {
                getRemoteLikePostUseCase(userEmail)
            }.onSuccess {
                Timber.i("ViewModel 인기순 데이터 수신")
                _userInfo.value = it.userInformation

                // 테스트 - 게시물 없는 경우에 대한 테스트
                _writtenLikeLastId = it.writtenPost.lastId
                _writtenLikeLastCount = it.writtenPost.lastCount
                _writtenLikePostList.value = it.writtenPost.drive

                _savedLikeLastId = it.savedPost.lastId
                _savedLikeLastCount = it.savedPost.lastCount
                _savedLikePostList.value = it.savedPost.drive
            }.onFailure {
                Timber.d("$TAG getLikePost() ${it.message.toString()}")
                _serverError.call()
            }
        }
    }

    fun getNewPost() {
        viewModelScope.launch {
            kotlin.runCatching {
                getRemoteNewPostUseCase(userEmail)
            }.onSuccess {
                Timber.i("ViewModel 최신순 데이터 수신")
                _userInfo.value = it.userInformation

                _writtenNewLastId = it.writtenPost.lastId
                _writtenNewPostList.value = it.writtenPost.drive

                _savedNewLastId = it.savedPost.lastId
                _savedNewPostList.value = it.savedPost.drive
            }.onFailure {
                Timber.d("$TAG getNewPost() ${it.message.toString()}")
                _serverError.call()
            }
        }
    }

    fun getMoreWrittenLikePost() {
        viewModelScope.launch {
            kotlin.runCatching {
                getRemoteMoreWrittenLikePostUseCase(
                    userEmail,
                    _writtenLikeLastId,
                    _writtenLikeLastCount
                )
            }.onSuccess {
                _writtenLikeLastId = it.lastId
                _writtenLikeLastCount = it.lastCount
                _writtenLikePostList.value =
                    _writtenLikePostList.value?.apply { addAll(it.drive) }
            }.onFailure {
                Timber.d("$TAG getMoreWrittenLikePost() ${it.message.toString()}")
                _serverError.call()
            }
        }
    }

    fun getMoreWrittenNewPost() {
        viewModelScope.launch {
            kotlin.runCatching {
                getRemoteMoreWrittenNewPostUseCase(userEmail, _writtenNewLastId)
            }.onSuccess {
                _writtenNewLastId = it.lastId
                _writtenNewPostList.value = _writtenNewPostList.value?.apply { addAll(it.drive) }
            }.onFailure {
                Timber.d("$TAG getMoreWrittenNewPost() ${it.message.toString()}")
                _serverError.call()
            }
        }
    }

    fun getMoreSavedLikePost() {
        viewModelScope.launch {
            kotlin.runCatching {
                getRemoteMoreSavedLikePostUseCase(userEmail, _savedLikeLastId, _savedLikeLastCount)
            }.onSuccess {
                _savedLikeLastId = it.lastId
                _savedLikeLastCount = it.lastCount
                _savedLikePostList.value = _savedLikePostList.value?.apply { addAll(it.drive) }
            }.onFailure {
                Timber.d("$TAG getMoreSavedLikePost() ${it.message.toString()}")
                _serverError.call()
            }
        }
    }

    fun getMoreSavedNewPost() {
        viewModelScope.launch {
            kotlin.runCatching {
                getRemoteMoreSavedNewPostUseCase(userEmail, _savedNewLastId)
            }.onSuccess {
                _savedNewLastId = it.lastId
                _savedNewPostList.value = _savedNewPostList.value?.apply { addAll(it.drive) }
            }.onFailure {
                Timber.d("$TAG getMoreSavedNewPost() ${it.message.toString()}")
                _serverError.call()
            }
        }
    }
}