package com.charo.android.presentation.ui.more.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charo.android.databinding.ItemMoreViewBinding
import com.charo.android.domain.model.more.MoreDrive
import com.charo.android.presentation.ui.more.MoreThemeContentViewFragment
import timber.log.Timber

class MoreThemeContentAdapter(
    private val itemClick: (MoreDrive) -> Unit,
    private val heartClick: (Int, Boolean) -> Unit,
    var link: MoreThemeContentViewFragment.DataToMoreThemeLike,
    var userId: String
) : RecyclerView.Adapter<MoreThemeContentAdapter.MoreViewViewHolder>() {

    private val _moreData = mutableListOf<MoreDrive>()
    private var moreData: List<MoreDrive> = _moreData
    var postId: Int = 0
    var select = true

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
        holder.binding.imgMoreViewHeart.setOnClickListener {
            moreData[position].moreIsFavorite = !moreData[position].moreIsFavorite
            it.isSelected = moreData[position].moreIsFavorite

            postId = moreData[position].morePostId
            link.getPostId(postId)

            heartClick(postId, it.isSelected)
        }

        holder.binding.root.setOnClickListener() {
            itemClick(moreData[position])
        }
    }

    override fun getItemCount(): Int {
        return moreData.size
    }

    class MoreViewViewHolder(
        val binding: ItemMoreViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(moreData: MoreDrive) {
            binding.apply {
                moreDrive = moreData
                binding.executePendingBindings()
            }
        }
    }

    fun setHomeTrendDrive(moreData: List<MoreDrive>) {
        Timber.d("이거 작동중 $moreData")
        this.moreData = moreData
        notifyDataSetChanged()
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