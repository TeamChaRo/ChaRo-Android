package com.charo.android.presentation.ui.follow.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charo.android.data.model.mypage.User
import com.charo.android.domain.usecase.follow.GetRemoteFollowListUseCase
import com.charo.android.domain.usecase.follow.PostFollowUseCase
import kotlinx.coroutines.launch
import timber.log.Timber

class FollowViewModel(
    private val getRemoteFollowListUseCase: GetRemoteFollowListUseCase,
    private val postFollowUseCase: PostFollowUseCase
) :
    ViewModel() {
    private val TAG = "mlog: FollowViewModel::"

    private var _userEmail: String = "@"
    val userEmail get() = _userEmail

    private var _myPageEmail: String = ""
    val myPageEmail get() = _myPageEmail

    private var _nickname = MutableLiveData<String>()
    val nickname: LiveData<String> get() = _nickname

    // 팔로워 정보
    private var _follower = MutableLiveData<List<User>>()
    val follower: LiveData<List<User>> get() = _follower

    // 팔로잉 정보
    private var _following = MutableLiveData<List<User>>()
    val following: LiveData<List<User>> get() = _following

    lateinit var newUser: User

    fun setUserEmail(userEmail: String) {
        _userEmail = userEmail
    }

    fun setMyPageEmail(myPageEmail: String) {
        _myPageEmail = myPageEmail
    }

    fun setNickname(nickname: String) {
        _nickname.value = nickname
    }

    fun getFollowList() {
        viewModelScope.launch {
            kotlin.runCatching {
                getRemoteFollowListUseCase(userEmail, myPageEmail)
            }.onSuccess {
                _follower.value = it.follower
                _following.value = it.following
            }.onFailure {
                Timber.e("$TAG getFollowList() ${it.message.toString()}")
            }
        }
    }

    fun postFollow(otherUserEmail: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                postFollowUseCase(userEmail, otherUserEmail)
                updateFollowList(otherUserEmail, null)
            }.onFailure {
                Timber.e("$TAG postFollow() ${it.message.toString()}")
            }
        }
    }

    private fun updateFollowList(otherUserEmail: String, isFollow: Boolean?) {
        val newFollowingUser: User?
        val updatedFollowerList = requireNotNull(_follower.value).map {
            if (it.userEmail == otherUserEmail) {
                it.copy().apply {
                    this.isFollow = isFollow ?: !this.isFollow
                }
            } else {
                it.copy()
            }
        }.toList()
        val updatedFollowingList = requireNotNull(_following.value).map {
            if (it.userEmail == otherUserEmail) {
                it.copy().apply {
                    this.isFollow = isFollow ?: !this.isFollow
                }
            } else {
                it.copy()
            }
        }.toMutableList()
        newFollowingUser = updatedFollowerList.find { it.userEmail == otherUserEmail }
        newFollowingUser?.let { newFollowingUser ->
            if (updatedFollowingList.find { it.userEmail == otherUserEmail } == null && userEmail == myPageEmail) {
                updatedFollowingList.add(0, newFollowingUser)
            }
        }
        _follower.value = updatedFollowerList
        _following.value = updatedFollowingList
    }

    fun updateNewUser() {
        updateFollowList(newUser.userEmail, newUser.isFollow)
    }
}