package com.example.charo_android.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.charo_android.databinding.ItemHomeThemeBinding

class HomeThemeAdapter() : RecyclerView.Adapter<HomeThemeAdapter.HomeThemeViewHolder>() {
    val themeData = mutableListOf<HomeThemeInfo>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeThemeAdapter.HomeThemeViewHolder {
        val binding = ItemHomeThemeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeThemeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeThemeAdapter.HomeThemeViewHolder, position: Int) {
        holder.onBind(themeData[position])
    }

    override fun getItemCount(): Int {
        return themeData.size
    }

    class HomeThemeViewHolder(
        private val binding: ItemHomeThemeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(homeThemeInfo: HomeThemeInfo) {
            binding.apply {
                imgHomeTheme.setImageResource(homeThemeInfo.homeThemeImage)
                textHomeTheme.text = homeThemeInfo.homeThemeTitle
            }
        }
    }
}
