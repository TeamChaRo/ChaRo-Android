package com.charo.android.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charo.android.databinding.ItemHomeLocationDriveBinding
import com.charo.android.domain.model.home.LocalDrive
import com.charo.android.presentation.ui.home.HomeFragment
import com.charo.android.presentation.util.LoginUtil

class HomeLocalDriveAdapter(
    private val itemClick: (Int, Int, String) -> Unit,
    val userId: String,
    var links: HomeFragment.DataToHomeLike
) :
    RecyclerView.Adapter<HomeLocalDriveAdapter.HomeLocationDriveViewHolder>() {
    private val _localDrive = mutableListOf<LocalDrive>()
    private var localDrive: List<LocalDrive> = _localDrive
    var postId: Int = 0
    var select = true
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeLocationDriveViewHolder {
        val binding = ItemHomeLocationDriveBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeLocationDriveViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: HomeLocationDriveViewHolder,
        position: Int
    ) {
        holder.onBind(localDrive[position])
        holder.binding.imgHomeLocationDriveHeart.setOnClickListener {
            if (userId == "@") {
                LoginUtil.loginPrompt(holder.itemView.context)
            } else {
                postId = localDrive[position].homeLocationDrivePostId
                if (select) {
                    it.isSelected = !localDrive[position].homeLocationDriveHeart
                    select = false
                } else {
                    it.isSelected = localDrive[position].homeLocationDriveHeart
                }

                links.getPostId(postId)
            }

        }
        holder.binding.root.setOnClickListener() {
            itemClick(position, localDrive[position].homeLocationDrivePostId, userId)
        }
    }

    override fun getItemCount(): Int {
        return localDrive.size
    }

    class HomeLocationDriveViewHolder(
        val binding: ItemHomeLocationDriveBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(localDrive: LocalDrive) {
            binding.apply {
                locationDrive = localDrive
                executePendingBindings()
            }
        }

    }

    fun setLocalDrive(localDrive: List<LocalDrive>) {
        this.localDrive = localDrive
        notifyDataSetChanged()
    }

    fun setLike(position: Int, postId: Int, update: Boolean) {
        if(position < 0 || position >= localDrive.size){
            return
        }
        if(localDrive[position].homeLocationDrivePostId == postId){
            this.localDrive[position].homeLocationDriveHeart = update
        }
        notifyDataSetChanged()
    }
}
