package com.example.charo_android.presentation.ui.charo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CharoViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Charo Fragment"
    }
    val text: LiveData<String> = _text
}