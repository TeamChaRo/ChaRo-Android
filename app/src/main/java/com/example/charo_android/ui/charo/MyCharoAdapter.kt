package com.example.charo_android.ui.charo

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.charo_android.data.mypage.Drive
import com.example.charo_android.data.mypage.Post
import com.example.charo_android.ui.main.MainActivity
import com.example.charo_android.ui.detail.DetailActivity
import com.example.charo_android.databinding.ItemCharoMyCharoBinding

class MyCharoAdapter(
    val userId: String
): RecyclerView.Adapter<MyCharoAdapter.MyCharoViewHolder>() {
    val itemList = mutableListOf<Drive>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCharoViewHolder {
        val binding = ItemCharoMyCharoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyCharoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        Log.d("recycler view item size", itemList.size.toString())
        return itemList.size
    }

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
        @SuppressLint("SetTextI18n")
        fun onBind(drive: Drive) {
//            val mContext = binding.imgMyCharoPicture.context
//            Glide.with(mContext)
//                .load(drive.image)
//                .transform(RoundedCorners(9))
//                .into(binding.imgMyCharoPicture)
            binding.driveData = drive
            drive.warning?.let {
                binding.tvMyCharoTag3.visibility = ViewGroup.VISIBLE
            }
        }
    }
}