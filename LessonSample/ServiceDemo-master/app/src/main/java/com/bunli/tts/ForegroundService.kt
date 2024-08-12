package com.bunli.tts

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import java.util.zip.ZipEntry

class ForegroundService : Service() {

    private var mediaPlayer: MediaPlayer? = null

    companion object {
        var instance: ForegroundService? = null
        private val TAG = ForegroundService::class.java.simpleName
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Log.e(TAG, "onCreate ${hashCode()}")
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

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val data = intent?.getIntExtra("data", -1)
        Log.e(TAG, "onStartCommand Data: $data, Flag: $flags, StartId: $startId")
        return START_STICKY
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        Log.e(TAG, "onTaskRemoved")
        super.onTaskRemoved(rootIntent)
    }

    override fun onDestroy() {
        Log.e(TAG, "onDestroy")
        mediaPlayer?.release()
        super.onDestroy()
    }

    fun pauseMedia() {
        mediaPlayer?.pause()
    }
}