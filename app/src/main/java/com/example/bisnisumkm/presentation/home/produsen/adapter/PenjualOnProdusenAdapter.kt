package com.example.bisnisumkm.presentation.home.produsen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bisnisumkm.data.remote.dto.DataSearchPenjualItem
import com.example.bisnisumkm.databinding.PenjualOnProdusenItemLayoutBinding
import com.example.bisnisumkm.util.setOnClickListenerWithDebounce

class PenjualOnProdusenAdapter : RecyclerView.Adapter<PenjualOnProdusenViewholder>() {

    private val differCallback = object : DiffUtil.ItemCallback<DataSearchPenjualItem>() {
        override fun areItemsTheSame(
            oldItem: DataSearchPenjualItem,
            newItem: DataSearchPenjualItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: DataSearchPenjualItem,
            newItem: DataSearchPenjualItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PenjualOnProdusenViewholder {
        return PenjualOnProdusenViewholder(PenjualOnProdusenItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PenjualOnProdusenViewholder, position: Int) {
        holder.apply {
            bind(differ.currentList[position].also { item ->
                itemView.setOnClickListenerWithDebounce {
                    onItemClickListener?.let { id ->
                        id(item.id)
                    }
                }
            })
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        fun instance() = PenjualOnProdusenAdapter()
    }
}