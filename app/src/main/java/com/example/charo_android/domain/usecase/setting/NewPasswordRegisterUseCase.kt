package com.example.charo_android.domain.usecase.setting

import com.example.charo_android.domain.model.StatusCode
import com.example.charo_android.domain.repository.setting.SettingRepository

class NewPasswordRegisterUseCase(private val repository : SettingRepository) {

    suspend fun execute(userEmail : String, newPassword : String) : StatusCode{
        return repository.newPasswordRegister(userEmail, newPassword)
    }
}