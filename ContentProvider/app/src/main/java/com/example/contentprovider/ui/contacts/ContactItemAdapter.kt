package com.example.contentprovider.ui.contacts

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.contentprovider.data.model.ContactSchema
import com.example.contentprovider.databinding.ContactItemRcvBinding

class ContactItemAdapter : RecyclerView.Adapter<ContactItemAdapter.ViewHolder>() {
    private val data: MutableList<ContactSchema> = mutableListOf()
    lateinit var invoke: (ContactSchema) -> Unit

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<ContactSchema>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val binding: ContactItemRcvBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContactSchema) {
            binding.textViewName.text = item.name
            binding.imageViewAvatar.text = item.name[0].toString()
            binding.imageViewAvatar.background =
                listOf(Color.RED, Color.BLUE, Color.GREEN).random().toDrawable()
            binding.textPhone.text = "CONTACT_ID: ${item.id}"
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ContactItemRcvBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val item = data[position]
        holder.itemView.setOnClickListener {
            invoke(item)
        }
        holder.bind(item)
    }
}
