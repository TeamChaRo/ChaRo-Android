package com.example.charo_android.data.mapper

import com.example.charo_android.data.model.request.RequestCertificationData
import com.example.charo_android.data.model.response.signup.ResponseCertificationData
import com.example.charo_android.data.model.response.signup.ResponseEmailCheckData
import com.example.charo_android.domain.model.signup.Email
import com.example.charo_android.domain.model.signup.EmailCheck

object SignUpMapper {
    fun mapperToEmailCheck(responseEmailCheckData: ResponseEmailCheckData) : Boolean {
        return responseEmailCheckData.success
    }


    fun mapperToEmailCertification(responseEmailCertificationData: ResponseCertificationData) : String{
        return responseEmailCertificationData.data

    }

    fun mapperToEmailRequest(requestEmailCertificationData: RequestCertificationData) : String{
        return requestEmailCertificationData.userEmail
    }
}