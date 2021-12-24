package com.example.charo_android.presentation.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.charo_android.databinding.ItemDetailImageViewPagerBinding

class DetailImageViewPagerAdapter :
    RecyclerView.Adapter<DetailImageViewPagerAdapter.DetailImageViewHolder>() {
    val itemList = mutableListOf<String>()

    class DetailImageViewHolder(private val binding: ItemDetailImageViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(imageUrl: String) {
            Glide.with(itemView.context)
                .load(imageUrl)
                .into(binding.imgDetailImageViewPager)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailImageViewHolder {
        val binding = ItemDetailImageViewPagerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DetailImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailImageViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}