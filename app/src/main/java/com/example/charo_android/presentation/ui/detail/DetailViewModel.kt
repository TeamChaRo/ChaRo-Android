package com.example.charo_android.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.charo_android.data.model.detail.ResponseDetailData

class DetailViewModel: ViewModel() {
    private var _data = MutableLiveData<ResponseDetailData.Data>(null)
    val data: LiveData<ResponseDetailData.Data> get() = _data
}