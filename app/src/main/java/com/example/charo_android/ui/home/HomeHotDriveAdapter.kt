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
           binding.homeHotDriveData = homeHotDriveInfo

                binding.apply{
                    with(imgHomeHotDriveHeart) {
                    isSelected = false
                    setOnClickListener { it.isSelected = !it.isSelected }
                }
            }

        }
    }
}