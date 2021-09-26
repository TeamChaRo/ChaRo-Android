package com.example.charo_android.presentation.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.charo_android.domain.model.home.*
import com.example.charo_android.domain.usecase.home.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getRemoteBannerUseCase: GetRemoteBannerUseCase,
    private val getRemoteCustomThemeUseCase: GetRemoteCustomThemeUseCase,
    private val getRemoteLocalDriveUseCase: GetRemoteLocalDriveUseCase,
    private val getRemoteTodayCharoDrive: GetRemoteTodayCharoDriveUseCase,
    private val getRemoteTrendDrive: GetRemoteTrendDriveUseCase,

) : ViewModel() {

    private val _banner = MutableLiveData<List<Banner>>()
    val banner: LiveData<List<Banner>>
        get() = _banner

    private val _customThemeDrive = MutableLiveData<List<CustomThemeDrive>>()
    val customThemeDrive: LiveData<List<CustomThemeDrive>>
        get() = _customThemeDrive

    private val _localDrive = MutableLiveData<List<LocalDrive>>()
    val localDrive: LiveData<List<LocalDrive>>
        get() = _localDrive

    private val _todayCharoDrive = MutableLiveData<List<TodayCharoDrive>>()
    val todayCharoDrive: LiveData<List<TodayCharoDrive>>
        get() = _todayCharoDrive

    private val _trendDrive = MutableLiveData<List<TrendDrive>>()
    val trendDrive: LiveData<List<TrendDrive>>
        get() = _trendDrive

    private val _theme = MutableLiveData<List<Theme>>()
    val theme : LiveData<List<Theme>>
        get() = _theme






     fun getBanner(userEmail: String) {
        viewModelScope.launch {
            runCatching { getRemoteBannerUseCase.execute(userEmail) }

                .onSuccess {
                    _banner.value = it
                    Log.d("new", "서버 통신 성공!")
                    Log.d("new", it.toString())
                }

                .onFailure {
                    it.printStackTrace()
                    Log.d("new", "서버 통신 실패")
                }


        }
    }

     fun getCustomTheme(userEmail: String) {
        viewModelScope.launch {
            runCatching { getRemoteCustomThemeUseCase.execute(userEmail) }
                .onSuccess {
                    _customThemeDrive.value = it
                    _theme.value = it.map{
                        Theme(
                            theme = it.homeNightDriveChip_2.toString()
                        )
                    }
                    Log.d("customtheme", "서버 통신 성공!")
                    Log.d("customtheme", _theme.value.toString())
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("new", "서버 통신 실패")
                }
        }
    }

     fun getLocalDrive(userEmail: String) {
        viewModelScope.launch {
            runCatching { getRemoteLocalDriveUseCase.execute(userEmail) }
                .onSuccess {
                    _localDrive.value = it
                    Log.d("new", "서버 통신 성공!")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("new", "서버 통신 실패")
                }
        }

    }


     fun getTodayCharoDrive(userEmail: String) {
        viewModelScope.launch {
            runCatching { getRemoteTodayCharoDrive.execute(userEmail) }
                .onSuccess {
                    _todayCharoDrive.value = it
                    Log.d("new", "서버 통신 성공!")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("new", "서버 통신 실패")
                }
        }
    }

     fun getTrendDrive(userEmail: String) {
        viewModelScope.launch {
            runCatching { getRemoteTrendDrive.execute(userEmail) }
                .onSuccess { it ->
                    _trendDrive.value = it
                    Log.d("trend", "서버 통신 성공!")
                    Log.d("trend", _theme.value.toString())
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("trend", "서버 통신 실패")

                }

        }
    }



}




