package com.example.charo_android.ui.home.more

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.charo_android.databinding.ItemMoreViewBinding

class MoreViewAdapter: RecyclerView.Adapter<MoreViewAdapter.MoreViewViewHolder>() {
    val moreData = mutableListOf<MoreViewInfo>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoreViewViewHolder {
        val binding = ItemMoreViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MoreViewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoreViewViewHolder, position: Int) {
        holder.onBind(moreData[position])
    }

    override fun getItemCount(): Int {
        return moreData.size
    }

    class MoreViewViewHolder(
        private val itemMoreViewbinding : ItemMoreViewBinding
    ): RecyclerView.ViewHolder(itemMoreViewbinding.root){
        fun onBind(moreViewInfo: MoreViewInfo){
            itemMoreViewbinding.apply {
                imgMoreView.setImageResource(moreViewInfo.moreViewImage)
                textMoreViewTitle.text = moreViewInfo.moreViewTitle
                chipMoreView1.text = moreViewInfo.moreViewChip_1
                chipMoreView2.text = moreViewInfo.moreViewChip_2
                chipMoreView3.text = moreViewInfo.moreViewChip_3
                with(imgMoreViewHeart){
                    isSelected = false
                    setOnClickListener { it.isSelected = !it.isSelected }
                }
            }

        }
    }
}