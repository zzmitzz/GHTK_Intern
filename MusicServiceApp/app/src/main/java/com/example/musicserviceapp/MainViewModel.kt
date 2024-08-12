package com.example.musicserviceapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicserviceapp.data.model.Music

class MainViewModel : ViewModel() {
    private val _music: MutableLiveData<Music?> = MutableLiveData(null)

    fun getMusic(): LiveData<Music?> {
        return _music
    }


    fun setMusic(music: Music?) {
        _music.value = music
    }


}