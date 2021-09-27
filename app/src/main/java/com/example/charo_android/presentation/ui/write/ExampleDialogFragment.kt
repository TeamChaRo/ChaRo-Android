package com.example.charo_android.presentation.ui.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.charo_android.R

class ExampleDialogFragment : MaterialDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_example, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.show_toast).setOnClickListener {
            Toast.makeText(requireContext(), "I am a toast!", Toast.LENGTH_LONG).show()
        }
    }
}
