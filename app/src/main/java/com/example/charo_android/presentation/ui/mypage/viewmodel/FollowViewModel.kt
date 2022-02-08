package com.example.charo_android.presentation.ui.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.charo_android.data.model.mypage.User
import com.example.charo_android.domain.usecase.follow.GetRemoteFollowListUseCase
import kotlinx.coroutines.launch

class FollowViewModel(private val getRemoteFollowListUseCase: GetRemoteFollowListUseCase) :
    ViewModel() {
    private val TAG = "mlog: FollowViewModel::"
    var userEmail = "and@naver.com"
    var myPageEmail = "and@naver.com"
    var nickname = "and@naver.com의 닉네임"

    // 팔로워 정보
    private var _follower = MutableLiveData<List<User>>()
    val follower: LiveData<List<User>> get() = _follower

    // 팔로잉 정보
    private var _following = MutableLiveData<List<User>>()
    val following: LiveData<List<User>> get() =_following

    fun getFollowList() {
        viewModelScope.launch {
            kotlin.runCatching {
                getRemoteFollowListUseCase(userEmail, myPageEmail)
            }.onSuccess {
                _follower.value = it.follower
                _following.value = it.following
            }.onFailure {
                Log.d(TAG + "getFollowList()", it.message.toString())
            }
        }
    }
}