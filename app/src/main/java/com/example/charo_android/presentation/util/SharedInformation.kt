package com.example.charo_android.presentation.util

import android.content.Context
import androidx.core.content.ContextCompat

object SharedInformation {
    private const val SOCIAL_KEY = "SOCIAL_KEY"
    private const val APP_EMAIL = "APP_EMAIL"
    private const val THEME_NUM = "THEME_NUM"

    // 카카오, 구글, 일반 로그아웃 구분 (카카오 : 1, 구글 : 2, 일반 : 0)
    fun getSocialId(context: Context): String{
        val sharedPreferences = context.getSharedPreferences(SOCIAL_KEY, Context.MODE_PRIVATE)
        return sharedPreferences.getString(SOCIAL_KEY, "") ?: ""
    }

    fun saveSocialId(context: Context, socialKey : String){
        val sharedPreferences = context.getSharedPreferences(SOCIAL_KEY, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putString(SOCIAL_KEY, socialKey)
            .apply()
    }

    fun removeSocialId(context: Context){
        val sharedPreferences = context.getSharedPreferences(SOCIAL_KEY, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .remove(SOCIAL_KEY)
            .apply()
    }

    fun getEmail(context: Context): String{
        val sharedPreferences = context.getSharedPreferences(APP_EMAIL, Context.MODE_PRIVATE)
        return sharedPreferences.getString(APP_EMAIL,  "") ?: ""
    }

    fun setEmail(context: Context, appEmail : String){
        val sharedPreferences = context.getSharedPreferences(APP_EMAIL, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putString(APP_EMAIL, appEmail)
            .apply()
    }

    fun removeEmail(context : Context){
        val sharedPreferences = context.getSharedPreferences(APP_EMAIL, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .remove(APP_EMAIL)
            .apply()
    }


    // 메인뷰 themeNum 저장
    fun setThemeNum(context: Context, themeNum : Int){
        val sharedPreferences = context.getSharedPreferences(THEME_NUM, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putInt(THEME_NUM, themeNum)
            .apply()
    }

    fun getThemeNum(context: Context): Int{
        val sharedPreferences = context.getSharedPreferences(THEME_NUM, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(THEME_NUM,  99)
    }

    fun removeThemeNum(context : Context){
        val sharedPreferences = context.getSharedPreferences(THEME_NUM, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .remove(THEME_NUM)
            .apply()
    }
}