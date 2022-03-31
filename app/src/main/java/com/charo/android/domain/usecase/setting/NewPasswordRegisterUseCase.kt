package com.charo.android.domain.usecase.setting

import com.charo.android.domain.model.StatusCode
import com.charo.android.domain.repository.setting.SettingRepository


class NewPasswordRegisterUseCase(private val repository : SettingRepository) {

    suspend fun execute(userEmail : String, newPassword : String) : StatusCode {
        return repository.newPasswordRegister(userEmail, newPassword)
    }
}