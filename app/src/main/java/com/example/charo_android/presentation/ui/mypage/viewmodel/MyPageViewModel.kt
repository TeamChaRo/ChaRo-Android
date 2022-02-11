package com.example.charo_android.presentation.ui.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.charo_android.data.model.mypage.Post
import com.example.charo_android.data.model.mypage.UserInformation
import com.example.charo_android.domain.usecase.mypage.*
import kotlinx.coroutines.launch

class MyPageViewModel(
    private val getRemoteLikePostUseCase: GetRemoteLikePostUseCase,
    private val getRemoteNewPostUseCase: GetRemoteNewPostUseCase,
    private val getRemoteMoreWrittenLikePostUseCase: GetRemoteMoreWrittenLikePostUseCase,
    private val getRemoteMoreWrittenNewPostUseCase: GetRemoteMoreWrittenNewPostUseCase,
    private val getRemoteMoreSavedLikePostUseCase: GetRemoteMoreSavedLikePostUseCase,
    private val getRemoteMoreSavedNewPostUseCase: GetRemoteMoreSavedNewPostUseCase
) : ViewModel() {
    private val TAG = "mlog: MyPageViewModel::"
    private val userEmail = "and@naver.com"

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

    fun getLikePost() {
        viewModelScope.launch {
            kotlin.runCatching {
                getRemoteLikePostUseCase(userEmail)
            }.onSuccess {
                _userInfo.value = it.userInformation

                // 테스트 - 게시물 없는 경우에 대한 테스트
                _writtenLikeLastId = it.writtenPost.lastId
                _writtenLikeLastCount = it.writtenPost.lastCount
                _writtenLikePostList.value = it.writtenPost.drive

                _savedLikeLastId = it.savedPost.lastId
                _savedLikeLastCount = it.savedPost.lastCount
                _savedLikePostList.value = it.savedPost.drive
            }.onFailure {
                Log.d(TAG + "getLikePost()", it.message.toString())
            }
        }
    }

    fun getNewPost() {
        viewModelScope.launch {
            kotlin.runCatching {
                getRemoteNewPostUseCase(userEmail)
            }.onSuccess {
                _userInfo.value = it.userInformation

                _writtenNewLastId = it.writtenPost.lastId
                _writtenNewPostList.value = it.writtenPost.drive

                _savedNewLastId = it.savedPost.lastId
                _savedNewPostList.value = it.savedPost.drive
            }.onFailure {
                Log.d(TAG + "getNewPost()", it.message.toString())
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
                _writtenLikePostList.value?.addAll(it.drive)
            }.onFailure {
                Log.d(TAG + "getMoreWrittenLikePost()", it.message.toString())
            }
        }
    }

    fun getMoreWrittenNewPost() {
        viewModelScope.launch {
            kotlin.runCatching {
                getRemoteMoreWrittenNewPostUseCase(userEmail, _writtenNewLastId)
            }.onSuccess {
                _writtenNewLastId = it.lastId
                _writtenNewPostList.value?.addAll(it.drive)
            }.onFailure {
                Log.d(TAG + "getMoreWrittenNewPost()", it.message.toString())
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
                _savedLikePostList.value?.addAll(it.drive)
            }.onFailure {
                Log.d(TAG + "getMoreSavedLikePost()", it.message.toString())
            }
        }
    }

    fun getMoreSavedNewPost() {
        viewModelScope.launch {
            kotlin.runCatching {
                getRemoteMoreSavedNewPostUseCase(userEmail, _savedNewLastId)
            }.onSuccess {
                _savedNewLastId = it.lastId
                _savedNewPostList.value?.addAll(it.drive)
            }.onFailure {
                Log.d(TAG + "getMoreSavedNewPost()", it.message.toString())
            }
        }
    }
}