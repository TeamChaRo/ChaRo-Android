package com.charo.android.presentation.ui.signup


import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.charo.android.R
import com.charo.android.databinding.FragmentSignUpEmailBinding
import com.charo.android.presentation.base.BaseFragment
import com.charo.android.presentation.ui.signup.viewmodel.SignUpEmailViewModel
import com.charo.android.presentation.util.KeyboardVisibilityUtils
import com.charo.android.presentation.util.dpToPx
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber


class SignUpEmailFragment :
    BaseFragment<FragmentSignUpEmailBinding>(R.layout.fragment_sign_up_email) {
    var pass = false
    private val signUpViewModel: SignUpEmailViewModel by sharedViewModel()
    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        signUpNum()
        certificationEmail()
        keyBoardChange()
    }

    private fun isNextBtnActive(boolean: Boolean){
        binding.textSignUpNext.isEnabled = boolean
        binding.textSignUpNextFocus.isEnabled = boolean
    }

    //이메일 포함 되어있는지 확인
    private fun initView() {
        with(binding) {

            clEmailNum.isVisible = false
            imgDeleteButton.setOnClickListener {
                etSignUpBlank.setText("")
            }
            etSignUpBlank.addTextChangedListener(object : TextWatcher {
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
                    tvEmailResend.isEnabled = true
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                        textInputSignUp.error = "사용 불가능한 이메일 형식입니다."
                        pass = false

                        isNextBtnActive(false)
                        tvEmailResend.isEnabled = false
                        binding.etInputEmailNum.setText("")

                    } else {
//                        pass = true
                        isNextBtnActive(true)

                        signUpViewModel.emailCheck(s.toString())
                        observeSuccess()
                    }
                }
            })
        }
    }

    //인증번호 체크
    private fun certificationEmail() {
        if(signUpViewModel.isConfirmAuthNum.value == true){
            isNextBtnActive(true)
        } else {
            isNextBtnActive(false)
        }

        with(binding) {
            imgEmailDeleteButton.setOnClickListener {
                etInputEmailNum.setText("")
            }
            etInputEmailNum.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    signUpViewModel.data.observe(viewLifecycleOwner) {
                        Timber.d("certification $it")
                        if (TextUtils.isEmpty(etInputEmailNum.text)){
                            textInputEmailNum.error = "인증번호를 입력해주세요."
                            signUpViewModel.isConfirmAuthNum.setValue(false)
                        } else if (s.toString() != it) {
                            textInputEmailNum.error = "입력하신 인증번호가 맞지 않습니다. 다시 한 번 확인해주세요."
                            signUpViewModel.isConfirmAuthNum.setValue(false)

                        } else {
                            textInputEmailNum.error = null
                            textInputEmailNum.isErrorEnabled = false
                            textInputEmailNum.isHelperTextEnabled = true
                            textInputEmailNum.helperText = "인증되었습니다."
                            signUpViewModel.isConfirmAuthNum.setValue(true)
                        }
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }
            })
        }

        signUpViewModel.isConfirmAuthNum.observe(viewLifecycleOwner) {
            if(it){
                binding.etInputEmailNum.isEnabled = false
                binding.tvEmailResend.isEnabled = false
                isNextBtnActive(true)

                binding.textSignUpNext.setOnClickListener {
                    confirmEmailAuthNum()
                }
                binding.textSignUpNextFocus.setOnClickListener {
                    confirmEmailAuthNum()
                }
            } else {
                isNextBtnActive(false)
                binding.etInputEmailNum.isEnabled = true
            }
        }
    }


    //이메일 중복 체크
    private fun observeSuccess() {
        signUpViewModel.success.observe(viewLifecycleOwner) {
            with(binding) {
                if (it) {
                    textInputSignUp.error = null
                    textInputSignUp.isErrorEnabled = false
                    textInputSignUp.isHelperTextEnabled = true
                    textInputSignUp.helperText = "사용 가능한 이메일 형식입니다."

                    //다음 버튼 활성화
                    isNextBtnActive(true)
                    tvEmailResend.isEnabled = true
                    //인증번호 입력창 초기화
                    etInputEmailNum.isEnabled = true
                    etInputEmailNum.setText("")
                    textInputEmailNum.error = null


                    textSignUpNext.setOnClickListener {
                        sendEmailAuthNum("SEND")
                    }
                    textSignUpNextFocus.setOnClickListener {
                        sendEmailAuthNum("SEND")
                    }
                    tvEmailResend.setOnClickListener {
                        sendEmailAuthNum("RESEND")
                    }

                } else {
                    if (signUpViewModel.emailCheckStatus.value == 2000){ //서버통신 성공 & response false
                        textInputSignUp.error = "중복된 이메일 형식입니다."
                    } else { //서버통신 실패
                        textInputSignUp.error = getString(R.string.server_error_general)
                    }

                    isNextBtnActive(false)
                    binding.etInputEmailNum.isEnabled = false
                    tvEmailResend.isEnabled = false
                }
            }
        }
    }

    private fun sendEmailAuthNum(TAG : String){
        with(binding) {
            signUpViewModel.data.value = ""
            signUpViewModel.emailCertification(etSignUpBlank.text.toString())

            signUpViewModel.data.observe(viewLifecycleOwner){
                if(!TextUtils.isEmpty(it)){
                    if(signUpViewModel.authNumSuccess.value == true){
                        signUpViewModel.userEmail.value = etSignUpBlank.text.toString()
                        Toast.makeText(requireContext(), "인증번호를 전송하였습니다", Toast.LENGTH_SHORT).show()

                    }
                }
            }
            signUpViewModel.authNumSuccess.observe(viewLifecycleOwner){
                if(!it){
                    Toast.makeText(requireContext(), "인증번호 전송이 실패하였습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                    signUpViewModel.authNumSuccess.value = null
                }
            }

            clEmailNum.isVisible = true
            binding.etInputEmailNum.setText("")
            isNextBtnActive(false)
            etInputEmailNum.isEnabled = true

            textInputEmailNum.helperText = ""
            textInputEmailNum.error = "인증번호를 입력해주세요."
            signUpViewModel.isConfirmAuthNum.setValue(false)
        }
    }

    private fun confirmEmailAuthNum(){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.apply {
            replace(R.id.fragment_container_email, SignUpPassWordFragment())
            addToBackStack(SignUpEmailFragment::class.simpleName)
            commit()
        }
    }

    //키보드 올라올 때 버튼 뷰 변경
    private fun keyBoardChange(){
        keyboardVisibilityUtils = KeyboardVisibilityUtils(requireActivity().window ,
        onShowKeyboard = {
            scrollUpToMyWantedPosition()
            binding.textSignUpNext.visibility = View.GONE
            binding.textSignUpNextFocus.visibility = View.VISIBLE

        },
        onHideKeyboard = {
            binding.textSignUpNext.visibility = View.VISIBLE
            binding.textSignUpNextFocus.visibility = View.GONE
        })
    }
    //일반 회원가입 번호
    private fun signUpNum(){
        signUpViewModel.socialLoginNum.value = 0
    }

    //키보드 스크롤
    private fun scrollUpToMyWantedPosition() =
        with(binding.nsSignUpEmail) {
            postDelayed({
                smoothScrollBy(0, binding.textSignUpNextFocus.y.toInt() + 38)
                binding.textInputEmailNum.setPadding(0,0,0,38.dpToPx)
            }, 200)
        }

    override fun onPause() {
        super.onPause()
        keyboardVisibilityUtils.detachKeyboardListeners()
    }
}