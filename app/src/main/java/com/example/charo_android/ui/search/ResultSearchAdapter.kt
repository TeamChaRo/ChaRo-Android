package com.example.charo_android.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.charo_android.R
import com.example.charo_android.api.ResponseSearchViewData
import com.example.charo_android.databinding.ItemResultSearchBinding

class ResultSearchAdapter(): RecyclerView.Adapter<ResultSearchAdapter.ResultSearchViewHolder>() {
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
    }

    override fun getItemCount(): Int {
        return searchData.size
    }

    class ResultSearchViewHolder(
        private val binding: ItemResultSearchBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(resultSearchData : ResponseSearchViewData.Data.Drive) {
            binding.apply{
                with(resultSearchData) {
                    Glide.with(imgResultSearch.context)
                        .load(resultSearchData.image)
                        .into(imgResultSearch)
                }

                textResultSearch.text = resultSearchData.title
                if (resultSearchData.tags.count() == 2) {
                    chipItemResultSearch1.text = resultSearchData.tags[0]
                    chipItemResultSearch2.text = resultSearchData.tags[1]
                    chipItemResultSearch3.visibility = View.INVISIBLE

                } else {
                    chipItemResultSearch1.text = resultSearchData.tags[0]
                    chipItemResultSearch2.text = resultSearchData.tags[1]
                    chipItemResultSearch3.text = resultSearchData.tags[2]
                }

                imgResultSearchHeart.setImageResource(R.drawable.selector_home_heart)
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