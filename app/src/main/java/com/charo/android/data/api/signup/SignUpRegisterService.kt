package com.charo.android.data.api.signup


import com.charo.android.data.model.request.signup.RequestSignUpGoogleData
import com.charo.android.data.model.request.signup.RequestSignUpKaKaoData
import com.charo.android.data.model.response.ResponseStatusCode
import com.charo.android.data.model.response.signup.ResponseRegisterData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SignUpRegisterService {
    @Multipart
    @POST("/user/register")
    suspend fun signUpRegister(
        @Part image : MultipartBody.Part,
        @Part ("userEmail") userEmail: RequestBody,
        @Part("password") password: RequestBody,
        @Part("nickname") nickname : RequestBody,
        @Part("pushAgree") pushAgree : RequestBody,
        @Part("emailAgree") emailAgree : RequestBody,

        ) : ResponseRegisterData

    @POST("/user/register/google")
    suspend fun signUpGoogleRegister(
        @Body requestSignUpSocialData : RequestSignUpGoogleData
    ) : ResponseStatusCode

    //카카오
    @POST("/user/register/kakao")
    suspend fun signUpKaKaoRegister(
        @Body requestSignUpKaKaoData: RequestSignUpKaKaoData
    ) : ResponseStatusCode
}
