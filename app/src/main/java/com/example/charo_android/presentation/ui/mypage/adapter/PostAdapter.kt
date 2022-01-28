package com.example.charo_android.presentation.ui.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.charo_android.R
import com.example.charo_android.data.model.mypage.Post
import com.example.charo_android.databinding.ItemMyPagePostBinding

class PostAdapter: RecyclerView.Adapter<PostAdapter.WrittenPostViewHolder>() {
    val itemList = mutableListOf<Post>()

    class WrittenPostViewHolder(private val binding: ItemMyPagePostBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Post) {
            binding.model = model
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WrittenPostViewHolder {
        val binding = DataBindingUtil.inflate<ItemMyPagePostBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_my_page_post,
            parent,
            false
        )
        return WrittenPostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WrittenPostViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}