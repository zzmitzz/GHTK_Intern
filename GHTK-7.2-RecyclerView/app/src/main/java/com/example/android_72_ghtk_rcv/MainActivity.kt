package com.example.android_72_ghtk_rcv

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/*
+ Sử dụng Recyclerview hiển thị danh sách các đơn hàng.
+ Có các cơ chế: Refresh/Load More danh sách
+ Fake data để list hiển thị tối thiểu 100 item, limit 1 page = 20

 */


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy{
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    private lateinit var rcv: androidx.recyclerview.widget.RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        setupRecyclerView()
    }
    private fun initView(){
        rcv = findViewById(R.id.recyclerView)
        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.rcvCont)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.reloadData()
            swipeRefreshLayout.isRefreshing = false
        }
    }
    private fun setupRecyclerView(){
        viewModel.initAdapter {
            rcv.adapter = viewModel.adapter
            rcv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
            rcv.setHasFixedSize(true)
            rcv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    Log.d("TAG", "onScrolled: $dx, $dy")
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                    // If user scroll until half of list, app will load more data for optimize experience
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount/2
                        && firstVisibleItemPosition >= 0) {
                        viewModel.loadMoreItems()
                    }

//                    if(firstVisibleItemPosition == 0){
//                        viewModel.reloadData()
//                    }
                }
            })
        }
    }
}