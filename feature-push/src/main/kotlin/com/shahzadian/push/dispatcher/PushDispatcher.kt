package com.shahzadian.push.dispatcher

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.shahzadian.push.api.PushNotificationData
import javax.inject.Inject
import javax.inject.Singleton

interface PushDispatcher {
    fun show(context: Context, data: PushNotificationData)
    fun showAll(context: Context, list: List<PushNotificationData>)
}

@Singleton
class PushDispatcherImpl @Inject constructor() : PushDispatcher {

    override fun show(context: Context, data: PushNotificationData) {
        ensureChannel(context, data)

        val builder = NotificationCompat.Builder(context, data.channelId)
            .setSmallIcon(data.iconRes)
            .setContentTitle(data.title)
            .setContentText(data.message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(data.pendingIntent)
            .setAutoCancel(true)

        val code = ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS)
        if (code == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat.from(context).notify(data.notificationId, builder.build())
        }

    }

    override fun showAll(context: Context, list: List<PushNotificationData>) {
        list.forEach { show(context, it) }
    }

    private fun ensureChannel(context: Context, data: PushNotificationData) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val existing = manager.getNotificationChannel(data.channelId)
            if (existing == null) {
                val channel = NotificationChannel(
                    data.channelId,
                    data.title,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                channel.description = data.message
                manager.createNotificationChannel(channel)
            }
        }
    }
}
