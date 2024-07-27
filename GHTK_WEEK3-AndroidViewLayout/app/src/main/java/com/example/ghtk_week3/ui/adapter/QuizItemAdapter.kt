package com.example.ghtk_week3.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ghtk_week3.R
import com.example.ghtk_week3.model.CharItem

class QuizItemAdapter() : RecyclerView.Adapter<QuizItemAdapter.QuizItemVH>() {
    var data: MutableList<Char> = mutableListOf()
    lateinit var invoke: (Int) -> (Unit)
    @SuppressLint("NotifyDataSetChanged")
    fun setListData(data: MutableList<Char>) {
        this.data = data
        notifyDataSetChanged()
    }
    fun updateItem(s: Char, position: Int){
        data[position] = s
        notifyItemChanged(position)

    }
    inner class QuizItemVH(val view: View ): RecyclerView.ViewHolder(view) {
        fun bind(char: Char) {
            view.findViewById<TextView>(R.id.textInput).apply {
                text = char.toString()
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizItemVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_input, parent, false)
        return QuizItemVH(view)
    }
    override fun onBindViewHolder(holder: QuizItemVH, position: Int) {
        holder.bind(data[position])
        holder.itemView.also {
            it.setOnClickListener{
                //Delete the selected text in Item
                invoke.invoke(position)
            }
        }
    }

    override fun getItemCount(): Int = data.size
}