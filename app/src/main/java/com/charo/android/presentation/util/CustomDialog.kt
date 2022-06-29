package com.charo.android.presentation.util

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil

import com.charo.android.R
import com.charo.android.databinding.CustomDialogWithdrawalBinding
import com.charo.android.databinding.DialogDetailDeleteBinding


class CustomDialog(context: Context) {
    private val dialog = Dialog(context)
    private lateinit var onClickedListener : ButtonClickListener
    interface ButtonClickListener{
        fun onClicked(num : Int)
    }
    fun setOnClickedListener(listener : ButtonClickListener){
        onClickedListener = listener
    }
    //로그아웃
    fun showDialog(context : Context,message : String){
        val binding = DataBindingUtil.inflate<DialogDetailDeleteBinding>(
            LayoutInflater.from(context),
            R.layout.dialog_detail_delete,
            null,
            false
        )
        dialog.setContentView(binding.root)
        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
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

    fun showWithdrawal(context : Context){
        val binding = DataBindingUtil.inflate<CustomDialogWithdrawalBinding>(
            LayoutInflater.from(context),
            R.layout.custom_dialog_withdrawal,
            null,
            false
        )
        dialog.setContentView(binding.root)
        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawableResource(R.drawable.inset_right_40_left_40)
        dialog.show()

        binding.textWithdrawalAgreement.setOnClickListener {
            onClickedListener.onClicked(1)
            dialog.dismiss()
        }

        binding.textWithdrawalAgreement.setOnClickListener {
            dialog.dismiss()
        }
    }


}