package com.example.coroutines.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


/*
- For updating UI of clock, we have 3 main approaches:
    +) Using Callback, passing Dependency Injection
    +) Using LiveData => More maintainable
    +) Use notifyItemChanged()
 */
class ClockAdapter : RecyclerView.Adapter<ClockAdapter.ClockVH>() {

    private val data: MutableList<CountTime> = mutableListOf()

    inner class ClockVH(view: View) : RecyclerView.ViewHolder(view){
        fun bind(countTime: CountTime){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClockVH {
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ClockVH, position: Int) {
    }
}