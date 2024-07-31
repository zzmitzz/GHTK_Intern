
package com.example.coroutines.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.coroutines.R


/*
- For updating UI of clock, we have 3 main approaches:
    +) Using Callback, passing Dependency Injection
    +) Using LiveData => More maintainable
    +) Use notifyItemChanged()
 */
class ClockAdapter : RecyclerView.Adapter<ClockAdapter.ClockVH>() {

    val data: MutableList<CountTime> = mutableListOf()
    fun setData(list: MutableList<CountTime>){
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }
//    fun itemUpdate(position: Int){
//        notifyItemChanged(position)
//    }
    inner class ClockVH(val view: View) : RecyclerView.ViewHolder(view){
        private val observer = Observer<Long>{
            view.findViewById<TextView>(R.id.textview).text = formatTime(it)
        }
        fun bindTime(countTime: CountTime){
            countTime.livedataTime.observeForever(observer)
        }
        fun removeObserve(countTime: CountTime){
            countTime.livedataTime.removeObserver(observer)
        }
        fun bind(countTime: CountTime){

            view.findViewById<ImageView>(R.id.play).setOnClickListener {
                if(countTime.getState()){
                    (it as ImageView).setImageResource(R.drawable.play_arrow_24px)
                    countTime.stop()
                }else{
                    (it as ImageView).setImageResource(R.drawable.pause_24px)
                    countTime.start()
                }
            }
            view.findViewById<ImageView>(R.id.restart).setOnClickListener {
                countTime.reset()
                (view.findViewById<ImageView>(R.id.play) as ImageView).setImageResource(R.drawable.play_arrow_24px)
            }

            checkStatus(countTime)
        }

        fun checkStatus(countTime: CountTime){
            if(countTime.isRunning){
                view.findViewById<ImageView>(R.id.play).setImageResource(R.drawable.pause_24px)
            }else{
                view.findViewById<ImageView>(R.id.play).setImageResource(R.drawable.play_arrow_24px)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClockVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ClockVH(layoutInflater.inflate(R.layout.clock_item, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ClockVH, position: Int) {
        holder.bind(data[position])
        holder.bindTime(data[position])
    }
    private fun formatTime(a: Long): String{
        val minute = a / 1000 / 60
        val second = a / 1000 % 60
        val mili = (a % 1000)/10
        return String.format("%02d:%02d:%02d", minute, second, mili)
    }
}