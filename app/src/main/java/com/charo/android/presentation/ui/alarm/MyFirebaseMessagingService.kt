package com.charo.android.presentation.ui.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.charo.android.R
import com.charo.android.data.model.request.alarm.RequestFcmData
import com.charo.android.data.model.response.alarm.ResponseFcmData
import com.charo.android.presentation.util.SharedInformation
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val TAG = "MyFirebaseMessagingService"

    override fun onNewToken(token: String) {
        Timber.d("$TAG new Token: $token")
        sendRegistrationToServer(token)
    }

    //fcm token update api
    private fun sendRegistrationToServer(token : String) {
        val userEmail = SharedInformation.getEmail(this)
        val requestFcmData: RequestFcmData = RequestFcmData(token, userEmail)

        val call: Call<ResponseFcmData> =
            com.charo.android.data.api.ApiService.alarmViewService.pushFcm(requestFcmData)
        call.enqueue(object : Callback<ResponseFcmData> {
            override fun onResponse(
                call: Call<ResponseFcmData>,
                response: Response<ResponseFcmData>
            ) {
                Timber.e("pushFcm param $requestFcmData")

                if (response.isSuccessful) {
                    Timber.d("server connect : Alarm Fcm Update  success")
                    Timber.d("server connect : Alarm Fcm Update  ${response.body()}")

                } else {
                    Timber.d("server connect : Alarm Fcm Update  error")
                    Timber.d("server connect : Alarm Fcm Update  ${response.errorBody()}")
                    Timber.d("server connect : Alarm Fcm Update  ${response.message()}")
                    Timber.d("server connect : Alarm Fcm Update  ${response.code()}")
                    Timber.d("server connect : Alarm Fcm Update  ${response.raw().request.url}")
                }
            }

            override fun onFailure(call: Call<ResponseFcmData>, t: Throwable) {
                Timber.d("server connect : Alarm Fcm Update  error: ${t.message}")
            }
        })
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage != null) {
            Timber.d("From:$TAG ${remoteMessage.data}")
            Timber.i("notice 바디: ${remoteMessage.notification?.title.toString()}")
            Timber.i("notice 타이틀: ${remoteMessage.notification?.body.toString()}")
            sendMainNotification(remoteMessage)
            sendNotification(remoteMessage)
        } else {
            Timber.i("notice 수신에러:   data가 비어있습니다. 메시지를 수신하지 못했습니다.")
            Timber.i("notice data 값: ${remoteMessage.data.toString()}")
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