package com.charo.android.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charo.android.databinding.ItemHomeViewpagerBinding
import com.charo.android.domain.model.home.Banner
import com.charo.android.domain.model.home.BannerRoad

class HomeViewPagerAdapter() :
    RecyclerView.Adapter<HomeViewPagerAdapter.HomeViewPagerViewHolder>() {
    private val _banner = mutableListOf<Banner>()
    var banner: List<Banner> = _banner

    private val _bannerRoad = mutableListOf<BannerRoad>()
    var bannerRoad : List<BannerRoad> = _bannerRoad

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
        holder.onBind(banner[position], bannerRoad[position])

    }

    override fun getItemCount(): Int {
        return banner.size
    }

    class HomeViewPagerViewHolder(
        private val binding: ItemHomeViewpagerBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(banner : Banner, bannerRoad: BannerRoad){
            binding.banner = banner
            binding.bannerRoad = bannerRoad
            binding.executePendingBindings()
        }
    }

    fun setHomeBanner(banner: List<Banner>, bannerRoad: List<BannerRoad>){
        this.banner = banner
        this.bannerRoad = bannerRoad
        notifyDataSetChanged()
    }

}