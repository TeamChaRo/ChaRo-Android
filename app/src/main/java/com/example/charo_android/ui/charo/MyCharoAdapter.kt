package com.example.charo_android.ui.charo

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.charo_android.MainActivity
import com.example.charo_android.ui.detail.DetailActivity
import com.example.charo_android.databinding.ItemCharoMyCharoBinding

class MyCharoAdapter(
    val userId: String
): RecyclerView.Adapter<MyCharoAdapter.MyCharoViewHolder>() {
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
            lateinit var activity: MainActivity
            intent.putExtra("userId", userId)
            intent.putExtra("postId", itemList[position].postId)
            // 서버통신 하기 전에
            // intent로 recyclerview item이
            // 내가 선택한 item의 ~~로 intent가 잘 넘어가는지
            // test -> O

            ContextCompat.startActivity(holder.itemView.context, intent, null)
            // startActivity 바꿔라.
        }
    }

    class MyCharoViewHolder(
        val binding: ItemCharoMyCharoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(myCharoInfo: MyCharoInfo) {
            val mContext = binding.imgMyCharoPicture.context
            Glide.with(mContext)
                .load(myCharoInfo.image)
                .transform(RoundedCorners(9))
                .into(binding.imgMyCharoPicture)
            binding.tvMyCharoTitle.text = myCharoInfo.title
            binding.tvMyCharoDate.text = "${myCharoInfo.year}.${myCharoInfo.month}.${myCharoInfo.day}"
            binding.tvMyCharoLikeCount.text = myCharoInfo.favoriteNum.toString()
            binding.tvMyCharoLikeCount.text = myCharoInfo.saveNum.toString()
            binding.tvMyCharoTag1.text = "#${myCharoInfo.tags[0]}"
            if(myCharoInfo.tags.size == 2) {
                binding.tvMyCharoTag2.visibility = ViewGroup.INVISIBLE
                binding.tvMyCharoTag3.visibility = ViewGroup.INVISIBLE
                binding.tvMyCharoTag2.text = "#${myCharoInfo.tags[1]}"
            } else if(myCharoInfo.tags.size == 3) {
                binding.tvMyCharoTag3.visibility = ViewGroup.INVISIBLE
                binding.tvMyCharoTag2.text = "#${myCharoInfo.tags[1]}"
                binding.tvMyCharoTag3.text = "#${myCharoInfo.tags[2]}"
            }
        }
    }
}