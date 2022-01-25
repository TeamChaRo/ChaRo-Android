package com.example.charo_android.presentation.ui.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.charo_android.data.model.mypage.SavedPost
import com.example.charo_android.data.model.mypage.UserInformation
import com.example.charo_android.data.model.mypage.WrittenPost
import com.example.charo_android.data.repository.remote.mypage.MyPageRepositoryImpl
import kotlinx.coroutines.launch

class MyPageViewModel: ViewModel() {
    // 유저 정보
    private var _userInformation = MutableLiveData<UserInformation>()
    val userInformation: LiveData<UserInformation> get() = _userInformation

    // 인기순 작성한 글 정보
    private var _writtenLikeLastId = -1
    private var _writtenLikeLastCount = -1
    private var _writtenLikePostList = MutableLiveData<List<WrittenPost.Data>>()
    val writtenLikePost: LiveData<List<WrittenPost.Data>> get() = _writtenLikePostList

    // 인기순 저장한 글 정보
    private var _savedLikeLastId = -1
    private var _savedLikeLastCount = -1
    private var _savedLikePostList = MutableLiveData<List<SavedPost.Data>>()
    val savedLikePostList: LiveData<List<SavedPost.Data>> get() = _savedLikePostList

    // 최신순 작성한 글 정보
    private var _writtenNewLastId = -1
    private var _writtenNewPostList = MutableLiveData<List<WrittenPost.Data>>()
    val writtenNewPostList: LiveData<List<WrittenPost.Data>> get() = _writtenNewPostList

    // 최신순 저장한 글 정보
    private var _savedNewLastId = -1
    private var _savedNewPostList = MutableLiveData<List<SavedPost.Data>>()
    val savedNewPostList: LiveData<List<SavedPost.Data>> get() = _savedNewPostList

    fun getLikePost() {
        // TODO: userEmail 따로 받아와야 함(ex: sharedPreference)
        val userEmail = "and@naver.com"

        // TODO: DI를 사용하면 뭔가 더 바꿔볼 수 있지 않을까?
        val myPageRepositoryImpl = MyPageRepositoryImpl()
        viewModelScope.launch {
            try {
                val response = myPageRepositoryImpl.getLikePost(userEmail).data
                // 유저 정보
                _userInformation.value = response.userInformation
                // 인기순 작성한 글 정보
                _writtenLikeLastId = response.writtenPost.lastId
                _writtenLikeLastCount = response.writtenPost.lastCount
                _writtenLikePostList.value = response.writtenPost.drive
                // 인기순 저장한 글 정보
                _savedLikeLastId = response.savedPost.lastId
                _savedLikeLastCount = response.savedPost.lastCount
                _savedLikePostList.value = response.savedPost.drive
            } catch (e: retrofit2.HttpException) {
                Log.e("mlog: MyPageViewModel::getLikePost", "${e.code()}, ${e.message()}")
            } catch (t: Throwable) {
                Log.e("mlog: MyPageViewModel::getLikePost", t.message.toString())
            }
        }
    }

    fun getNewPost() {
        // TODO: userEmail 따로 받아와야 함(ex: sharedPreference)
        val userEmail = "and@naver.com"

        // TODO: DI를 사용하면 뭔가 더 바꿔볼 수 있지 않을까?
        val myPageRepositoryImpl = MyPageRepositoryImpl()
        viewModelScope.launch {
            try {
                val response = myPageRepositoryImpl.getNewPost(userEmail).data
                // 유저 정보
                _userInformation.value = response.userInformation
                // 인기순 작성한 글 정보
                _writtenNewLastId = response.writtenPost.lastId
                _writtenNewPostList.value = response.writtenPost.drive
                // 인기순 저장한 글 정보
                _savedNewLastId = response.savedPost.lastId
                _savedNewPostList.value = response.savedPost.drive
            } catch (e: retrofit2.HttpException) {
                Log.e("mlog: MyPageViewModel::getLikePost", "${e.code()}, ${e.message()}")
            } catch (t: Throwable) {
                Log.e("mlog: MyPageViewModel::getLikePost", t.message.toString())
            }
        }
    }
}