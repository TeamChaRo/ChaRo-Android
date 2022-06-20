package com.charo.android.presentation.ui.setting

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.charo.android.R
import com.charo.android.databinding.FragmentSettingBottomSheetBinding
import com.charo.android.presentation.ui.setting.viewmodel.SettingViewModel
import com.charo.android.presentation.util.SharedInformation
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
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        val permissions: Array<String> =
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)

        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ){
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                ActivityCompat.requestPermissions(requireActivity(), permissions, 1)
            } else {
                ActivityCompat.requestPermissions(requireActivity(), permissions, 0)

                //SharedInformation 다시묻지않기
                SharedInformation.showForceRequestPermission(requireContext())
            }
        } else{
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            intent.setType("image/*")
            profileImageChange.launch(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            0 -> {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    SharedInformation.setPermissionNever(requireContext(), false)
                    Toast.makeText(requireActivity(), getString(R.string.txt_allow_permission), Toast.LENGTH_SHORT).show()
                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.type = MediaStore.Images.Media.CONTENT_TYPE
                    intent.setType("image/*")
                    profileImageChange.launch(intent)
                } else {
                    //다시묻지않기
                    SharedInformation.setPermissionNever(requireContext(), true)
                }
            }
            1 -> {
                //거부
                SharedInformation.setPermissionNever(requireContext(), false)
                Toast.makeText(requireActivity(), getString(R.string.txt_need_permission), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}