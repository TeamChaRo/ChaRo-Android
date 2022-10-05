package com.charo.android.presentation.util

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.charo.android.R
import com.charo.android.databinding.CustomDialogWithdrawalBinding
import com.charo.android.databinding.DialogDetailDeleteBinding
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase


class CustomDialog(context: Context) {
    private val dialog = Dialog(context)
    private lateinit var onClickedListener: ButtonClickListener

    interface ButtonClickListener {
        fun onClicked(num: Int)
    }

    fun setOnClickedListener(listener: ButtonClickListener) {
        onClickedListener = listener
    }

    //로그아웃
    fun showDialog(context: Context, message: String) {
        val binding = DataBindingUtil.inflate<DialogDetailDeleteBinding>(
            LayoutInflater.from(context),
            R.layout.dialog_detail_delete,
            null,
            false
        )
        binding.dialogText = message
        dialog.setContentView(binding.root)
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(R.drawable.inset_right_40_left_40)
        dialog.show()


        binding.textOk.setOnClickListener {
            onClickedListener.onClicked(1)
            dialog.dismiss()
        }

        binding.textNo.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun showWithdrawal(context: Context) {
        val binding = DataBindingUtil.inflate<CustomDialogWithdrawalBinding>(
            LayoutInflater.from(context),
            R.layout.custom_dialog_withdrawal,
            null,
            false
        )
        dialog.setContentView(binding.root)
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(R.drawable.inset_right_40_left_40)
        dialog.show()

        binding.textWithdrawalAgreement.setOnClickListener {
            onClickedListener.onClicked(1)
            dialog.dismiss()
        }

        binding.textWithdrawalCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun showServerErrorDialog(context: Context): Dialog {
        val dialog = AlertDialog.Builder(context)
            .setTitle("문제가 발생했습니다.")
            .setMessage("서버 오류가 발생했습니다.\n앱을 종료합니다.")
            .setPositiveButton("앱 종료하고 나가기") { _, _ ->
                try {
                    throw Throwable()
                } catch (e: Exception) {
                    Firebase.crashlytics.recordException(Throwable("서버가 터져부러쓰 ㅋㅋ 안드로이드요"))
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }
            .create()
        return dialog
    }
}