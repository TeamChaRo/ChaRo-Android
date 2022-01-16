package com.example.charo_android.presentation.util

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import androidx.annotation.LayoutRes
import com.example.charo_android.R
import kotlinx.android.synthetic.main.custom_dialog_log_out.*
import kotlinx.android.synthetic.main.custom_dialog_withdrawal.*

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
    fun showDialog(@LayoutRes layout : Int){
        dialog.setContentView(layout)
        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawableResource(R.drawable.inset_right_40_left_40)
        dialog.show()


        dialog.text_ok.setOnClickListener {
            onClickedListener.onClicked(1)
            dialog.dismiss()
        }

        dialog.text_no.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun showWithdrawal(@LayoutRes layout : Int){
        dialog.setContentView(layout)
        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawableResource(R.drawable.inset_right_40_left_40)
        dialog.show()

        dialog.text_withdrawal_agreement.setOnClickListener {
            onClickedListener.onClicked(1)
            dialog.dismiss()
        }

        dialog.text_withdrawal_cancel.setOnClickListener {
            dialog.dismiss()
        }
    }


}