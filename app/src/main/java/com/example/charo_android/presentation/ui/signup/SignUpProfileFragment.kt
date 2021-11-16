package com.example.charo_android.presentation.ui.signup

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentSignUpProfileBinding
import com.example.charo_android.presentation.base.BaseFragment
import com.example.charo_android.presentation.ui.signup.viewmodel.SignUpEmailViewModel
import kotlinx.android.synthetic.main.fragment_sign_up_profile.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.regex.Pattern


class SignUpProfileFragment :
    BaseFragment<FragmentSignUpProfileBinding>(R.layout.fragment_sign_up_profile) {
    private val signUpViewModel: SignUpEmailViewModel by sharedViewModel()
    private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            signUpViewModel.profileImage.value = result.data?.data
            Log.d("imageProfile", signUpViewModel.profileImage.value.toString())

            Glide.with(this)
                .load(signUpViewModel.profileImage.value)
                .circleCrop()
                .into(binding.imgSignUpProfile)

        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        checkNickName()
        registerProfile()
    }


    //닉네임 중복  체크
    private fun checkNickName() {
        val nickNamePattern = "^[가-힣ㄱ-ㅎ]{0,5}$"
        with(binding) {
            img_sign_up_profile_delete_button.setOnClickListener {
                etSignUpNickname.setText("")
            }

            etSignUpNickname.addTextChangedListener(object : TextWatcher {
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
                        textInputNickname.error = "5자 이내로 작성해주세요"
                    } else if (!Pattern.matches(nickNamePattern, s.toString())) {
                        textInputNickname.error = "한글만 사용해주세요"
                    } else {
                        signUpViewModel.nickNameCheck(s.toString())
                        signUpViewModel.nickNameCheck.observe(viewLifecycleOwner) {
                            if (!it) {
                                textInputNickname.error = "중복되는 닉네임이 존재합니다"
                            } else {
                                textInputNickname.error = null
                                textInputNickname.isErrorEnabled = false
                                textInputNickname.isHelperTextEnabled = true
                                textInputNickname.helperText = "사용 가능한 닉네임입니다"
                                signUpViewModel.nickName.value = etSignUpNickname.text.toString()

                                imgSignUpNicknameNext.setOnClickListener {
                                    val transaction =
                                        activity?.supportFragmentManager?.beginTransaction()
                                    transaction?.apply {
                                        replace(R.id.fragment_container_email, SignUpTermFragment())
                                        commit()
                                    }
                                }
                            }
                        }
                    }
                }
            })

        }
    }

    // 프로필 사진 등록
    private fun registerProfile() {
        val REQ_STORAGE_PERMISSION = 1001
        var writePermission = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        var readPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
        binding.imgSignUpProfile.setOnClickListener {
            if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    REQ_STORAGE_PERMISSION
                )
            } else {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = MediaStore.Images.Media.CONTENT_TYPE
                intent.type = "image/*"
                getContent.launch(intent)

            }

        }
    }






}