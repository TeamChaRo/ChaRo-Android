package com.example.charo_android.presentation.ui.detailpost.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.charo_android.R
import com.example.charo_android.databinding.ItemDetailPostImageBinding

class DetailPostImageViewPagerAdapter :
    RecyclerView.Adapter<DetailPostImageViewPagerAdapter.DetailPostImageViewHolder>() {
    private val asyncDiffer = AsyncListDiffer<String>(this, diffCallback)

    class DetailPostImageViewHolder(private val binding: ItemDetailPostImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: String) {
            binding.image = image
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailPostImageViewHolder {
        val binding = DataBindingUtil.inflate<ItemDetailPostImageBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_detail_post_image,
            parent,
            false
        )
        return DetailPostImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailPostImageViewHolder, position: Int) {
        holder.bind(asyncDiffer.currentList[position])
    }

    override fun getItemCount(): Int {
        return asyncDiffer.currentList.size
    }

    fun replaceItem(itemList: List<String>) {
        asyncDiffer.submitList(itemList)
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

        }
    }
}