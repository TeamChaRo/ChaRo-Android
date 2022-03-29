package com.example.charo_android.presentation.ui.write

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.charo_android.data.model.write.WriteImgInfo
import com.example.charo_android.databinding.ItemWriteImgBinding

class WriteAdapter(private val itemClick: (Int) -> Unit) : RecyclerView.Adapter<WriteAdapter.WriteImgViewHolder>() {

    var imgList = mutableListOf<WriteImgInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WriteImgViewHolder {
        val binding = ItemWriteImgBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WriteImgViewHolder(binding, itemClick)
    }

    override fun getItemCount(): Int = imgList.size

    override fun onBindViewHolder(holder: WriteImgViewHolder, position: Int) {

        holder.onBind(imgList[position], position)
    }

    class WriteImgViewHolder(
        private val binding: ItemWriteImgBinding,
        private val itemClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(writeImgInfo: WriteImgInfo, position: Int) {
            Glide.with(itemView)
                .load(writeImgInfo.imgUri)
                .transform(CenterCrop(), RoundedCorners(20))
                .into(binding.itemWriteImg)

            binding.itemImgDelete.setOnClickListener {
                itemClick(position)
            }
        }
    }
}
