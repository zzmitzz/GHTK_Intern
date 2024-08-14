package com.example.musicserviceapp.ui.homepage.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicserviceapp.MainViewModel
import com.example.musicserviceapp.base.BaseFragment
import com.example.musicserviceapp.data.model.Music
import com.example.musicserviceapp.databinding.FragmentMusicPlayerBinding
import com.example.musicserviceapp.ui.homepage.adapter.MusicAdapter

class HomePageFragment : BaseFragment<MainViewModel, FragmentMusicPlayerBinding>() {
    interface HomePageFragmentListener {
        fun musicPick(music: Music)
        fun bottomTrigger()
        fun detachBottomTrigger()
    }

    private var listner: HomePageFragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomePageFragmentListener) {
            listner = context
        } else {
            throw RuntimeException("$context must implement HomePageFragmentListener")
        }
    }

    private lateinit var mAdapter: MusicAdapter

    override fun onResume() {
        super.onResume()
        listner?.bottomTrigger()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        mAdapter = MusicAdapter()
        mAdapter.invoke = {
            viewModel.setMusic(it)
            listner?.musicPick(it)
        }
        binding.recycler.apply {
            adapter = mAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun initBinding() {
        binding = FragmentMusicPlayerBinding.inflate(layoutInflater)
    }

    override fun onStop() {
        super.onStop()
        listner?.detachBottomTrigger()
    }
    override fun initObserver() {

    }

    override fun initListener() {
    }
}
