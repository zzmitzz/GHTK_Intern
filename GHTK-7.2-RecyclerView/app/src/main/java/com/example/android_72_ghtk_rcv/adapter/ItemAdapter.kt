package com.example.android_72_ghtk_rcv.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_72_ghtk_rcv.Constant
import com.example.android_72_ghtk_rcv.R
import com.example.android_72_ghtk_rcv.model.OrderStatus

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemVH>() {
    private var data: MutableList<OrderStatus> = mutableListOf()

    fun setData(data: MutableList<OrderStatus>){
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when(data[position].status){
            false -> Constant.processingState
            true -> Constant.finishProcess
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when(viewType){
            Constant.processingState ->{
                val view = layoutInflater.inflate(R.layout.item_processing, parent, false)
                ItemVH(view)
            }

            Constant.finishProcess ->{
                val view = layoutInflater.inflate(R.layout.item_processed, parent, false)
                ItemVH(view)
            }

            else -> {
                val view = layoutInflater.inflate(R.layout.item_processing, parent, false)
                ItemVH(view)
            }
        }
    }

    override fun onBindViewHolder(holder: ItemVH, @SuppressLint("RecyclerView") position: Int) {
            val item = data[position]
            holder.textview.text = item.name
//            for (i in item.contact){
//                holder.linearLayout.addView(TextView(holder.itemView.context).apply {
//                    textSize = 10f
//                    text = i
//                })
//            }
//            for(i in item.tag){
//                holder.linearLayout2.addView(TextView(holder.itemView.context).apply {
//                    textSize = 10f
//                    text = i
//                })
//            }
    }

    override fun getItemCount(): Int = data.size

    inner class ItemVH(view: View): RecyclerView.ViewHolder(view){

        val textview = view.findViewById<TextView>(R.id.name)
        val linearLayout = view.findViewById<LinearLayout>(R.id.contact)
        val linearLayout2 = view.findViewById<LinearLayout>(R.id.tags)

    }
}