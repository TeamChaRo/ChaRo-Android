package com.charo.android.presentation.ui.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.charo_android.R
import com.charo.android.data.api.ApiService
import com.example.charo_android.data.model.request.alarm.RequestReadPushData
import com.example.charo_android.data.model.response.ResponseStatusCode
import com.example.charo_android.data.model.response.alarm.ResponseAlarmDeleteData
import com.example.charo_android.data.model.response.alarm.ResponseAlarmListData
import com.example.charo_android.databinding.ActivityAlarmBinding
import com.example.charo_android.presentation.util.SharedInformation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlarmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmBinding
    private lateinit var alarmViewModel: AlarmViewModel

    private lateinit var alarmAdapter: AlarmListAdapter
    private lateinit var alarmSwipeHelperCallback: AlarmSwipeHelperCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //fcm test TODO: 서버에서 보내는 알람 test 필요
        sendFCM()

        goHome()
        alarmViewModel = AlarmViewModel()

        alarmSwipeHelperCallback = AlarmSwipeHelperCallback().apply {
            setClamp(170f)
        }
        val itemTouchHelper = ItemTouchHelper(alarmSwipeHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rcvAlarmList)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        with(binding) {
            alarmAdapter = AlarmListAdapter(){
                //itemClick 이벤트 구현
                postReadAlarm(it.pushId)
            }

            rcvAlarmList.adapter = alarmAdapter
//            alarmViewModel.getInitAlarmData()

            rcvAlarmList.setOnClickListener {
                alarmSwipeHelperCallback.removePreviousClamp(rcvAlarmList)
                false
            }
            getInitAlarmData()
        }
    }

    private fun getInitAlarmData(){
        val userEmail = SharedInformation.getEmail(this)

        Log.e("getAlarmList param", userEmail)
        val call: Call<ResponseAlarmListData> =
            com.charo.android.data.api.ApiService.alarmViewService.getAlarmList(userEmail)
        call.enqueue(object : Callback<ResponseAlarmListData> {
            override fun onResponse(
                call: Call<ResponseAlarmListData>,
                response: Response<ResponseAlarmListData>
            ) {
                Log.e("getAlarmList param111", userEmail)

                if (response.isSuccessful) {
                    Log.d("server connect : Alarm", "success")
                    Log.d("server connect : Alarm", "${response.body()}")
                    val pushList = response.body()?.pushList

                    //test
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
                        )
                    )
                    alarmAdapter.notifyDataSetChanged()
                    ////////////

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

    fun serveDeleteItem(it: AlarmListInfo, pushId: Int){ //AlarmViewModel
        Log.e("postDeleteAlarm param", "$pushId")
        val call: Call<ResponseAlarmDeleteData> =
            com.charo.android.data.api.ApiService.alarmViewService.postDeleteAlarm(pushId)
        call.enqueue(object : Callback<ResponseAlarmDeleteData> {
            override fun onResponse(
                call: Call<ResponseAlarmDeleteData>,
                response: Response<ResponseAlarmDeleteData>
            ) {
                if (response.isSuccessful) {
                    Log.d("server connect : Alarm delete", "success")
                    Log.d("server connect : Alarm delete", "${response.body()}")

                    alarmAdapter.removeItem(it)
                    alarmAdapter.notifyDataSetChanged()

                } else {
                    Log.d("server connect : Alarm delete", "error")
                    Log.d("server connect : Alarm delete", "$response.errorBody()")
                    Log.d("server connect : Alarm delete", response.message())
                    Log.d("server connect : Alarm delete", "${response.code()}")
                    Log.d("server connect : Alarm delete", "${response.raw().request.url}")
                }
            }

            override fun onFailure(call: Call<ResponseAlarmDeleteData>, t: Throwable) {
                Log.d("server connect : Alarm delete", "error: ${t.message}")
            }
        })
    }

    private fun postReadAlarm(pushId: Int){
        Log.e("postReadAlarm param", "$pushId")
        val call: Call<ResponseStatusCode> =
            com.charo.android.data.api.ApiService.alarmViewService.postReadAlarm(RequestReadPushData(pushId))
        call.enqueue(object : Callback<ResponseStatusCode> {
            override fun onResponse(
                call: Call<ResponseStatusCode>,
                response: Response<ResponseStatusCode>
            ) {
                if (response.isSuccessful) {
                    Log.d("server connect : Alarm read", "success")
                    Log.d("server connect : Alarm read", "${response.body()}")

                    //TODO: 알람 내용에 맞는 화면으로 이동 (API 확인 후)

                    Toast.makeText(this@AlarmActivity, "알람 클릭 $response.body()", Toast.LENGTH_LONG).show()

                } else {
                    Log.d("server connect : Alarm read", "error")
                    Log.d("server connect : Alarm read", "$response.errorBody()")
                    Log.d("server connect : Alarm read", response.message())
                    Log.d("server connect : Alarm read", "${response.code()}")
                    Log.d("server connect : Alarm read", "${response.raw().request.url}")
                }
            }

            override fun onFailure(call: Call<ResponseStatusCode>, t: Throwable) {
                Log.d("server connect : Alarm read", "error: ${t.message}")
            }
        })
    }

    private fun sendFCM(){
        var NOTIFICATION_CHANNEL_ID = "0000"
        var NOTIFICATION_CHANNEL_NAME = "ChaRo-Android"
        var notificationManager = getSystemService(NotificationManager::class.java) as NotificationManager?

        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val importance = NotificationManager.IMPORTANCE_LOW
                    val notificationChannel = NotificationChannel(
                        NOTIFICATION_CHANNEL_ID,
                        NOTIFICATION_CHANNEL_NAME,
                        importance
                    )
                    notificationChannel.enableLights(true)
                    notificationChannel.lightColor = Color.RED
                    notificationChannel.enableVibration(true)
                    notificationChannel.vibrationPattern =
                        longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
                    notificationManager?.createNotificationChannel(notificationChannel)
                }

                val mNotificationBuilder: NotificationCompat.Builder =
                    NotificationCompat.Builder(applicationContext,NOTIFICATION_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_main_logo_blue)
                        .setContentTitle("ChaRo")
                        .setContentText("새로운 드라이브 코스를 확인하세요!")
                // .setAutoCancel(false)

                notificationManager!!.notify(0, mNotificationBuilder.build())
            }
        }, 0)
    }

    private fun goHome() {
        binding.imgBackHomeAlarm.setOnClickListener {
            onBackPressed()
        }
    }
}
