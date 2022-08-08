package com.charo.android.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charo.android.databinding.ItemHomeNightDriveBinding
import com.charo.android.domain.model.home.CustomThemeDrive
import com.charo.android.presentation.ui.home.HomeFragment
import com.charo.android.presentation.util.LoginUtil

class HomeCustomThemeAdapter(
    private val itemClick: (Int, Int) -> Unit,
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
                postId = customThemeDrive[position].homeNightDrivePostId
                if (select) {
                    it.isSelected = !customThemeDrive[position].homeNightDriveHeart
                    select = false
                } else {
                    it.isSelected = customThemeDrive[position].homeNightDriveHeart
                    select = true
                }

                links.getPostId(postId)
            }
        }
        holder.binding.root.setOnClickListener() {
            itemClick(position, customThemeDrive[position].homeNightDrivePostId)
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

    fun setLike(position: Int, postId: Int, update: Boolean) {
        if(position < 0 || position >= customThemeDrive.size){
            return
        }
        if(customThemeDrive[position].homeNightDrivePostId == postId){
            this.customThemeDrive[position].homeNightDriveHeart = update
        }
        notifyDataSetChanged()
    }
}