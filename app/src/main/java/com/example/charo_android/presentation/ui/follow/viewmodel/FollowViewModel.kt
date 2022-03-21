package com.example.charo_android.presentation.ui.follow.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.charo_android.data.model.mypage.User
import com.example.charo_android.domain.usecase.follow.GetRemoteFollowListUseCase
import com.example.charo_android.domain.usecase.follow.PostFollowUseCase
import kotlinx.coroutines.launch

class FollowViewModel(
    private val getRemoteFollowListUseCase: GetRemoteFollowListUseCase,
    private val postFollowUseCase: PostFollowUseCase
) :
    ViewModel() {
    private val TAG = "mlog: FollowViewModel::"
    var userEmail = "and@naver.com"

    var nickname = MutableLiveData<String>()

    // 팔로워 정보
    private var _follower = MutableLiveData<List<User>>()
    val follower: LiveData<List<User>> get() = _follower

    // 팔로잉 정보
    private var _following = MutableLiveData<List<User>>()
    val following: LiveData<List<User>> get() = _following

    fun getFollowList(myPageEmail: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                getRemoteFollowListUseCase(userEmail, myPageEmail)
            }.onSuccess {
                _follower.value = it.follower
                _following.value = it.following
            }.onFailure {
                Log.e(TAG + "getFollowList()", it.message.toString())
            }
        }
    }

    fun postFollow(otherUserEmail: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                postFollowUseCase(userEmail, otherUserEmail)
            }.onFailure {
                Log.e(TAG + "postFollow()", it.message.toString())
            }
        }
    }
}