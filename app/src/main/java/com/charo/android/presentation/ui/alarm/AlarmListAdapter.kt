package com.charo.android.presentation.ui.alarm

import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.charo.android.R
import com.charo.android.data.model.response.alarm.ResponseAlarmListData
import com.charo.android.databinding.ItemAlarmBinding
import com.charo.android.presentation.ui.mypage.other.OtherMyPageActivity
import com.charo.android.presentation.ui.write.WriteShareActivity

class AlarmListAdapter(
    private val itemDeleteClick: (ResponseAlarmListData.PushList) -> Unit
    ) : //AlarmViewModel
    RecyclerView.Adapter<AlarmListAdapter.AlarmListViewHolder>(){
    val itemList = mutableListOf<ResponseAlarmListData.PushList>()

    class AlarmListViewHolder(val binding: ItemAlarmBinding, val itemDeleteClick: (ResponseAlarmListData.PushList) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        private val alarmActivity: AlarmActivity = AlarmActivity()

        fun onBind(alarmListInfo: ResponseAlarmListData.PushList) {
            Glide.with(binding.imgAlarmListProfile)
                .load(alarmListInfo.image)
                .error(R.drawable.ic_profile)
                .circleCrop()
                .into(binding.imgAlarmListProfile)

            binding.tvAlarmDate.text = "${alarmListInfo.month}월 ${alarmListInfo.day}일"
            binding.tvAlarmContext.text = alarmListInfo.body
            binding.tvAlarmStatus.text = alarmListInfo.title
            binding.tvAlarmStatus.isSelected = true

            when(alarmListInfo.isRead){
                1-> {  // 조회한 알림
                    binding.itemAlarmList.isSelected = true
                }
                else -> {
                    binding.itemAlarmList.isSelected = false
                }
            }

            binding.itemAlarmList.setOnClickListener{
                binding.itemAlarmList.isSelected = true

                //itemClick 이벤트 구현
                alarmActivity.postReadAlarm(alarmListInfo.pushId)
                clickItem(alarmListInfo,it)
            }

            binding.tvAlarmDelete.setOnClickListener {
                itemDeleteClick(alarmListInfo)
            }
        }

        private fun clickItem(alarmListInfo: ResponseAlarmListData.PushList, view: View){
            when(alarmListInfo.type) {
                "like","post" -> { //좋아요, 게시글 알림 : 게시글로 이동 (postId)
                    val intent = Intent(view.context, WriteShareActivity::class.java)
                    intent.apply {
                        putExtra("destination", "detail")
                        putExtra("postId", alarmListInfo.postId)
                    }
                    view.context.startActivity(intent)
                }
                "following" -> { //팔로우 팔로잉 알림 : 해당 user 로 이동 (followed)
                    if(alarmListInfo.followed != null && !TextUtils.isEmpty(alarmListInfo.followed)){
                        val intent = Intent(view.context, OtherMyPageActivity::class.java)
                        intent.putExtra("userEmail", alarmListInfo.followed)
                        view.context.startActivity(intent)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmListViewHolder {
        val binding =
            ItemAlarmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlarmListViewHolder(binding, itemDeleteClick)
    }

    override fun onBindViewHolder(holder: AlarmListViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun removeItem(data: ResponseAlarmListData.PushList){
        val position = itemList.indexOf(data)
        itemList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemList.size)
    }
}