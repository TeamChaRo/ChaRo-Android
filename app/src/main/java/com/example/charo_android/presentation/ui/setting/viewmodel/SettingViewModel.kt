package com.example.charo_android.presentation.ui.setting.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.charo_android.domain.model.setting.ProfileChangeData
import com.example.charo_android.domain.model.setting.ProfileRequestChangeData
import com.example.charo_android.domain.usecase.setting.ProfileImageChangeUseCase
import com.example.charo_android.domain.usecase.setting.ProfileNickNameChangeUseCase
import com.example.charo_android.domain.usecase.signup.GetRemoteSignUpNickNameCheckUseCase
import com.example.charo_android.presentation.ui.signup.viewmodel.SignUpEmailViewModel
import com.example.charo_android.presentation.util.NonNullLiveData
import com.example.charo_android.presentation.util.NonNullMutableLiveData
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSink

class SettingViewModel(
    private val getRemoteProfileNickNameCheckUseCase: GetRemoteSignUpNickNameCheckUseCase,
    private val profileImageChangeUseCase : ProfileImageChangeUseCase,
    private val profileNickNameChangeUseCase: ProfileNickNameChangeUseCase
) : ViewModel() {


    //중복 닉네임 체크
    private val _profileNickName : MutableLiveData<Boolean> = MutableLiveData(false)
    var profileNickName : LiveData<Boolean> = _profileNickName


    val userId = MutableLiveData<String>()

    // 변경 uri
    val profileChangeUri = MutableLiveData<Uri>()


    private val _updateNickName : MutableLiveData<String> = MutableLiveData()
    val updateNickName : LiveData<String> = _updateNickName

    // toolbar 텍스트
    val updateTabText = MutableLiveData<String>()

    // 받아오는 프로필 이미지
    val originProfileUri = MutableLiveData<String>("newnew")

    // 프로필 변경시 success
    private val _profileChangeData : MutableLiveData<ProfileChangeData> = MutableLiveData<ProfileChangeData>()
    val profileChangeData : LiveData<ProfileChangeData> = _profileChangeData

    // 프로필 변경, 닉네임만 변경 체크
    var numCheck : MutableLiveData<Int> = MutableLiveData()


    fun profileNickNameCheck(nickname: String) {
        viewModelScope.launch {
            runCatching { getRemoteProfileNickNameCheckUseCase.execute(nickname) }
                .onSuccess {
                   _profileNickName.value = it
                    Log.d("nickname", "서버 통신 성공!")
                    Log.d("nickname", it.toString())
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("nickname", "서버 통신 실패!")
                }
        }
    }
    // 둘다 변경 시 또는 프로필 이미지만 변경 시
    fun profileImageChange(
        userEmail: String,
        image: Uri,
        originImage: String,
        newNickname: String,
        context : Context
    ){
        val originImageRequestBody : RequestBody = originImage.toRequestBody("text/plain".toMediaTypeOrNull())
        val newNicknameRequestBody: RequestBody = newNickname.toRequestBody("text/plain".toMediaTypeOrNull())
        val bitmap : Bitmap

        if(Build.VERSION.SDK_INT < 28) {
            bitmap = MediaStore.Images.Media.getBitmap(
                context.contentResolver,
                image
            )
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, image)
            bitmap = ImageDecoder.decodeBitmap(source)
        }

        val imageRequestBody = bitmap?.let { BitmapRequestBody(it) }
        val imageMultiPartBody: MultipartBody.Part =
            MultipartBody.Part.createFormData("image", "image.jpeg", imageRequestBody)

        viewModelScope.launch {
            runCatching { profileImageChangeUseCase.execute(
                userEmail,
                imageMultiPartBody,
                originImageRequestBody,
                newNicknameRequestBody
            )  }
                .onSuccess {
                    _profileChangeData.value = it
                    Log.d("profileChange", "서버 통신 성공")
                }
                .onFailure {
                    Log.d("profileChange", "서버 통신 실패패")
               }
        }
    }
    //닉네임만 변경 시
    fun profileNickNameChange(
        userEmail: String,
        profileImage: String,
        newNickname: String
    ){
        val profileImageRequestBody : RequestBody = profileImage.toRequestBody("text/plain".toMediaTypeOrNull())
        val newNickNameRequestBody : RequestBody = newNickname.toRequestBody("text/plain".toMediaTypeOrNull())

        viewModelScope.launch {
            runCatching { profileNickNameChangeUseCase.execute(
                userEmail,
                profileImageRequestBody,
                newNickNameRequestBody
            ) }
                .onSuccess {
                    _profileChangeData.value = it
                    Log.d("profileChange", "서버 통신 성공")
                }
                .onFailure {
                    Log.d("profileChange", "서버 통신 실패")
                }
        }
    }



    inner class BitmapRequestBody(private val bitmap: Bitmap) : RequestBody(){
        override fun contentType(): MediaType? = "image/jpeg".toMediaType()


        override fun writeTo(sink: BufferedSink) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, sink.outputStream())
        }
    }

}