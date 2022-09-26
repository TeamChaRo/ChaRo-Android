package com.charo.android.presentation.ui.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.charo.android.R
import com.charo.android.data.api.ApiService
import com.charo.android.data.model.request.alarm.RequestReadPushData
import com.charo.android.data.model.response.ResponseStatusCode
import com.charo.android.data.model.response.alarm.ResponseAlarmDeleteData
import com.charo.android.data.model.response.alarm.ResponseAlarmListData
import com.charo.android.databinding.ActivityAlarmBinding
import com.charo.android.presentation.util.SharedInformation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class AlarmActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
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

        //TODO: 서버에서 보내는 알람 test 필요
        //fcm test
//        sendFCM()

        alarmViewModel = AlarmViewModel()

        alarmSwipeHelperCallback = AlarmSwipeHelperCallback().apply {
            setClamp(170f)
        }
        val itemTouchHelper = ItemTouchHelper(alarmSwipeHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rcvAlarmList)

        initRecyclerView()
        binding.srAlarmList.setColorSchemeResources(R.color.blue_main_0f6fff)
        binding.srEmptyList.setColorSchemeResources(R.color.blue_main_0f6fff)
        binding.srAlarmList.setOnRefreshListener(this)
        binding.srEmptyList.setOnRefreshListener(this)
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
                //itemDelete 이벤트 구현
                serveDeleteItem(it)
            }

            rcvAlarmList.adapter = alarmAdapter

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
                binding.srAlarmList.isRefreshing = false
                binding.srEmptyList.isRefreshing = false

                if (response.isSuccessful) {
                    Timber.d("server connect : Alarm success")
                    Timber.d("server connect : Alarm ${response.body()}")
                    val pushList = response.body()?.pushList

                    if(pushList != null && pushList.isNotEmpty()){
                        binding.rcvAlarmList.visibility = View.VISIBLE
                        binding.clEmptyList.visibility = View.GONE
                        binding.srAlarmList.visibility = View.VISIBLE
                        binding.srEmptyList.visibility = View.GONE
                        alarmAdapter.itemList.clear()
                        alarmAdapter.itemList.addAll(pushList)
                        alarmAdapter.notifyDataSetChanged()
                    } else {
                        binding.rcvAlarmList.visibility = View.GONE
                        binding.clEmptyList.visibility = View.VISIBLE
                        binding.srAlarmList.visibility = View.GONE
                        binding.srEmptyList.visibility = View.VISIBLE
                    }
                } else {
                    Timber.d("server connect : Alarm error")
                    Timber.d("server connect : Alarm ${response.errorBody()}")
                    Timber.d("server connect : Alarm ${response.message()}")
                    Timber.d("server connect : Alarm ${response.code()}")
                    Timber.d("server connect : Alarm  ${response.raw().request.url}")
                    Toast.makeText(this@AlarmActivity, R.string.server_error_general, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseAlarmListData>, t: Throwable) {
                Timber.d("server connect : Alarm error: ${t.message}")
                binding.srAlarmList.isRefreshing = false
                binding.srEmptyList.isRefreshing = false
                Toast.makeText(this@AlarmActivity, R.string.server_error_general, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun serveDeleteItem(it: ResponseAlarmListData.PushList){
        Timber.e("postDeleteAlarm param ${it.pushId}")
        val call: Call<ResponseAlarmDeleteData> = ApiService.alarmViewService.postDeleteAlarm(it.pushId)
        call.enqueue(object : Callback<ResponseAlarmDeleteData> {
            override fun onResponse(
                call: Call<ResponseAlarmDeleteData>,
                response: Response<ResponseAlarmDeleteData>
            ) {
                if (response.isSuccessful) {
                    Timber.d("server connect : Alarm delete success")
                    Timber.d("server connect : Alarm delete ${response.body()}")

                    alarmAdapter.removeItem(it)
                    Toast.makeText(this@AlarmActivity, "삭제되었습니다.", Toast.LENGTH_SHORT).show()

                } else {
                    Timber.d("server connect : Alarm delete error")
                    Timber.d("server connect : Alarm delete ${response.errorBody()}")
                    Timber.d("server connect : Alarm delete${response.message()}")
                    Timber.d("server connect : Alarm delete ${response.code()}")
                    Timber.d("server connect : Alarm delete ${response.raw().request.url}")
                    Toast.makeText(this@AlarmActivity, "삭제할 수 없는 알림입니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseAlarmDeleteData>, t: Throwable) {
                Timber.d("server connect : Alarm delete fail: ${t.message}")
                Toast.makeText(this@AlarmActivity, R.string.server_error_general, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun postReadAlarm(pushId: Int){
        Timber.e("postReadAlarm param $pushId")
        val call: Call<ResponseStatusCode> = ApiService.alarmViewService.postReadAlarm(RequestReadPushData(pushId))
        call.enqueue(object : Callback<ResponseStatusCode> {
            override fun onResponse(
                call: Call<ResponseStatusCode>,
                response: Response<ResponseStatusCode>
            ) {
                if (response.isSuccessful) {
                    Timber.d("server connect : Alarm read success")
                    Timber.d("server connect : 알람 클릭 ${response.body()}")
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
                Toast.makeText(this@AlarmActivity, R.string.server_error_general, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun sendFCM(){
        var NOTIFICATION_CHANNEL_ID = "0000"
        var NOTIFICATION_CHANNEL_NAME = "ChaRo-Android"
        var notificationManager = getSystemService(NotificationManager::class.java)

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

    override fun onRefresh() {
        Timber.d("onRefresh alarm")
        getInitAlarmData()
    }
}
