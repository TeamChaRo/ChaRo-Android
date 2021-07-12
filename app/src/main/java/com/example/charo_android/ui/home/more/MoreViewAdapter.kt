package com.example.charo_android.ui.home.more

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.charo_android.R
import com.example.charo_android.api.ResponseMoreViewData
import com.example.charo_android.databinding.ItemMoreViewBinding

class MoreViewAdapter() : RecyclerView.Adapter<MoreViewAdapter.MoreViewViewHolder>() {
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
    }

    override fun getItemCount(): Int {
        return moreData.size
    }

    class MoreViewViewHolder(
        private val itemMoreViewBinding: ItemMoreViewBinding
    ) : RecyclerView.ViewHolder(itemMoreViewBinding.root) {
        fun onBind(moreViewItem: ResponseMoreViewData.Data.Drive) {

            itemMoreViewBinding.apply {
                with(moreViewItem) {
                    Glide.with(imgMoreView.context)
                        .load(this.image)
                        .into(imgMoreView)

                    textMoreViewTitle.text = this.title

                    if (moreViewItem.tags.count() == 2) {
                        chipMoreView1.text = this.tags[0]
                        chipMoreView2.text = this.tags[1]
                        chipMoreView3.visibility = View.INVISIBLE

                    } else {
                        chipMoreView1.text = this.tags[0]
                        chipMoreView2.text = this.tags[1]
                        chipMoreView3.text = this.tags[2]
                    }

                    Log.d("more", this.title)
                    imgMoreViewHeart.setImageResource(R.drawable.selector_home_heart)
                    if (this.isFavorite == false){
                        with(imgMoreViewHeart) {
                            this.isSelected = false
                            setOnClickListener { this.isSelected != this.isSelected }
                        }
                    } else{
                        with(imgMoreViewHeart){
                            this.isSelected = true
                            setOnClickListener { this.isSelected != this.isSelected }
                        }
                    }



                }


            }

        }
    }
}