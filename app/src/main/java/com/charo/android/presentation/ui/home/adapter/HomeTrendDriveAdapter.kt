package com.charo.android.presentation.ui.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.charo.android.databinding.ItemHomeHotDriveBinding
import com.charo.android.domain.model.home.TrendDrive
import com.charo.android.presentation.ui.home.HomeFragment
import com.charo.android.presentation.ui.write.WriteShareActivity
import com.charo.android.presentation.util.LoginUtil
import timber.log.Timber

class HomeTrendDriveAdapter(
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
                postId = trendDrive[position].homeTrendDrivePostId
                if (select) {
                    it.isSelected = !trendDrive[position].homeTrendDriveHeart
                    select = false
                } else {
                    it.isSelected = trendDrive[position].homeTrendDriveHeart
                    select = true
                }

                links.getPostId(postId)
            }

        }
        holder.binding.root.setOnClickListener() {
            val intent = Intent(holder.itemView.context, WriteShareActivity::class.java)
            intent.apply {
                putExtra("postId", trendDrive[position].homeTrendDrivePostId)
                putExtra("destination", "detail")
            }
            ContextCompat.startActivity(holder.itemView.context, intent, null)

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
}
