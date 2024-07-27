package com.example.ghtk_intern_week2.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ghtk_intern_week2.databinding.ItemViewBinding

class CharAdapter : RecyclerView.Adapter<CharAdapter.VH>() {
    private var dataRaw = mutableListOf<Char>()
    private var list = mutableMapOf<Char,Int>()


    @SuppressLint("NotifyDataSetChanged")
    fun setList(a: MutableMap<Char,Int>){
        this.list = a
        dataRaw.clear()
        for(x in list.keys){
            dataRaw.add(x)
        }
        notifyDataSetChanged()
    }
    inner class VH(var binding: ItemViewBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(a: Char){
            binding.apply {
                character.text = a.toString()
                freq.text = list[a].toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemViewBinding.inflate(layoutInflater, parent, false)
        return VH(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        (holder as VH).bind(dataRaw[position])
    }
}