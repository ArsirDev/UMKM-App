package com.example.bisnisumkm.domain.adapter.produsen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bisnisumkm.data.remote.dto.DataSearchProdusenItem
import com.example.bisnisumkm.databinding.ProdusenLayoutItemBinding

class ProdusenAdapter: RecyclerView.Adapter<ProdusenViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<DataSearchProdusenItem>() {
        override fun areItemsTheSame(
            oldItem: DataSearchProdusenItem,
            newItem: DataSearchProdusenItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: DataSearchProdusenItem,
            newItem: DataSearchProdusenItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdusenViewHolder {
        return ProdusenViewHolder(ProdusenLayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ProdusenViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    companion object {
        fun instance() = ProdusenAdapter()
    }
}