package com.amos.infotaimos.model

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.car.app.notification.CarAppExtender
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.amos.infotaimos.MainActivity
import com.amos.infotaimos.R

/**
 * Object for handling notification channel creation and sending notifications
 * @property TAG Tag for logging purposes
 * @property channelID ID of notification channel
 * @property channelReady Flag indication notification channel setup
 */
object NotificationManager {
    private const val TAG = "NOTIFICATION_MANAGER"
    private const val channelID = "InfotAiMOS_NOTIF_CHANNEL"
    private var channelReady = false

    /**
     * Function for setting up notification channel if needed
     * @param context Context object for accessing notification management services
     */
    fun createNotificationChannel(context: Context) {
        if (!channelReady) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelID, "InfotAiMOS", importance).apply {
                description = "InfotAiMOS Notification Channel"
            }
            with(NotificationManagerCompat.from(context)) {
                createNotificationChannel(channel)
                channelReady = true
                Log.d(TAG, "Notification Channel ready")
            }
        } else {
            Log.d(TAG, "Notification Channel already ready")
        }
    }

    /**
     * Function for creating and sending a new notification
     * @param context Context object for accessing notification management services
     * @param message String with content of notification
     */
    @SuppressLint("MissingPermission")
    fun createNotification(
        context: Context,
        message: String) {
        if (!channelReady) {
            Log.d(TAG, "Notification Channel not ready for notification")
            return
        }

        val contentIntent = PendingIntent.getActivity(
            context, 0, Intent(
                context,
                MainActivity::class.java
            ).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
            .setContentTitle("InfotAiMOS")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setContentIntent(contentIntent)
            .extend(CarAppExtender.Builder().setImportance(NotificationManager.IMPORTANCE_HIGH).build())
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(context)) {
            notify(1, builder.build())
            Log.d(TAG, "Sent Notification")
        }
    }
}