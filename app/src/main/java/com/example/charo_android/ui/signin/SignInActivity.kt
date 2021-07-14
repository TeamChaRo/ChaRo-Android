package com.example.charo_android.ui.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.charo_android.MainActivity
import com.example.charo_android.api.ApiService
import com.example.charo_android.data.RequestSignInData
import com.example.charo_android.data.ResponseSignInData
import com.example.charo_android.databinding.ActivitySignInBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnLoginOnClickEvent()
        imgClearOnClickEvent()
    }

    private fun btnLoginOnClickEvent() {
        binding.btnSigninLogin.setOnClickListener() {
            if (binding.etSigninId == null || binding.etSigninPw == null) {
                Toast.makeText(this, "ID/PW를 입력해주세요!", Toast.LENGTH_LONG).show()
            } else {
                val requestSignInData = RequestSignInData(
                    id = binding.etSigninId.text.toString(),
                    password = binding.etSigninPw.text.toString()
                )

                val call: Call<ResponseSignInData> = ApiService.signInViewService.postSignIn(requestSignInData)

                call.enqueue(object : Callback<ResponseSignInData> {
                    override fun onResponse(
                        call: Call<ResponseSignInData>,
                        response: Response<ResponseSignInData>
                    ) {
                        if (response.isSuccessful) {
                            Log.d("server connect", "success")
                            val data = response.body()?.data
                            Toast.makeText(
                                applicationContext,
                                "${data?.nickname}님 환영합니다!",
                                Toast.LENGTH_LONG
                            ).show()
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            intent.putExtra("userId", binding.etSigninId.text.toString())
                            intent.putExtra("nickname", data?.nickname)
                            startActivity(intent)
                        } else {
                            Log.d("server connect", "fail")
                            Log.d("server connect", "${response.errorBody()}")
                            Log.d("server connect", "${response.message()}")
                            Log.d("server connect", "${response.code()}")
                            Log.d("server connect", "${response.raw().request.url}")
                            Toast.makeText(applicationContext, "ID/PW를 확인해주세요!", Toast.LENGTH_LONG).show()
                            binding.etSigninPw.text = null
                        }
                    }

                    override fun onFailure(call: Call<ResponseSignInData>, t: Throwable) {
                        Log.d("server connect", "error:${t.message}")
                    }
                })
            }
        }
    }

    private fun imgClearOnClickEvent() {
        binding.imgSigninIdClear.setOnClickListener() {
            binding.etSigninId.text.clear()
        }
        binding.imgSigninPwClear.setOnClickListener() {
            binding.etSigninPw.text.clear()
        }
    }
}