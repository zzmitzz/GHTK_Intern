package com.example.ghtk_intern_week2.ui.screen

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.ghtk_intern_week2.MainViewModel
import com.example.ghtk_intern_week2.base.BaseFragment
import com.example.ghtk_intern_week2.databinding.Fragment1Binding
import com.example.ghtk_intern_week2.ui.adapter.CharAdapter
import com.example.ghtk_intern_week2.viewmodel.ViewModelFactory

class Fragment1 : BaseFragment<MainViewModel>() {

    private val binding by lazy {
        Fragment1Binding.inflate(layoutInflater)
    }
    private var inputString = ""
    private var listResult = mutableMapOf<Char, Int>()
    private lateinit var charAdapter: CharAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialRecycleView(listResult)
    }
    override fun initListener() {
        binding.apply {
            button.setOnClickListener{
                inputString = binding.editText.text.toString()
                if(inputString.isNotEmpty()){
                    viewModel.characterCountFrequency(inputString){
                        listResult = it
                        charAdapter.setList(listResult)
                    }
                }
            }
            clear.setOnClickListener{
                binding.editText.text.clear()
            }
        }
    }

    override fun observeLiveData() {
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory())[MainViewModel::class.java]
        Log.d("TAG", "Fragment initViewModel: ${viewModel.hashCode()}")
    }
    private fun initialRecycleView(map : MutableMap<Char,Int>){
        charAdapter = CharAdapter()
        charAdapter.setList(map)
        binding.rcview.apply {
            adapter = charAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, VERTICAL))
        }
    }
}