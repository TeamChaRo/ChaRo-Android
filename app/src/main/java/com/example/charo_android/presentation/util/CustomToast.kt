package com.example.charo_android.presentation.util

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentWriteMapBinding
import com.example.charo_android.databinding.ToastMapCustomBinding
import com.example.charo_android.databinding.ToastPasswordUpdateCustomBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

object CustomToast {
    fun createToast(context: Context, message: String): Toast? {
        val inflater = LayoutInflater.from(context)
        var binding: ToastMapCustomBinding
            = DataBindingUtil.inflate(inflater, R.layout.toast_map_custom, null, false)

        binding.textToast.text = message

        return Toast(context).apply {
            setGravity(Gravity.CENTER, 0,0) //16.toPx
            duration =  Toast.LENGTH_LONG
            view = binding.root
        }
    }

    fun createPasswordUpdateToast(context: Context, message: String) : Toast? {
        val inflater = LayoutInflater.from(context)
        var binding: ToastPasswordUpdateCustomBinding
            = DataBindingUtil.inflate(inflater, R.layout.toast_password_update_custom, null, false)

        return Toast(context).apply {
            setGravity(Gravity.CENTER, 0, 0)
            duration = Toast.LENGTH_SHORT
            view = binding.root
        }
    }

    fun createDialog(context: Context): Dialog? {
        val inflater = LayoutInflater.from(context)
        var binding: ToastMapCustomBinding
                = DataBindingUtil.inflate(inflater, R.layout.toast_map_custom, null, false)

//        binding.textToast.text = message

        return Dialog(context).apply {
            val dialog = Dialog(context)
            dialog.setContentView(binding.root)
            dialog.show()
        }
    }
}