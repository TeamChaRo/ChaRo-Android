package com.charo.android.presentation.ui.setting

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.charo.android.R
import com.charo.android.databinding.FragmentSettingMainBinding
import com.charo.android.presentation.base.BaseFragment
import com.charo.android.presentation.ui.setting.viewmodel.SettingViewModel
import com.charo.android.presentation.ui.signin.SocialSignInActivity
import com.charo.android.presentation.util.CustomDialog
import com.charo.android.presentation.util.SharedInformation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.user.UserApiClient
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber


class SettingMainFragment :
    BaseFragment<FragmentSettingMainBinding>(R.layout.fragment_setting_main) {
    private val settingViewModel: SettingViewModel by sharedViewModel()
    private val permissionCheck = (android.Manifest.permission.READ_EXTERNAL_STORAGE)
    private lateinit var googleSignInClient: GoogleSignInClient
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
        clickOpenSource()
    }

    override fun onResume() {
        super.onResume()
        allowAccess()
    }

    // 알림, 사진 접근 허용
    private fun allowAccess() {
        binding.switchImg.isChecked = ContextCompat.checkSelfPermission(
            requireActivity(),
            permissionCheck
        ) == PackageManager.PERMISSION_GRANTED

        binding.switchImg.setOnClickListener {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    permissionCheck
                )
            ) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(permissionCheck), 1)
            } else {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(permissionCheck), 0)

                //다시묻지않기
                showForceRequestPermission()
            }
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
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SharedInformation.setPermissionNever(requireContext(), false)
                    binding.switchImg.isChecked = true
                } else {
                    //다시묻지않기
                    showForceRequestPermission()
                }
            }
            1 -> {
                //거부
                SharedInformation.setPermissionNever(requireContext(), false)
            }
        }
    }

    private fun showForceRequestPermission() {
        SharedInformation.setPermissionNever(requireContext(), true)

        val builder = AlertDialog.Builder(requireContext(), R.style.Dialog)
        builder.setMessage(getString(R.string.txt_go_permission_setting))
        builder.setCancelable(false)
        builder.setPositiveButton("예") { dialog, which ->
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + requireActivity().packageName)
            )
            startActivity(intent)
        }
        builder.setNegativeButton("아니요") { dialog, which ->
        }
        builder.show()
    }

    //오픈 소스
    private fun clickOpenSource() {
        binding.textSettingOpenSource.setOnClickListener {
            val intent = Intent(requireActivity(), OssLicensesMenuActivity::class.java)
            startActivity(intent)

            OssLicensesMenuActivity.setActivityTitle("오픈소스 라이선스")
        }

    }

    // 개인 정보 처리
    private fun clickPrivacy() {
        binding.textSettingPrivacy.setOnClickListener {
            val intent = Intent(requireActivity(), SettingPrivacyActivity::class.java)
            startActivity(intent)
        }
    }

    private fun clickServiceTerm() {
        binding.textSettingService.setOnClickListener {
            val intent = Intent(requireActivity(), SettingServiceTermActivity::class.java)
            startActivity(intent)
        }
    }

    private fun clickReport() {
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
            if (SharedInformation.getSignUp(requireActivity()) == 0) {
                changeSettingFragment(SettingPasswordUpdateFragment())
            } else {
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
            dialog.showDialog(requireActivity(), getString(R.string.log_out))
            dialog.setOnClickedListener(object : CustomDialog.ButtonClickListener {
                override fun onClicked(num: Int) {
                    if (num == 1) {
                        if (socialKey == "1") {
                            UserApiClient.instance.logout { error ->
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
                                    Toast.makeText(
                                        requireActivity(),
                                        "카카오 로그아웃 성공",
                                        Toast.LENGTH_SHORT
                                    )
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
                            Toast.makeText(requireActivity(), "구글 로그아웃 성공", Toast.LENGTH_SHORT)
                                .show()
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
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
            val socialKey = SharedInformation.getSocialId(requireActivity())
            val userEmail = SharedInformation.getEmail(requireActivity())
            val dialog = CustomDialog(requireActivity())
            dialog.showWithdrawal(requireActivity())
            dialog.setOnClickedListener(object : CustomDialog.ButtonClickListener {
                override fun onClicked(num: Int) {
                    if (num == 1) {
                        Timber.d("회원탈퇴 클릭")
                        when (socialKey) {
                            "1" -> {
                                UserApiClient.instance.unlink { error ->
                                    if(error != null){
                                        Toast.makeText(requireActivity(), "카카오 회원 탈퇴 실패", Toast.LENGTH_SHORT).show()
                                    }else{
                                        checkWithdrawal(userEmail)
                                        Toast.makeText(requireActivity(), "카카오 회원 탈퇴 성공", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                            "2" -> {
                                Firebase.auth.signOut()
                                googleSignInClient.revokeAccess().addOnCompleteListener {
                                    Toast.makeText(requireActivity(), "구글 탈퇴 성공", Toast.LENGTH_SHORT).show()
                                    checkWithdrawal(userEmail)
                                }
                            }
                            else -> {
                                checkWithdrawal(userEmail)
                            }
                        }
                    }
                }
            })
        }
    }

    private fun checkWithdrawal(userEmail : String){
        settingViewModel.withdrawalUser(userEmail)
        settingViewModel.withdrawalStatus.observe(viewLifecycleOwner) {
            if (it) {
                SharedInformation.removeNickName(requireActivity())
                SharedInformation.removeEmail(requireActivity())
                SharedInformation.removeSocialId(requireActivity())

                ActivityCompat.finishAffinity(requireActivity())

            } else {
                Toast.makeText(requireActivity(), "회원 탈퇴 실패", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun changeSettingFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.apply {
            replace(R.id.fragment_container_setting, fragment)
            commit()
        }
    }

    private fun getVersionName() {
        val pInfo = requireContext().packageManager.getPackageInfo(requireContext().packageName, 0);
        val versionName = pInfo.versionName + ""

        binding.textSettingVersionNum.text = versionName
    }

}