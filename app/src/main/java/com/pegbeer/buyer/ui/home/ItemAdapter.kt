package com.pegbeer.buyer.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pegbeer.buyer.R
import com.pegbeer.buyer.databinding.ItemBinding
import com.pegbeer.domain.model.Item

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Item>() {

        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(data:List<Item>?){
        differ.submitList(data)
    }

    inner class ViewHolder(private val binding:ItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item:Item){
            with(binding){
                tipoTv.text = item.tipoCompra
                origenTv.text = String.format("Origen: %s",item.origen)
                delimitadorTv.text = String.format("Delimitador: %s", item.delimitador)
                signoTv.text = item.signo
                when(item.signo){
                    "+" ->{
                        signoTv.setTextColor(root.context.getColor(R.color.green))
                    }
                    "-" ->{
                        signoTv.setTextColor(root.context.getColor(R.color.red))
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }


}