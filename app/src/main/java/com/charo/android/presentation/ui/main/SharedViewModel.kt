package com.charo.android.presentation.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.charo_android.domain.usecase.home.GetRemoteHomeTitle
import kotlinx.coroutines.launch

class SharedViewModel(
    private val getRemoteHomeTitle: GetRemoteHomeTitle
) : ViewModel() {

    val num = MutableLiveData<Int>()

    private val _customThemeTitle = MutableLiveData<String>()
    val customThemeTitle : LiveData<String>
        get() = _customThemeTitle

    private val _localThemeTitle = MutableLiveData<String>()
    val localThemeTitle : LiveData<String>
        get() = _localThemeTitle

    //메인뷰 테마 프래그먼트 숫자
    var themeNum : MutableLiveData<Int> = MutableLiveData()


    //postId
    var postId : MutableLiveData<Int> = MutableLiveData()

    //로그인 유저 & 둘러보기 유저 구분
    var lookForEmail : MutableLiveData<String> = MutableLiveData()

    fun getHomeTitle(userEmail: String){
        viewModelScope.launch {
            kotlin.runCatching { getRemoteHomeTitle.execute(userEmail) }
                .onSuccess {
                    _customThemeTitle.value = it.customTitle
                    _localThemeTitle.value = it.localTitle

                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("title", "서버 통신 실패")
                }
        }
    }

}