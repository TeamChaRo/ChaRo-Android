package com.example.charo_android.presentation.util

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import com.example.charo_android.presentation.ui.signin.SocialSignInActivity

object LoginUtil{

    fun loginPrompt(mContext: Context) {
        val builder = AlertDialog.Builder(mContext)
        builder.setMessage("로그인 후 이용 가능한 페이지입니다.")
        builder.setCancelable(false)
        builder.setPositiveButton("로그인하러 가기") { dialog, which ->
            val intent = Intent(mContext, SocialSignInActivity::class.java)
            mContext.startActivity(intent)
        }
        builder.setNegativeButton("나중에 보기") { dialog, which -> }
        builder.show()
    }
}