package com.bunli.tts

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MainActivity : AppCompatActivity() {
    private var boundService: BoundService? = null
    private var bound: Boolean = false
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder: BoundService.BoundServiceBinder =
                service as BoundService.BoundServiceBinder
            boundService = binder.getBinder()
            bound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            bound = false
        }

    }

    private var mixBoundService: MixBoundService? = null
    private var mixBound: Boolean = false
    private val mixConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder: MixBoundService.MixBoundServiceBinder =
                service as MixBoundService.MixBoundServiceBinder
            mixBoundService = binder.getBinder()
            mixBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mixBound = false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //background service
        findViewById<Button>(R.id.start_background_services_btn).setOnClickListener {
            val backgroundIntentService = Intent(this, UnboundService::class.java)
            startService(backgroundIntentService)
        }

        findViewById<Button>(R.id.stop_background_services_btn).setOnClickListener {
            val backgroundIntentService = Intent(this, UnboundService::class.java)
            stopService(backgroundIntentService)
        }

        //foreground service
        findViewById<Button>(R.id.start_foreground_services_btn).setOnClickListener {
            val foregroundIntentService = Intent(this, ForegroundService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(foregroundIntentService)
            } else {
                startService(foregroundIntentService)
            }
        }

        findViewById<Button>(R.id.pass_data_foreground_services_btn).setOnClickListener {
            val foregroundIntentService = Intent(this, ForegroundService::class.java)
            foregroundIntentService.putExtra("data", 1)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(foregroundIntentService)
            } else {
                startService(foregroundIntentService)
            }
        }

        findViewById<Button>(R.id.stop_foreground_services_btn).setOnClickListener {
            val foregroundIntentService = Intent(this, ForegroundService::class.java)
            stopService(foregroundIntentService)
        }

        //bound service
        findViewById<Button>(R.id.start_bound_services_btn).setOnClickListener {
            val intent = Intent(this, BoundService::class.java)
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }

        findViewById<Button>(R.id.play_media_btn).setOnClickListener {
            boundService?.playMedia()
        }

        findViewById<Button>(R.id.pause_media_btn).setOnClickListener {
            boundService?.pauseMedia()
        }

        findViewById<Button>(R.id.start_mix_services_btn).setOnClickListener {
            val mixIntentService = Intent(this, MixBoundService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(mixIntentService)
            } else {
                startService(mixIntentService)
            }
            bindService(mixIntentService, mixConnection, Context.BIND_AUTO_CREATE)
        }

        findViewById<Button>(R.id.stop_foreground_mix_services_btn).setOnClickListener {
            val mixIntentService = Intent(this, MixBoundService::class.java)
            stopService(mixIntentService)
        }

        findViewById<Button>(R.id.play_mix_services_btn).setOnClickListener {
            mixBoundService?.playMedia()
        }

        findViewById<Button>(R.id.pause_mix_services_btn).setOnClickListener {
            mixBoundService?.pauseMedia()
        }
    }

    override fun onStop() {
        super.onStop()
        if (bound) {
            unbindService(connection)
            bound = false
        }
        if (mixBound) {
            unbindService(mixConnection)
            mixBound = false
        }
    }
}