package com.example.charo_android.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.charo_android.R
import com.example.charo_android.api.ResponseHomeViewData
import com.example.charo_android.databinding.ItemHomeHotDriveBinding

class HomeHotDriveAdapter () : RecyclerView.Adapter<HomeHotDriveAdapter.HomeHotDriveViewHolder>() {
    val hotData = mutableListOf<ResponseHomeViewData.Data.TrendDrive>()

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
        fun onBind(responseHomeViewTrendData: ResponseHomeViewData.Data.TrendDrive) {
           binding.apply{
               with(responseHomeViewTrendData){
                   Glide.with(imgHomeHotDrive.context)
                       .load(this.image)
                       .into(imgHomeHotDrive)

                   textHomeHotDriveTitle.text = this.title

                   if (this.tags.count() == 2) {
                       chipHomeHotDrive1.text = this.tags[0]
                       chipHomeHotDrive2.text = this.tags[1]
                       chipHomeHotDrive3.visibility = View.INVISIBLE

                   } else if(this.tags.count() == 1){
                       chipHomeHotDrive1.text = this.tags[0]
                       chipHomeHotDrive2.visibility = View.INVISIBLE
                       chipHomeHotDrive3.visibility = View.INVISIBLE
                   }
                   else {
                       chipHomeHotDrive1.text = this.tags[0]
                       chipHomeHotDrive2.text = this.tags[1]
                       chipHomeHotDrive3.text = this.tags[2]
                   }
               }

               imgHomeHotDriveHeart.setImageResource(R.drawable.selector_home_heart)
               if (responseHomeViewTrendData.isFavorite == false){
                   with(imgHomeHotDriveHeart) {
                       this.isSelected = false
                       this.setOnClickListener { this.isSelected =! this.isSelected }
                   }
               } else{
                   with(imgHomeHotDriveHeart){
                       this.isSelected = true
                       this.setOnClickListener { this.isSelected =! this.isSelected }
                   }
               }
           }
        }
    }
}