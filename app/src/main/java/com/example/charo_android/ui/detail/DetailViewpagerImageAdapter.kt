package com.example.charo_android.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.charo_android.data.DetailViewpagerImageInfo
import com.example.charo_android.databinding.ItemDetailImageBinding

class DetailViewpagerImageAdapter: RecyclerView.Adapter<DetailViewpagerImageAdapter.DetailImageViewHolder>() {
    val imageList = mutableListOf<DetailViewpagerImageInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailImageViewHolder {
        val binding = ItemDetailImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DetailImageViewHolder(binding)
    }

    override fun getItemCount(): Int = imageList.size

    override fun onBindViewHolder(holder: DetailImageViewHolder, position: Int) {
        holder.onBind(imageList[position])
    }

    class DetailImageViewHolder(
        private val binding : ItemDetailImageBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun onBind(detailImageInfo: DetailViewpagerImageInfo){
//            binding.imgDetailViewpagerImage.setImageResource(detailImageInfo.image)
            Glide.with(itemView.context)
                .load(detailImageInfo.image)
                .into(binding.imgDetailViewpagerImage)
        }
    }
}