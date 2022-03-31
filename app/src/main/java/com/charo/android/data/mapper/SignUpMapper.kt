package com.charo.android.data.mapper

import com.charo.android.data.model.response.ResponseStatusCode
import com.charo.android.data.model.response.signup.ResponseCertificationData
import com.charo.android.data.model.response.signup.ResponseEmailCheckData
import com.charo.android.data.model.response.signup.ResponseNickNameCheckData
import com.charo.android.data.model.response.signup.ResponseRegisterData
import com.charo.android.domain.model.StatusCode


object SignUpMapper {
    fun mapperToEmailCheck(responseEmailCheckData: ResponseEmailCheckData) : Boolean {
        return responseEmailCheckData.success
    }


    fun mapperToEmailCertification(responseEmailCertificationData: ResponseCertificationData) : String{
        return responseEmailCertificationData.data

    }

    fun mapperToRegister(responseRegisterData: ResponseRegisterData) : Boolean{
        return responseRegisterData.success
    }

    fun mapperToNickNameCheck(responseNickNameCheckData: ResponseNickNameCheckData) : Boolean{
        return responseNickNameCheckData.success
    }

    fun mapperToSocialSignUp(responseStatusCode: ResponseStatusCode) : StatusCode {
        return StatusCode(
            success = responseStatusCode.success
        )

    }
}