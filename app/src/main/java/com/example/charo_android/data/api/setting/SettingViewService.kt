package com.example.charo_android.data.api.setting

import com.example.charo_android.data.model.response.ResponseStatusCode
import com.example.charo_android.data.model.response.setting.ResponseProfileUpdateData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface SettingViewService {
    @Multipart
    @PUT("/user/{userEmail}")
    suspend fun profileChange(
        @Path("userEmail") userEmail : String,
        @Part image : MultipartBody.Part,
        @Part("originImage") originImage : RequestBody,
        @Part("newNickname") newNickname : RequestBody
    ) : ResponseProfileUpdateData

    @Multipart
    @PUT("/user/{userEmail}")
    suspend fun nickNameChange(
        @Path("userEmail") userEmail: String,
        @Part("profileImage") profileImage : RequestBody,
        @Part("newNickname") newNickname: RequestBody
    ) : ResponseProfileUpdateData

    @DELETE("/user/{userEmail}")
    suspend fun withdrawalUser(
        @Path("userEmail") userEmail : String
    ) : ResponseStatusCode

    @GET("/user/password")
    suspend fun originPasswordCheck(
        @Query("userEmail") userEmail : String,
        @Query("password") password : String
    ) : ResponseStatusCode
}