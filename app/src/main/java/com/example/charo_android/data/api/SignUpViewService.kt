package com.example.charo_android.data.api

import com.example.charo_android.data.model.request.RequestEmailCheckData
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpViewService {
    @POST("/user/register/email")
    fun emailCheck(
        @Body body : RequestEmailCheckData

    )
}