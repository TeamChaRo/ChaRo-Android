package com.example.charo_android.presentation.ui.setting

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.view.isEmpty
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentSettingProfileUpdateBinding
import com.example.charo_android.presentation.base.BaseFragment
import com.example.charo_android.presentation.ui.setting.viewmodel.SettingViewModel
import kotlinx.android.synthetic.main.fragment_setting_profile_update.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.regex.Pattern


class SettingProfileUpdateFragment : BaseFragment<FragmentSettingProfileUpdateBinding>
    (R.layout.fragment_setting_profile_update) {
    private val settingViewModel: SettingViewModel by sharedViewModel()
    private var image = false
    private var text = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileCheckNickName()
        initBottomSheet()
        changeProfileImage()
        changeTabText()
        editTextFocusRemove()
        notGoNext()
    }


    private fun profileCheckNickName() {
        val nickNamePattern = "^[가-힣ㄱ-ㅎ]{0,5}$"
        with(binding) {
            img_profile_change_button.setOnClickListener {
                etSettingProfileChangeNickname.setText("")
            }

            etSettingProfileChangeNickname.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) =
                    if (s.toString().length > 5) {
                        text_input_profile_change_nick_name.error = "5자 이내로 작성해주세요"
                    } else if (!Pattern.matches(nickNamePattern, s.toString())) {
                        text_input_profile_change_nick_name.error = "한글만 사용해주세요"
                    } else if (s.toString().isEmpty()) {
                        if(image){
                            binding.imgProfileUpdateButton.setImageResource(R.drawable.sign_up_next)
                        }else{
                            binding.imgProfileUpdateButton.setImageResource(R.drawable.setting_no_next)
                        }
                        text_input_profile_change_nick_name.error = null
                        text_input_profile_change_nick_name.isErrorEnabled = false
                        text_input_profile_change_nick_name.isHelperTextEnabled = false

                    } else {
                        text = true
                        settingViewModel.profileNickNameCheck(s.toString())
                        settingViewModel.profileNickName.observe(viewLifecycleOwner) {
                            if (!it) {
                                text_input_profile_change_nick_name.error = "중복되는 닉네임이 존재합니다"
                            } else {
                                text_input_profile_change_nick_name.error = null
                                text_input_profile_change_nick_name.isErrorEnabled = false
                                text_input_profile_change_nick_name.isHelperTextEnabled = true
                                text_input_profile_change_nick_name.helperText = "사용 가능한 닉네임입니다"
                                settingViewModel.updateNickName.value =
                                    etSettingProfileChangeNickname.text.toString()
                                imgProfileUpdateButton.setImageResource(R.drawable.sign_up_next)
                            }
                        }
                    }
            })
        }
    }


    private fun initBottomSheet() {
        binding.imgProfileChange.setOnClickListener {
            val bottomSheet = SettingBottomSheetFragment()
            bottomSheet.show(activity?.supportFragmentManager!!, bottomSheet.tag)
        }
    }

    private fun changeProfileImage() {
        settingViewModel.profileChangeUri.observe(viewLifecycleOwner) {
            if (it != null) {
                image = true
                Glide.with(this)
                    .load(it)
                    .circleCrop()
                    .into(binding.imgProfileChange)
                Log.d("updateProfile", it.toString())
                binding.imgProfileUpdateButton.setImageResource(R.drawable.sign_up_next)

            } else {
                with(binding) {
                    imgProfileChange.setImageResource(R.drawable.setting_profile_update)
                    if(text){
                        imgProfileUpdateButton.setImageResource(R.drawable.sign_up_next)
                    }

                }

            }


        }

    }

    private fun changeTabText() {
        settingViewModel.updateTabText.value = "프로필 변경"
    }

    private fun editTextFocusRemove() {
        with(binding) {
            clSettingProfileUpdate.setOnClickListener {
                etSettingProfileChangeNickname.clearFocus()
                text_input_profile_change_nick_name.isHelperTextEnabled = false
            }
        }

    }

    private fun notGoNext() {
        settingViewModel.profileChangeUri.observe(viewLifecycleOwner) {
            if (it == null && binding.textInputProfileChangeNickName.isEmpty()) {
                binding.imgProfileUpdateButton.setImageResource(R.drawable.setting_no_next)
            }
        }
    }
}


