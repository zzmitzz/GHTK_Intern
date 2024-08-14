package com.example.musicserviceapp

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicserviceapp.data.model.Music
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _music: MutableLiveData<Music?> = MutableLiveData(null)
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    var currentPosition: MutableLiveData<Int> = MutableLiveData(0)
    private val handler = Handler(Looper.getMainLooper())
    var currentSongPossition : Int = 0
    private val dispatcher = Dispatchers.Default
    var mediaPlayer: MediaPlayer? = null

    fun getIsLoading(): LiveData<Boolean> = _isLoading

    fun getMusic(): LiveData<Music?> = _music

    var isPlaying: MutableLiveData<Boolean> = MutableLiveData(false)

    fun setMusic(music: Music?) {
        _music.value = music
    }

    fun observeCurrentPosition() {
        viewModelScope.launch(dispatcher) {
            if (mediaPlayer != null) {
                handler.post(
                    object : Runnable {
                        override fun run() {
                            currentPosition.postValue(mediaPlayer!!.currentPosition)
                            handler.postDelayed(this, 100)
                        }
                    },
                )
            }
        }
    }
}
