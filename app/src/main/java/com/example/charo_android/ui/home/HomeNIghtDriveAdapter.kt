package com.example.charo_android.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.charo_android.databinding.ItemHomeNightDriveBinding

class HomeNIghtDriveAdapter():RecyclerView.Adapter<HomeNIghtDriveAdapter.HomeNightDriveViewHolder>() {
    val nightData = mutableListOf<HomeNightDriveInfo>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeNIghtDriveAdapter.HomeNightDriveViewHolder {
        val binding = ItemHomeNightDriveBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeNightDriveViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: HomeNIghtDriveAdapter.HomeNightDriveViewHolder,
        position: Int
    ) {
        holder.onBind(nightData[position])
    }

    override fun getItemCount(): Int {
        return nightData.size
    }

    class HomeNightDriveViewHolder(
        private val binding : ItemHomeNightDriveBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun onBind(homeNightDriveInfo: HomeNightDriveInfo){
            binding.apply {
                imgHomeNightDrive.setImageResource(homeNightDriveInfo.homeNightDriveImage)
                textHomeNightDriveTitle.text = homeNightDriveInfo.homeNightDriveTitle
                chipHomeNightDrive1.text = homeNightDriveInfo.homeNightDriveChip_1
                chipHomeNightDrive2.text = homeNightDriveInfo.homeNightDriveChip_2
                chipHomeNightDrive3.text = homeNightDriveInfo.homeNightDriveChip_3

                with(imgHomeNightDriveHeart) {
                    isSelected = false
                    setOnClickListener { it.isSelected = !it.isSelected }
                }
            }
        }

    }
}