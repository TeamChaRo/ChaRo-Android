package com.charo.android.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charo.android.databinding.ItemHomeNightDriveBinding
import com.charo.android.domain.model.home.CustomThemeDrive
import com.charo.android.presentation.ui.home.HomeFragment
import com.charo.android.presentation.util.LoginUtil

class HomeCustomThemeAdapter(
    private val itemClick: (CustomThemeDrive) -> Unit,
    private val heartClick: (Int, Boolean) -> Unit,
    val userId: String,
    var links: HomeFragment.DataToHomeLike
) :
    RecyclerView.Adapter<HomeCustomThemeAdapter.HomeNightDriveViewHolder>() {
    private val _customThemeDrive = mutableListOf<CustomThemeDrive>()
    private var customThemeDrive: List<CustomThemeDrive> = _customThemeDrive
    var postId: Int = 0
    var select = true
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeNightDriveViewHolder {
        val binding = ItemHomeNightDriveBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeNightDriveViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: HomeNightDriveViewHolder,
        position: Int
    ) {
        holder.onBind(customThemeDrive[position])
        holder.binding.imgHomeNightDriveHeart.setOnClickListener {
            if (userId == "@") {
                LoginUtil.loginPrompt(holder.itemView.context)
            } else {
                customThemeDrive[position].homeNightDriveHeart = !customThemeDrive[position].homeNightDriveHeart
                it.isSelected = customThemeDrive[position].homeNightDriveHeart
                postId = customThemeDrive[position].homeNightDrivePostId

                heartClick(postId, it.isSelected)
                links.getPostId(postId)
            }
        }
        holder.binding.root.setOnClickListener() {
            itemClick(customThemeDrive[position])
        }
    }

    override fun getItemCount(): Int {
        return customThemeDrive.size
    }

    class HomeNightDriveViewHolder(
        val binding: ItemHomeNightDriveBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(customThemeDrive: CustomThemeDrive) {
            binding.apply {
                binding.customThemeDrive = customThemeDrive
                binding.executePendingBindings()
            }
        }

    }

    fun setCustomThemeDrive(customThemeDrive: List<CustomThemeDrive>) {
        this.customThemeDrive = customThemeDrive
        notifyDataSetChanged()
    }

    fun setLike(postId: Int, update: Boolean) {
        for(item in customThemeDrive) {
            if(item.homeNightDrivePostId == postId) {
                item.homeNightDriveHeart = update
            }
        }
        notifyDataSetChanged()
    }
}