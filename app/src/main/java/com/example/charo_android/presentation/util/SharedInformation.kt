package com.example.charo_android.presentation.util

import android.content.Context
import androidx.core.content.ContextCompat

object SharedInformation {
    private const val SOCIAL_KEY = "SOCIAL_KEY"
    private const val APP_EMAIL = "APP_EMAIL"
    private const val THEME_NUM = "THEME_NUM"
    private const val KAKAO_SIGN_UP = "KAKAO_SIGN_UP"
    private const val SIGN_UP = "SIGN_UP"
    private const val LOG_OUT = "LOG_OUT"
    private const val APP_PASSWORD = "APP_PASSWORD"

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

    //이메일 저장

    fun getEmail(context: Context): String{
        val sharedPreferences = context.getSharedPreferences(APP_EMAIL, Context.MODE_PRIVATE)
        return sharedPreferences.getString(APP_EMAIL,  "@") ?: ""
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
    //패스워드 저장
    fun getPassword(context: Context) : String{
        val sharedPreferences = context.getSharedPreferences(APP_PASSWORD, Context.MODE_PRIVATE)
        return sharedPreferences.getString(APP_PASSWORD,  "") ?: ""
    }

    fun setPassword(context: Context, password : String){
        val sharedPreferences = context.getSharedPreferences(APP_PASSWORD, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putString(APP_PASSWORD, password)
            .apply()
    }

    fun removePassword(context : Context){
        val sharedPreferences = context.getSharedPreferences(APP_PASSWORD, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .remove(APP_PASSWORD)
            .apply()
    }

    //닉네임 저장

    // 메인뷰 themeNum 저장(HomeThemeAdapter)
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

    //카카오 로그인 회원가입/로그아웃 해결
    fun setKaKaoSignUp(context: Context, kakaoSignUp : Int){
        val sharedPreferences = context.getSharedPreferences(KAKAO_SIGN_UP, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putInt(KAKAO_SIGN_UP, kakaoSignUp)
            .apply()
    }

    fun getKaKaoSignUp(context: Context): Int{
        val sharedPreferences = context.getSharedPreferences(KAKAO_SIGN_UP, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(KAKAO_SIGN_UP,  999)
    }


    //카카오,구글, 일반 로그인 회원가입
    fun setSignUp(context: Context, signUp : Int){
        val sharedPreferences = context.getSharedPreferences(SIGN_UP, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putInt(SIGN_UP, signUp)
            .apply()
    }
    fun getSignUp(context: Context): Int{
        val sharedPreferences = context.getSharedPreferences(SIGN_UP, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(SIGN_UP,  999)
    }


    //로그아웃
    fun setLogout(context: Context, logout : String ){
        val sharedPreferences = context.getSharedPreferences(LOG_OUT, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putString(LOG_OUT, logout)
            .apply()
    }

    fun getLogout(context: Context): String{
        val sharedPreferences = context.getSharedPreferences(SIGN_UP, Context.MODE_PRIVATE)
        return sharedPreferences.getString(LOG_OUT, "") ?: ""
    }

    fun removeLogout(context : Context){
        val sharedPreferences = context.getSharedPreferences(LOG_OUT, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .remove(LOG_OUT)
            .apply()
    }
}