package com.charo.android.presentation.ui.signup.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charo.android.data.model.request.signup.RequestSignUpGoogleData
import com.charo.android.data.model.request.signup.RequestSignUpKaKaoData
import com.charo.android.domain.model.StatusCode
import com.charo.android.domain.usecase.signup.*
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSink
import timber.log.Timber

class SignUpEmailViewModel(
    private val getRemoteSignUpEmailCheckUseCase: GetRemoteSignUpEmailCheckUseCase,
    private val getRemoteSignUpEmailCertificationUseCase: GetRemoteSignUpEmailCertificationUseCase,
    private val getRemoteSignUpNickNameCheckUseCase: GetRemoteSignUpNickNameCheckUseCase,
    private val postRemoteSignUpRegisterUseCase: PostRemoteSignUpRegisterUseCase,
    private val PostRemoteSocialSIgnUpRegisterUseCase : PostRemoteSocialSignUpRegisterUseCase,
    private val postRemoteKaKaoSignUpRegisterUseCase: PostRemoteKaKaoSignUpRegisterUseCase
) : ViewModel() {
    private val _isConfirmAuthNum = MutableLiveData<Boolean>(false)
    val isConfirmAuthNum: MutableLiveData<Boolean>
        get() = _isConfirmAuthNum

    //이메일 중복체크 확인
    private val _success = MutableLiveData<Boolean>(false)
    val success: LiveData<Boolean>
        get() = _success

    //이메일 중복체크 서버통신
    private val _emailCheckStatus = MutableLiveData<Int>()
    val emailCheckStatus: LiveData<Int>
        get() = _emailCheckStatus

    //인증번호
    private val _data = MutableLiveData<String>()
    val data: MutableLiveData<String>
        get() = _data

    //인증번호 발송 설종여부
    private val _authNumSuccess = MutableLiveData<Boolean>()
    val authNumSuccess: MutableLiveData<Boolean>
        get() = _authNumSuccess

    private val _nickNameCheck = MutableLiveData<Boolean>()
    val nickNameCheck: LiveData<Boolean>
        get() = _nickNameCheck

    private val _registerSuccess = MutableLiveData<Boolean>()
    val registerSuccess: LiveData<Boolean>
        get() = _registerSuccess

    //구글 회원가입 데이터
    private val _googleRegisterSuccess = MutableLiveData<StatusCode>()
    val googleRegisterSuccess: LiveData<StatusCode>
        get() = _googleRegisterSuccess

    //카카오 회원가입 데이터
    private val _kakaoRegisterSuccess = MutableLiveData<StatusCode>()
    val kakaoRegisterSuccess: LiveData<StatusCode>
        get() = _kakaoRegisterSuccess

    //회원가입시 필요한 것
    val profileImage = MutableLiveData<Uri>()
    val pushAgree = MutableLiveData<Boolean>()
    val emailAgree = MutableLiveData<Boolean>()
    val userEmail = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val nickName = MutableLiveData<String>()

    //소셜 로그인으로 회원가입 구분
    val socialLoginNum = MutableLiveData<Int>()
    val googleProfileImage = MutableLiveData<String>()

    //이메일 중복 체크
    fun emailCheck(email: String) {
        viewModelScope.launch {
            runCatching { getRemoteSignUpEmailCheckUseCase.execute(email) }
                .onSuccess {
                    _success.value = it
                    _emailCheckStatus.value = 2000
                    Timber.d("signUp emailCheck 서버 통신 성공!")
                    Timber.d("signUp emailCheck $it")
                }
                .onFailure {
                    it.printStackTrace()
                    _success.value = false
                    Timber.d("signUp emailCheck 서버 통신 실패")
                }
        }
    }

    //이메일 인증
    fun emailCertification(userEmail: String) {
        viewModelScope.launch {
            runCatching { getRemoteSignUpEmailCertificationUseCase.execute(userEmail) }
                .onSuccess {
                    _authNumSuccess.value = true
                    _data.value = it
                    Timber.d("signUps 서버 통신 성공!")
                    Timber.d("signUp $it")
                }
                .onFailure {
                    _authNumSuccess.value = false
                    _data.value = ""
                    it.printStackTrace()
                    Timber.d("signUps 서버 통신 실패!")

                }
        }
    }
    //닉네임 중복 체크
    fun nickNameCheck(nickname: String) {
        viewModelScope.launch {
            runCatching { getRemoteSignUpNickNameCheckUseCase.execute(nickname) }
                .onSuccess {
                    _nickNameCheck.value = it
                    Timber.d("nickname 서버 통신 성공!")
                    Timber.d("nickname $it")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("nickname 서버 통신 실패!")
                }
        }

    }
    //일반 회원가입 등록
    fun signUpRegister(
        image: Uri,
        userEmail: String,
        password: String,
        nickname: String,
        pushAgree: Boolean,
        emailAgree: Boolean,
        context : Context
    ) {
        val userEmailRequestBody: RequestBody = userEmail.toRequestBody("text/plain".toMediaTypeOrNull())
         val passwordRequestBody: RequestBody = password.toRequestBody("text/plain".toMediaTypeOrNull())
        val nicknameRequestBody: RequestBody = nickname.toRequestBody("text/plain".toMediaTypeOrNull())
        val pushAgreeRequestBody : RequestBody = pushAgree.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val emailAgreeRequestBody : RequestBody = emailAgree.toString().toRequestBody("text/plain".toMediaTypeOrNull())
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
            runCatching {postRemoteSignUpRegisterUseCase.execute(
                imageMultiPartBody,
                userEmailRequestBody,
                passwordRequestBody,
                nicknameRequestBody,
                pushAgreeRequestBody,
                emailAgreeRequestBody
            )}
                .onSuccess {
                    _registerSuccess.value = it
                    Timber.d("register 서버 통신 성공!")
                }
                .onFailure {
                    Timber.d("register 서버 통신 실패!")
                    Timber.d("register ${it.printStackTrace()}")
                }
        }
    }
    inner class BitmapRequestBody(private val bitmap: Bitmap) : RequestBody(){
        override fun contentType(): MediaType? = "image/jpeg".toMediaType()

        override fun writeTo(sink: BufferedSink) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, sink.outputStream())
        }
    }

    //구글 회원가입 등록
    fun signUpGoogle(userEmail: String, googleProfileImage:String, pushAgree : Boolean, emailAgree: Boolean){
        viewModelScope.launch {
            runCatching { PostRemoteSocialSIgnUpRegisterUseCase.execute(
                RequestSignUpGoogleData(userEmail, googleProfileImage, pushAgree, emailAgree)
            )
            }
                .onSuccess {
                    _googleRegisterSuccess.value = it
                    Timber.d("googleSignUp 구글 회원가입 성공!")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("googleSignUp 구글 회원가입 실패!")
                }
        }
    }

    //카카오 회원가입 등록
    fun signUpKaKao(userEmail : String, profileImage : String, nickName : String, pushAgree : Boolean, emailAgree: Boolean ){
        viewModelScope.launch {
            runCatching { postRemoteKaKaoSignUpRegisterUseCase(
                RequestSignUpKaKaoData(userEmail, profileImage, nickName, pushAgree, emailAgree)
            ) }
                .onSuccess {
                    _kakaoRegisterSuccess.value = it
                    Timber.d("kakaoSignUp 카카오 회원가입 성공!")
                }
                .onFailure {
                    Timber.d("kakaoSignUp 카카오 회원가입 실패!")
                }

        }
    }

}