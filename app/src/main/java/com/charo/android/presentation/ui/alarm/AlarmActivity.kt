package com.charo.android.presentation.ui.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import com.charo.android.R
import com.charo.android.data.api.ApiService
import com.charo.android.data.model.request.alarm.RequestReadPushData
import com.charo.android.data.model.response.ResponseStatusCode
import com.charo.android.data.model.response.alarm.ResponseAlarmDeleteData
import com.charo.android.data.model.response.alarm.ResponseAlarmListData
import com.charo.android.databinding.ActivityAlarmBinding
import com.charo.android.presentation.ui.mypage.other.OtherMyPageActivity
import com.charo.android.presentation.ui.write.WriteShareActivity
import com.charo.android.presentation.util.SharedInformation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class AlarmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmBinding
    private lateinit var alarmViewModel: AlarmViewModel

    private lateinit var alarmAdapter: AlarmListAdapter
    private lateinit var alarmSwipeHelperCallback: AlarmSwipeHelperCallback

    private lateinit var userEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userEmail = SharedInformation.getEmail(this)


        initToolbar(getString(R.string.alarm))

        //fcm test TODO: 서버에서 보내는 알람 test 필요
        sendFCM()

        alarmViewModel = AlarmViewModel()

        alarmSwipeHelperCallback = AlarmSwipeHelperCallback().apply {
            setClamp(170f)
        }
        val itemTouchHelper = ItemTouchHelper(alarmSwipeHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rcvAlarmList)

        initRecyclerView()
    }

    private fun initToolbar(title : String){
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_1)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbarTitle.text = title
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRecyclerView() {
        with(binding) {
            alarmAdapter = AlarmListAdapter(){
                //itemClick 이벤트 구현
                postReadAlarm(it.pushId)
                Timber.tag("jinhee").d(" $it")

                when(it.type) {
                    "like","post" -> { //좋아요, 게시글 알림 : 게시글로 이동 (postId)
                        val intent = Intent(this@AlarmActivity, WriteShareActivity::class.java)
                        intent.apply {
                            putExtra("userId", userEmail)
                            putExtra("destination", "detail")
                            putExtra("postId", it.postId)
                        }
                        ContextCompat.startActivity(this@AlarmActivity, intent, null)
                    }
                    "following" -> { //팔로우 팔로잉 알림 : 해당 user 로 이동 (followed)
                        val intent = Intent(this@AlarmActivity, OtherMyPageActivity::class.java)
                        intent.putExtra("userEmail", it.followed)
                        startActivity(intent)
                    }
                }
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
        Timber.e("getAlarmList param $userEmail")
        val call: Call<ResponseAlarmListData> = ApiService.alarmViewService.getAlarmList(userEmail)
        call.enqueue(object : Callback<ResponseAlarmListData> {
            override fun onResponse(
                call: Call<ResponseAlarmListData>,
                response: Response<ResponseAlarmListData>
            ) {
                Timber.e("getAlarmList param111 $userEmail")

                if (response.isSuccessful) {
                    Timber.d("server connect : Alarm success")
                    Timber.d("server connect : Alarm ${response.body()}")
                    val pushList = response.body()?.pushList

                    if(pushList != null){
                        alarmAdapter.itemList.addAll(
                            listOf<ResponseAlarmListData.PushList>(
                                ResponseAlarmListData.PushList(
                                    pushList.pushId,
                                    pushList.pushCode,
                                    pushList.isRead,
                                    pushList.token,
                                    pushList.image,
                                    pushList.title,
                                    pushList.body,
                                    pushList.month,
                                    pushList.day,
                                    pushList.type,
                                    pushList.postId,
                                    pushList.followed
                                )
                            )
                        )
                    }
                    alarmAdapter.notifyDataSetChanged()
                } else {
                    Timber.d("server connect : Alarm error")
                    Timber.d("server connect : Alarm ${response.errorBody()}")
                    Timber.d("server connect : Alarm ${response.message()}")
                    Timber.d("server connect : Alarm ${response.code()}")
                    Timber.d("server connect : Alarm  ${response.raw().request.url}")
                }
            }

            override fun onFailure(call: Call<ResponseAlarmListData>, t: Throwable) {
                Timber.d("server connect : Alarm error: ${t.message}")
            }
        })
    }

    fun serveDeleteItem(it: ResponseAlarmListData.PushList, pushId: Int){
        Timber.e("postDeleteAlarm param $pushId")
        val call: Call<ResponseAlarmDeleteData> = ApiService.alarmViewService.postDeleteAlarm(pushId)
        call.enqueue(object : Callback<ResponseAlarmDeleteData> {
            override fun onResponse(
                call: Call<ResponseAlarmDeleteData>,
                response: Response<ResponseAlarmDeleteData>
            ) {
                if (response.isSuccessful) {
                    Timber.d("server connect : Alarm delete success")
                    Timber.d("server connect : Alarm delete ${response.body()}")

                    alarmAdapter.removeItem(it)
                    alarmAdapter.notifyDataSetChanged()

                } else {
                    Toast.makeText(this@AlarmActivity, "삭제할 수 없는 알림입니다.", Toast.LENGTH_LONG).show()
                    Timber.d("server connect : Alarm delete error")
                    Timber.d("server connect : Alarm delete ${response.errorBody()}")
                    Timber.d("server connect : Alarm delete${response.message()}")
                    Timber.d("server connect : Alarm delete ${response.code()}")
                    Timber.d("server connect : Alarm delete ${response.raw().request.url}")
                }
            }

            override fun onFailure(call: Call<ResponseAlarmDeleteData>, t: Throwable) {
                Toast.makeText(this@AlarmActivity, "삭제할 수 없는 알림입니다.", Toast.LENGTH_LONG).show()
                Timber.d("server connect : Alarm delete error: ${t.message}")
            }
        })
    }

    private fun postReadAlarm(pushId: Int){
        Timber.e("postReadAlarm param $pushId")
        val call: Call<ResponseStatusCode> = ApiService.alarmViewService.postReadAlarm(RequestReadPushData(pushId))
        call.enqueue(object : Callback<ResponseStatusCode> {
            override fun onResponse(
                call: Call<ResponseStatusCode>,
                response: Response<ResponseStatusCode>
            ) {
                if (response.isSuccessful) {
                    Timber.d("server connect : Alarm read success")
                    Timber.d("server connect : Alarm read${response.body()}")

                    //TODO: 알람 내용에 맞는 화면으로 이동 (API 확인 후)

                    Toast.makeText(this@AlarmActivity, "알람 클릭 $response.body()", Toast.LENGTH_LONG).show()

                } else {
                    Timber.d("server connect : Alarm read error")
                    Timber.d("server connect : Alarm read ${response.errorBody()}")
                    Timber.d("server connect : Alarm read ${response.message()}")
                    Timber.d("server connect : Alarm read ${response.code()}")
                    Timber.d("server connect : Alarm read ${response.raw().request.url}")
                }
            }

            override fun onFailure(call: Call<ResponseStatusCode>, t: Throwable) {
                Timber.d("server connect : Alarm read error: ${t.message}")
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
}
