package com.example.ghtk_intern_week2.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ghtk_intern_week2.R
import com.example.ghtk_intern_week2.databinding.HistoryItemBinding
import com.example.ghtk_intern_week2.model.network.response.History

class HistoryItemAdapter : RecyclerView.Adapter<HistoryItemAdapter.HistoryViewHolder>() {
    private val listdata: MutableList<History> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<History>){
        listdata.clear()
        listdata.addAll(list)
        notifyDataSetChanged()
    }
    inner class HistoryViewHolder(val binding: HistoryItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : History){
            if(item.is_up){
                binding.iconView.setImageResource(R.drawable.level_up)
            }else{
                binding.iconView.setImageResource(R.drawable.level_down)
            }
            binding.text.text = if(item.is_up) "UP! ${item.title}" else "DOWN! ${item.title}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun getItemCount(): Int = listdata.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(listdata[position])
    }
}