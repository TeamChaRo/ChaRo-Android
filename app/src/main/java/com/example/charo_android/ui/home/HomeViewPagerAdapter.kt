package com.example.charo_android.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.charo_android.api.ResponseHomeViewData
import com.example.charo_android.databinding.ItemHomeViewpagerBinding

class HomeViewPagerAdapter() :
    RecyclerView.Adapter<HomeViewPagerAdapter.HomeViewPagerViewHolder>() {
    val item = mutableListOf<ResponseHomeViewData.Data.Banner>()
    val localItem = mutableListOf<HomeViewPagerInfo>()

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
        holder.onBind(item[position], localItem[position])
    }

    override fun getItemCount(): Int {
        return item.size and localItem.size
    }

    class HomeViewPagerViewHolder(
        private val binding: ItemHomeViewpagerBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            responseMainViewDataBanner: ResponseHomeViewData.Data.Banner,
            homeViewPagerInfo: HomeViewPagerInfo
        ) {
            binding.apply {
                with(responseMainViewDataBanner) {
                    Glide.with(imgViewpager.context)
                        .load(this.bannerImage)
                        .into(imgViewpager)

                    textViewpagerTitle.text = this.bannerTitle
                    textViewpagerHashtag.text = this.bannerTag
                }
                imgViewpagerLine.setImageResource(homeViewPagerInfo.homeViewPagerRoadImage)
            }


        }

    }
}