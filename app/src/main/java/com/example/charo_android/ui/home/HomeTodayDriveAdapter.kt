package com.example.charo_android.ui.home

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.charo_android.R
import com.example.charo_android.api.ResponseHomeViewData
import com.example.charo_android.api.ResponseMoreViewData
import com.example.charo_android.databinding.ItemHomeTodayDriveBinding
import com.example.charo_android.ui.detail.DetailActivity

class HomeTodayDriveAdapter(val userId: String): RecyclerView.Adapter<HomeTodayDriveAdapter.HomeTodayDriveViewHolder>() {
    val driveData = mutableListOf<ResponseHomeViewData.Data.TodayCharoDrive>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeTodayDriveAdapter.HomeTodayDriveViewHolder {
        val binding = ItemHomeTodayDriveBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeTodayDriveViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: HomeTodayDriveAdapter.HomeTodayDriveViewHolder,
        position: Int
    ) {
        holder.onBind(driveData[position])
        val intent = Intent(holder.itemView?.context, DetailActivity::class.java)
        intent.putExtra("userId", userId)
        intent.putExtra("postId", driveData[position].postId)
        ContextCompat.startActivity(holder.itemView.context, intent, null)
    }

    override fun getItemCount(): Int {
        return driveData.size
    }

    class HomeTodayDriveViewHolder(
        private val binding : ItemHomeTodayDriveBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(responseHomeViewData : ResponseHomeViewData.Data.TodayCharoDrive){
            binding.apply {
                with(responseHomeViewData) {
                    Glide.with(imgHomeTodayDrive.context)
                        .load(responseHomeViewData.image)
                        .placeholder(R.drawable.ic_mask_group_4)
                        .into(imgHomeTodayDrive)

                    Log.d("go",responseHomeViewData.image.toString())

                    textHomeTodayDriveTitle.text = this.title
                    if (this.tags.count() == 2) {
                        chipHomeTodayDrive1.text = this.tags[0]
                        chipHomeTodayDrive2.text = this.tags[1]
                        chipHomeTodayDrive3.visibility = View.INVISIBLE

                    } else if(this.tags.count() == 1){
                        chipHomeTodayDrive1.text = this.tags[0]
                        chipHomeTodayDrive2.visibility = View.INVISIBLE
                        chipHomeTodayDrive3.visibility = View.INVISIBLE
                    }
                    else {
                        chipHomeTodayDrive1.text = this.tags[0]
                        chipHomeTodayDrive2.text = this.tags[1]
                        chipHomeTodayDrive3.text = this.tags[2]
                    }

                }


                imgHomeTodayDriveHeart.setImageResource(R.drawable.selector_home_heart)
                if (responseHomeViewData.isFavorite == false){
                    with(imgHomeTodayDriveHeart) {
                        this.isSelected = false
                        this.setOnClickListener { this.isSelected =! this.isSelected }
                    }
                } else{
                    with(imgHomeTodayDriveHeart){
                        this.isSelected = true
                        this.setOnClickListener { this.isSelected =! this.isSelected }
                    }
                }
            }
        }
    }
}