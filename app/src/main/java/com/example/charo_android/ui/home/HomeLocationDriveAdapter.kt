package com.example.charo_android.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.charo_android.databinding.ItemHomeLocationDriveBinding

class HomeLocationDriveAdapter: RecyclerView.Adapter<HomeLocationDriveAdapter.HomeLocationDriveViewHolder>() {
    val locationData = mutableListOf<HomeLocationDriveInfo>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeLocationDriveAdapter.HomeLocationDriveViewHolder {
        val binding = ItemHomeLocationDriveBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeLocationDriveViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: HomeLocationDriveAdapter.HomeLocationDriveViewHolder,
        position: Int
    ) {
        holder.onBind(locationData[position])
    }

    override fun getItemCount(): Int {
        return locationData.size
    }

    class HomeLocationDriveViewHolder(
        private val binding : ItemHomeLocationDriveBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun onBind(homeLocationDriveInfo : HomeLocationDriveInfo){
            binding.apply {
                imgHomeLocationDrive.setImageResource(homeLocationDriveInfo.homeLocationDriveImage)
                textHomeLocationDriveTitle.text = homeLocationDriveInfo.homeLocationDriveTitle
                chipHomeLocationDrive1.text = homeLocationDriveInfo.homeLocationDriveChip_1
                chipHomeLocationDrive2.text = homeLocationDriveInfo.homeLocationDriveChip_2
                chipHomeLocationDrive3.text = homeLocationDriveInfo.homeLocationDriveChip_3

                with(imgHomeLocationDriveHeart) {
                    isSelected = false
                    setOnClickListener { it.isSelected = !it.isSelected }
                }
            }

        }

    }
}