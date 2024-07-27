package com.example.ghtk_intern_week2.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ghtk_intern_week2.databinding.ItemServiceBinding

class ItemServicesAdapter : RecyclerView.Adapter<ItemServicesAdapter.VH>() {

    private val list = mutableListOf<ServiceItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(a: List<ServiceItem>){
        this.list.clear()
        this.list.addAll(a)
        notifyDataSetChanged()
    }
    inner class VH(val binding: ItemServiceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(a: ServiceItem) {
            binding.apply {
                imageView.setImageResource(a.image)
                textView.text = a.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater = LayoutInflater.from(parent.context)
        return VH(ItemServiceBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        // Un-clickable. Not handle any change when click these items
        (holder as ItemServicesAdapter.VH).bind(list[position])
    }
}

data class ServiceItem(
    val image: Int,
    val name: String
)