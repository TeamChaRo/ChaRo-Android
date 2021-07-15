package com.example.charo_android.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.charo_android.R
import com.example.charo_android.api.ResponseHomeViewData
import com.example.charo_android.databinding.ItemHomeNightDriveBinding

class HomeNightDriveAdapter() :
    RecyclerView.Adapter<HomeNightDriveAdapter.HomeNightDriveViewHolder>() {
    val nightData = mutableListOf<ResponseHomeViewData.Data.CustomThemeDrive>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeNightDriveAdapter.HomeNightDriveViewHolder {
        val binding = ItemHomeNightDriveBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeNightDriveViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: HomeNightDriveAdapter.HomeNightDriveViewHolder,
        position: Int
    ) {
        holder.onBind(nightData[position])
    }

    override fun getItemCount(): Int {
        return nightData.size
    }

    class HomeNightDriveViewHolder(
        private val binding: ItemHomeNightDriveBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(responseHomeCustomThemeDrive: ResponseHomeViewData.Data.CustomThemeDrive) {
            binding.apply {
                with(responseHomeCustomThemeDrive) {
                    Glide.with(imgHomeNightDrive.context)
                        .load(this.image)
                        .into(imgHomeNightDrive)


                    textHomeNightDriveTitle.text = this.title
                    if (this.tags.count() == 2) {
                        chipHomeNightDrive1.text = this.tags[0]
                        chipHomeNightDrive2.text = this.tags[1]
                        chipHomeNightDrive3.visibility = View.INVISIBLE

                    } else if (this.tags.count() == 1) {
                        chipHomeNightDrive1.text = this.tags[0]
                        chipHomeNightDrive2.visibility = View.INVISIBLE
                        chipHomeNightDrive3.visibility = View.INVISIBLE
                    } else {
                        chipHomeNightDrive1.text = this.tags[0]
                        chipHomeNightDrive2.text = this.tags[1]
                        chipHomeNightDrive3.text = this.tags[2]
                    }

                }

                imgHomeNightDriveHeart.setImageResource(R.drawable.selector_home_heart)
                if (responseHomeCustomThemeDrive.isFavorite == false) {
                    with(imgHomeNightDriveHeart) {
                        this.isSelected = false
                        this.setOnClickListener { this.isSelected = !this.isSelected }
                    }
                } else {
                    with(imgHomeNightDriveHeart) {
                        this.isSelected = true
                        this.setOnClickListener { this.isSelected = !this.isSelected }
                    }
                }
            }
        }
    }

}