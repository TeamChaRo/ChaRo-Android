package com.example.charo_android.presentation.ui.charo

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.charo_android.data.model.mypage.Drive
import com.example.charo_android.databinding.ItemCharoLoadingBinding
import com.example.charo_android.databinding.ItemCharoMyCharoBinding
import com.example.charo_android.presentation.ui.detail.DetailActivity

class CharoAdapter(
    val userId: String,
    val itemClick: (Drive?) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    var itemList = mutableListOf<Drive?>()
    var spinnerPosition = 0

    inner class MyCharoViewHolder(
        val binding: ItemCharoMyCharoBinding,
        itemClick: (Drive?) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(drive: Drive) {
            binding.driveData = drive
            drive.warning?.let {
                binding.tvMyCharoTag3.visibility = ViewGroup.VISIBLE
            }

            binding.root.setOnClickListener { itemClick(drive) }
        }

//        fun startDetailView() {
//            binding.root.setOnClickListener {
//                val intent = Intent(binding.root.context, DetailActivity::class.java)
//                intent.putExtra("postId", itemList[bindingAdapterPosition]!!.postId)
//                intent.putExtra("title", itemList[bindingAdapterPosition]!!.title)
//                intent.putExtra("date", "${itemList[bindingAdapterPosition]!!.year}년 ${itemList[bindingAdapterPosition]!!.month}월 ${itemList[bindingAdapterPosition]!!.day}일")
//            }
//        }
    }

    inner class LoadingViewHolder(
        val binding: ItemCharoLoadingBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return if (itemList[position] != null) {
            Log.d("itemList[position]", "$position -> not null")
            VIEW_TYPE_ITEM
        } else {
            Log.d("itemList[position]", "$position -> null")
            VIEW_TYPE_LOADING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCharoMyCharoBinding.inflate(layoutInflater, parent, false)
                MyCharoViewHolder(binding, itemClick)
            }
            else -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCharoLoadingBinding.inflate(layoutInflater, parent, false)
                LoadingViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyCharoViewHolder) {
            holder.onBind(itemList[position]!!)

//            holder.binding.root.setOnClickListener() {
//                val intent = Intent(holder.itemView.context, DetailActivity::class.java)
//                intent.putExtra("userId", userId)
//                intent.putExtra("postId", itemList[position]!!.postId)
//
//                ContextCompat.startActivity(holder.itemView.context, intent, null)
//                // startActivity 바꿔라.
//            }
        }
    }

    fun removeLoading() {
        if(itemList.last() == null) {
            itemList.removeLast()
        }
    }

    fun addLoading() {
        itemList.add(null)
        this.notifyItemRangeInserted(itemList.size, 1)
    }
}