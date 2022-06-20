package com.charo.android.presentation.util

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.provider.Settings
import com.charo.android.R

object SharedInformation {
    var searchWrite: Boolean = false
    var notRequestAllow: Boolean = true

    private const val SOCIAL_KEY = "SOCIAL_KEY"
    private const val APP_EMAIL = "APP_EMAIL"
    private const val THEME_NUM = "THEME_NUM"
    private const val KAKAO_SIGN_UP = "KAKAO_SIGN_UP"
    private const val SIGN_UP = "SIGN_UP"
    private const val LOG_OUT = "LOG_OUT"
    private const val APP_PASSWORD = "APP_PASSWORD"
    private const val NICKNAME = "NICK_NAME"
    private const val ON_BOARDING = "ONBOARDING"
    private const val PROFILE_IMAGE = "PROFILE_IMAGE"
    private const val PERMISSION_QUESTION_NEVER = "PERMISSION_QUESTION_NEVER"

    // 카카오, 구글, 일반 로그아웃 구분 (카카오 : 1, 구글 : 2, 일반 : 3)
    fun getSocialId(context: Context): String {
        val sharedPreferences = context.getSharedPreferences(SOCIAL_KEY, Context.MODE_PRIVATE)
        return sharedPreferences.getString(SOCIAL_KEY, "") ?: ""
    }

