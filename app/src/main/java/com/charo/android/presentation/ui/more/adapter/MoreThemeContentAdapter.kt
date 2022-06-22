package com.charo.android.presentation.ui.more.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.charo.android.databinding.ItemMoreViewBinding
import com.charo.android.domain.model.more.MoreDrive
import com.charo.android.presentation.ui.more.MoreThemeContentViewFragment
import com.charo.android.presentation.ui.write.WriteShareActivity

class MoreThemeContentAdapter(
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
            postId = moreData[position].morePostId
            if (select) {
                it.isSelected = !moreData[position].moreIsFavorite
                select = false
            } else {
                it.isSelected = moreData[position].moreIsFavorite
                select = true
            }
            link.getPostId(postId)
        }

        holder.binding.root.setOnClickListener() {
            val intent = Intent(holder.itemView?.context, WriteShareActivity::class.java)
            intent.apply {
                putExtra("userId", userId)
                putExtra("destination", "detail")
                putExtra("postId", moreData[position].morePostId)
            }
            ContextCompat.startActivity(holder.itemView.context, intent, null)
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
        this.moreData = moreData
        notifyDataSetChanged()
    }
}