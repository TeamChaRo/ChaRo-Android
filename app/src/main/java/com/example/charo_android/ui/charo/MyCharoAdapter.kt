package com.example.charo_android.ui.charo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.charo_android.databinding.ItemCharoMyCharoBinding

class MyCharoAdapter : RecyclerView.Adapter<MyCharoAdapter.MyCharoViewHolder>() {
    val itemList = mutableListOf<MyCharoInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCharoViewHolder {
        val binding = ItemCharoMyCharoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyCharoViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: MyCharoViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }

    class MyCharoViewHolder(
        private val binding: ItemCharoMyCharoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(myCharoInfo: MyCharoInfo) {
            binding.imgMyCharoPicture.setImageResource(myCharoInfo.image)
            binding.tvMyCharoTitle.text = myCharoInfo.title
            binding.tvMyCharoTag1.text = myCharoInfo.hashtag1
            binding.tvMyCharoTag2.text = myCharoInfo.hashtag2
            binding.tvMyCharoTag3.text = myCharoInfo.hashtag3
            binding.tvMyCharoDate.text = myCharoInfo.date
            binding.tvMyCharoLikeCount.text = myCharoInfo.likeCount.toString()
        }
    }
}