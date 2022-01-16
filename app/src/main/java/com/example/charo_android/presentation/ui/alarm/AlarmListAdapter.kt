package com.example.charo_android.presentation.ui.alarm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.charo_android.databinding.ItemAlarmBinding

class AlarmListAdapter(val itemClick: (AlarmViewModel) -> Unit) :
    RecyclerView.Adapter<AlarmListAdapter.AlarmListViewHolder>() {
    val itemList = mutableListOf<AlarmViewModel>()

    class AlarmListViewHolder(val binding: ItemAlarmBinding, val itemClick: (AlarmViewModel) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(alarmViewModel: AlarmViewModel) {
            Glide.with(binding.imgAlarmListProfile)
                .load(alarmViewModel.image)
                .circleCrop()
                .into(binding.imgAlarmListProfile)

            binding.tvAlarmStatus.text = alarmViewModel.title.value

            binding.itemAlarmList.setOnClickListener {
                itemClick(alarmViewModel)
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
}