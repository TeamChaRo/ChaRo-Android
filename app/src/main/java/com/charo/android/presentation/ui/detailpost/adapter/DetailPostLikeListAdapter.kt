package com.charo.android.presentation.ui.detailpost.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.charo.android.R
import com.charo.android.databinding.ItemDetailLikeBinding
import com.charo.android.domain.model.detailpost.User

class DetailPostLikeListAdapter(
    private val userEmail: String,
    private val itemClick: (User) -> Unit,
    private val followClick: (User) -> Unit
) :
    RecyclerView.Adapter<DetailPostLikeListAdapter.DetailPostLikeListViewHolder>() {
    val asyncDiffer = AsyncListDiffer(this, diffCallback)

    class DetailPostLikeListViewHolder(
        private val binding: ItemDetailLikeBinding,
        private val userEmail: String,
        private val itemClick: (User) -> Unit,
        private val followClick: (User) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: User) {
            binding.model = model
            if(model.userEmail == userEmail) {
                binding.tvDetailLikeFollow.visibility = View.GONE
            }
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                itemClick(model)
            }
            binding.tvDetailLikeFollow.setOnClickListener {
                followClick(model)
                model.isFollow = !model.isFollow
                binding.model = model
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
        return DetailPostLikeListViewHolder(binding, userEmail, itemClick, followClick)
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