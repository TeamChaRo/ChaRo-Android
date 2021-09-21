package com.example.charo_android
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

open class MaterialDialogFragment : DialogFragment() {

    private var dialogView: View? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext(), R.style.Dialog).apply {
            dialogView = onCreateView(LayoutInflater.from(requireContext()), null, savedInstanceState)

            dialogView?.let {
                onViewCreated(it, savedInstanceState)
            }
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            setView(dialogView)
        }.create()
    }

    override fun getView(): View? {
        return dialogView
    }
}