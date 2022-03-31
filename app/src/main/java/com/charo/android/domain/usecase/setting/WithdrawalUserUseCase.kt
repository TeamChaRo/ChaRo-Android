package com.charo.android.domain.usecase.setting

import com.charo.android.domain.model.StatusCode
import com.charo.android.domain.repository.setting.SettingRepository

class WithdrawalUserUseCase(private val repository : SettingRepository) {

    suspend fun execute(userEmail: String) : StatusCode {
        return repository.withdrawalUser(userEmail)
    }
}