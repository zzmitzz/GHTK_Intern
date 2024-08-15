package com.bunli.tts.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.bunli.tts.R

class BoundService : Service() {

    private var mediaPlayer: MediaPlayer? = null

    private val binder = BoundServiceBinder()

    companion object {
        private val TAG = BoundService::class.java.simpleName
    }

    inner class BoundServiceBinder : Binder() {
        fun getBinder() = this@BoundService
    }

    override fun onBind(intent: Intent): IBinder {
        Log.e(TAG, "onBind")
        return binder
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