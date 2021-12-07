package com.example.charo_android.presentation.ui.setting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentSettingMainBinding
import com.example.charo_android.presentation.base.BaseFragment
import com.example.charo_android.presentation.ui.main.MainActivity
import com.example.charo_android.presentation.ui.setting.viewmodel.SettingViewModel
import com.example.charo_android.presentation.ui.signin.SocialSignInActivity
import com.example.charo_android.presentation.ui.signup.SignUpPassWordFragment
import com.example.charo_android.presentation.util.CustomDialog
import com.example.charo_android.presentation.util.SharedInformation
import com.example.charo_android.presentation.util.changeFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.user.UserApiClient
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SettingMainFragment :
    BaseFragment<FragmentSettingMainBinding>(R.layout.fragment_setting_main) {
    private val settingViewModel: SettingViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickProfileUpdate()
        changeTabText()
        clickLogOut()
        withdrawal()
        clickPasswordUpdate()
        Log.d("sharedLog", SharedInformation.getSocialId(requireActivity()))
        Log.d("sharedLog", SharedInformation.getEmail(requireActivity()))

        binding.switchAlarm.setOnCheckedChangeListener(object :
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked) {

                } else {

                }
            }
        })
    }

    //프로필 수정
    private fun clickProfileUpdate() {
        binding.textSettingProfileUpdate.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.apply {
                replace(R.id.fragment_container_setting, SettingProfileUpdateFragment())
                addToBackStack(null)
                commit()
            }

        }
    }

    //비밀번호 수정
    private fun clickPasswordUpdate(){
        binding.textSettingPasswordUpdate.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.apply {
                replace(R.id.fragment_container_setting, SettingPasswordUpdate())
                addToBackStack(null)
                commit()
            }
        }
    }

    //제목 변경
    private fun changeTabText() {
        val userEmail = SharedInformation.getEmail(requireActivity())
        settingViewModel.updateTabText.value = "설정"
        binding.textSettingUserEmail.text = userEmail
    }

    //로그아웃
    private fun clickLogOut() {
        val socialKeyStorage = SharedInformation
        val socialKey = socialKeyStorage.getSocialId(requireActivity())
        val dialog = CustomDialog(requireActivity())
        binding.textSettingLogout.setOnClickListener {
            SharedInformation.removeEmail(requireActivity())
            dialog.showDialog(R.layout.custom_dialog_log_out)
            dialog.setOnClickedListener(object : CustomDialog.ButtonClickListener {
                override fun onClicked(num: Int) {
                    if (num == 1) {
                        if (socialKey == "1") {
                            UserApiClient.instance.unlink { error ->
                                if (error != null) {
                                    Toast.makeText(
                                        requireActivity(),
                                        "로그아웃 실패 $error",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    SharedInformation.removeEmail(requireActivity())
                                    SharedInformation.removeSocialId(requireActivity())
                                    Toast.makeText(requireActivity(), "로그아웃 성공", Toast.LENGTH_SHORT)
                                        .show()
                                    ActivityCompat.finishAffinity(requireActivity())
                                    val intent =
                                        Intent(requireActivity(), SocialSignInActivity::class.java)
                                    startActivity(intent)

                                }

                            }
                        } else if (socialKey == "3") {
                            SharedInformation.removeEmail(requireActivity())
                            SharedInformation.removeSocialId(requireActivity())
                            Toast.makeText(requireActivity(), "로그아웃 성공", Toast.LENGTH_SHORT).show()
                            ActivityCompat.finishAffinity(requireActivity())
                            val intent = Intent(requireActivity(), SocialSignInActivity::class.java)
                            startActivity(intent)

                        } else {
                            Firebase.auth.signOut()
                            SharedInformation.removeEmail(requireActivity())
                            SharedInformation.removeSocialId(requireActivity())
                            Toast.makeText(requireActivity(), "로그아웃 성공", Toast.LENGTH_SHORT).show()
                            ActivityCompat.finishAffinity(requireActivity())
                            val intent = Intent(requireActivity(), SocialSignInActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            })

        }

    }

    //회원 탈퇴
    private fun withdrawal() {
        binding.textSettingUserOut.setOnClickListener {
            val dialog = CustomDialog(requireActivity())
            dialog.showWithdrawal(R.layout.custom_dialog_withdrawal)
            dialog.setOnClickedListener(object : CustomDialog.ButtonClickListener {
                override fun onClicked(num: Int) {
                        if(num == 1){
                            settingViewModel.withdrawalUser("test@naver.com")
                            settingViewModel.withdrawalStatus.observe(viewLifecycleOwner){
                                if (it) {
                                    Toast.makeText(requireActivity(), "회원 탈퇴 성공", Toast.LENGTH_SHORT).show()
                                    ActivityCompat.finishAffinity(requireActivity())
                                }else{
                                    Toast.makeText(requireActivity(), "회원 탈퇴 실패", Toast.LENGTH_SHORT).show()
                                }
                            }

                        }
                }
            })

        }
    }
}