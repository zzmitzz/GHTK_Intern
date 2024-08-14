package com.example.musicserviceapp.ui.homepage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicserviceapp.R
import com.example.musicserviceapp.data.model.Music
import com.example.musicserviceapp.data.model.MusicData
import com.example.musicserviceapp.databinding.MusicItemBinding

class MusicAdapter : RecyclerView.Adapter<MusicAdapter.ViewHolderMusic>() {
    var invoke : (Music) -> Unit = {}
    val data = MusicData.data
    inner class ViewHolderMusic(val binding: MusicItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(music: Music) {
            binding.musicTitle.text = music.title
            binding.artist.text = music.artist
            binding.root.setOnClickListener {
                invoke(music)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMusic {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MusicItemBinding.inflate(inflater, parent, false)
        return ViewHolderMusic(binding)
    }

    override fun getItemCount(): Int  = data.size

    override fun onBindViewHolder(holder: ViewHolderMusic, position: Int) {
        holder.bind(data[position])
    }
}