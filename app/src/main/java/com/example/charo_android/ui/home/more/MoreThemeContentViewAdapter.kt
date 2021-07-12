package com.example.charo_android.ui.home.more

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.charo_android.databinding.ItemMoreThemeContentViewBinding

class MoreThemeContentViewAdapter :
    RecyclerView.Adapter<MoreThemeContentViewAdapter.MoreThemeContentViewHolder>() {
    var moreThemeData = mutableListOf<MoreThemeInfo>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoreThemeContentViewAdapter.MoreThemeContentViewHolder {
        val binding = ItemMoreThemeContentViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MoreThemeContentViewAdapter.MoreThemeContentViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MoreThemeContentViewAdapter.MoreThemeContentViewHolder,
        position: Int
    ) {
        holder.onBind(moreThemeData[position])
    }

    override fun getItemCount(): Int {
        return moreThemeData.size
    }

    class MoreThemeContentViewHolder(
        private val binding: ItemMoreThemeContentViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(moreThemeInfo: MoreThemeInfo) {
            binding.apply {
                imgMoreTheme.setImageResource(moreThemeInfo.moreThemeImage)
                textMoreThemeTitle.text = moreThemeInfo.moreThemeTitle
                chipMoreTheme1.text = moreThemeInfo.moreThemeChip_1
                chipMoreTheme2.text = moreThemeInfo.moreThemeChip_2
                chipMoreTheme3.text = moreThemeInfo.moreThemeChip_3

                with(imgMoreThemeHeart) {
                    isSelected = false
                    setOnClickListener { it.isSelected = !it.isSelected }
                }
            }

        }
    }
}