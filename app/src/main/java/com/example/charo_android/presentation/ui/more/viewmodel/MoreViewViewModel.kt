package com.example.charo_android.presentation.ui.more.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.charo_android.domain.model.more.LastId
import com.example.charo_android.domain.model.more.MoreDrive
import com.example.charo_android.domain.usecase.GetRemoteMoreDriveUseCase
import com.example.charo_android.domain.usecase.GetRemoteMoreLastIdUseCase
import com.example.charo_android.domain.usecase.GetRemoteMoreNewDriveUseCase
import kotlinx.coroutines.launch

class MoreViewViewModel(
    private val getRemoteMoreDriveUseCase: GetRemoteMoreDriveUseCase,
    private val getRemoteMoreLastIdUseCase: GetRemoteMoreLastIdUseCase,
    private val getRemoteMoreNewDriveViewUseCase: GetRemoteMoreNewDriveUseCase
) : ViewModel() {

    private val _drive = MutableLiveData<List<MoreDrive>>()
    val drive: LiveData<List<MoreDrive>>
        get() = _drive

    private val _lastId = MutableLiveData<LastId>()
    val lastId: LiveData<LastId>
        get() = lastId

    private val _newDrive = MutableLiveData<List<MoreDrive>>()
    val newDrive: LiveData<List<MoreDrive>>
        get() = _newDrive


    fun getMoreView(userEmail: String, identifer: String, value: String) {
        viewModelScope.launch {
            runCatching { getRemoteMoreDriveUseCase.execute(userEmail, identifer, value) }
                .onSuccess {
                    _drive.value = it
                    Log.d("more", "서버 통신 성공!")
                    Log.d("more", _drive.value.toString())
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("more", "서버 통신 실패")

                }

        }
    }

    fun getMoreNewView(userEmail: String, identifer: String, value: String) {
        viewModelScope.launch {
            runCatching { getRemoteMoreNewDriveViewUseCase.execute(userEmail, identifer, value) }
                .onSuccess {
                    _newDrive.value = it
                    Log.d("more", "서버 통신 성공!")
                    Log.d("more", _drive.value.toString())
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("more", "서버 통신 실패")

                }

        }
    }
}
