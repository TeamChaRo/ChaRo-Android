package com.charo.android.presentation.ui.setting

import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.charo.android.R
import com.charo.android.databinding.FragmentSettingProfileUpdateBinding
import com.charo.android.presentation.base.BaseFragment
import com.charo.android.presentation.ui.setting.viewmodel.SettingViewModel
import com.charo.android.presentation.util.SharedInformation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import java.util.regex.Pattern


class SettingProfileUpdateFragment : BaseFragment<FragmentSettingProfileUpdateBinding>
    (R.layout.fragment_setting_profile_update) {
    private val settingViewModel: SettingViewModel by sharedViewModel()
    private var mLastClickTime = 0L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.profileViewModel = settingViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProfileImage()
        initIsProfileNum()
        profileCheckNickName()
        initBottomSheet()
        changeTabText()
        editTextFocusRemove()
        changeProfileImage()
    }

    private fun setProfileImage() {
        val image = SharedInformation.getProfileImage(requireContext())
        settingViewModel.originProfileUri.value = image
    }

    // isProfileUpdate 초기화
    private fun initIsProfileNum() {
        settingViewModel.images.value = false
        settingViewModel.nickName.value = false
        settingViewModel.buttonClick.value = false
    }


    private fun profileCheckNickName() {
        val nickNamePattern = "^[가-힣ㄱ-ㅎ]{0,5}$"
        with(binding) {
            imgProfileChangeButton.setOnClickListener {
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
                        textInputProfileChangeNickName.isErrorEnabled = true
                        textInputProfileChangeNickName.error = "5자 이내로 작성해주세요"
                    } else if (!Pattern.matches(nickNamePattern, s.toString())) {
                        textInputProfileChangeNickName.isErrorEnabled = true
                        textInputProfileChangeNickName.error = "한글만 사용해주세요"
                    } else if (s.toString().isEmpty()) {
                        textInputProfileChangeNickName.isErrorEnabled = false
                        textInputProfileChangeNickName.isHelperTextEnabled = false
                        checkOkBtn(false)

                    } else {
                        //닉네임 중복 체크
                        settingViewModel.profileNickNameCheck(s.toString())
                        settingViewModel.profileNickName.observe(viewLifecycleOwner) {
                            if (!it) {
                                textInputProfileChangeNickName.error = "중복되는 닉네임이 존재합니다"
                            } else {
                                textInputProfileChangeNickName.isErrorEnabled = false
                                textInputProfileChangeNickName.isHelperTextEnabled = true
                                textInputProfileChangeNickName.helperText = "사용 가능한 닉네임입니다"
                                checkOkBtn(true)
                                Timber.d("niceshot $s")
                                binding.textProfileChangeNext.setOnClickListener {
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
        //더블 클릭 방지
        binding.imgProfileChange.setOnClickListener {
            if (SystemClock.elapsedRealtime() - mLastClickTime > 800) {
                val bottomSheet = SettingBottomSheetFragment()
                bottomSheet.show(activity?.supportFragmentManager!!, bottomSheet.tag)
            }
            mLastClickTime = SystemClock.elapsedRealtime()
        }

        binding.textProfileChange.setOnClickListener {
            if (SystemClock.elapsedRealtime() - mLastClickTime > 800) {
                val bottomSheet = SettingBottomSheetFragment()
                bottomSheet.show(activity?.supportFragmentManager!!, bottomSheet.tag)
            }
            mLastClickTime = SystemClock.elapsedRealtime()
        }
    }

    private fun changeProfileImage() {
        settingViewModel.profileChangeUri.observe(viewLifecycleOwner) { its ->
            if (its != null) {
                Glide.with(this)
                    .load(its)
                    .circleCrop()
                    .into(binding.imgProfileChange)
                Timber.d("updateProfile $its")
                settingViewModel.images.value = true
                checkOkBtn(true)
                binding.textProfileChangeNext.setOnClickListener {
                    settingViewModel.buttonClick.value = true
                    changeSettingMain()
                }
                profileImageUpdateChange(its, "")
                settingViewModel.newNickName.observe(viewLifecycleOwner) {
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
    private fun profileImageUpdateChange(imageUri: Uri, s: String) {
        settingViewModel.isProfileUpdate.observe(viewLifecycleOwner) {
            val email = SharedInformation.getEmail(requireActivity())
            Timber.d("love $it")
            if (it == 0) {
                settingViewModel.profileImageChange(
                    email,
                    imageUri,
                    "",
                    s,
                    requireActivity()
                )
                SharedInformation.setNickName(requireActivity(), s)

            } else if (it == 2) {
                settingViewModel.profileImageChange(
                    email,
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
            val email = SharedInformation.getEmail(requireActivity())
            Timber.d("loves $it")
            if (it == 1) {
                settingViewModel.profileNickNameChange(
                    email,
                    "",
                    s
                )
                SharedInformation.setNickName(requireActivity(), s)
            }
        }
    }

    private fun changeTabText() {
        (activity as SettingActivity).binding.toolbarTitle.text = "프로필 수정"
    }

    private fun editTextFocusRemove() {
        with(binding) {
            clSettingProfileUpdate.setOnClickListener {
                etSettingProfileChangeNickname.clearFocus()
                textInputProfileChangeNickName.isHelperTextEnabled = false
            }
        }

    }

    //fragment 전환
    private fun changeSettingMain() {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.apply {
            replace(R.id.fragment_container_setting, SettingMainFragment())
            commit()
        }
    }

    //완료 버튼 클릭 전환
    private fun checkOkBtn(boolean : Boolean){
        binding.textProfileChangeNext.isEnabled = boolean
        binding.textProfileChangeNext.isClickable = boolean
    }
}




