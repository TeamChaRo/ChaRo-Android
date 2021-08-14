package com.example.charo_android.ui.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.charo_android.R
import com.example.charo_android.data.response.ResponseHomeViewData
import com.example.charo_android.databinding.ItemHomeHotDriveBinding
import com.example.charo_android.ui.detail.DetailActivity

class HomeHotDriveAdapter (val userId : String) : RecyclerView.Adapter<HomeHotDriveAdapter.HomeHotDriveViewHolder>() {
    val hotData = mutableListOf<ResponseHomeViewData.Data.TrendDrive>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeHotDriveViewHolder {
        val binding = ItemHomeHotDriveBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeHotDriveViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: HomeHotDriveViewHolder,
        position: Int
    ) {

        holder.onBind(hotData[position])

        holder.binding.root.setOnClickListener() {
            val intent = Intent(holder.itemView?.context, DetailActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("postId", hotData[position].postId)
            ContextCompat.startActivity(holder.itemView.context, intent, null)

        }

    }

    override fun getItemCount(): Int {
        return hotData.size
    }

    class HomeHotDriveViewHolder(
        val binding: ItemHomeHotDriveBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(responseHomeViewTrendData: ResponseHomeViewData.Data.TrendDrive) {
           binding.apply{
               with(responseHomeViewTrendData){
                   Glide.with(imgHomeHotDrive.context)
                       .load(this.image)
                       .transform(RoundedCorners(20))
                       .into(imgHomeHotDrive)

                   textHomeHotDriveTitle.text = this.title

                   if (this.tags.count() == 2) {
                       chipHomeHotDrive1.text = "#${this.tags[0]}"
                       chipHomeHotDrive2.text = "#${this.tags[1]}"
                       chipHomeHotDrive3.visibility = View.INVISIBLE

                   } else if(this.tags.count() == 1){
                       chipHomeHotDrive1.text = "#${this.tags[0]}"
                       chipHomeHotDrive2.visibility = View.INVISIBLE
                       chipHomeHotDrive3.visibility = View.INVISIBLE
                   }
                   else {
                       chipHomeHotDrive1.text = "#${this.tags[0]}"
                       chipHomeHotDrive2.text = "#${this.tags[1]}"
                       chipHomeHotDrive3.text = "#${this.tags[2]}"
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