package com.example.bluetoothmessage.ui.chat.adapter

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.health.connect.datatypes.Device
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.bluetoothmessage.databinding.ItemConnectionBinding

class DeviceItemAdapter : RecyclerView.Adapter<DeviceItemAdapter.VH>() {

    var data: MutableList<BluetoothDevice> = mutableListOf()

    var invoke: (BluetoothDevice) -> Unit = {}

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: MutableSet<BluetoothDevice>){
        this.data = data.toMutableList()
        notifyDataSetChanged()
    }

    inner class VH(val binding: ItemConnectionBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("MissingPermission")
        fun bind(device: BluetoothDevice){
            binding.deviceName.text = device.name
            binding.macAddress.text = device.address
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemConnectionBinding.inflate(layoutInflater, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        val device = data[position]
        holder.bind(device)
        holder.itemView.setOnClickListener{
            invoke.invoke(data[position])
        }
    }

    override fun getItemCount(): Int = data.size
}