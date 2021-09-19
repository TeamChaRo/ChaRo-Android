package com.example.charo_android.presentation.ui.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.charo_android.R
import com.example.charo_android.data.model.response.ResponseSearchViewData
import com.example.charo_android.databinding.ItemResultSearchBinding
import com.example.charo_android.presentation.ui.detail.DetailActivity

class ResultSearchAdapter(val userId : String): RecyclerView.Adapter<ResultSearchAdapter.ResultSearchViewHolder>() {
    val searchData = mutableListOf<ResponseSearchViewData.Data.Drive>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResultSearchAdapter.ResultSearchViewHolder {
        val binding = ItemResultSearchBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ResultSearchViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ResultSearchAdapter.ResultSearchViewHolder,
        position: Int
    ) {
        holder.onBind(searchData[position])
        holder.binding.root.setOnClickListener() {
            val intent = Intent(holder.itemView?.context, DetailActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("postId", searchData[position].postId)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return searchData.size
    }

    class ResultSearchViewHolder(
        val binding: ItemResultSearchBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(resultSearchData : ResponseSearchViewData.Data.Drive) {
            binding.apply{
                with(resultSearchData) {
                    Glide.with(imgResultSearch.context)
                        .load(resultSearchData.image)
                        .placeholder(R.drawable.result_search_shape)
                        .transform(RoundedCorners(9))
                        .into(imgResultSearch)
                }

                textResultSearch.text = resultSearchData.title
                if (resultSearchData.tags.count() == 2) {
                    chipItemResultSearch1.text = "#${resultSearchData.tags[0]}"
                    chipItemResultSearch2.text = "#${resultSearchData.tags[1]}"
                    chipItemResultSearch3.visibility = View.INVISIBLE

                } else {
                    chipItemResultSearch1.text = "#${resultSearchData.tags[0]}"
                    chipItemResultSearch2.text = "#${resultSearchData.tags[1]}"
                    chipItemResultSearch3.text = "#${resultSearchData.tags[2]}"
                }

                imgResultSearchHeart.setImageResource(R.drawable.selector_home_heart)
                imgResultSearchHeart.setOnClickListener {
                if (resultSearchData.isFavorite == false) {
                    with(imgResultSearchHeart) {
                        this.isSelected = false
                        setOnClickListener { this.isSelected != this.isSelected }
                    }
                } else {
                    with(imgResultSearchHeart) {
                        this.isSelected = true
                        setOnClickListener { this.isSelected != this.isSelected }
                    }
                }
                }

            }

        }
    }
}