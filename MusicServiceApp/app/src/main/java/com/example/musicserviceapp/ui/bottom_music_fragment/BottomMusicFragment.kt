package com.example.musicserviceapp.ui.bottom_music_fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.lifecycle.ViewModelProvider
import com.example.musicserviceapp.MainViewModel
import com.example.musicserviceapp.R
import com.example.musicserviceapp.base.BaseFragment
import com.example.musicserviceapp.databinding.BottomViewMusicBinding



class BottomMusicFragment : BaseFragment<MainViewModel, BottomViewMusicBinding>() {

    interface BottomMusicFragmentListener {
        fun onBottomMusicFragmentClick()
        fun onPlayClick()
        fun onSeekbarChange(progress: Int)
    }
    private var listener: BottomMusicFragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BottomMusicFragmentListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement BottomMusicFragmentListener")
        }
    }
    override fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }
    override fun initBinding() {
        binding = BottomViewMusicBinding.inflate(layoutInflater)
    }

    override fun initObserver() {
        viewModel.isPlaying.observe(this){
            if(it){
                binding.playPause.setImageResource(android.R.drawable.ic_media_pause)
            }else{
                binding.playPause.setImageResource(android.R.drawable.ic_media_play)
            }
        }
        viewModel.getMusic().observe(this){
            binding.musicTitle.text = it?.title
            binding.artist.text = it?.artist
        }
        viewModel.currentPosition.observe(this){
            binding.seekBar.progress = it
        }
    }
    override fun initListener() {
        binding.bottom.setOnClickListener {
            listener?.onBottomMusicFragmentClick()
        }
        binding.playPause.setOnClickListener {
            listener?.onPlayClick()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSeekbar()
    }

    private fun initSeekbar(){
        if(viewModel.mediaPlayer != null){
            binding.seekBar.max = viewModel.mediaPlayer!!.duration
        }
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    listener?.onSeekbarChange(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
    }
}