package com.charo.android.presentation.ui.signin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.charo.android.R
import com.charo.android.data.model.request.signin.RequestSignInData
import com.charo.android.databinding.ActivitySignInBinding
import com.charo.android.presentation.ui.main.MainActivity
import com.charo.android.presentation.ui.signin.viewmodel.EmailSignInViewModel
import com.charo.android.presentation.util.SharedInformation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val emailSignInViewModel : EmailSignInViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)

        emailSignToast()
        login()
        goPasswordSearch()
        binding.imgSigninIdClear.setOnClickListener() { clearEmail() }
        binding.imgSigninPwClear.setOnClickListener() { clearPassword() }
        setContentView(binding.root)
    }

    private fun login() {
        binding.btnSigninLogin.setOnClickListener {
            if (binding.etSigninId.text.isBlank() || binding.etSigninPw.text.isBlank()) {
                Toast.makeText(this, "ID/PW를 입력해주세요!", Toast.LENGTH_LONG).show()
            } else {
                val id = "3"
                SharedInformation.saveSocialId(this, id)
                SharedInformation.setEmail(this, binding.etSigninId.text.toString())
                Timber.d("etSigninPw ${binding.etSigninPw.text.toString()}")
                val requestSignInData = RequestSignInData(
                    userEmail = binding.etSigninId.text.toString(),
                    password = binding.etSigninPw.text.toString()
                )

                emailSignInViewModel.getEmailSignInData(requestSignInData)
                emailSignInViewModel.emailSignInData.observe(this, Observer {
                    if(it.success) {
                        SharedInformation.setNickName(this, it.data.nickname.toString())
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        //로그인 후 firebase init
                        initFirebase()
                    }
                })
            }
        }
    }

    private fun clearEmail() {
        binding.etSigninId.text.clear()
    }

    private fun clearPassword() {
        binding.etSigninPw.text.clear()
    }
    //로그인 토스트 메세지
    private fun emailSignToast(){
        emailSignInViewModel.emailSignInStatus.observe(this){
            if(it == 404){
                Toast.makeText(this, "아이디 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
            }
        }

    }




    private fun initFirebase(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(
        OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Timber.w(
                    "FirebaseTAG Fetching FCM registration token failed ${task.exception}"
                )
                return@OnCompleteListener
            } else {
                val token = task.result
                val msg = getString(R.string.msg_token_fmt, token)
                Timber.d("Firebase Success $msg")
            }
        })
    }

    //비밀번호 찾기 이동
    private fun goPasswordSearch(){
        binding.tvSigninPassword.setOnClickListener {
            val intent = Intent(this, PasswordSearchActivity::class.java)
            startActivity(intent)
        }


    }
}