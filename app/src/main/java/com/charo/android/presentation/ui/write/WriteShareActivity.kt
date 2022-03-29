package com.charo.android.presentation.ui.write

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.example.charo_android.R
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.charo_android.databinding.ActivityWriteShareBinding

class WriteShareActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWriteShareBinding
    private lateinit var sharedViewModel: WriteSharedViewModel

    private lateinit var writeFragment : WriteFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteShareBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedViewModel = ViewModelProvider(this@WriteShareActivity).get(WriteSharedViewModel::class.java)

        writeFragment = WriteFragment()
        replaceFragment(writeFragment)

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

    override fun onBackPressed(){
        supportFragmentManager.popBackStack();
        sharedViewModel.latitude.value = 0.0
        sharedViewModel.longitude.value = 0.0
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                AlertDialog.Builder(this)
                .setMessage(R.string.noti_cancel_write)
                .setNeutralButton(R.string.write_continue) { dialog, which ->
                }
                .setPositiveButton(R.string.write_cancel) { dialog, which ->
                    finish()
                }
                .show()

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}