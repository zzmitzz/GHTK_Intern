package com.example.bluetoothmessage.ui.chat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.bluetoothmessage.databinding.MessageReceiveItemBinding
import com.example.bluetoothmessage.databinding.MessageSendItemBinding
import com.example.bluetoothmessage.ui.chat.model.Message

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.VH>() {
    private val data: MutableList<Message> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Message>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun insertItem(newMessage: Message) {
        this.data.add(newMessage)
        notifyItemInserted(data.size - 1)
    }

    inner class VH(
        private val binding: ViewBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(m: Message) {
            if (binding is MessageSendItemBinding) {
                (binding as MessageSendItemBinding).textMessage.text = m.text
            } else {
                (binding as MessageReceiveItemBinding).textMessage.text = m.text
            }
        }
    }

    override fun getItemViewType(position: Int): Int = if (data[position].isMine) 1 else 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MessageAdapter.VH {
        val layoutInflater = LayoutInflater.from(parent.context)
        if (viewType == 0) {
            return VH(MessageReceiveItemBinding.inflate(layoutInflater, parent, false))
        } else {
            return VH(MessageSendItemBinding.inflate(layoutInflater, parent, false))
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(
        holder: MessageAdapter.VH,
        position: Int,
    ) {
        holder.bind(data[position])
    }
}
