package com.example.charo_android.presentation.ui.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.charo_android.data.model.detail.DetailViewpagerImageInfo
import com.example.charo_android.databinding.ItemDetailImageBinding

class DetailViewpagerAdapter(private val itemClick: (String) -> Unit) :
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
        holder.onBind(itemList[position], position + 1, itemList.size)
    }

    class DetailImageViewHolder(
        private val binding: ItemDetailImageBinding,
        private val itemClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(url: String, index: Int, itemCount: Int) {
            Glide.with(itemView.context)
                .load(url)
                .into(binding.imgDetailViewpagerImage)

            binding.root.setOnClickListener {
                itemClick(url)
            }
        }
    }
}