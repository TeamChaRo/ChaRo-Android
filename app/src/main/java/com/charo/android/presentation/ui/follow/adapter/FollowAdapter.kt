package com.charo.android.presentation.ui.follow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.charo.android.R
import com.charo.android.data.model.mypage.User
import com.charo.android.databinding.ItemMyPageFollowBinding

class FollowAdapter(
    private val itemClick: (User) -> Unit,
    private val followClick: (User) -> Unit,
    private val userEmail: String
) :
    RecyclerView.Adapter<FollowAdapter.FollowViewHolder>() {
    private val asyncDiffer = AsyncListDiffer(this, diffCallback)

    class FollowViewHolder(
        private val binding: ItemMyPageFollowBinding,
        private val itemClick: (User) -> Unit,
        private val followClick: (User) -> Unit,
        private val userEmail: String
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: User) {
            binding.model = model
            binding.isMe = model.userEmail == userEmail
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                itemClick(model)
            }

            binding.tvFollow.setOnClickListener {
                followClick(model)
                model.isFollow = !model.isFollow
                binding.model = model
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        val binding = DataBindingUtil.inflate<ItemMyPageFollowBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_my_page_follow,
            parent,
            false
        )
        return FollowViewHolder(binding, itemClick, followClick, userEmail)
    }

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        holder.bind(asyncDiffer.currentList[position])
    }

    override fun getItemCount(): Int {
        return asyncDiffer.currentList.size
    }

    fun replaceItem(itemList: List<User>) {
        asyncDiffer.submitList(itemList)
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.userEmail == newItem.userEmail
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

        }
    }
}