    fun saveSocialId(context: Context, socialKey: String) {
        val sharedPreferences = context.getSharedPreferences(SOCIAL_KEY, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putString(SOCIAL_KEY, socialKey)
            .apply()
    }

    fun removeSocialId(context: Context) {
        val sharedPreferences = context.getSharedPreferences(SOCIAL_KEY, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .remove(SOCIAL_KEY)
            .apply()
    }

    //이메일 저장

    fun getEmail(context: Context): String {
        val sharedPreferences = context.getSharedPreferences(APP_EMAIL, Context.MODE_PRIVATE)
        return sharedPreferences.getString(APP_EMAIL, "@") ?: ""
    }

    fun setEmail(context: Context, appEmail: String?) {
        val sharedPreferences = context.getSharedPreferences(APP_EMAIL, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putString(APP_EMAIL, appEmail)
            .apply()
    }

    fun removeEmail(context: Context) {
        val sharedPreferences = context.getSharedPreferences(APP_EMAIL, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .remove(APP_EMAIL)
            .apply()
    }

    // 프로필 이미지
    fun getProfileImage(context: Context): String? {
        val sharedPresentException =
            context.getSharedPreferences(PROFILE_IMAGE, Context.MODE_PRIVATE)
        return sharedPresentException.getString(PROFILE_IMAGE, null)
    }

    fun setProfileImage(context: Context, profileImage: String?) {
        val sharedPresentException =
            context.getSharedPreferences(PROFILE_IMAGE, Context.MODE_PRIVATE)
        sharedPresentException.edit()
            .putString(PROFILE_IMAGE, profileImage)
            .apply()
    }

    fun removeProfileImage(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PROFILE_IMAGE, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .remove(PROFILE_IMAGE)
            .apply()
    }

    //닉네임 저장
    fun setNickName(context: Context, nickName: String) {
        val sharedPreferences = context.getSharedPreferences(NICKNAME, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putString(NICKNAME, nickName)
            .apply()
    }

    fun getNickName(context: Context): String {
        val sharedPreferences = context.getSharedPreferences(NICKNAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(NICKNAME, "") ?: ""
    }

    fun removeNickName(context: Context) {
        val sharedPreferences = context.getSharedPreferences(NICKNAME, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .remove(NICKNAME)
            .apply()

    }


    //패스워드 저장
    fun getPassword(context: Context): String {
        val sharedPreferences = context.getSharedPreferences(APP_PASSWORD, Context.MODE_PRIVATE)
        return sharedPreferences.getString(APP_PASSWORD, "") ?: ""
    }

    fun setPassword(context: Context, password: String) {
        val sharedPreferences = context.getSharedPreferences(APP_PASSWORD, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putString(APP_PASSWORD, password)
            .apply()
    }

    fun removePassword(context: Context) {
        val sharedPreferences = context.getSharedPreferences(APP_PASSWORD, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .remove(APP_PASSWORD)
            .apply()
    }

    //닉네임 저장

    // 메인뷰 themeNum 저장(HomeThemeAdapter)
    fun setThemeNum(context: Context, themeNum: Int) {
        val sharedPreferences = context.getSharedPreferences(THEME_NUM, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putInt(THEME_NUM, themeNum)
            .apply()
    }

    fun getThemeNum(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(THEME_NUM, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(THEME_NUM, 99)
    }

    fun removeThemeNum(context: Context) {
        val sharedPreferences = context.getSharedPreferences(THEME_NUM, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .remove(THEME_NUM)
            .apply()
    }

    //카카오 로그인 회원가입/로그아웃 해결
    fun setKaKaoSignUp(context: Context, kakaoSignUp: Int) {
        val sharedPreferences = context.getSharedPreferences(KAKAO_SIGN_UP, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putInt(KAKAO_SIGN_UP, kakaoSignUp)
            .apply()
    }

    fun getKaKaoSignUp(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(KAKAO_SIGN_UP, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(KAKAO_SIGN_UP, 999)
    }


    //카카오,구글(1), 일반 로그인 회원가입
    fun setSignUp(context: Context, signUp: Int) {
        val sharedPreferences = context.getSharedPreferences(SIGN_UP, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putInt(SIGN_UP, signUp)
            .apply()
    }

    fun getSignUp(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(SIGN_UP, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(SIGN_UP, 999)
    }


    //로그아웃
    fun setLogout(context: Context, logout: String) {
        val sharedPreferences = context.getSharedPreferences(LOG_OUT, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putString(LOG_OUT, logout)
            .apply()
    }

    fun getLogout(context: Context): String {
        val sharedPreferences = context.getSharedPreferences(SIGN_UP, Context.MODE_PRIVATE)
        return sharedPreferences.getString(LOG_OUT, "") ?: ""
    }

    fun removeLogout(context: Context) {
        val sharedPreferences = context.getSharedPreferences(LOG_OUT, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .remove(LOG_OUT)
            .apply()
    }

    // 온보딩
    fun setOnBoarding(context: Context) {
        val sharedPreferences =
            context.getSharedPreferences(ON_BOARDING, Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putBoolean(ON_BOARDING, true)
            .apply()
    }

    fun getOnBoarding(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(ON_BOARDING, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(ON_BOARDING, false)
    }

    //권한 설정 다시 묻지 않기 여부
    fun setPermissionNever(context: Context, never: Boolean) {
        val sharedPresentException =
            context.getSharedPreferences(PERMISSION_QUESTION_NEVER, Context.MODE_PRIVATE)
        sharedPresentException.edit()
            .putBoolean(PERMISSION_QUESTION_NEVER, never)
            .apply()
    }

    fun getPermissionNever(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(PERMISSION_QUESTION_NEVER, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(PERMISSION_QUESTION_NEVER, false)
    }

    fun showForceRequestPermission(context: Context){
        if(getPermissionNever(context)){ //다시묻지않기
            val builder = AlertDialog.Builder(context, R.style.Dialog)
            builder.setMessage(context.getString(R.string.txt_need_permission) + "\n" + context.getString(R.string.txt_go_permission_setting))
            builder.setCancelable(false)
            builder.setPositiveButton("예") { dialog, which ->
                val intent = Intent(Settings.ACTION_APPLICATION_SETTINGS)
                context.startActivity(intent)
            }
            builder.setNegativeButton("아니요") { dialog, which ->
                setPermissionNever(context, true)
            }
            builder.show()
        }
    }
}