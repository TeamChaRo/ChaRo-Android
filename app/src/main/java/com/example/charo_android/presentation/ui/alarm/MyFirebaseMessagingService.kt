package com.example.charo_android.presentation.ui.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.charo_android.R
import com.example.charo_android.data.api.ApiService
import com.example.charo_android.data.model.request.alarm.RequestFcmData
import com.example.charo_android.data.model.response.alarm.ResponseFcmData
import com.example.charo_android.hidden.Hidden
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val TAG = "MyFirebaseMessagingService"

    override fun onNewToken(token: String) {
        Log.d(TAG, "new Token: $token")
        sendRegistrationToServer(token)
    }

    //fcm token update api
    private fun sendRegistrationToServer(token : String) {
        //TODO: otherUserEmail -> userEmail 수정
        val requestFcmData: RequestFcmData = RequestFcmData(token, Hidden.otherUserEmail)

        val call: Call<ResponseFcmData> =
            ApiService.alarmViewService.postFcm(requestFcmData)
        call.enqueue(object : Callback<ResponseFcmData> {
            override fun onResponse(
                call: Call<ResponseFcmData>,
                response: Response<ResponseFcmData>
            ) {
                Log.e("postFcm param","$requestFcmData")

                if (response.isSuccessful) {
                    Log.d("server connect : Alarm Fcm Update", "success")
                    Log.d("server connect : Alarm Fcm Update", "${response.body()}")

                } else {
                    Log.d("server connect : Alarm Fcm Update", "error")
                    Log.d("server connect : Alarm Fcm Update", "$response.errorBody()")
                    Log.d("server connect : Alarm Fcm Update", response.message())
                    Log.d("server connect : Alarm Fcm Update", "${response.code()}")
                    Log.d("server connect : Alarm Fcm Update", "${response.raw().request.url}")
                }
            }

            override fun onFailure(call: Call<ResponseFcmData>, t: Throwable) {
                Log.d("server connect : Alarm Fcm Update", "error: ${t.message}")
            }
        })
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage != null) {
            Log.d(TAG, "From: " + remoteMessage.data)
            Log.i("notice 바디: ", remoteMessage.notification?.title.toString())
            Log.i("notice 타이틀: ", remoteMessage.notification?.body.toString())
            sendMainNotification(remoteMessage)
            sendNotification(remoteMessage)
        } else {
            Log.i("notice 수신에러: ", "data가 비어있습니다. 메시지를 수신하지 못했습니다.")
            Log.i("notice data 값: ", remoteMessage.data.toString())
        }
    }

    private fun sendMainNotification(remoteMessage: RemoteMessage) {
        val uniId = remoteMessage.sentTime.toInt()

        val intent = Intent(this, AlarmActivity::class.java)
        intent.putExtra("isOpenFromPushAlarm", true)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
            this, uniId, intent, PendingIntent.FLAG_ONE_SHOT
        )

        val channelId = "Notification Main Message"

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder =
            NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_main_logo_blue)
                .setContentTitle(remoteMessage.notification?.title.toString())
                .setContentText(remoteMessage.notification?.body.toString())
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, "Notice", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(uniId, notificationBuilder.build())
    }


    private fun sendNotification(remoteMessage: RemoteMessage) {
        val uniId = remoteMessage.sentTime.toInt()

        val intent = Intent(this, MyFirebaseMessagingService::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
            this, uniId, intent, PendingIntent.FLAG_ONE_SHOT
        )

        val channelId = "Notification Message"

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder =
            NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_main_logo_blue)
                .setContentTitle(remoteMessage.notification?.title.toString())
                .setContentText(remoteMessage.notification?.body.toString())
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, "Notice", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(uniId, notificationBuilder.build())
    }
}