package com.bunli.tts

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import android.util.Log


class UnboundService : Service() {

    private var mediaPlayer: MediaPlayer? = null

    companion object {
        private val TAG = UnboundService::class.java.simpleName
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG, "onCreate ${hashCode()}")
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setOnCompletionListener {
            stopSelf()
        }
        mediaPlayer?.setDataSource(
            applicationContext,
            Uri.parse("android.resource://" + packageName + "/" + R.raw.demo)
        )
        mediaPlayer?.prepare();
        mediaPlayer?.start()
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
}