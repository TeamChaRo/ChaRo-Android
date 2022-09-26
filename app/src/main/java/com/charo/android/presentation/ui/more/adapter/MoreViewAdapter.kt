package com.charo.android.presentation.ui.more.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charo.android.databinding.ItemCharoLoadingBinding
import com.charo.android.databinding.ItemMoreViewBinding
import com.charo.android.domain.model.more.MoreDrive
import com.charo.android.presentation.ui.more.MoreViewFragment
import timber.log.Timber

class MoreViewAdapter(
    private val itemClick: (MoreDrive) -> Unit,
    val userId: String,
    var links: MoreViewFragment.DataToMoreLike,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var moreData: MutableList<MoreDrive> = mutableListOf()

    var postId: Int = 0
    var select = true

    //position
    //무한 스크롤 뷰타입
    private val VIEW_TYPE = 0
    private val VIEW_TYPE_LOADING = 1


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMoreViewBinding.inflate(layoutInflater, parent, false)
                MoreViewViewHolder(binding)
            }
            else -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCharoLoadingBinding.inflate(layoutInflater, parent, false)
                LoadingViewHolder(binding)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (moreData[position].moreDay != "") {
            VIEW_TYPE
        } else {
            VIEW_TYPE_LOADING
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MoreViewViewHolder) {
            holder.onBind(moreData[position])
            holder.binding.imgMoreViewHeart.setOnClickListener {
                moreData[position].moreIsFavorite = !moreData[position].moreIsFavorite
                it.isSelected = moreData[position].moreIsFavorite

                postId = moreData[position].morePostId
                links.getPostId(postId)
            }

            holder.binding.root.setOnClickListener() {
                itemClick(moreData[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return moreData.size
    }

    inner class MoreViewViewHolder(
        val binding: ItemMoreViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(moreData: MoreDrive) {
            binding.apply {
                moreDrive = moreData
                binding.executePendingBindings()

            }
        }
    }

    inner class LoadingViewHolder(private val binding: ItemCharoLoadingBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setHomeTrendDrive(moreData: MutableList<MoreDrive>) {
        if(moreData.size > 0 && moreData[0].moreDay != "1"){
            this.moreData.addAll(moreData)
        }
        notifyDataSetChanged()

    }

    fun removeHomeTrendDrive() {
        Timber.d("moreDataRemove 삭제되는 중")
        this.moreData.clear()
        notifyDataSetChanged()

    }

    fun addLoading() {
        moreData.add(MoreDrive("", "", false, "", 0, "", "", "", "", ""))
        this.notifyItemRangeInserted(moreData.size, 1)
    }

    fun removeLoading() {
        if (moreData.last() == MoreDrive("", "", false, "", 0, "", "", "", "", "")) {
            moreData.removeAt(moreData.lastIndex)
        }
    }

    fun setLike(postId: Int, update: Boolean) {
        for(item in moreData) {
            if(item.morePostId == postId) {
                item.moreIsFavorite = update
            }
        }
        notifyDataSetChanged()
    }
}
