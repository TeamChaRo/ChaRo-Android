package com.example.charo_android.presentation.ui.mypage.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.charo_android.R
import com.example.charo_android.data.model.mypage.SampleDataClass
import com.example.charo_android.databinding.ItemProfilePostBinding

class PostAdapter: RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    var itemList = mutableListOf<SampleDataClass>(
        SampleDataClass(1), SampleDataClass(1), SampleDataClass(1), SampleDataClass(1), SampleDataClass(1), SampleDataClass(1), SampleDataClass(1),
    )

    class PostViewHolder(private val binding: ItemProfilePostBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SampleDataClass) {
            Log.d("mlog: PostAdapter::sample data id", data.id.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = DataBindingUtil.inflate<ItemProfilePostBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_profile_post,
            parent,
            false
        )
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}