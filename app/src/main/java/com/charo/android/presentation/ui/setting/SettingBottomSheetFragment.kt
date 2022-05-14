package com.charo.android.presentation.ui.setting

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.charo.android.R
import com.charo.android.databinding.FragmentSettingBottomSheetBinding
import com.charo.android.presentation.ui.setting.viewmodel.SettingViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber


class SettingBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding : FragmentSettingBottomSheetBinding? = null
    val binding get() = _binding!!
    private val settingViewModel: SettingViewModel by sharedViewModel()

    private val profileImageChange: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                settingViewModel.profileChangeUri.value = it.data?.data
                Timber.d("yam ${settingViewModel.profileChangeUri.value.toString()}")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting_bottom_sheet, container, false)
        clickBottomSheet()

        return binding.root

    }

    private fun clickBottomSheet(){
        binding.textBottomSheetNormal.setOnClickListener {
            settingViewModel.profileChangeUri.value = null
            dismiss()
        }
        binding.textBottomSheetLibrary.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            intent.setType("image/*")
            profileImageChange.launch(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}