package com.charo.android.presentation.ui.setting

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.signature.ApplicationVersionSignature
import com.charo.android.R
import com.charo.android.databinding.FragmentSettingMainBinding
import com.charo.android.presentation.base.BaseFragment
import com.charo.android.presentation.ui.setting.viewmodel.SettingViewModel
import com.charo.android.presentation.ui.signin.SocialSignInActivity
import com.charo.android.presentation.util.CustomDialog
import com.charo.android.presentation.util.SharedInformation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.user.UserApiClient
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber


class SettingMainFragment :
    BaseFragment<FragmentSettingMainBinding>(R.layout.fragment_setting_main) {
    private val settingViewModel: SettingViewModel by sharedViewModel()
    private val permissionCheck = (android.Manifest.permission.READ_EXTERNAL_STORAGE)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getVersionName()
        clickProfileUpdate()
        changeTabText()
        clickLogOut()
        withdrawal()
        clickPasswordUpdate()
        clickNotice()
        clickCs()
        allowAccess()
        clickPrivacy()
        clickServiceTerm()
        clickReport()
    }

    // 알림, 사진 접근 허용
    private fun allowAccess() {
        binding.switchImg.isChecked = ContextCompat.checkSelfPermission(
            requireActivity(),
            permissionCheck
        ) == PackageManager.PERMISSION_GRANTED


        binding.switchImg.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!isChecked){
                val intent = Intent(Settings.ACTION_APPLICATION_SETTINGS)
                startActivity(intent)
            }
        }
    }

    // 개인 정보 처리
    private fun clickPrivacy(){
        binding.textSettingPrivacy.setOnClickListener {
            val intent = Intent(requireActivity(), SettingPrivacyActivity::class.java)
            startActivity(intent)
        }
    }

    private fun clickServiceTerm(){
        binding.textSettingService.setOnClickListener {
            val intent = Intent(requireActivity(), SettingServiceTermActivity::class.java)
            startActivity(intent)
        }
    }

    private fun clickReport(){
        binding.textSettingReport.setOnClickListener {
            val intent = Intent(requireActivity(), SettingReportActivity::class.java)
            startActivity(intent)
        }
    }

    //프로필 수정
    private fun clickProfileUpdate() {
        binding.textSettingProfileUpdate.setOnClickListener {
            changeSettingFragment(SettingProfileUpdateFragment())
        }
    }

    //비밀번호 수정
    private fun clickPasswordUpdate() {
        binding.textSettingPasswordUpdate.setOnClickListener {
            if(SharedInformation.getSignUp(requireActivity()) == 0){
                changeSettingFragment(SettingPasswordUpdateFragment())
            }else{
                Toast.makeText(requireActivity(), "소셜 로그인은 수정이 불가능 합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 공지사항
    private fun clickNotice() {
        binding.textSettingNotice.setOnClickListener {
            changeSettingFragment(SettingNoticeFragment())
        }
    }

    //1:1문의
    private fun clickCs() {
        binding.textSettingCsQuery.setOnClickListener {
            val intent = Intent(requireActivity(), SettingCsActivity::class.java)
            startActivity(intent)
        }

    }

    //제목 변경
    private fun changeTabText() {
        (activity as SettingActivity).binding.toolbarTitle.text = getString(R.string.setting)

        val userEmail = SharedInformation.getEmail(requireActivity())
        binding.textSettingUserEmail.text = userEmail
    }

    //로그아웃
    private fun clickLogOut() {
        val dialog = CustomDialog(requireActivity())
        binding.textSettingLogout.setOnClickListener {
            val socialKey = SharedInformation.getSocialId(requireActivity())
            Timber.d("socialKey $socialKey")
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
                                    SharedInformation.setLogout(requireActivity(), "Logout")
                                    SharedInformation.removeNickName(requireActivity())
                                    SharedInformation.removeEmail(requireActivity())
                                    SharedInformation.removeSocialId(requireActivity())
                                    Toast.makeText(requireActivity(), "카카오 로그아웃 성공", Toast.LENGTH_SHORT)
                                        .show()
                                    ActivityCompat.finishAffinity(requireActivity())
                                    val intent = Intent(requireActivity(), SocialSignInActivity::class.java)
                                    startActivity(intent)

                                }

                            }
                        } else if (socialKey == "3") {
                            SharedInformation.setLogout(requireActivity(), "Logout")
                            SharedInformation.removeEmail(requireActivity())
                            SharedInformation.removeSocialId(requireActivity())
                            Toast.makeText(requireActivity(), "로그아웃 성공", Toast.LENGTH_SHORT).show()
                            ActivityCompat.finishAffinity(requireActivity())
                            val intent = Intent(requireActivity(), SocialSignInActivity::class.java)
                            startActivity(intent)

                        } else {
                            Firebase.auth.signOut()
                            SharedInformation.setLogout(requireActivity(), "Logout")
                            SharedInformation.removeEmail(requireActivity())
                            SharedInformation.removeSocialId(requireActivity())
                            Toast.makeText(requireActivity(), "구글 로그아웃 성공", Toast.LENGTH_SHORT).show()
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
                    if (num == 1) {
                        settingViewModel.withdrawalUser("test@naver.com")
                        settingViewModel.withdrawalStatus.observe(viewLifecycleOwner) {
                            if (it) {
                                SharedInformation.removeNickName(requireActivity())
                                SharedInformation.removeEmail(requireActivity())
                                SharedInformation.removeSocialId(requireActivity())
                                Toast.makeText(requireActivity(), "회원 탈퇴 성공", Toast.LENGTH_SHORT)
                                    .show()
                                ActivityCompat.finishAffinity(requireActivity())
                            } else {
                                Toast.makeText(requireActivity(), "회원 탈퇴 실패", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                    }
                }
            })
        }
    }

    private fun changeSettingFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.apply {
            replace(R.id.fragment_container_setting, fragment)
            commit()
        }
    }

    private fun getVersionName(){
        val pInfo = requireContext().packageManager.getPackageInfo(requireContext().packageName, 0);
        val versionName = pInfo.versionName + ""

        binding.textSettingVersionNum.text = versionName
    }

}