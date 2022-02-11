package com.example.charo_android.presentation.ui.follow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.charo_android.R
import com.example.charo_android.data.model.mypage.User
import com.example.charo_android.databinding.ItemMyPageFollowBinding

class FollowAdapter : RecyclerView.Adapter<FollowAdapter.FollowViewHolder>() {
    var itemList = mutableListOf<User>()

    class FollowViewHolder(private val binding: ItemMyPageFollowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: User) {
            binding.model = model
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        val binding = DataBindingUtil.inflate<ItemMyPageFollowBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_my_page_follow,
            parent,
            false
        )
        return FollowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}