package com.charo.android.presentation.ui.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.charo.android.databinding.ItemResultSearchBinding
import com.charo.android.domain.model.search.SearchDrive
import com.charo.android.presentation.ui.detail.DetailActivity
import com.charo.android.presentation.ui.detailpost.DetailPostActivity
import com.charo.android.presentation.ui.write.WriteShareActivity
import com.charo.android.presentation.util.LoginUtil

class ResultSearchAdapter(
    var email : String,
    var links : ResultSearchFragment.DataToSearchLike
) :
    RecyclerView.Adapter<ResultSearchAdapter.ResultSearchViewHolder>() {
    private val _searchData = mutableListOf<SearchDrive>()
    private var searchData: List<SearchDrive> = _searchData
    var postId = 0
    var select = true
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResultSearchViewHolder {
        val binding = ItemResultSearchBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ResultSearchViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ResultSearchViewHolder,
        position: Int
    ) {
        holder.onBind(searchData[position])
        holder.binding.imgResultSearchHeart.setOnClickListener {
            postId = searchData[position].postId
            if(email == "@"){
                LoginUtil.loginPrompt(holder.itemView.context)
            }else{
                if(select){
                    it.isSelected = !searchData[position].isFavorite
                    select = false
                }else{
                    it.isSelected = searchData[position].isFavorite
                }
                links.getPostId(postId)
            }

        }

        holder.binding.root.setOnClickListener() {
            val intent = Intent(holder.itemView?.context, WriteShareActivity::class.java)
            intent.apply {
                putExtra("postId", searchData[position].postId)
                putExtra("destination","detail")
            }
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return searchData.size
    }

    class ResultSearchViewHolder(
        val binding: ItemResultSearchBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(searchData: SearchDrive) {
            binding.apply{
                searchDrive = searchData
                binding.executePendingBindings()
            }

        }

    }

    fun setSearchDrive(searchDrive: List<SearchDrive>){
        this.searchData = searchDrive
        notifyDataSetChanged()
    }
}


