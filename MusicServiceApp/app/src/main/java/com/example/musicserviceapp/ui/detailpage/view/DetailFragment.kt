package com.example.musicserviceapp.ui.detailpage.view

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import com.example.musicserviceapp.MainViewModel
import com.example.musicserviceapp.base.BaseFragment
import com.example.musicserviceapp.data.model.Music
import com.example.musicserviceapp.databinding.DetailFragmentBinding
import com.example.musicserviceapp.ui.bottom_music_fragment.BottomMusicFragment
import com.example.musicserviceapp.ui.homepage.view.HomePageFragment
import com.example.musicserviceapp.utils.toTime


class DetailFragment : BaseFragment<MainViewModel, DetailFragmentBinding>() {
    private var music: Music? = null

    private lateinit var audioManager: AudioManager
    interface DetailFragmentListener {
        fun nextSong()
        fun previousSong()
    }

    private var listener : DetailFragmentListener? = null
    private var listener2: BottomMusicFragment.BottomMusicFragmentListener? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DetailFragmentListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement DetailFragmentListener")
        }
        if (context is BottomMusicFragment.BottomMusicFragmentListener) {
            listener2 = context
        } else {
            throw RuntimeException("$context must implement HomePageFragmentListener")
        }
    }
    override fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        music = viewModel.getMusic().value
    }

    override fun initBinding() {
        binding = DetailFragmentBinding.inflate(layoutInflater)
    }

    override fun initObserver() {
        viewModel.getMusic().observe(this) {
            music = it
            initSeekbar()
            binding.musicTitle.text = music?.title ?: "Null"
            binding.musicArtist.text = music?.artist ?: "Null"

        }
        viewModel.currentPosition.observe(this) {
            binding.seekBarMusic.progress = it
            binding.currentPosition.text = it.toTime()
        }

        viewModel.isPlaying.observe(this) {
            if (it) {
                binding.playPauseButton.setImageResource(android.R.drawable.ic_media_pause)
            } else {
                binding.playPauseButton.setImageResource(android.R.drawable.ic_media_play)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        audioManager = requireActivity().getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
    private fun initSeekbar(){
        if(viewModel.mediaPlayer != null){
            binding.seekBarMusic.max = viewModel.mediaPlayer!!.duration
            binding.seekBarMusic.setOnSeekBarChangeListener(object :
                android.widget.SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: android.widget.SeekBar, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        listener2?.onSeekbarChange(progress)
                    }
                }
                override fun onStartTrackingTouch(seekBar: android.widget.SeekBar) {
                }
                override fun onStopTrackingTouch(seekBar: android.widget.SeekBar) {
                }
            })

            binding.musicDuration.text = viewModel.mediaPlayer!!.duration.toTime()
            binding.seekBarVolume.apply {
                max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                progress = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        // Set the volume to the new value
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    }
                })
            }
        }
    }
    override fun initListener() {
        binding.backButton.setOnClickListener{
            val fragmentManager = requireActivity().supportFragmentManager
            if (fragmentManager.backStackEntryCount > 0) {
                fragmentManager.popBackStack() // Pop the top fragment from the back stack
            } else {
                requireActivity().onBackPressed() // Otherwise, handle the back button normally
            }
        }
        binding.playPauseButton.setOnClickListener{
            listener2?.onPlayClick()
        }
        binding.nextButton.setOnClickListener{
            listener?.nextSong()
        }
        binding.previousButton.setOnClickListener{
            listener?.previousSong()
        }
    }
}