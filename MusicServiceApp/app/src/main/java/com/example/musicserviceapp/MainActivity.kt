package com.example.musicserviceapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.musicserviceapp.data.model.Music
import com.example.musicserviceapp.data.model.MusicData
import com.example.musicserviceapp.databinding.ActivityMainBinding
import com.example.musicserviceapp.ui.bottom_music_fragment.BottomMusicFragment
import com.example.musicserviceapp.ui.detailpage.view.DetailFragment
import com.example.musicserviceapp.ui.homepage.view.HomePageFragment

class MainActivity :
    AppCompatActivity(),
    BottomMusicFragment.BottomMusicFragmentListener,
    HomePageFragment.HomePageFragmentListener,
    DetailFragment.DetailFragmentListener,
    NotificationReceiver.NotificationReceiverListener {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var receiver: NotificationReceiver
    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    private val loadingDialogStupid by lazy {
        AlertDialog
            .Builder(this)
            .setTitle("Loading")
            .setCancelable(false)
            .create()
    }
    private val data = MusicData.data
    private var musicService: MusicPlayerController? = null
    private var bounded: Boolean = false
    private val mConnection =
        object : ServiceConnection {
            override fun onServiceConnected(
                name: ComponentName,
                service: IBinder,
            ) {
                val binder: MusicService.MusicBinder =
                    service as MusicService.MusicBinder
                musicService = binder.getBinder()
                bounded = true
                Log.d("MainActivity", "onServiceConnected")
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                bounded = false
                Log.d("MainActivity", "onServiceDisconnected")
            }
        }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment1, HomePageFragment())
            .commit()
        binding.fragment2.visibility = View.GONE
        initObserve()
        initListener()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun bottomTrigger() {
        if (viewModel.getMusic().value != null) {
            binding.fragment2.visibility = View.VISIBLE
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment2, BottomMusicFragment())
                .commit()
        }
    }

    override fun detachBottomTrigger() {
        binding.fragment2.visibility = View.GONE
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, MusicService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
        receiver = NotificationReceiver()
        receiver.setListener(this)
        registerReceiver(receiver, IntentFilter("NOTIFICATION"), RECEIVER_NOT_EXPORTED)
    }

    private fun initObserve() {
        viewModel.getMusic().observe(this) {
            if (it != null) {
                musicRunning(it)
            }
        }
        viewModel.getIsLoading().observe(this) {
            if (it) {
                loadingDialogStupid.show()
            } else {
                loadingDialogStupid.dismiss()
            }
        }
        viewModel.isPlaying.observe(this) {
            musicService?.updateNotification(
                viewModel.getMusic().value!!.title,
                viewModel.getMusic().value!!.artist,
                it,
            )
        }
    }

    private fun initListener() {
    }

    private fun musicRunning(music: Music) {
        musicService?.prepareSong(music.path)
        viewModel.mediaPlayer = musicService?.getMediaPlayer()!!
        viewModel.observeCurrentPosition()
        viewModel.isPlaying.value = true
    }

    override fun onBottomMusicFragmentClick() {
        navigateToDetailFragment()
    }

    override fun onPlayClick() {
        if (viewModel.mediaPlayer != null) {
            if (viewModel.mediaPlayer!!.isPlaying) {
                musicService?.pause()
                viewModel.isPlaying.value = false
            } else {
                musicService?.play()
                viewModel.isPlaying.value = true
            }
        }
    }

    override fun onSeekbarChange(progress: Int) {
        musicService?.seekTo(progress)
    }

    override fun musicPick(music: Music) {
        navigateToDetailFragment()
    }

    private fun navigateToDetailFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment1, DetailFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
        unbindService(mConnection)
    }

    // The application is for the service practicing
    override fun nextSong() {
        viewModel.setMusic(data.random())
    }

    override fun previousSong() {
        viewModel.setMusic(data.random())
    }

    override fun previous() {
        previousSong()
    }

    override fun play() {
        onPlayClick()
    }

    override fun next() {
        nextSong()
    }
}
