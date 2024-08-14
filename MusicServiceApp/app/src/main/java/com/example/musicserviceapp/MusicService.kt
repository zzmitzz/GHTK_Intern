package com.example.musicserviceapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.musicserviceapp.utils.Constant

interface MusicPlayerController {
    fun prepareSong(path: Int)

    fun play()

    fun pause()

    fun removeSong()

    fun seekTo(position: Int)

    // :) too bad, i gonna take the shit media reference
    fun getMediaPlayer(): MediaPlayer?

    fun updateNotification(
        title: String,
        artist: String,
        isPlaying: Boolean,
    )
}

class MusicService :
    Service(),
    MusicPlayerController {
    private var mediaPlayer: MediaPlayer? = null
    private val binder = MusicBinder()

    inner class MusicBinder : Binder() {
        fun getBinder(): MusicPlayerController = this@MusicService
    }

    override fun updateNotification(
        title: String,
        artist: String,
        isPlaying: Boolean,
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val channelName = "Music Service"
            val channel =
                NotificationChannel(
                    packageName,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH,
                ).also {
                    it.lightColor = Color.BLUE
                    it.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
                    manager.createNotificationChannel(it)
                }
        }
        val intentPrev =
            Intent(this, NotificationReceiver::class.java).also {
                it.putExtra(Constant.ACTION, Constant.PREVIOUS)
            }
        val intentPlayPause =
            Intent(this, NotificationReceiver::class.java).also {
                it.putExtra(Constant.ACTION, Constant.PLAY)
            }

        val intentNext =
            Intent(this, NotificationReceiver::class.java).also {
                it.putExtra(Constant.ACTION, Constant.NEXT)
            }




        val pendingIntentPrevious =
            PendingIntent.getBroadcast(
                applicationContext,
                123,
                intentPrev,
                PendingIntent.FLAG_IMMUTABLE,
            )
        val pendingIntentPlay =
            PendingIntent.getBroadcast(
                applicationContext,
                1234,
                intentPlayPause,
                PendingIntent.FLAG_IMMUTABLE,
            )
        val pendingIntentNext =
            PendingIntent.getBroadcast(
                applicationContext,
                3245,
                intentNext,
                PendingIntent.FLAG_IMMUTABLE,
            )

        val remoteObject = RemoteViews(packageName, R.layout.notification_music)
        remoteObject.setTextViewText(R.id.tvSongTitle, title)
        remoteObject.setTextViewText(R.id.tvArtistName, artist)
        remoteObject.setOnClickPendingIntent(R.id.ivPrevious, pendingIntentPrevious)
        remoteObject.setOnClickPendingIntent(R.id.ivPlayPause, pendingIntentPlay)
        remoteObject.setOnClickPendingIntent(R.id.ivNext, pendingIntentNext)

        if (isPlaying) {
            remoteObject.setImageViewResource(R.id.ivPlayPause, R.drawable.pause_24px)
        } else {
            remoteObject.setImageViewResource(R.id.ivPlayPause, R.drawable.play_arrow_24px)
        }
        val notification =
            NotificationCompat
                .Builder(this, packageName)
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setCustomBigContentView(remoteObject)
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setSilent(true)
                .build()
        startForeground(1, notification)
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        stopSelf()
        super.onDestroy()
    }

    override fun prepareSong(path: Int) {
        if (mediaPlayer != null) {
            mediaPlayer!!.release()
        }
        mediaPlayer = MediaPlayer.create(this, path)
        mediaPlayer!!.setOnCompletionListener {
            play()
        }
        play()
    }

    override fun play() = mediaPlayer!!.start()

    override fun pause() = mediaPlayer!!.pause()

    override fun removeSong() {
        mediaPlayer!!.release()
    }

    override fun seekTo(position: Int) {
        mediaPlayer!!.seekTo(position)
    }

    override fun getMediaPlayer() = mediaPlayer

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int = START_STICKY
}
