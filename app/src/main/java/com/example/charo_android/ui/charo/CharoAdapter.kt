package com.example.charo_android.ui.charo

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.charo_android.data.mypage.Drive
import com.example.charo_android.databinding.ItemCharoLoadingBinding
import com.example.charo_android.ui.main.MainActivity
import com.example.charo_android.ui.detail.DetailActivity
import com.example.charo_android.databinding.ItemCharoMyCharoBinding

class CharoAdapter(
    val userId: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    var itemList = mutableListOf<Drive?>()
    var spinnerPosition = 0

    inner class MyCharoViewHolder(
        val binding: ItemCharoMyCharoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(drive: Drive) {
            binding.driveData = drive
            drive.warning?.let {
                binding.tvMyCharoTag3.visibility = ViewGroup.VISIBLE
            }
        }
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
                MyCharoViewHolder(binding)
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

            holder.binding.root.setOnClickListener() {
                val intent = Intent(holder.itemView.context, DetailActivity::class.java)
                intent.putExtra("userId", userId)
                intent.putExtra("postId", itemList[position]!!.postId)

                ContextCompat.startActivity(holder.itemView.context, intent, null)
                // startActivity 바꿔라.
            }
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