package com.example.charo_android.presentation.ui.setting.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.charo_android.domain.usecase.signup.GetRemoteSignUpNickNameCheckUseCase
import kotlinx.coroutines.launch

class SettingViewModel(
    private val getRemoteProfileNickNameCheckUseCase: GetRemoteSignUpNickNameCheckUseCase
) : ViewModel() {


    private val _profileNickName = MutableLiveData<Boolean>()
    val profileNickName: LiveData<Boolean>
        get() = _profileNickName

    val userId = MutableLiveData<String>()

    val profileChangeUri = MutableLiveData<Uri>()

    val updateNickName = MutableLiveData<String>()

    val updateTabText = MutableLiveData<String>()

    fun profileNickNameCheck(nickname: String) {
        viewModelScope.launch {
            runCatching { getRemoteProfileNickNameCheckUseCase.execute(nickname) }
                .onSuccess {
                    _profileNickName.value = it
                    Log.d("nickname", "서버 통신 성공!")
                    Log.d("nickname", it.toString())
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("nickname", "서버 통신 실패!")
                }
        }

    }
}