package com.charo.android.presentation.ui.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.charo.android.databinding.ItemHomeViewpagerLocalBinding
import com.charo.android.domain.model.home.BannerLocal
import com.charo.android.domain.model.home.BannerRoad
import com.charo.android.presentation.ui.home.BannerAboutCharoActivity
import com.charo.android.presentation.ui.home.BannerDriveTheaterActivity
import com.charo.android.presentation.ui.home.BannerGangneungActivity
import com.charo.android.presentation.ui.home.BannerSpringPlaylistActivity

class HomeViewPagerLocalAdapter() :
    RecyclerView.Adapter<HomeViewPagerLocalAdapter.HomeViewPagerViewHolder>() {
    private val _banner = mutableListOf<BannerLocal>()
    var banner: List<BannerLocal> = _banner

    private val _bannerRoad = mutableListOf<BannerRoad>()
    var bannerRoad: List<BannerRoad> = _bannerRoad

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeViewPagerViewHolder {
        val binding = ItemHomeViewpagerLocalBinding.inflate(
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

        holder.itemView.setOnClickListener {
            val activity = it.context as AppCompatActivity

            when (position) {
                0 -> {
                    activity.startActivity(Intent(activity, BannerGangneungActivity::class.java))
                }
                1 -> {
                    activity.startActivity(
                        Intent(
                            activity,
                            BannerSpringPlaylistActivity::class.java
                        )
                    )
                }
                2 -> {
                    activity.startActivity(Intent(activity, BannerDriveTheaterActivity::class.java))
                }
                3 -> {
                    activity.startActivity(Intent(activity, BannerAboutCharoActivity::class.java))
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return banner.size
    }

    class HomeViewPagerViewHolder(
        private val binding: ItemHomeViewpagerLocalBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(banner: BannerLocal) {

            Glide.with(binding.imgViewpager)
                .load(banner.homeViewPagerRoadImage)
                .into(binding.imgViewpager)

            if (banner.homeViewPagerSubTitleImg == 0) {
                binding.textViewpagerSubTitleImg.visibility = View.GONE
            } else {
                binding.textViewpagerSubTitleImg.visibility = View.VISIBLE
                binding.textViewpagerSubTitleImg.setImageResource(banner.homeViewPagerSubTitleImg)
            }

            if (banner.charoImgVisible) {
                binding.imgViewpagerCharo.visibility = View.VISIBLE
            } else {
                binding.imgViewpagerCharo.visibility = View.INVISIBLE
            }
            binding.textViewpagerTitle.textSize = banner.titleFontSize
            binding.banner = banner
            binding.executePendingBindings()
        }
    }

    fun setHomeBanner(banner: List<BannerLocal>) {
        this.banner = banner
        notifyDataSetChanged()
    }

}