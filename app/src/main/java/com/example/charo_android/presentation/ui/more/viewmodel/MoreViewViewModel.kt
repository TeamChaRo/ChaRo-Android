package com.example.charo_android.presentation.ui.more.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.charo_android.data.model.request.home.RequestHomeLikeData
import com.example.charo_android.domain.model.StatusCode
import com.example.charo_android.domain.model.more.LastId
import com.example.charo_android.domain.model.more.MoreDrive
import com.example.charo_android.domain.usecase.more.GetRemoteMoreDriveUseCase
import com.example.charo_android.domain.usecase.more.GetRemoteMoreLastIdUseCase
import com.example.charo_android.domain.usecase.more.GetRemoteMoreNewDriveUseCase
import com.example.charo_android.domain.usecase.home.PostRemoteHomeLikeUseCase
import com.example.charo_android.domain.usecase.more.GetRemoteMoreViewInfiniteUseCase
import kotlinx.coroutines.launch

class MoreViewViewModel(
    private val getRemoteMoreDriveUseCase: GetRemoteMoreDriveUseCase,
    //인기순 LastId 받아오는 거
    private val getRemoteMoreLastIdUseCase: GetRemoteMoreLastIdUseCase,
    private val getRemoteMoreNewDriveViewUseCase: GetRemoteMoreNewDriveUseCase,
    private val postRemoteHomeLikeUseCase: PostRemoteHomeLikeUseCase,

    //인기순 무한 스크롤
    private val getRemoteMoreViewInfiniteUseCase: GetRemoteMoreViewInfiniteUseCase
) : ViewModel() {
    //인기순
    var drive = MutableLiveData<List<MoreDrive>>()


    private val _lastId = MutableLiveData<LastId>()
    val lastId: LiveData<LastId>
        get() = _lastId
    //최신순
    var newDrive = MutableLiveData<List<MoreDrive>>()


    private val _statusCode = MutableLiveData<StatusCode>()
    val statusCode : LiveData<StatusCode>
        get() = _statusCode

    //처음 인기 순
    fun getMoreView(userEmail: String, identifer: String, value: String) {
        viewModelScope.launch {
            runCatching { getRemoteMoreDriveUseCase.execute(userEmail, identifer, value) }
                .onSuccess {
                    drive.value = it
                    Log.d("more", "서버 통신 성공!")
                    Log.d("more", drive.value.toString())
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("more", "서버 통신 실패")

                }

        }
    }
    //처음 최신순
    fun getMoreNewView(userEmail: String, identifer: String, value: String) {
        viewModelScope.launch {
            runCatching { getRemoteMoreNewDriveViewUseCase.execute(userEmail, identifer, value) }
                .onSuccess {
                    newDrive.value = it
                    Log.d("more", "서버 통신 성공!")
                    Log.d("more", drive.value.toString())
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("more", "서버 통신 실패")

                }

        }
    }
    //게시물 좋아요
    fun postLike(requestHomeLikeData: RequestHomeLikeData){
        viewModelScope.launch {
            runCatching { postRemoteHomeLikeUseCase.execute(requestHomeLikeData) }
                .onSuccess {
                    _statusCode.value = it
                    Log.d("moreLike", "서버 통신 성공!")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("moreLike", "서버 통신 실패패!")
                }
        }
    }

    //인기순 마지막 Id 및 count
    fun getMoreViewLastId(userEmail: String, identifer: String, value: String){
        viewModelScope.launch {
            runCatching {  getRemoteMoreLastIdUseCase.execute(userEmail, identifer, value)}
                .onSuccess {
                    _lastId.value = it

                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("moreViewLastId", "서버 통신 실패!")
                }
        }
    }

    //인기순 무한 스크롤
    fun getPreview(userEmail: String, identifer: String, postId : Int, count : Int, value: String){
        viewModelScope.launch {
            runCatching { getRemoteMoreViewInfiniteUseCase.execute(userEmail, identifer, postId, count, value) }
                .onSuccess {
                    _lastId.value = LastId(it.lastCount, it.lastId)
                    drive.value = it.drive
                    Log.d("moreViewInfinite", "서버 통신 성공!")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("moreViewInfinite", "서버 통신 실패!")
                }
        }
    }
}