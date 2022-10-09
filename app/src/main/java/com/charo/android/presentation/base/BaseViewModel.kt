package com.charo.android.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    private var _serverErrorOccurred: MutableLiveData<Boolean> = MutableLiveData(false)
    val serverErrorOccurred: LiveData<Boolean> get() = _serverErrorOccurred

    protected fun setServerErrorFlag(flag: Boolean) {
        _serverErrorOccurred.value = flag
    }
}