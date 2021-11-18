package com.example.charo_android.presentation.ui.more.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.charo_android.R
import com.example.charo_android.data.model.response.more.ResponseMoreViewData
import com.example.charo_android.databinding.ItemMoreViewBinding
import com.example.charo_android.domain.model.more.MoreDrive
import com.example.charo_android.presentation.ui.detail.DetailActivity

class MoreViewAdapter(val userId: String) :
    RecyclerView.Adapter<MoreViewAdapter.MoreViewViewHolder>() {
     private val _moreData = mutableListOf<MoreDrive>()
     private var moreData : List<MoreDrive> = _moreData


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoreViewViewHolder {
        val binding = ItemMoreViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MoreViewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoreViewViewHolder, position: Int) {
        holder.onBind(moreData[position])
        holder.binding.root.setOnClickListener() {
            val intent = Intent(holder.itemView?.context, DetailActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("postId", moreData[position].morePostId)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return moreData.size
    }

    class MoreViewViewHolder(
        val binding: ItemMoreViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(moreData: MoreDrive) {
            binding.apply {
                moreDrive = moreData
                binding.executePendingBindings()

            }


        }

    }

    fun setHomeTrendDrive(moreData: List<MoreDrive>){
        this.moreData = moreData
        notifyDataSetChanged()
    }
}
