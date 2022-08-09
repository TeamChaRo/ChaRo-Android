package com.charo.android.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charo.android.databinding.ItemHomeTodayDriveBinding
import com.charo.android.domain.model.home.TodayCharoDrive
import com.charo.android.presentation.ui.home.HomeFragment
import com.charo.android.presentation.util.LoginUtil

class HomeTodayDriveAdapter(
    private val itemClick: (TodayCharoDrive) -> Unit,
    private val heartClick: (Int, Boolean) -> Unit,
    val userId: String,
    var links: HomeFragment.DataToHomeLike
) : RecyclerView.Adapter<HomeTodayDriveAdapter.HomeTodayDriveViewHolder>() {
    private val _todayCharoDrive = mutableListOf<TodayCharoDrive>()
    private var todayCharoDrive: List<TodayCharoDrive> = _todayCharoDrive
    var postId: Int = 0
    var select = true

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeTodayDriveViewHolder {
        val binding = ItemHomeTodayDriveBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeTodayDriveViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: HomeTodayDriveViewHolder,
        position: Int
    ) {
        holder.onBind(todayCharoDrive[position])
        holder.binding.imgHomeTodayDriveHeart.setOnClickListener {
            if (userId == "@") {
                LoginUtil.loginPrompt(holder.itemView.context)
            } else {
                todayCharoDrive[position].homeTodayDriveHeart = !todayCharoDrive[position].homeTodayDriveHeart
                it.isSelected = todayCharoDrive[position].homeTodayDriveHeart
                postId = todayCharoDrive[position].homeTodayDrivePostId

                heartClick(postId, it.isSelected)
                links.getPostId(postId)
            }


        }
        holder.binding.root.setOnClickListener() {
            itemClick(todayCharoDrive[position])
        }
    }

    override fun getItemCount(): Int {
        return todayCharoDrive.size
    }

    class HomeTodayDriveViewHolder(
        val binding: ItemHomeTodayDriveBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(todayCharoDrive: TodayCharoDrive) {
            binding.apply {
                binding.todayCharoDrive = todayCharoDrive
                binding.executePendingBindings()
            }
        }
    }

    fun setTodayDrive(todayCharoDrive: List<TodayCharoDrive>) {
        this.todayCharoDrive = todayCharoDrive
        notifyDataSetChanged()
    }

    fun setLike(postId: Int, update: Boolean) {
        for(item in todayCharoDrive) {
            if(item.homeTodayDrivePostId == postId) {
                item.homeTodayDriveHeart = update
            }
        }
        notifyDataSetChanged()
    }
}