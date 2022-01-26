package com.example.charo_android.presentation.ui.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.charo_android.R
import com.example.charo_android.data.model.mypage.Post
import com.example.charo_android.databinding.ItemProfilePostBinding

class PostAdapter: RecyclerView.Adapter<PostAdapter.WrittenPostViewHolder>() {
    val itemList = mutableListOf<Post>()

    class WrittenPostViewHolder(private val binding: ItemProfilePostBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Post) {
            binding.model = model
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WrittenPostViewHolder {
        val binding = DataBindingUtil.inflate<ItemProfilePostBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_profile_post,
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