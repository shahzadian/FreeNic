package com.shahzadian.push.dispatcher

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
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
        val launchIntent =
            context.packageManager.getLaunchIntentForPackage(context.packageName)?.apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            launchIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val builder = NotificationCompat.Builder(context, data.channelId)
            .setSmallIcon(data.iconRes)
            .setContentTitle(data.title)
            .setContentText(data.message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        NotificationManagerCompat.from(context).notify(data.notificationId, builder.build())
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
