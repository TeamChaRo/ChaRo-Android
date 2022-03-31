package com.charo.android.presentation.ui.charo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charo.android.data.model.charo.Drive
import com.charo.android.databinding.ItemCharoLoadingBinding
import com.charo.android.databinding.ItemCharoMyCharoBinding

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
    }

    inner class LoadingViewHolder(
        val binding: ItemCharoLoadingBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return if (itemList[position] != null) {
//            Log.d("itemList[position]", "$position -> not null")
            VIEW_TYPE_ITEM
        } else {
//            Log.d("itemList[position]", "$position -> null")
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