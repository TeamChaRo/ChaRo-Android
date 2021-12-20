package com.example.charo_android.presentation.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.charo_android.R
import com.example.charo_android.data.model.detail.UserData
import com.example.charo_android.databinding.ItemDetailLikeBinding

class DetailLikeAdapter(val itemClick: (UserData) -> Unit) :
    RecyclerView.Adapter<DetailLikeAdapter.DetailLikeViewHolder>() {
    val itemList = mutableListOf<UserData>()

    class DetailLikeViewHolder(
        private val binding: ItemDetailLikeBinding,
        private val itemClick: (UserData) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: UserData) {
            Glide.with(binding.imgDetailLikeImage.context)
                .load(data.image)
                .circleCrop()
                .error(R.drawable.myimage)
                .into(binding.imgDetailLikeImage)

            binding.tvDetailLikeNickname.text = data.nickname
            when (data.is_follow) {
                true -> {
                    binding.tvDetailLikeFollow.apply {
                        isSelected = true
                        text = "팔로잉"
                    }
                }
                else -> {
                    binding.tvDetailLikeFollow.apply {
                        isSelected = false
                        text = "팔로우"
                    }
                }
            }

            binding.tvDetailLikeNickname.setOnClickListener {
                itemClick(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailLikeViewHolder {
        val binding = ItemDetailLikeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailLikeViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: DetailLikeViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}