package com.charo.android.presentation.ui.signup

import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.charo.android.R
import com.charo.android.databinding.FragmentSignUpProfileBinding
import com.charo.android.presentation.base.BaseFragment
import com.charo.android.presentation.ui.signup.viewmodel.SignUpEmailViewModel
import com.charo.android.presentation.util.KeyboardVisibilityUtils
import com.charo.android.presentation.util.SharedInformation
import com.charo.android.presentation.util.dpToPx
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import java.util.regex.Pattern


class SignUpProfileFragment :
    BaseFragment<FragmentSignUpProfileBinding>(R.layout.fragment_sign_up_profile) {
    private val signUpViewModel: SignUpEmailViewModel by sharedViewModel()

    private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val preImage = signUpViewModel.profileImage.value
                ?: Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.ic_profile) + '/' + resources.getResourceTypeName(R.drawable.ic_profile) + '/' + resources.getResourceEntryName(R.drawable.ic_profile))
            signUpViewModel.profileImage.value = result.data?.data ?: preImage
            Timber.d("imageProfile ${signUpViewModel.profileImage.value.toString()}")

            Glide.with(requireActivity())
                .load(signUpViewModel.profileImage.value)
                .error(R.drawable.ic_profile)
                .circleCrop()
                .into(binding.imgSignUpProfile)

        }
    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        checkNickName()
        registerProfile()
        keyBoardChange()

        Glide.with(requireActivity())
            .load(signUpViewModel.profileImage.value)
            .error(R.drawable.ic_profile)
            .circleCrop()
            .into(binding.imgSignUpProfile)
    }


    //닉네임 중복 체크
    private fun checkNickName() {
        val nickNamePattern = "^[가-힣ㄱ-ㅎ]{0,5}$"
        with(binding) {
            imgSignUpProfileDeleteButton.setOnClickListener {
                etSignUpNickname.setText("")
                textSignUpNicknameNext.isEnabled = false
                textSignUpNicknameNextFocus.isEnabled = false
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
                    textSignUpNicknameNext.isEnabled = false
                    textSignUpNicknameNextFocus.isEnabled = false

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

                                textSignUpNicknameNext.isEnabled = true
                                textSignUpNicknameNextFocus.isEnabled = true

                                textSignUpNicknameNext.setOnClickListener {
                                    SharedInformation.setNickName(requireActivity(), s.toString())
                                    val transaction = activity?.supportFragmentManager?.beginTransaction()
                                    transaction?.apply {
                                        replace(R.id.fragment_container_email, SignUpTermFragment())
                                        addToBackStack(SignUpProfileFragment::class.simpleName)
                                        commit()
                                    }
                                }
                                textSignUpNicknameNextFocus.setOnClickListener {
                                    SharedInformation.setNickName(requireActivity(), s.toString())
                                    val transaction = activity?.supportFragmentManager?.beginTransaction()
                                    transaction?.apply {
                                        replace(R.id.fragment_container_email, SignUpTermFragment())
                                        addToBackStack(SignUpProfileFragment::class.simpleName)
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
        binding.imgSignUpProfile.setOnClickListener {
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
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            intent.type = "image/*"
            getContent.launch(intent)
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
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = MediaStore.Images.Media.CONTENT_TYPE
                    intent.type = "image/*"
                    getContent.launch(intent)
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

    //키보드 올라올 때 버튼 뷰 변경
    private fun keyBoardChange(){
        keyboardVisibilityUtils = KeyboardVisibilityUtils(requireActivity().window ,
            onShowKeyboard = {keyBoardHeight ->
                scrollUpToMyWantedPosition()
                binding.textSignUpNicknameNext.visibility = View.GONE
                binding.textSignUpNicknameNextFocus.visibility = View.VISIBLE

            },
            onHideKeyboard = {
                binding.textSignUpNicknameNext.visibility = View.VISIBLE
                binding.textSignUpNicknameNextFocus.visibility = View.GONE
            })
    }

    //키보드 스크롤
    private fun scrollUpToMyWantedPosition() =
        with(binding.nsSignUpProfile) {
            postDelayed({
                smoothScrollBy(0, binding.textInputNickname.y.toInt() + 38)
                binding.textInputNickname.setPadding(0,0,0,38.dpToPx)
            }, 200)
        }
    override fun onPause() {
        super.onPause()
        keyboardVisibilityUtils.detachKeyboardListeners()
    }

}