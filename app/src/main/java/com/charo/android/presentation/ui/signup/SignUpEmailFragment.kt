package com.charo.android.presentation.ui.signup


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                        textInputSignUp.error = "사용 불가능한 이메일 형식입니다."
                        pass = false
                    } else {
                        pass = true
                        signUpViewModel.emailCheck(s.toString())
                        observeSuccess(pass)
                    }
                }
            })
        }
    }
    //인증번호 체크
    private fun certificationEmail() {
        with(binding) {

            etInputEmailNum.addTextChangedListener(object : TextWatcher {
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
                    signUpViewModel.data.observe(viewLifecycleOwner) {
                        Log.d("certification", it.toString())
                        if (s.toString() != it) {
                            textInputEmailNum.error = "입력하신 인증번호가 맞지 않습니다. 다시 한 번 확인해주세요."
                        } else {
                            textInputEmailNum.error = null
                            textInputEmailNum.isErrorEnabled = false
                            textInputEmailNum.isHelperTextEnabled = true
                            textInputEmailNum.helperText = "인증되었습니다."

                            textSignUpNext.setOnClickListener {
                                val transaction = activity?.supportFragmentManager?.beginTransaction()
                                transaction?.apply {
                                    replace(R.id.fragment_container_email, SignUpPassWordFragment())
                                    commit()
                                }
                            }
                            textSignUpNextFocus.setOnClickListener {
                                val transaction = activity?.supportFragmentManager?.beginTransaction()
                                transaction?.apply {
                                    replace(R.id.fragment_container_email, SignUpPassWordFragment())
                                    commit()
                                }
                            }
                        }
                    }
                }
            })
        }
    }


    //이메일 중복 체크
    private fun observeSuccess(pass : Boolean) {
        signUpViewModel.success.observe(viewLifecycleOwner) {
            with(binding) {
                if (it) {
                    textInputSignUp.error = null
                    textInputSignUp.isErrorEnabled = false
                    textInputSignUp.isHelperTextEnabled = true
                    textInputSignUp.helperText = "사용 가능한 이메일 형식입니다."

                        textSignUpNext.setOnClickListener {
                            signUpViewModel.emailCertification(etSignUpBlank.text.toString())
                            signUpViewModel.userEmail.value = etSignUpBlank.text.toString()
                            Log.d("되라",etSignUpBlank.text.toString())
                            clEmailNum.isVisible = true
                        }
                        textSignUpNextFocus.setOnClickListener {
                            signUpViewModel.emailCertification(etSignUpBlank.text.toString())
                            signUpViewModel.userEmail.value = etSignUpBlank.text.toString()
                            Log.d("되라",etSignUpBlank.text.toString())
                            clEmailNum.isVisible = true
                        }
                    tvEmailResend.setOnClickListener {
                        signUpViewModel.emailCertification(etSignUpBlank.text.toString())
                        Toast.makeText(requireContext(), "재전송 했습니다",Toast.LENGTH_SHORT).show()
                    }

                } else {
                    if (pass){
                        textInputSignUp.error = "중복된 이메일 형식입니다."
                    }
                }
            }
        }

    }

    //키보드 올라올 때 버튼 뷰 변경
    private fun keyBoardChange(){
        keyboardVisibilityUtils = KeyboardVisibilityUtils(requireActivity().window ,
        onShowKeyboard = {keyBoardHeight ->
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