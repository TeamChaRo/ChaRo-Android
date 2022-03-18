package com.example.charo_android.presentation.ui.write

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.charo_android.data.WriteImgInfo
import com.example.charo_android.databinding.ItemWriteImgBinding

class WriteAdapter : RecyclerView.Adapter<WriteAdapter.WriteImgViewHolder>() {

    var imgList = mutableListOf<WriteImgInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WriteImgViewHolder {
        val binding = ItemWriteImgBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WriteImgViewHolder(binding)
    }

    override fun getItemCount(): Int = imgList.size

    override fun onBindViewHolder(holder: WriteImgViewHolder, position: Int) {

        holder.onBind(imgList[position])
    }

    class WriteImgViewHolder(
        private val binding: ItemWriteImgBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(writeImgInfo: WriteImgInfo) {
            binding.itemWriteImg.setImageURI(writeImgInfo.imgUri)
        }
    }
}
