package com.charo.android.presentation.ui.home.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.charo.android.R
import com.charo.android.data.model.entity.HomeThemeInfo
import com.charo.android.databinding.ItemHomeThemeBinding
import com.charo.android.presentation.ui.more.MoreThemeViewFragment
import com.charo.android.presentation.util.SharedInformation


class HomeThemeAdapter(

) : RecyclerView.Adapter<HomeThemeAdapter.HomeThemeViewHolder>() {

    val themeData = mutableListOf<HomeThemeInfo>()
    var themeId: Int = 99

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

        holder.binding.root.setOnClickListener {
            val activity = it.context as AppCompatActivity
            SharedInformation.setThemeNum(activity, position)
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
         val binding: ItemHomeThemeBinding
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
