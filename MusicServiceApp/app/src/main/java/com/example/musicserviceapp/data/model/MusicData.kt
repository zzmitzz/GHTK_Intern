package com.example.musicserviceapp.data.model

import com.example.musicserviceapp.R

object MusicData {
    val data: List<Music> = listOf(
        Music("A song", "artist1", R.raw.a),
        Music("B song", "artist2", R.raw.b),
        Music("C song", "artist3", R.raw.c),
        Music("abc", "artist1", R.raw.a),
        Music("cab", "artist2", R.raw.b),
        Music("asdf", "artist3", R.raw.c),
        Music("eeaf", "artist1", R.raw.a),
        Music("vca", "artist2", R.raw.b),
        Music("axc", "artist3", R.raw.c),
    )
}