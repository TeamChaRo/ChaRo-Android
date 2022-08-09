package com.charo.android.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charo.android.domain.usecase.home.GetRemoteHomeTitle
import kotlinx.coroutines.launch
import timber.log.Timber

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

    //더보기뷰 뒤로 가기
    var moreFragment : MutableLiveData<Boolean> = MutableLiveData(false)

    //postId
    var postId : MutableLiveData<Int> = MutableLiveData()

    //themeNum
    var themeNum : MutableLiveData<Int> = MutableLiveData()

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
                    Timber.d("title 서버 통신 실패")
                }
        }
    }

}