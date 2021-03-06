package com.charo.android.presentation.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

// νμΈμλ§
open class NonNullLiveData<T : Any>(value: T) : LiveData<T>(value) {
    override fun getValue(): T {
        return super.getValue() as T
    }

    inline fun observe(owner: LifecycleOwner, crossinline observer: (t: T) -> Unit) {
        this.observe(owner, Observer { it?.let(observer) })
    }
}