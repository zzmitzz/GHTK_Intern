package com.example.kotlinflow.ui.liststaff

import android.annotation.SuppressLint
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinflow.databinding.StaffItemBinding
import com.example.kotlinflow.model.Staff

class StaffAdapter : RecyclerView.Adapter<StaffAdapter.VH>() {
    private var data: MutableList<Staff> = mutableListOf()
    lateinit var invoke: (Staff) -> Unit

    fun insertData(list: MutableList<Staff>){
        data.addAll(list)
        notifyItemInserted(data.size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: MutableList<Staff>){
        data = list
        notifyDataSetChanged()
    }
    inner class VH(private val binding: StaffItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(staff: Staff){
            binding.tvName.text = staff.name
            binding.tvBirth.text = staff.year
            binding.tvBirthplace.text = staff.birthPlace
            if(::invoke.isInitialized){
                binding.ivDelete.setOnClickListener {
                    invoke.invoke(staff)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = StaffItemBinding.inflate(layoutInflater,parent,false)
        return VH(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: VH, position: Int){
        holder.bind(staff = data[position])
    }
}