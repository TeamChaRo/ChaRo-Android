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
    private val getRemoteUserInformationUseCase: GetRemoteUserInformationUseCase,
    private val getRemoteLikePostUseCase: GetRemoteLikePostUseCase,
    private val getRemoteNewPostUseCase: GetRemoteNewPostUseCase,
    private val getRemoteMoreWrittenLikePostUseCase: GetRemoteMoreWrittenLikePostUseCase,
    private val getRemoteMoreWrittenNewPostUseCase: GetRemoteMoreWrittenNewPostUseCase,
    private val getRemoteMoreSavedLikePostUseCase: GetRemoteMoreSavedLikePostUseCase,
    private val getRemoteMoreSavedNewPostUseCase: GetRemoteMoreSavedNewPostUseCase
): ViewModel() {
    // TODO: DI를 사용하면 뭔가 더 바꿔볼 수 있지 않을까?
    // private val myPageRepositoryImpl = MyPageRepositoryImpl()

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

    // TODO: userInformation 받아오기
    fun getUserInformation() {
        // TODO: userEmail 따로 받아와야 함(ex: sharedPreference)
        val userEmail = "and@naver.com"

        viewModelScope.launch {
            kotlin.runCatching {
                getRemoteUserInformationUseCase(userEmail)
            }.onSuccess {
                _userInfo.value = it
            }.onFailure {
                Log.d("mlog: MyPageViewModel::getUserInformation", it.message.toString())
            }
        }
    }

//    fun getLikePost() {
//        // TODO: userEmail 따로 받아와야 함(ex: sharedPreference)
//        val userEmail = "and@naver.com"
//
//        viewModelScope.launch {
//            try {
//                val response = myPageRepositoryImpl.getLikePost(userEmail).data
//                // 유저 정보
//                _userInformation.value = response.userInformation
//                // 인기순 작성한 글 정보
//                _writtenLikeLastId = response.writtenPost.lastId
//                _writtenLikeLastCount = response.writtenPost.lastCount
//                _writtenLikePostList.value = response.writtenPost.drive
//                // 인기순 저장한 글 정보
//                _savedLikeLastId = response.savedPost.lastId
//                _savedLikeLastCount = response.savedPost.lastCount
//                _savedLikePostList.value = response.savedPost.drive
//            } catch (e: retrofit2.HttpException) {
//                Log.e("mlog: MyPageViewModel::getLikePost", "${e.code()}, ${e.message()}")
//            } catch (t: Throwable) {
//                Log.e("mlog: MyPageViewModel::getLikePost", t.message.toString())
//            }
//        }
//    }
//
//    fun getNewPost() {
//        // TODO: userEmail 따로 받아와야 함(ex: sharedPreference)
//        val userEmail = "and@naver.com"
//
//        viewModelScope.launch {
//            try {
//                val response = myPageRepositoryImpl.getNewPost(userEmail).data
//                // 유저 정보
//                _userInformation.value = response.userInformation
//                // 인기순 작성한 글 정보
//                _writtenNewLastId = response.writtenPost.lastId
//                _writtenNewPostList.value = response.writtenPost.drive
//                // 인기순 저장한 글 정보
//                _savedNewLastId = response.savedPost.lastId
//                _savedNewPostList.value = response.savedPost.drive
//            } catch (e: retrofit2.HttpException) {
//                Log.e("mlog: MyPageViewModel::getLikePost", "${e.code()}, ${e.message()}")
//            } catch (t: Throwable) {
//                Log.e("mlog: MyPageViewModel::getLikePost", t.message.toString())
//            }
//        }
//    }
//
//    fun getMoreWrittenLikePost() {
//        // TODO: userEmail 따로 받아와야 함(ex: sharedPreference)
//        val userEmail = "and@naver.com"
//
//        viewModelScope.launch {
//            try {
//                val response = myPageRepositoryImpl.getMoreWrittenLikePost(userEmail, _writtenLikeLastId, _writtenLikeLastCount).data
//                // 인기순 작성한 글 정보
//                _writtenLikePostList.value?.addAll(response.drive)
//                _writtenLikeLastId = response.lastId
//                _writtenLikeLastCount = response.lastCount
//                Log.d("mlog: MyPageViewModel::getMoreWrittenLikePost", "success")
//            } catch (e: retrofit2.HttpException) {
//                Log.d("mlog: MyPageViewModel::getMoreWrittenLikePost", "${e.code()}, ${e.message()}")
//            } catch (t: Throwable) {
//                Log.e("mlog: MyPageViewModel::getMoreWrittenLikePost", t.message.toString())
//            }
//        }
//    }
//
//    fun getMoreWrittenNewPost() {
//        // TODO: userEmail 따로 받아와야 함(ex: sharedPreference)
//        val userEmail = "and@naver.com"
//
//        viewModelScope.launch {
//            try {
//                val response = myPageRepositoryImpl.getMoreWrittenNewPost(userEmail, _writtenNewLastId).data
//                // 인기순 작성한 글 정보
//                _writtenNewPostList.value?.addAll(response.drive)
//                _writtenNewLastId = response.lastId
//                Log.d("mlog: MyPageViewModel::getMoreWrittenNewPost", "success")
//            } catch (e: retrofit2.HttpException) {
//                Log.d("mlog: MyPageViewModel::getMoreWrittenNewPost", "${e.code()}, ${e.message()}")
//            } catch (t: Throwable) {
//                Log.e("mlog: MyPageViewModel::getMoreWrittenNewPost", t.message.toString())
//            }
//        }
//    }
//
//    fun getMoreSavedLikePost() {
//        // TODO: userEmail 따로 받아와야 함(ex: sharedPreference)
//        val userEmail = "and@naver.com"
//
//        viewModelScope.launch {
//            try {
//                val response = myPageRepositoryImpl.getMoreSavedLikePost(userEmail, _savedLikeLastId, _savedLikeLastCount).data
//                // 인기순 작성한 글 정보
//                _savedLikePostList.value?.addAll(response.drive)
//                _savedLikeLastId = response.lastId
//                _savedLikeLastCount = response.lastCount
//                Log.d("mlog: MyPageViewModel::getMoreSavedLikePost", "success")
//            } catch (e: retrofit2.HttpException) {
//                Log.d("mlog: MyPageViewModel::getMoreSavedLikePost", "${e.code()}, ${e.message()}")
//            } catch (t: Throwable) {
//                Log.e("mlog: MyPageViewModel::getMoreSavedLikePost", t.message.toString())
//            }
//        }
//    }
//
//    fun getMoreSavedNewPost() {
//        // TODO: userEmail 따로 받아와야 함(ex: sharedPreference)
//        val userEmail = "and@naver.com"
//
//        viewModelScope.launch {
//            try {
//                val response = myPageRepositoryImpl.getMoreSavedNewPost(userEmail, _savedNewLastId).data
//                // 인기순 작성한 글 정보
//                _savedNewPostList.value?.addAll(response.drive)
//                _savedNewLastId = response.lastId
//                Log.d("mlog: MyPageViewModel::getMoreSavedNewPost", "success")
//            } catch (e: retrofit2.HttpException) {
//                Log.d("mlog: MyPageViewModel::getMoreSavedNewPost", "${e.code()}, ${e.message()}")
//            } catch (t: Throwable) {
//                Log.e("mlog: MyPageViewModel::getMoreSavedNewPost", t.message.toString())
//            }
//        }
//    }
}