package com.bunli.tts.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.bunli.tts.R

class MixBoundService : Service() {

    private var mediaPlayer: MediaPlayer? = null

    private val binder = MixBoundServiceBinder()

    companion object {
        private val TAG = BoundService::class.java.simpleName
    }

    inner class MixBoundServiceBinder : Binder() {
        fun getBinder() = this@MixBoundService
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setOnCompletionListener {
            stopSelf()
        }
        mediaPlayer?.setDataSource(
            applicationContext,
            Uri.parse("android.resource://" + packageName + "/" + R.raw.demo)
        )
        mediaPlayer?.prepare()
        mediaPlayer?.start()

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channelName = "Play Music Background Service"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                packageName,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.lightColor = Color.BLUE
            channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            manager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(this, packageName)
        val notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Tèn tén ten")
            .setContentText("Cùng nghe nhạc nào mọi người!")
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        startForeground(1, notification)
    }

    override fun onBind(intent: Intent): IBinder {
        Log.e(TAG, "onBind")
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.e(TAG, "onRebind")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.e(TAG, "onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        Log.e(TAG, "onDestroy")
        super.onDestroy()
    }

    fun playMedia() {
        mediaPlayer?.start()
    }

    fun pauseMedia() {
        mediaPlayer?.pause()
    }
}