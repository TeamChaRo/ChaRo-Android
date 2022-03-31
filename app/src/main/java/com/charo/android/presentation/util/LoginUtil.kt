package com.charo.android.presentation.util

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import com.charo.android.presentation.ui.signin.SocialSignInActivity


object LoginUtil{

    fun loginPrompt(mContext: Context) {
        val builder = AlertDialog.Builder(mContext)
        builder.setMessage("로그인 후 이용 가능한 서비스입니다. 로그인 화면으로 이동하시겠습니까?")
        builder.setCancelable(false)
        builder.setPositiveButton("예") { dialog, which ->
            val intent = Intent(mContext, SocialSignInActivity::class.java)
            mContext.startActivity(intent)
        }
        builder.setNegativeButton("아니요") { dialog, which -> }
        builder.show()
    }
}