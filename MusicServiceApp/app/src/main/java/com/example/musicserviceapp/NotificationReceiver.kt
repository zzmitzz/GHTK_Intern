package com.example.musicserviceapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.musicserviceapp.utils.Constant





class NotificationReceiver() : BroadcastReceiver() {

    object NotificationListenerHolder {
        var listener: NotificationReceiverListener? = null
    }


    fun setListener(listener: NotificationReceiverListener) {
        NotificationListenerHolder.listener = listener
    }

    interface NotificationReceiverListener {
        fun previous()

        fun play()

        fun next()
    }


    override fun onReceive(
        context: Context?,
        intent: Intent?,
    ) {
        val action = intent?.getStringExtra(Constant.ACTION)
        Log.d("TAG", action ?: "null")
        Log.d("TAG", NotificationListenerHolder.listener.toString())
        when (action) {
            Constant.PREVIOUS -> {
                Log.d("TAG", "previous")
                NotificationListenerHolder.listener?.previous()
            }
            Constant.NEXT -> {
                Log.d("TAG", "next")
                NotificationListenerHolder.listener?.next()
            }

            Constant.PLAY -> {
                Log.d("TAG", "play")
                NotificationListenerHolder.listener?.play()
            }
        }
    }
}
