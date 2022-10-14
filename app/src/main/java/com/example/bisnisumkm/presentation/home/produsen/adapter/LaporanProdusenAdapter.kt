package com.example.bisnisumkm.presentation.home.produsen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bisnisumkm.data.remote.dto.DataGetLaporanItem
import com.example.bisnisumkm.databinding.LaporanItemLayoutBinding

class LaporanProdusenAdapter: RecyclerView.Adapter<LaporanProdusenViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<DataGetLaporanItem>(){
        override fun areItemsTheSame(
            oldItem: DataGetLaporanItem,
            newItem: DataGetLaporanItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: DataGetLaporanItem,
            newItem: DataGetLaporanItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaporanProdusenViewHolder {
        return LaporanProdusenViewHolder(LaporanItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: LaporanProdusenViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    companion object {
        fun Instance() = LaporanProdusenAdapter()
    }
}