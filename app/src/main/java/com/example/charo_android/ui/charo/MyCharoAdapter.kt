package com.example.charo_android.ui.charo

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.charo_android.ui.detail.DetailActivity
import com.example.charo_android.databinding.ItemCharoMyCharoBinding

class MyCharoAdapter: RecyclerView.Adapter<MyCharoAdapter.MyCharoViewHolder>() {
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

        holder.binding.root.setOnClickListener() {
            val intent = Intent(holder.itemView?.context, DetailActivity::class.java)
            intent.putExtra("userId", "111")
            intent.putExtra("postId", itemList[position].postId)
            // 서버통신 하기 전에
            // intent로 recyclerview item이
            // 내가 선택한 item의 ~~로 intent가 잘 넘어가는지
            // test -> O
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    class MyCharoViewHolder(
        val binding: ItemCharoMyCharoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(myCharoInfo: MyCharoInfo) {
            binding.imgMyCharoPicture.setImageResource(myCharoInfo.image)
            binding.tvMyCharoTitle.text = myCharoInfo.title
            binding.tvMyCharoTag1.text = myCharoInfo.hashtag1
            binding.tvMyCharoTag2.text = myCharoInfo.hashtag2
            binding.tvMyCharoTag3.text = myCharoInfo.hashtag3
            binding.tvMyCharoDate.text = myCharoInfo.date
            binding.tvMyCharoLikeCount.text = myCharoInfo.likeCount.toString()
            binding.tvMyCharoSaveCount.text = myCharoInfo.saveCount.toString()
        }
    }
}