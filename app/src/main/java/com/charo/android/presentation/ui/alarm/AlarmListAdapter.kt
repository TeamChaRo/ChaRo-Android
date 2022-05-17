package com.charo.android.presentation.ui.alarm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.charo.android.data.model.response.alarm.ResponseAlarmListData
import com.charo.android.databinding.ItemAlarmBinding

class AlarmListAdapter(
    private val itemClick: (ResponseAlarmListData.PushList) -> Unit
    ) : //AlarmViewModel
    RecyclerView.Adapter<AlarmListAdapter.AlarmListViewHolder>(){
    val itemList = mutableListOf<ResponseAlarmListData.PushList>()

    class AlarmListViewHolder(val binding: ItemAlarmBinding, val itemClick: (ResponseAlarmListData.PushList) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        private val alarmActivity: AlarmActivity = AlarmActivity()

        fun onBind(alarmListInfo: ResponseAlarmListData.PushList) {
            Glide.with(binding.imgAlarmListProfile)
                .load(alarmListInfo.image)
                .circleCrop()
                .into(binding.imgAlarmListProfile)

            binding.tvAlarmDate.text = "${alarmListInfo.month}월 ${alarmListInfo.day}일"
            binding.tvAlarmContext.text = alarmListInfo.body
            binding.tvAlarmStatus.text = alarmListInfo.title
            binding.tvAlarmStatus.isSelected = true

            when(alarmListInfo.isRead){
                1-> {  // 조회한 알림
                    binding.itemAlarmList.isSelected = true
                }
                else -> {
                    binding.itemAlarmList.isSelected = false
                }
            }

            binding.itemAlarmList.setOnClickListener{
                itemClick(alarmListInfo)
                binding.itemAlarmList.isSelected = true
            }

            binding.tvAlarmDelete.setOnClickListener {
                alarmActivity.serveDeleteItem(alarmListInfo, it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmListViewHolder {
        val binding =
            ItemAlarmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlarmListViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: AlarmListViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun removeItem(data: ResponseAlarmListData.PushList){ //AlarmViewModel
        itemList.remove(data)
        notifyDataSetChanged()
    }
}