package com.example.charo_android.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.charo_android.databinding.ItemHomeTodayDriveBinding

class HomeTodayDriveAdapter(): RecyclerView.Adapter<HomeTodayDriveAdapter.HomeTodayDriveViewHolder>() {
    val driveData = mutableListOf<HomeTodayDriverInfo>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeTodayDriveAdapter.HomeTodayDriveViewHolder {
        val binding = ItemHomeTodayDriveBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeTodayDriveViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: HomeTodayDriveAdapter.HomeTodayDriveViewHolder,
        position: Int
    ) {
        holder.onBind(driveData[position])
    }

    override fun getItemCount(): Int {
        return driveData.size
    }

    class HomeTodayDriveViewHolder(
        private val binding : ItemHomeTodayDriveBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(homeTodayDriverInfo : HomeTodayDriverInfo){
            binding.apply {
                imgHomeTodayDrive.setImageResource(homeTodayDriverInfo.homeTodayDriveImage)
                textHomeTodayDriveTitle.text = homeTodayDriverInfo.homeTodayDriveTitle
                chipHomeTodayDrive1.text = homeTodayDriverInfo.homeTodayDriveChip_1
                chipHomeTodayDrive2.text = homeTodayDriverInfo.homeTodayDriveChip_2
                chipHomeTodayDrive3.text = homeTodayDriverInfo.homeTodayDriveChip_3

                with(imgHomeTodayDriveHeart){
                    isSelected = false
                    setOnClickListener { it.isSelected = !it.isSelected }
                }
            }
        }
    }
}