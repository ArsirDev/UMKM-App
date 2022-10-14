package com.example.bisnisumkm.presentation.home.penjual.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bisnisumkm.data.remote.dto.DataAllDetailProdusenRequestResponseItem
import com.example.bisnisumkm.databinding.ProdusenLayoutItemBinding
import com.example.bisnisumkm.util.setOnClickListenerWithDebounce

class ProdusenAdapter: RecyclerView.Adapter<ProdusenViewHolder>() {

    private val differCallback = object: DiffUtil.ItemCallback<DataAllDetailProdusenRequestResponseItem>() {
        override fun areItemsTheSame(
            oldItem: DataAllDetailProdusenRequestResponseItem,
            newItem: DataAllDetailProdusenRequestResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: DataAllDetailProdusenRequestResponseItem,
            newItem: DataAllDetailProdusenRequestResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdusenViewHolder {
        return ProdusenViewHolder(ProdusenLayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ProdusenViewHolder, position: Int) {
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
        fun instance() = ProdusenAdapter()
    }
}