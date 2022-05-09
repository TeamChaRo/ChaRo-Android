package com.charo.android.presentation.ui.signin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.charo.android.R
import com.charo.android.databinding.ActivityPasswordSearchBinding
import com.charo.android.presentation.base.BaseActivity
import com.charo.android.presentation.ui.signin.viewmodel.PasswordSearchViewModel
import com.charo.android.presentation.util.KeyboardVisibilityUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class PasswordSearchActivity :
    BaseActivity<ActivityPasswordSearchBinding>(R.layout.activity_password_search) {
    private val passwordSearchViewModel: PasswordSearchViewModel by viewModel()
    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        keyBoardChange()
        initView()
        changeBtnText()
        clickNextBtn()
        checkSuccess()
    }


    //키보드 올라올 때 버튼 뷰 변경
    private fun keyBoardChange() {
        keyboardVisibilityUtils = KeyboardVisibilityUtils(this.window,
            onShowKeyboard = { keyBoardHeight ->
                binding.textPasswordSearchNext.visibility = View.GONE
                binding.textPasswordSearchNextFocus.visibility = View.VISIBLE

            },
            onHideKeyboard = {
                binding.textPasswordSearchNext.visibility = View.VISIBLE
                binding.textPasswordSearchNextFocus.visibility = View.GONE
            })
    }

    //이메일 적는거 체크
    private fun initView() {
        with(binding) {
            imgPasswordSearchDeleteButton.setOnClickListener {
                etPasswordSearchContent.setText("")
            }
            etPasswordSearchContent.addTextChangedListener(object : TextWatcher {
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
                    passwordSearchViewModel.postInputEmail(true)
                    passwordSearchViewModel.userInputEmail.value = s.toString()
                }
            })
        }
    }


    //하단 버튼 텍스트 변경
    private fun changeBtnText() {
        passwordSearchViewModel.inputEmail.observe(this) {
            if (it) {
                with(binding){
                    textPasswordSearchNext.text = getString(R.string.temporary_password_send)
                    textPasswordSearchNextFocus.text = getString(R.string.temporary_password_send)
                }
            }else{
                with(binding){
                    textPasswordSearchNext.text = getString(R.string.next)
                    textPasswordSearchNextFocus.text = getString(R.string.next)
                }
            }

        }
    }

    //다음 버튼 클릭
    private fun clickNextBtn(){
        binding.textPasswordSearchNext.setOnClickListener {
            clickNextBtnEvent()
        }
        binding.textPasswordSearchNextFocus.setOnClickListener {
            clickNextBtnEvent()
        }
    }

    //다음 버튼 클릭 이벤트
    private fun clickNextBtnEvent(){
        val inputEmail = passwordSearchViewModel.inputEmail.value ?: false
        val userInputEmail = passwordSearchViewModel.userInputEmail.value ?: ""
        if(inputEmail){
            passwordSearchViewModel.getPasswordSearch(userInputEmail)
            passwordSearchViewModel.postInputEmail(false)
        }
    }

    //서버 성공시 종료
    private fun checkSuccess(){
        passwordSearchViewModel.passwordSuccess.observe(this){
            if(it){
                Toast.makeText(this, "임시 비밀번호가 발급되었습니다", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this, "이메일을 다시 한번 확인해주세요", Toast.LENGTH_SHORT).show()
            }
        }


    }
    //뒤로 가기 버튼
    private fun clickBackBtn(){
        binding.imgPasswordSearchDeleteButton.setOnClickListener { 
            finish()
        }
    }
    
    override fun onPause() {
        super.onPause()
        keyboardVisibilityUtils.detachKeyboardListeners()
    }
}