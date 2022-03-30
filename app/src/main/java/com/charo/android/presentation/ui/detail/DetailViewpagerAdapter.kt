package com.charo.android.presentation.ui.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.charo.android.databinding.ItemDetailImageBinding

class DetailViewpagerAdapter(private val itemClick: (Int) -> Unit) :
    RecyclerView.Adapter<DetailViewpagerAdapter.DetailImageViewHolder>() {
    val itemList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailImageViewHolder {
        val binding = ItemDetailImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DetailImageViewHolder(binding, itemClick)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: DetailImageViewHolder, position: Int) {
        holder.onBind(itemList[position], position)
    }

    class DetailImageViewHolder(
        private val binding: ItemDetailImageBinding,
        private val itemClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(url: String, position: Int) {
            Glide.with(itemView.context)
                .load(url)
                .into(binding.imgDetailViewpagerImage)

            binding.root.setOnClickListener {
                itemClick(position)
            }
        }
    }
}