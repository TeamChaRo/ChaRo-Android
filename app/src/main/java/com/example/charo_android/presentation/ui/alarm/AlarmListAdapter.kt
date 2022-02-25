package com.example.charo_android.presentation.ui.alarm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.charo_android.databinding.ItemAlarmBinding

class AlarmListAdapter(
    private val itemClick: (AlarmListInfo) -> Unit
    ) : //AlarmViewModel
    RecyclerView.Adapter<AlarmListAdapter.AlarmListViewHolder>(){
    val itemList = mutableListOf<AlarmListInfo>() //AlarmViewModel

    class AlarmListViewHolder(val binding: ItemAlarmBinding, val itemClick: (AlarmListInfo) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        private val alarmActivity: AlarmActivity = AlarmActivity()

        fun onBind(alarmListInfo: AlarmListInfo) { //alarmViewModel: AlarmViewModel
            Glide.with(binding.imgAlarmListProfile)
                .load(alarmListInfo.image) //alarmViewModel.image
                .circleCrop()
                .into(binding.imgAlarmListProfile)

            binding.tvAlarmStatus.text = alarmListInfo.title
            binding.tvAlarmStatus.isSelected = true
            binding.tvAlarmDate.text = "${alarmListInfo.month}월 ${alarmListInfo.day}일"
            binding.tvAlarmContext.text = alarmListInfo.body

//            binding.tvAlarmStatus.text = alarmViewModel.title.value

            binding.itemAlarmList.setOnClickListener{
                itemClick(alarmListInfo)
                binding.itemAlarmList.isSelected = true
            }

            binding.tvAlarmDelete.setOnClickListener {
                alarmActivity.serveDeleteItem(alarmListInfo, alarmListInfo.pushId)
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

    fun removeItem(data: AlarmListInfo){ //AlarmViewModel
        itemList.remove(data)
    }
}