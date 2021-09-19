package com.example.charo_android.presentation.ui.more

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.charo_android.R
import com.example.charo_android.data.model.response.ResponseMoreViewData
import com.example.charo_android.databinding.ItemMoreViewBinding
import com.example.charo_android.presentation.ui.detail.DetailActivity

class MoreNewViewAdapter(val userId : String): RecyclerView.Adapter<MoreNewViewAdapter.MoreNewViewHolder>() {
    val moreNewData = mutableListOf<ResponseMoreViewData.Data.Drive>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoreNewViewAdapter.MoreNewViewHolder {
        val binding = ItemMoreViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MoreNewViewAdapter.MoreNewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoreNewViewAdapter.MoreNewViewHolder, position: Int) {
        holder.onBind(moreNewData[position])

        holder.itemMoreViewBinding.root.setOnClickListener() {
            val intent = Intent(holder.itemView?.context, DetailActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("postId", moreNewData[position].postId)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }



    override fun getItemCount(): Int {
        return moreNewData.size
    }

    class MoreNewViewHolder(
        val itemMoreViewBinding: ItemMoreViewBinding
    ): RecyclerView.ViewHolder(itemMoreViewBinding.root) {
        fun onBind(moreViewItem: ResponseMoreViewData.Data.Drive){
            itemMoreViewBinding.apply {
                textMoreViewTitle.text = moreViewItem.title

                with(moreViewItem) {
                    Glide.with(imgMoreView.context)
                        .load(moreViewItem.image)
                        .transform(RoundedCorners(9))
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