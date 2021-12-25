package com.example.charo_android.presentation.ui.charo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.charo_android.data.model.mypage.User
import com.example.charo_android.databinding.ItemCharoListBinding

class CharoListFollowAdapter : RecyclerView.Adapter<CharoListFollowAdapter.CharoListViewHolder>() {
    val itemList = mutableListOf<User>()

    class CharoListViewHolder(val binding: ItemCharoListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(userData: User) {
            Glide.with(binding.imgCharoListProfile)
                .load(userData.image)
                .circleCrop()
                .into(binding.imgCharoListProfile)

            binding.tvCharoListNickname.text = userData.nickname
            when(userData.is_follow) {
                true -> {
                    binding.tvCharoListIsFollow.isSelected = true
                    binding.tvCharoListIsFollow.text = "팔로잉"
                }
                else -> {
                    binding.tvCharoListIsFollow.isSelected = false
                    binding.tvCharoListIsFollow.text = "팔로우"
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharoListViewHolder {
        val binding = ItemCharoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharoListViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}