package com.charo.android.presentation.ui.setting.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charo.android.databinding.ItemSettingNoticeBinding
import com.charo.android.domain.model.setting.SettingNoticeData
import timber.log.Timber


class SettingNoticeAdapter : RecyclerView.Adapter<SettingNoticeAdapter.SettingNoticeViewHolder>() {
    var settingNotice : MutableList<SettingNoticeData> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SettingNoticeViewHolder {
        val binding = ItemSettingNoticeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SettingNoticeViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SettingNoticeViewHolder,
        position: Int
    ) {
        holder.onBind(settingNotice[position])

    }

    override fun getItemCount(): Int {
        Timber.d("settingNotice ${settingNotice.size}")
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