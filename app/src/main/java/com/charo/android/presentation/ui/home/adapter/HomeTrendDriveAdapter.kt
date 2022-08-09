package com.charo.android.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charo.android.databinding.ItemHomeHotDriveBinding
import com.charo.android.domain.model.home.TrendDrive
import com.charo.android.presentation.ui.home.HomeFragment
import com.charo.android.presentation.util.LoginUtil
import timber.log.Timber

class HomeTrendDriveAdapter(
    private val itemClick: (TrendDrive) -> Unit,
    private val heartClick: (Int, Boolean) -> Unit,
    val userId: String,
    var links: HomeFragment.DataToHomeLike
) :
    RecyclerView.Adapter<HomeTrendDriveAdapter.HomeHotDriveViewHolder>() {
    private val _trendDrive = mutableListOf<TrendDrive>()
    private var trendDrive: List<TrendDrive> = _trendDrive
    var postId: Int = 0
    var select = true
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeHotDriveViewHolder {
        val binding = ItemHomeHotDriveBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeHotDriveViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: HomeHotDriveViewHolder,
        position: Int
    ) {

        holder.onBind(trendDrive[position])
        holder.binding.imgHomeHotDriveHeart.setOnClickListener {
            if (userId == "@") {
                Timber.d("homeHotDrive 로그인 유도")
                LoginUtil.loginPrompt(holder.itemView.context)
            } else {
                trendDrive[position].homeTrendDriveHeart = !trendDrive[position].homeTrendDriveHeart
                it.isSelected = trendDrive[position].homeTrendDriveHeart
                postId = trendDrive[position].homeTrendDrivePostId

                heartClick(postId, it.isSelected)
                links.getPostId(postId)
            }

        }
        holder.binding.root.setOnClickListener() {
            itemClick(trendDrive[position])
        }

    }

    override fun getItemCount(): Int {
        return trendDrive.size
    }

    class HomeHotDriveViewHolder(
        val binding: ItemHomeHotDriveBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(trendDrive: TrendDrive) {
            binding.trendDrive = trendDrive
            binding.executePendingBindings()
        }
    }

    fun setHomeTrendDrive(trendDrive: List<TrendDrive>) {
        this.trendDrive = trendDrive
        notifyDataSetChanged()
    }

    fun setLike(postId: Int, update: Boolean) {
        for(item in trendDrive) {
            if(item.homeTrendDrivePostId == postId) {
                item.homeTrendDriveHeart = update
            }
        }
        notifyDataSetChanged()
    }
}
