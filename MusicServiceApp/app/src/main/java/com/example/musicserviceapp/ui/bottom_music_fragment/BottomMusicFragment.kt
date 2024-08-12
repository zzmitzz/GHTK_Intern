package com.example.musicserviceapp.ui.bottom_music_fragment

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.musicserviceapp.MainViewModel
import com.example.musicserviceapp.base.BaseFragment
import com.example.musicserviceapp.databinding.BottomViewMusicBinding



class BottomMusicFragment : BaseFragment<MainViewModel, BottomViewMusicBinding>() {

    interface BottomMusicFragmentListener {
        fun onBottomMusicFragmentClick()
        fun onPlayClick()
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
    }

    override fun initListener() {
        binding.imageView.setOnClickListener {
            listener?.onBottomMusicFragmentClick()
        }
        binding.playPause.setOnClickListener {
            listener?.onPlayClick()
        }
    }
}