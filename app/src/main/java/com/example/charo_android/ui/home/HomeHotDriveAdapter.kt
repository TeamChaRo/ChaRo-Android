package com.example.charo_android.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.charo_android.databinding.ItemHomeHotDriveBinding

class HomeHotDriveAdapter() : RecyclerView.Adapter<HomeHotDriveAdapter.HomeHotDriveViewHolder>() {
    val hotData = mutableListOf<HomeHotDriveInfo>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeHotDriveAdapter.HomeHotDriveViewHolder {
        val binding = ItemHomeHotDriveBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeHotDriveViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: HomeHotDriveAdapter.HomeHotDriveViewHolder,
        position: Int
    ) {

        holder.onBind(hotData[position])

    }

    override fun getItemCount(): Int {
        return hotData.size
    }

    class HomeHotDriveViewHolder(
        private val binding: ItemHomeHotDriveBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(homeHotDriveInfo: HomeHotDriveInfo) {
            binding.apply {
                imgHomeHotDrive.setImageResource(homeHotDriveInfo.homeHotDriveImage)
                textHomeHotDriveTitle.text = homeHotDriveInfo.homeHotDriveTitle
                chipHomeHotDrive1.text = homeHotDriveInfo.homeHotDriveChip_1
                chipHomeHotDrive2.text = homeHotDriveInfo.homeHotDriveChip_2
                chipHomeTodayDrive3.text = homeHotDriveInfo.homeHotDriveChip_3

                with(imgHomeHotDriveHeart) {
                    isSelected = false
                    setOnClickListener { it.isSelected = !it.isSelected }
                }
            }

        }
    }
}