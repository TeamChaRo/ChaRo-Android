package com.example.charo_android.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.charo_android.data.LocalHomeViewPagerDataSource
import com.example.charo_android.databinding.ItemHomeViewpagerBinding

class HomeViewPagerAdapter(): RecyclerView.Adapter<HomeViewPagerAdapter.HomeViewPagerViewHolder>() {
    val item = mutableListOf<HomeViewPagerImage>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeViewPagerAdapter.HomeViewPagerViewHolder {
        val binding = ItemHomeViewpagerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: HomeViewPagerAdapter.HomeViewPagerViewHolder,
        position: Int
    ) {
        holder.onBind(item[position])
    }

    override fun getItemCount(): Int {
        return item.size
    }
    class HomeViewPagerViewHolder(
        private val binding: ItemHomeViewpagerBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(homeViewPagerImage: HomeViewPagerImage){
            binding.imgViewpager.setImageResource(homeViewPagerImage.homeViewPagerImage)
        }

    }
}