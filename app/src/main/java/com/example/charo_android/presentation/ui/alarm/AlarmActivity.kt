package com.example.charo_android.presentation.ui.alarm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.charo_android.data.api.ApiService
import com.example.charo_android.data.model.response.alarm.ResponseAlarmListData
import com.example.charo_android.databinding.ActivityAlarmBinding
import com.example.charo_android.hidden.Hidden
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlarmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmBinding
    private lateinit var alarmViewModel: AlarmViewModel

    private lateinit var alarmAdapter: AlarmListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        goHome()
        alarmViewModel = AlarmViewModel()

        initRecyclerView()
    }

    private fun initRecyclerView() {
        with(binding) {
            alarmAdapter = AlarmListAdapter(){
                //itemClick 이벤트 구현
            }
            rcvAlarmList.adapter = alarmAdapter
//            alarmViewModel.getInitAlarmData()
            Log.e("getAlarmList param", "${Hidden.otherUserEmail}")
            val call: Call<ResponseAlarmListData> =
                ApiService.alarmViewService.getAlarmList(Hidden.otherUserEmail)
            call.enqueue(object : Callback<ResponseAlarmListData> {
                override fun onResponse(
                    call: Call<ResponseAlarmListData>,
                    response: Response<ResponseAlarmListData>
                ) {
                    Log.e("getAlarmList param111", "${Hidden.otherUserEmail}")

                    if (response.isSuccessful) {
                        Log.d("server connect : Alarm", "success")
                        Log.d("server connect : Alarm", "${response.body()}")
                        val pushList = response.body()?.pushList

                        if(pushList != null){
                            //alarmViewModel 사용 시
                            alarmViewModel._pushId.value = pushList.pushId
                            alarmViewModel._pushCode.value = pushList.pushCode
                            alarmViewModel._isRead.value = pushList.isRead
                            alarmViewModel._token.value = pushList.token
                            alarmViewModel._image.value = pushList.image
                            alarmViewModel._title.value = pushList.title
                            alarmViewModel._body.value = pushList.body
                            alarmViewModel._month.value = pushList.month
                            alarmViewModel._day.value = pushList.day


                            //alarmListInfo 사용 시
                            alarmAdapter.itemList.addAll(
                                listOf<AlarmListInfo>(
                                    AlarmListInfo(
                                        20,
                                        2,
                                        0,
                                        "and@naver.com",
                                        "https://charo-image.s3.ap-northeast-2.amazonaws.com/dummy/profileImage/default2.jpeg",
                                        "팔로잉",
                                        "안드네 토끼양님이 회원님을 팔로우하기 시작했습니다.",
                                        "09",
                                        "12"
                                    ),
                                    AlarmListInfo(
                                        pushList.pushId,
                                        pushList.pushCode,
                                        pushList.isRead,
                                        pushList.token,
                                        pushList.image,
                                        pushList.title,
                                        pushList.body,
                                        pushList.month,
                                        pushList.day
                                    )
                                )
                            )
                        }else{

                        }

                        Log.e("alarmAdapter", "${alarmAdapter.itemList}")
                        Log.e("alarmAdapter", "${alarmAdapter.itemCount}")


                        alarmAdapter.notifyDataSetChanged()
                    } else {
                        Log.d("server connect : Alarm ", "error")
                        Log.d("server connect : Alarm ", "$response.errorBody()")
                        Log.d("server connect : Alarm ", response.message())
                        Log.d("server connect : Alarm ", "${response.code()}")
                        Log.d("server connect : Alarm ", "${response.raw().request.url}")
                    }
                }

                override fun onFailure(call: Call<ResponseAlarmListData>, t: Throwable) {
                    Log.d("server connect : Alarm ", "error: ${t.message}")
                }
            })
        }
    }

    private fun goHome() {
        binding.imgBackHomeAlarm.setOnClickListener {
            onBackPressed()
        }
    }
}
