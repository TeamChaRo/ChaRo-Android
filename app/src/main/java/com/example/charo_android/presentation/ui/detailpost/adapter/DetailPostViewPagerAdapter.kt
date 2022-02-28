package com.example.charo_android.presentation.ui.detailpost.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.charo_android.R
import com.example.charo_android.databinding.ItemDetailPostBinding
import com.example.charo_android.databinding.ItemMyPagePostBinding

class DetailPostViewPagerAdapter :
    RecyclerView.Adapter<DetailPostViewPagerAdapter.DetailPostViewHolder>() {
    private val asyncDiffer = AsyncListDiffer(this, diffCallback)

    class DetailPostViewHolder(private val binding: ItemDetailPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: String) {
            binding.image = image
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailPostViewHolder {
        val binding = DataBindingUtil.inflate<ItemDetailPostBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_detail_post,
            parent,
            false
        )
        return DetailPostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailPostViewHolder, position: Int) {
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