package com.charo.android.presentation.ui.write

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.charo.android.R
import com.charo.android.databinding.ActivityWriteShareBinding
import com.charo.android.presentation.ui.detailpost.DetailPostFragment
import com.charo.android.presentation.ui.detailpost.viewmodel.DetailPostViewModel
import com.charo.android.presentation.util.SharedInformation
import org.koin.androidx.viewmodel.ext.android.viewModel


class WriteShareActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWriteShareBinding
//    원래 진희코드
//    private lateinit var sharedViewModel: WriteSharedViewModel
    private val sharedViewModel by viewModel<WriteSharedViewModel>()

    private lateinit var writeFragment: WriteFragment

    // TODO: ViewModel 갈아끼우기(승현)
    private val viewModel by viewModel<DetailPostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteShareBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        원래 진희코드
//        sharedViewModel =
//            ViewModelProvider(this@WriteShareActivity).get(WriteSharedViewModel::class.java)

        // TODO: 알아서 정리할 거 정리하기(승현)
        sharedViewModel.postId = intent.getIntExtra("postId", -1)
        sharedViewModel.userEmail = SharedInformation.getEmail(this)

        when (intent.getStringExtra("destination")) {
            "detail" -> {
                replaceFragment(DetailPostFragment())
            }
            else -> {
                writeFragment = WriteFragment()
                replaceFragment(writeFragment)
            }
        }
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.write_share_layout, fragment)
            .commit()
    }

    fun replaceAddStackFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.write_share_layout, fragment, tag)
            .addToBackStack(tag)
            .commit()

    }

    private fun stopWrite(){
        AlertDialog.Builder(this, R.style.Dialog)
            .setMessage(R.string.noti_cancel_write)
            .setNeutralButton(R.string.write_continue) { dialog, which ->
            }
            .setPositiveButton(R.string.write_cancel) { dialog, which ->
                finish()
            }
            .show()
    }

    override fun onBackPressed() {
        // TODO: 상세보기에서는 뒤로가기 누를 수 있게 하고 싶은데
        val currentFragment = supportFragmentManager.findFragmentById(R.id.write_share_layout)
        if (currentFragment is DetailPostFragment) {
            super.onBackPressed()
        } else if (currentFragment is WriteFragment){
            stopWrite()
        } else {
            supportFragmentManager.popBackStack()
            sharedViewModel.latitude.value = 0.0
            sharedViewModel.longitude.value = 0.0
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.write_share_layout)
        Log.e("aaaa", "sharedViewModel.isAuthorFlag.value " + sharedViewModel.isAuthorFlag.value)
        if (currentFragment is DetailPostFragment && sharedViewModel.isAuthorFlag.value == true) {
            val menuInflater: MenuInflater = MenuInflater(this)
            menuInflater.inflate(R.menu.detail_menu, menu)

//        return super.onCreateOptionsMenu(menu);
            return true
        }
        return super.onCreateOptionsMenu(menu)
    }
}