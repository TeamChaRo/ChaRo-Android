package com.example.charo_android.presentation.ui.home.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.charo_android.R
import com.example.charo_android.data.model.response.ResponseHomeViewData
import com.example.charo_android.databinding.ItemHomeTodayDriveBinding
import com.example.charo_android.domain.model.home.TodayCharoDrive
import com.example.charo_android.presentation.ui.detail.DetailActivity

class HomeTodayDriveAdapter(val userId: String): RecyclerView.Adapter<HomeTodayDriveAdapter.HomeTodayDriveViewHolder>() {
    private val _todayCharoDrive = mutableListOf<TodayCharoDrive>()
    private var todayCharoDrive : List<TodayCharoDrive> = _todayCharoDrive

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeTodayDriveViewHolder {
        val binding = ItemHomeTodayDriveBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeTodayDriveViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: HomeTodayDriveViewHolder,
        position: Int
    ) {
        holder.onBind(todayCharoDrive[position])

        holder.binding.root.setOnClickListener() {
            val intent = Intent(holder.itemView?.context, DetailActivity::class.java)
            intent.putExtra("userId", userId)
            // intent.putExtra("postId", driveData[position].postId)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return todayCharoDrive.size
    }

    class HomeTodayDriveViewHolder(
         val binding : ItemHomeTodayDriveBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(todayCharoDrive: TodayCharoDrive){
            binding.apply {
               binding.todayCharoDrive = todayCharoDrive
                binding.executePendingBindings()

            }
        }
    }

    fun setTodayDrive(todayCharoDrive: List<TodayCharoDrive>){
        this.todayCharoDrive = todayCharoDrive
        notifyDataSetChanged()
    }
}