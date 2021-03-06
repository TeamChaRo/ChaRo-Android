package com.charo.android.presentation.ui.more.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.charo.android.databinding.ItemCharoLoadingBinding
import com.charo.android.databinding.ItemMoreViewBinding
import com.charo.android.domain.model.more.MoreDrive
import com.charo.android.presentation.ui.more.MoreViewFragment
import com.charo.android.presentation.ui.write.WriteShareActivity
import timber.log.Timber

class MoreViewAdapter(
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
                postId = moreData[position].morePostId
                if (select) {
                    it.isSelected = !moreData[position].moreIsFavorite
                    select = false
                } else {
                    it.isSelected = moreData[position].moreIsFavorite
                    select = true
                }
                links.getPostId(postId)
            }

            holder.binding.root.setOnClickListener() {
                val intent = Intent(holder.itemView.context, WriteShareActivity::class.java)
                intent.apply {
                    putExtra("userId", userId)
                    putExtra("destination", "detail")
                    putExtra("postId", moreData[position].morePostId)
                }
                ContextCompat.startActivity(holder.itemView.context, intent, null)
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
        this.moreData.addAll(moreData)
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

}
