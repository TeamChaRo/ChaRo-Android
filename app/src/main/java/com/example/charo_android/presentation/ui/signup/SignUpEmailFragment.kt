package com.example.charo_android.presentation.ui.signup


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentSignUpEmailBinding
import com.example.charo_android.domain.model.signup.Email
import com.example.charo_android.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SignUpEmailFragment :
    BaseFragment<FragmentSignUpEmailBinding>(R.layout.fragment_sign_up_email) {
    private var pass = false
    private val signUpViewModel: SignUpEmailViewModel by sharedViewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeSuccess()
        certificationEmail()
    }

    private fun initView() {
        val naver = "@naver.com"
        val gmail = "@gmail.com"
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
                    if (!s.toString().contains(naver) and !s.toString().contains(gmail)) {
                        textInputSignUp.error = "사용 불가능한 이메일 형식입니다."
                        pass = false
                    } else {
                        pass = true
                        checkEmail(s.toString())
                    }
                }
            })
        }
    }

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

                            imgSignUpNext.setOnClickListener {
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


    private fun observeSuccess() {
        signUpViewModel.success.observe(viewLifecycleOwner) {
            with(binding) {
                if (it) {
                    textInputSignUp.error = null
                    textInputSignUp.isErrorEnabled = false
                    textInputSignUp.isHelperTextEnabled = true
                    textInputSignUp.helperText = "사용 가능한 이메일 형식입니다."

                        imgSignUpNext.setOnClickListener {
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
                    if (pass)
                        textInputSignUp.error = "중복된 이메일 형식입니다."
                }
            }
        }

    }

    private fun checkEmail(email: String) {
        return signUpViewModel.emailCheck(email)
    }

}