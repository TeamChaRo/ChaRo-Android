package com.example.charo_android.domain.usecase.setting

import com.example.charo_android.domain.model.StatusCode
import com.example.charo_android.domain.repository.setting.SettingRepository

class ProfilePasswordCheckUseCase(private val repository : SettingRepository) {

    suspend fun execute(userEmail : String, password : String) : StatusCode{
        return repository.originPasswordCheck(userEmail, password)
    }
}