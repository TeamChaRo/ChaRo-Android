package com.example.charo_android.presentation.ui.setting


import android.os.Bundle
import android.widget.CompoundButton
import com.example.charo_android.R
import com.example.charo_android.databinding.ActivitySettingBinding
import com.example.charo_android.presentation.base.BaseActivity

class SettingActivity : BaseActivity<ActivitySettingBinding>(R.layout.activity_setting) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding.switchAlarm.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if(isChecked){

                }else{

                }
            }
        })
    }
}