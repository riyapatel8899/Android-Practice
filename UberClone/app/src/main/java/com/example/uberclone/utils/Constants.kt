package com.example.uberclone.utils

import com.example.uberclone.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.uberclone.models.DriverInfoModel

object Constants {

    fun buildWelcomeMessage(): String {
        return StringBuilder("Welcome, ")
            .append(currentUser?.firstName)
            .append("")
            .append(currentUser?.lastName)
            .toString()
    }

//    fun showNotification(context: Context, id: Int, title: String?, body: String?, intent: Intent? = null) {
//        var pendingIntent: PendingIntent? = null
//        if (intent != null) {
//            pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE)
//            val NOTIFICATION_CHANNEL_ID = "riya_uber_clone"
//            var notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                val notificationChannel =  NotificationChannel(NOTIFICATION_CHANNEL_ID, "Uber Clone",
//                    NotificationManager.IMPORTANCE_HIGH)
//                notificationChannel.description = "Uber Clone"
//                notificationChannel.enableLights(true)
//                notificationChannel.lightColor = Color.RED
//                notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
//                notificationChannel.enableVibration(true)
//
//                notificationManager.createNotificationChannel(notificationChannel)
//            }
//            else {
//                val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
//                builder.setContentTitle(title)
//                builder.setAutoCancel(false)
//                builder.setPriority(NotificationCompat.PRIORITY_HIGH)
//                builder.setDefaults(Notification.DEFAULT_VIBRATE)
//                builder.setSmallIcon(R.drawable.baseline_directions_car)
//                builder.setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.baseline_directions_car))
//
//                if (pendingIntent != null)
//                    builder.setContentIntent(pendingIntent)
//                val notification = builder.build()
//                notificationManager.notify(id, notification)
//            }
//        }
//     }


    fun showNotification(context: Context, id: Int, title: String?, body: String?, intent: Intent? = null) {
        val NOTIFICATION_CHANNEL_ID = "riya_uber_clone"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create channel if needed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Uber Clone",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.description = "Uber Clone"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            notificationChannel.enableVibration(true)

            notificationManager.createNotificationChannel(notificationChannel)
        }

        // Build the notification
        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(Notification.DEFAULT_VIBRATE)
            .setSmallIcon(R.drawable.baseline_directions_car)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.baseline_directions_car))

        // Add PendingIntent if intent is not null
        if (intent != null) {
            val pendingIntent = PendingIntent.getActivity(
                context,
                id,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            builder.setContentIntent(pendingIntent)
        }

        // Notify
        val notification = builder.build()
        notificationManager.notify(id, notification)
    }


    val NOTI_BODY: String = "body"
    val NOTI_TITLE = "title"
    val TOKEN_REFERENCE = "Token"
    var currentUser: DriverInfoModel? = null

    const val DRIVER_INFO_REFERENCE = "DriverInfo"
    const val DRIVERS_LOCATION_REFERENCE = "DriversLocation"
}