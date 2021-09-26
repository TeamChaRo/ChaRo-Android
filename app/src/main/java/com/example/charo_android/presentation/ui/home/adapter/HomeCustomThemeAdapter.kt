package com.example.charo_android.presentation.ui.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.charo_android.databinding.ItemHomeNightDriveBinding
import com.example.charo_android.domain.model.home.CustomThemeDrive
import com.example.charo_android.presentation.ui.detail.DetailActivity

class HomeCustomThemeAdapter(val userId: String) :
    RecyclerView.Adapter<HomeCustomThemeAdapter.HomeNightDriveViewHolder>() {
    private val _customThemeDrive = mutableListOf<CustomThemeDrive>()
    private var customThemeDrive: List<CustomThemeDrive> = _customThemeDrive

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

        holder.binding.root.setOnClickListener() {
            val intent = Intent(holder.itemView?.context, DetailActivity::class.java)
            intent.putExtra("userId", userId)
            // intent.putExtra("postId", nightData[position].postId)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
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
    fun setCustomThemeDrive(customThemeDrive : List<CustomThemeDrive>){
        this.customThemeDrive = customThemeDrive
        notifyDataSetChanged()
    }
}