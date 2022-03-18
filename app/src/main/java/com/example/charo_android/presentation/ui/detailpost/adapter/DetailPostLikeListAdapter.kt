package com.example.charo_android.presentation.ui.detailpost.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.charo_android.R
import com.example.charo_android.databinding.ItemDetailLikeBinding
import com.example.charo_android.domain.model.detailpost.User

class DetailPostLikeListAdapter(private val itemClick: (User) -> Unit) :
    RecyclerView.Adapter<DetailPostLikeListAdapter.DetailPostLikeListViewHolder>() {
    val asyncDiffer = AsyncListDiffer(this, diffCallback)

    class DetailPostLikeListViewHolder(
        private val binding: ItemDetailLikeBinding,
        private val itemClick: (User) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: User) {
            binding.model = model
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                itemClick(model)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailPostLikeListViewHolder {
        val binding = DataBindingUtil.inflate<ItemDetailLikeBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_detail_like,
            parent,
            false
        )
        return DetailPostLikeListViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: DetailPostLikeListViewHolder, position: Int) {
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