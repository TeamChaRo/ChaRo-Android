package com.example.charo_android.ui.home.more

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.charo_android.R
import com.example.charo_android.api.ResponseMoreViewData
import com.example.charo_android.databinding.ItemMoreViewBinding
import com.example.charo_android.ui.detail.DetailActivity

class MoreViewAdapter(val userId: String) : RecyclerView.Adapter<MoreViewAdapter.MoreViewViewHolder>() {
    var moreData = mutableListOf<ResponseMoreViewData.Data.Drive>()

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

        val intent = Intent(holder.itemView?.context, DetailActivity::class.java)
        intent.putExtra("userId", userId)
        intent.putExtra("postId", moreData[position].postId)
        ContextCompat.startActivity(holder.itemView.context, intent, null)
    }

    override fun getItemCount(): Int {
        return moreData.size
    }

    class MoreViewViewHolder(
        private val binding: ItemMoreViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(moreViewItem: ResponseMoreViewData.Data.Drive) {

            binding.apply {
                textMoreViewTitle.text = moreViewItem.title

                with(moreViewItem) {
                    Glide.with(imgMoreView.context)
                        .load(moreViewItem.image)
                        .placeholder(R.drawable.image_more_view)
                        .into(imgMoreView)
                }


                if (moreViewItem.tags.count() == 2) {
                    chipMoreView1.text = moreViewItem.tags[0]
                    chipMoreView2.text = moreViewItem.tags[1]
                    chipMoreView3.visibility = View.INVISIBLE

                } else {
                    chipMoreView1.text = moreViewItem.tags[0]
                    chipMoreView2.text = moreViewItem.tags[1]
                    chipMoreView3.text = moreViewItem.tags[2]
                }

                imgMoreViewHeart.setImageResource(R.drawable.selector_home_heart)
                if (moreViewItem.isFavorite == false) {
                    with(imgMoreViewHeart) {
                        this.isSelected = false
                        setOnClickListener { this.isSelected != this.isSelected }
                    }
                } else {
                    with(imgMoreViewHeart) {
                        this.isSelected = true
                        setOnClickListener { this.isSelected != this.isSelected }
                    }
                }


            }


        }

    }
}
