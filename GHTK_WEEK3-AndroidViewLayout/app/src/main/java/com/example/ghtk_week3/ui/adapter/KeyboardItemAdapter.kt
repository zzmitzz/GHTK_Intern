package com.example.ghtk_week3.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ghtk_week3.R
import com.example.ghtk_week3.manager.KeyboardInputManager

class KeyboardItemAdapter : RecyclerView.Adapter<KeyboardItemAdapter.KeyboardVH>() {

    private var data: MutableList<Char> = mutableListOf()
    lateinit var invoke: (Int) -> (Unit)
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: MutableList<Char>){
        this.data = data
        notifyDataSetChanged()
    }
    fun updateItem(position: Int, payload: Any?){
        if(payload != null){
            notifyItemChanged(position, payload)
        }
    }
    inner class KeyboardVH(val view: View) : RecyclerView.ViewHolder(view){
        fun bind(char: Char){
           view.findViewById<TextView>(R.id.textInput).apply {
               text = char.toString()
           }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeyboardVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_question, parent, false)
        return KeyboardVH(view)
    }

    override fun getItemCount(): Int = data.size
    override fun onBindViewHolder(holder: KeyboardVH, position: Int, payloads: MutableList<Any>) {
        if(payloads.isNotEmpty()){
            for(payload in payloads){
                if(payload is String && payload == "update"){
                    holder.itemView.visibility = View.VISIBLE
                }
            }
            Log.d("TAG", "onBindViewHolder: $payloads")
        }

        super.onBindViewHolder(holder, position, payloads)
    }
    override fun onBindViewHolder(holder: KeyboardVH, position: Int) {
        Log.d("TAG", "onBindViewHolder: $position")
        holder.bind(data[position])
        holder.itemView.setOnClickListener{
            Log.d("TAG", "onBindViewHolder: $position clicked")
            invoke.invoke(position)
//            it.visibility = View.INVISIBLE
        }
    }

}