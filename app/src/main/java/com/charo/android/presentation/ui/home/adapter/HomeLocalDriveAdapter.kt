package com.charo.android.presentation.ui.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.charo.android.databinding.ItemHomeLocationDriveBinding
import com.charo.android.domain.model.home.LocalDrive
import com.charo.android.presentation.ui.home.HomeFragment
import com.charo.android.presentation.ui.write.WriteShareActivity
import com.charo.android.presentation.util.LoginUtil

class HomeLocalDriveAdapter(
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
            val intent = Intent(holder.itemView?.context, WriteShareActivity::class.java)
            intent.apply {
                putExtra("userId", userId)
                putExtra("postId", localDrive[position].homeLocationDrivePostId)
                putExtra("destination", "detail")
            }
            ContextCompat.startActivity(holder.itemView.context, intent, null)
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

}
