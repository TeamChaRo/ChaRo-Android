package com.charo.android.data.datasource.remote.signup

import com.charo.android.data.model.request.RequestCertificationData
import com.charo.android.data.model.response.signup.ResponseCertificationData


interface RemoteSignUpEmailCertificationDataSource {
    suspend fun emailCertification(userEmail: RequestCertificationData) : ResponseCertificationData
}