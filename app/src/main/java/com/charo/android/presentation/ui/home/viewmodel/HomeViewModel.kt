package com.charo.android.presentation.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charo.android.R
import com.charo.android.data.model.request.home.RequestHomeLikeData
import com.charo.android.domain.model.StatusCode
import com.charo.android.domain.model.home.*
import com.charo.android.domain.usecase.home.*
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(
    private val getRemoteBannerUseCase: GetRemoteBannerUseCase,
    private val getRemoteCustomThemeUseCase: GetRemoteCustomThemeUseCase,
    private val getRemoteLocalDriveUseCase: GetRemoteLocalDriveUseCase,
    private val getRemoteTodayCharoDrive: GetRemoteTodayCharoDriveUseCase,
    private val getRemoteTrendDrive: GetRemoteTrendDriveUseCase,
    private val postRemoteHomeLikeUseCase: PostRemoteHomeLikeUseCase
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

    private val _statusCode = MutableLiveData<StatusCode>()
    val statusCode : LiveData<StatusCode>
        get() = _statusCode



    fun getBannerRoad() : List<BannerRoad>{
        return listOf(
            BannerRoad(
                R.drawable.road_1
            ),
            BannerRoad(
                R.drawable.road_2
            ),
            BannerRoad(
                R.drawable.road_3
            ),
            BannerRoad(
                R.drawable.road_4
            )
        )
    }


    fun getBanner(userEmail: String) {
        viewModelScope.launch {
            runCatching { getRemoteBannerUseCase.execute(userEmail) }

                .onSuccess {
                    _banner.value = it
                    Timber.d("new 서버 통신 성공!")
                    Timber.d("new $it")
                }

                .onFailure {
                    it.printStackTrace()
                    Timber.d("new 서버 통신 실패")
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
                    Timber.d("customtheme 서버 통신 성공!")
                    Timber.d("customtheme ${_theme.value.toString()}")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("new 서버 통신 실패")
                }
        }
    }

    fun getLocalDrive(userEmail: String) {
        viewModelScope.launch {
            runCatching { getRemoteLocalDriveUseCase.execute(userEmail) }
                .onSuccess {
                    _localDrive.value = it
                    Timber.d("new 서버 통신 성공!")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("new 서버 통신 실패")
                }
        }

    }


    fun getTodayCharoDrive(userEmail: String) {
        viewModelScope.launch {
            runCatching { getRemoteTodayCharoDrive.execute(userEmail) }
                .onSuccess {
                    _todayCharoDrive.value = it
                    Timber.d("new 서버 통신 성공!")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("new 서버 통신 실패")
                }
        }
    }

    fun getTrendDrive(userEmail: String) {
        viewModelScope.launch {
            runCatching { getRemoteTrendDrive.execute(userEmail) }
                .onSuccess {
                    _trendDrive.value = it
                    Timber.d("trend 서버 통신 성공!")

                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("trend 서버 통신 실패")

                }

        }
    }


    //Post 좋아요
    fun postLike(requestHomeLikeData: RequestHomeLikeData){
        viewModelScope.launch {
            runCatching { postRemoteHomeLikeUseCase.execute(requestHomeLikeData) }
                .onSuccess {
                    _statusCode.value = it
                    Timber.d("homeLike 서버 통신 성공!")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("homeLike 서버 통신 실패!")
                }
       }
    }

}