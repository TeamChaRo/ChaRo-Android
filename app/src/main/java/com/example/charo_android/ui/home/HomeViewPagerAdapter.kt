package com.example.charo_android.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.charo_android.databinding.ItemHomeViewpagerBinding

class HomeViewPagerAdapter(): RecyclerView.Adapter<HomeViewPagerAdapter.HomeViewPagerViewHolder>() {
    val item = mutableListOf<HomeViewPagerInfo>()

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
        holder: HomeViewPagerViewHolder,
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
        fun onBind(homeViewPagerinfo: HomeViewPagerInfo){
            binding.imgViewpager.setImageResource(homeViewPagerinfo.homeViewPagerImage)
            binding.textViewpagerTitle.text = homeViewPagerinfo.homeViewPagerTitle
            binding.textViewpagerHashtag.text = homeViewPagerinfo.homeViewPagerHashTag
        }

    }
}