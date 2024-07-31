package com.example.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.coroutines.adapter.ClockAdapter
import com.example.coroutines.adapter.CountTime
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.FieldPosition

class MainActivity : AppCompatActivity() {

    private var mAdapter: ClockAdapter? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var addClock: FloatingActionButton
    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initListener()
    }
    private fun initView(){
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        addClock = findViewById<FloatingActionButton>(R.id.addClock)
    }
    private fun initListener(){
        addClock.setOnClickListener{
            viewModel.listClock.add(CountTime())
            mAdapter?.setData(viewModel.listClock)
        }

        findViewById<Button>(R.id.continueAll).setOnClickListener {
            viewModel.listClock.forEachIndexed() { index, it ->
                it.start()
                updateUI(index)
            }
        }
        findViewById<Button>(R.id.startAll).setOnClickListener {
            viewModel.listClock.forEachIndexed { index, it ->
                it.start()
                updateUI(index)
            }
        }
        findViewById<Button>(R.id.pauseAll).setOnClickListener {
            viewModel.listClock.forEachIndexed { index, it ->
                it.stop()
                updateUI(index)
            }
        }
        findViewById<Button>(R.id.resetAll).setOnClickListener {
            viewModel.listClock.forEachIndexed() { index, it ->
                it.reset()
                removeObserver(index)
            }
            viewModel.listClock.clear()
            mAdapter?.data!!.clear()
            mAdapter?.notifyDataSetChanged()
        }

    }
    private fun initRecyclerView(){
        recyclerView.apply {
            adapter = mAdapter
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }

    }

    override fun onStart() {
        mAdapter = ClockAdapter()
        initRecyclerView()
        super.onStart()
    }
    override fun onStop() {
        mAdapter = null
        super.onStop()
    }

    fun updateUI(position: Int){
        recyclerView.findViewHolderForAdapterPosition(position)?.let {
            (it as ClockAdapter.ClockVH).checkStatus(viewModel.listClock[position])
        }
    }
    fun removeObserver(position: Int){
        recyclerView.findViewHolderForAdapterPosition(position)?.let {
            (it as ClockAdapter.ClockVH).removeObserve(viewModel.listClock[position])
        }
    }
}