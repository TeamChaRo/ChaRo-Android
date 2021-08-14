package com.example.charo_android.ui.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.charo_android.R
import com.example.charo_android.data.response.ResponseHomeViewData
import com.example.charo_android.databinding.ItemHomeLocationDriveBinding
import com.example.charo_android.ui.detail.DetailActivity

class HomeLocationDriveAdapter(val userId: String) :
    RecyclerView.Adapter<HomeLocationDriveAdapter.HomeLocationDriveViewHolder>() {
    val locationData = mutableListOf<ResponseHomeViewData.Data.LocalDrive>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeLocationDriveViewHolder {
        val binding = ItemHomeLocationDriveBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeLocationDriveViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: HomeLocationDriveViewHolder,
        position: Int
    ) {
        holder.onBind(locationData[position])

        holder.binding.root.setOnClickListener() {
            val intent = Intent(holder.itemView?.context, DetailActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("postId", locationData[position].postId)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }
    override fun getItemCount(): Int {
        return locationData.size
    }

    class HomeLocationDriveViewHolder(
         val binding: ItemHomeLocationDriveBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(responseHomeLocalData: ResponseHomeViewData.Data.LocalDrive) {
            binding.apply {
                with(responseHomeLocalData) {
                    com.bumptech.glide.Glide.with(imgHomeLocationDrive.context)
                        .load(this.image)
                        .transform(RoundedCorners(20))
                        .into(imgHomeLocationDrive)
                }
                textHomeLocationDriveTitle.text = responseHomeLocalData.title
                if (responseHomeLocalData.tags.count() == 2) {
                    chipHomeLocationDrive1.text = "#${responseHomeLocalData.tags[0]}"
                    chipHomeLocationDrive2.text = "#${responseHomeLocalData.tags[1]}"
                    chipHomeLocationDrive3.visibility = View.INVISIBLE

                } else if (responseHomeLocalData.tags.count() == 1) {
                    chipHomeLocationDrive1.text = "#${responseHomeLocalData.tags[0]}"
                    chipHomeLocationDrive2.visibility = View.INVISIBLE
                    chipHomeLocationDrive3.visibility = View.INVISIBLE
                } else {
                    chipHomeLocationDrive1.text = "#${responseHomeLocalData.tags[0]}"
                    chipHomeLocationDrive2.text = "#${responseHomeLocalData.tags[1]}"
                    chipHomeLocationDrive3.text = "#${responseHomeLocalData.tags[2]}"
                }


                    imgHomeLocationDriveHeart.setImageResource(R.drawable.selector_home_heart)
                if (responseHomeLocalData.isFavorite == false) {
                    with(imgHomeLocationDriveHeart) {
                        this.isSelected = false
                        this.setOnClickListener { this.isSelected = !this.isSelected }
                    }
                } else {
                    with(imgHomeLocationDriveHeart) {
                        this.isSelected = true
                        this.setOnClickListener { this.isSelected = !this.isSelected }
                    }
                }
            }
        }

    }

}
