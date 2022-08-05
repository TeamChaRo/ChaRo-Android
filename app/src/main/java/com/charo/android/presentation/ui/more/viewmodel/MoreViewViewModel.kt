package com.charo.android.presentation.ui.more.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charo.android.data.model.request.home.RequestHomeLikeData
import com.charo.android.domain.model.StatusCode
import com.charo.android.domain.model.more.LastId
import com.charo.android.domain.model.more.MoreDrive
import com.charo.android.domain.usecase.home.PostRemoteHomeLikeUseCase
import com.charo.android.domain.usecase.more.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class MoreViewViewModel(
    private val getRemoteMoreDriveUseCase: GetRemoteMoreDriveUseCase,
    //인기순 LastId 받아오는 거
    private val getRemoteMoreLastIdUseCase: GetRemoteMoreLastIdUseCase,

    //최신순 LastId 받아오는 거
    private val getRemoteMoreNewLastIdUseCase : GetRemoteMoreNewLastIdUseCase,

    private val getRemoteMoreNewDriveViewUseCase: GetRemoteMoreNewDriveUseCase,
    private val postRemoteHomeLikeUseCase: PostRemoteHomeLikeUseCase,

    //인기순 무한 스크롤
    private val getRemoteMoreViewInfiniteUseCase: GetRemoteMoreViewInfiniteUseCase,

    //최신순 무한 스크롤
    private val getRemoteMoreNewViewInfiniteUseCase: GetRemoteMoreNewViewInfiniteUseCase
) : ViewModel() {
    //인기순

    private var _drive = MutableStateFlow<List<MoreDrive>>(
        emptyList()
    )
    val drive : StateFlow<List<MoreDrive>>
        get() = _drive


    private val _lastId = MutableStateFlow(
        LastId(0,0)
    )
    val lastId: StateFlow<LastId>
        get() = _lastId


    //최신순
    var newDrive = MutableLiveData<List<MoreDrive>>()


    //스피넌 position
    var position = MutableLiveData<Int>()

    private val _statusCode = MutableLiveData<StatusCode>()
    val statusCode : LiveData<StatusCode>
        get() = _statusCode

    //처음 인기 순
    fun getMoreView(userEmail: String, identifer: String, value: String) {
        viewModelScope.launch {
            runCatching { getRemoteMoreDriveUseCase(userEmail, identifer, value) }
                .onSuccess { it ->
                    it.collectLatest {
                        _drive.value = it
                    }
                    Timber.d("more 서버 통신 성공!")
                    Timber.d("more ${drive.value.toString()}")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("more 서버 통신 실패")
                }
        }
    }
    //처음 최신순
    fun getMoreNewView(userEmail: String, identifer: String, value: String) {
        viewModelScope.launch {
            runCatching { getRemoteMoreNewDriveViewUseCase.execute(userEmail, identifer, value) }
                .onSuccess {
                    newDrive.value = it
                    Timber.d("more 서버 통신 성공!")
                    Timber.d("more ${drive.value.toString()}")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("more 서버 통신 실패")

                }

        }
    }
    //게시물 좋아요
    fun postLike(requestHomeLikeData: RequestHomeLikeData){
        viewModelScope.launch {
            runCatching { postRemoteHomeLikeUseCase.execute(requestHomeLikeData) }
                .onSuccess {
                    _statusCode.value = it
                    Timber.d("moreLike 서버 통신 성공!")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("moreLike 서버 통신 실패패!")
                }
        }
    }

    //인기순 마지막 Id 및 count
    fun getMoreViewLastId(userEmail: String, identifer: String, value: String){
        viewModelScope.launch {
            runCatching {  getRemoteMoreLastIdUseCase(userEmail, identifer, value)}
                .onSuccess {
                    it.collectLatest {
                        _lastId.value = it
                    }
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("moreViewLastId 서버 통신 실패!")
                }
        }
    }

    //최신순 마지막 ID 및 count
    fun getMoreNewViewLastId(userEmail:String, identifier: String, value : String){
        viewModelScope.launch {
            runCatching { getRemoteMoreNewLastIdUseCase.execute(userEmail, identifier, value) }
                .onSuccess {
                    _lastId.value = it
                    Timber.d("moreNewViewLastId 서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("moreNewViewLastId 서버 통신 실패")
                }
        }

    }

    //인기순 무한 스크롤
    fun getPreview(userEmail: String, identifer: String, postId : Int, count : Int, value: String){
        viewModelScope.launch {
            runCatching { getRemoteMoreViewInfiniteUseCase.execute(userEmail, identifer, postId, count, value) }
                .onSuccess {
                    _lastId.value = LastId(it.lastCount, it.lastId)
                    _drive.value = it.drive
                    Timber.d("moreViewInfinite 서버 통신 성공!")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("moreViewInfinite 서버 통신 실패!")
                }
        }
    }

    //최신순 무한 스크롤
    fun getNewPreview(userEmail: String, identifer: String, postId : Int, value: String){
        viewModelScope.launch {
            runCatching { getRemoteMoreNewViewInfiniteUseCase.execute(userEmail, identifer, postId, value) }
                .onSuccess {
                    _lastId.value = LastId(it.lastCount, it.lastId)
                    newDrive.value = it.drive
                    Timber.d("moreNewViewInfinite 서버 통신 성공!")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("moreNewViewInfinite 서버 통신 실패!")
                }
        }


    }
}