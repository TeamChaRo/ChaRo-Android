package com.example.charo_android.presentation.util

import android.content.Context
import androidx.core.content.ContextCompat

object SharedInformation {
    private const val SOCIAL_KEY = "social_KEY"

    fun getSocialId(context: Context): String{
        val sharedPreferences = context.getSharedPreferences(
            "${context.packageName}.$SOCIAL_KEY",
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getString(SOCIAL_KEY, "") ?: ""
    }

    fun saveSocialId(context: Context, socialKey : String){
        val sharedPreferences = context.getSharedPreferences(
            "${context.packageName}.$SOCIAL_KEY",
            Context.MODE_PRIVATE
        )
        sharedPreferences.edit()
            .putString(SOCIAL_KEY, socialKey)
            .apply()
    }
}