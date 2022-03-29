package com.charo.android.presentation.ui.setting

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextUtils.replace
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentSettingProfileUpdateBinding
import com.example.charo_android.presentation.base.BaseFragment
import com.example.charo_android.presentation.ui.setting.viewmodel.SettingViewModel
import kotlinx.android.synthetic.main.fragment_setting_profile_update.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.regex.Pattern
import kotlin.properties.Delegates


class SettingProfileUpdateFragment : BaseFragment<FragmentSettingProfileUpdateBinding>
    (R.layout.fragment_setting_profile_update) {
    private val settingViewModel: SettingViewModel by sharedViewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initIsProfileNum()
        profileCheckNickName()
        initBottomSheet()
        changeTabText()
        editTextFocusRemove()
        changeProfileImage()
    }
    // isProfileUpdate 초기화

    private fun initIsProfileNum(){
        settingViewModel.images.value = false
        settingViewModel.nickName.value = false
        settingViewModel.buttonClick.value = false
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

                override fun afterTextChanged(s: Editable?) {
                    if (s.toString().length > 5) {
                        text_input_profile_change_nick_name.isErrorEnabled = true
                        text_input_profile_change_nick_name.error = "5자 이내로 작성해주세요"
                    } else if (!Pattern.matches(nickNamePattern, s.toString())) {
                        text_input_profile_change_nick_name.isErrorEnabled = true
                        text_input_profile_change_nick_name.error = "한글만 사용해주세요"
                    } else if (s.toString().isEmpty()) {
                        text_input_profile_change_nick_name.isErrorEnabled = false
                        text_input_profile_change_nick_name.isHelperTextEnabled = false
                        binding.imgProfileUpdateButton.setImageResource(R.drawable.setting_no_next)

                    } else {
                        //닉네임 중복 체크
                        settingViewModel.profileNickNameCheck(s.toString())
                        settingViewModel.profileNickName.observe(viewLifecycleOwner) {
                            if (!it) {
                                text_input_profile_change_nick_name.error = "중복되는 닉네임이 존재합니다"
                            } else {
                                text_input_profile_change_nick_name.isErrorEnabled = false
                                text_input_profile_change_nick_name.isHelperTextEnabled = true
                                text_input_profile_change_nick_name.helperText = "사용 가능한 닉네임입니다"
                                binding.imgProfileUpdateButton.setImageResource(R.drawable.sign_up_next)
                                Log.d("niceshot", s.toString())
                                binding.imgProfileUpdateButton.setOnClickListener {
                                    settingViewModel.newNickName.value = s.toString()
                                    settingViewModel.nickName.value = true
                                    settingViewModel.buttonClick.value = true
                                    changeSettingMain()
                                }
                            }
                        }
                        profileNickNameUpdateChange(s.toString())


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
        settingViewModel.profileChangeUri.observe(viewLifecycleOwner) { its ->
            if (its != null) {
                Glide.with(this)
                    .load(its)
                    .circleCrop()
                    .into(binding.imgProfileChange)
                Log.d("updateProfile", its.toString())
                settingViewModel.images.value = true
                binding.imgProfileUpdateButton.setImageResource(R.drawable.sign_up_next)
                binding.imgProfileUpdateButton.setOnClickListener {
                    settingViewModel.buttonClick.value = true
                    changeSettingMain()
                }
                profileImageUpdateChange(its, "")
                settingViewModel.newNickName.observe(viewLifecycleOwner){
                    profileImageUpdateChange(its, it)
                }
            } else {
                with(binding) {
                    imgProfileChange.setImageResource(R.drawable.setting_profile_update)
                    settingViewModel.images.value = false
                }
            }


        }

    }
    // 프로필 사진 변경했을 때(닉네임이랑 / 닉네임 없이)
    private fun profileImageUpdateChange( imageUri: Uri, s: String) {
        settingViewModel.isProfileUpdate.observe(viewLifecycleOwner) {
            Log.d("love", it.toString())
            if (it == 0) {
                settingViewModel.profileImageChange(
                    "and@naver.com",
                    imageUri,
                    "",
                    s,
                    requireActivity()
                )


            } else if (it == 2) {
                settingViewModel.profileImageChange(
                    "and@naver.com",
                    imageUri,
                    "",
                    "",
                    requireActivity()
                )

            }


        }
    }




    //닉네임만 변경했을 때
    private fun profileNickNameUpdateChange(s: String) {
        settingViewModel.isProfileUpdate.observe(viewLifecycleOwner) {
            Log.d("loves", it.toString())
            if (it == 1) {
                settingViewModel.profileNickNameChange(
                    "and@naver.com",
                    "",
                    s
                )

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

    //fragment 전환
    private fun changeSettingMain(){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.apply {
            replace(R.id.fragment_container_setting, SettingMainFragment())
            commit()
        }
    }


}




