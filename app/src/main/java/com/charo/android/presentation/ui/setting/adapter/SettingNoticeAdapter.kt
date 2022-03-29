package com.charo.android.presentation.ui.setting.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.charo_android.databinding.ItemSettingNoticeBinding
import com.example.charo_android.domain.model.setting.SettingNoticeData

class SettingNoticeAdapter : RecyclerView.Adapter<SettingNoticeAdapter.SettingNoticeViewHolder>() {
    var settingNotice : MutableList<SettingNoticeData> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SettingNoticeAdapter.SettingNoticeViewHolder {
        val binding = ItemSettingNoticeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SettingNoticeViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SettingNoticeAdapter.SettingNoticeViewHolder,
        position: Int
    ) {
        holder.onBind(settingNotice[position])

    }

    override fun getItemCount(): Int {
        Log.d("settingNotice", settingNotice.size.toString())
        return settingNotice.size
    }


    class SettingNoticeViewHolder(
         val binding : ItemSettingNoticeBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(settingNotice : SettingNoticeData){
            binding.apply {
                settingNoticeData = settingNotice
                executePendingBindings()

                imgSettingNoticeExpand.setOnClickListener {
                    if(!settingNotice.view){
                        settingNotice.view = true
                        clSettingNoticeExpand.visibility = View.VISIBLE
                    }else{
                        settingNotice.view = false
                        clSettingNoticeExpand.visibility = View.GONE
                    }

                }
            }
        }
    }

    fun setNoticeData(settingNotice : MutableList<SettingNoticeData>){
        this.settingNotice = settingNotice
        notifyDataSetChanged()
    }
}