package com.example.charo_android.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.charo_android.databinding.ItemHomeViewpagerBinding
import com.example.charo_android.data.model.entity.BannerEntity
import com.example.charo_android.domain.model.home.Banner

class HomeViewPagerAdapter() :
    RecyclerView.Adapter<HomeViewPagerAdapter.HomeViewPagerViewHolder>() {
    private val _banner = mutableListOf<Banner>()
    var banner: List<Banner> = _banner

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeViewPagerViewHolder {
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
        holder.onBind(banner[position])
    }

    override fun getItemCount(): Int {
        return banner.size
    }

    class HomeViewPagerViewHolder(
        private val binding: ItemHomeViewpagerBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(banner : Banner){
            binding.banner = banner
            binding.executePendingBindings()
        }
    }

    fun setHomeBanner(banner: List<Banner>){
        this.banner = banner
        notifyDataSetChanged()
    }

}