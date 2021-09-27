package com.example.charo_android.presentation.ui.more.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.charo_android.R
import com.example.charo_android.data.model.response.more.ResponseMoreViewData
import com.example.charo_android.databinding.ItemMoreThemeContentViewBinding
import com.example.charo_android.presentation.ui.detail.DetailActivity

class MoreThemeContentViewAdapter(val userId: String) :
    RecyclerView.Adapter<MoreThemeContentViewAdapter.MoreThemeContentViewHolder>() {
    var moreThemeData = mutableListOf<ResponseMoreViewData.Data.Drive>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoreThemeContentViewHolder {
        val binding = ItemMoreThemeContentViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MoreThemeContentViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MoreThemeContentViewHolder,
        position: Int
    ) {
        holder.onBind(moreThemeData[position])
        holder.binding.root.setOnClickListener() {
        val intent = Intent(holder.itemView?.context, DetailActivity::class.java)
        intent.putExtra("userId", userId)
        intent.putExtra("postId", moreThemeData[position].postId)
        ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return moreThemeData.size
    }

    class MoreThemeContentViewHolder(
        val binding: ItemMoreThemeContentViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(moreThemeInfo: ResponseMoreViewData.Data.Drive) {
            binding.apply {
                with(moreThemeInfo) {
                    Glide.with(imgMoreTheme.context)
                        .load(moreThemeInfo.image)
                        .placeholder(R.drawable.more_theme_shape)
                        .transform(RoundedCorners(20))
                        .into(imgMoreTheme)
                }
                textMoreThemeTitle.text = moreThemeInfo.title


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