package com.example.charo_android.presentation.ui.home.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.charo_android.R
import com.example.charo_android.databinding.ItemHomeThemeBinding
import com.example.charo_android.data.model.entity.HomeThemeInfo
import com.example.charo_android.presentation.ui.more.MoreThemeViewFragment


class HomeThemeAdapter : RecyclerView.Adapter<HomeThemeAdapter.HomeThemeViewHolder>() {
    val themeData = mutableListOf<HomeThemeInfo>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeThemeViewHolder {
        val binding = ItemHomeThemeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeThemeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeThemeViewHolder, position: Int) {
        holder.onBind(themeData[position])

        holder.itemView.setOnClickListener {
                val activity = it.context as AppCompatActivity
                val moreThemeViewFragment = MoreThemeViewFragment()
                if (!moreThemeViewFragment.isAdded) {
                    activity.supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, moreThemeViewFragment)
                        .addToBackStack(null)
                        .commit()
                }

            }
        }


    override fun getItemCount(): Int {
        return themeData.size
    }

    inner class HomeThemeViewHolder(
        private val binding: ItemHomeThemeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(homeThemeInfo: HomeThemeInfo) {
            binding.apply {
                with(homeThemeInfo) {
                    Glide.with(imgHomeTheme.context)
                        .load(this.homeThemeImage)
                        .circleCrop()
                        .into(imgHomeTheme)

                    textHomeTheme.text = homeThemeInfo.homeThemeTitle

                }
            }
        }
    }
}
