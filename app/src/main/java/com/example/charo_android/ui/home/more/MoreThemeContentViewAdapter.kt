package com.example.charo_android.ui.home.more

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.charo_android.R
import com.example.charo_android.api.ResponseMoreViewData
import com.example.charo_android.databinding.ItemMoreThemeContentViewBinding

class MoreThemeContentViewAdapter :
    RecyclerView.Adapter<MoreThemeContentViewAdapter.MoreThemeContentViewHolder>() {
    var moreThemeData = mutableListOf<ResponseMoreViewData.Data.Drive>()

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
        fun onBind(moreThemeInfo: ResponseMoreViewData.Data.Drive) {
            binding.apply {
                with(moreThemeInfo) {
                    Glide.with(imgMoreTheme.context)
                        .load(moreThemeInfo.image)
                        .into(imgMoreTheme)
                }
                textMoreThemeTitle.text = moreThemeInfo.title
                if (moreThemeInfo.tags.count() == 2) {
                    chipMoreTheme1.text = moreThemeInfo.tags[0]
                    chipMoreTheme2.text = moreThemeInfo.tags[1]
                    chipMoreTheme3.visibility = View.INVISIBLE

                } else if (moreThemeInfo.tags.count() == 1) {
                    chipMoreTheme1.text = moreThemeInfo.tags[0]
                    chipMoreTheme2.visibility = View.INVISIBLE
                    chipMoreTheme3.visibility = View.INVISIBLE
                } else {
                    chipMoreTheme1.text = moreThemeInfo.tags[0]
                    chipMoreTheme2.text = moreThemeInfo.tags[1]
                    chipMoreTheme3.text = moreThemeInfo.tags[2]
                }


                imgMoreThemeHeart.setImageResource(R.drawable.selector_home_heart)
                if (moreThemeInfo.isFavorite == false) {
                    with(imgMoreThemeHeart) {
                        this.isSelected = false
                        this.setOnClickListener { this.isSelected = !this.isSelected }
                    }
                } else {
                    with(imgMoreThemeHeart) {
                        this.isSelected = true
                        this.setOnClickListener { this.isSelected = !this.isSelected }
                    }
                }
            }

        }
    }
}