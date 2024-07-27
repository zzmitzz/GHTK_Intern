package com.example.ghtk_intern_week2.ui.screen.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.ghtk_intern_week2.MainViewModel
import com.example.ghtk_intern_week2.R
import com.example.ghtk_intern_week2.base.BaseFragment
import com.example.ghtk_intern_week2.base.SpaceItemDecoration
import com.example.ghtk_intern_week2.databinding.FragmentAchievemeentBinding
import com.example.ghtk_intern_week2.ui.adapter.HistoryItemAdapter
import com.example.ghtk_intern_week2.ui.screen.AccountFragment
import com.example.ghtk_intern_week2.viewmodel.ViewModelFactory
import java.security.AccessControlContext

class AchievementFragment : BaseFragment<AccountViewModel>() {
    private lateinit var recyclerView: RecyclerView
    private val binding by lazy {
        FragmentAchievemeentBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun initView() {
        super.initView()
        setupRecyclerView()
    }

    override fun initListener() {

    }

    override fun observeLiveData() {
        viewModel.historyList.observe(viewLifecycleOwner){
            setupRecyclerView()
        }
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(requireParentFragment(), ViewModelFactory())[AccountViewModel::class.java]
        Log.d("TAG", viewModel.hashCode().toString())
    }

    private fun setupRecyclerView(){
        val adapter = HistoryItemAdapter()
        adapter.setList(viewModel.historyList.value ?: listOf() )
        binding.rcview.apply {
            this.adapter = adapter
            addItemDecoration(SpaceItemDecoration(15))
            setHasFixedSize(true)
        }
    }
}