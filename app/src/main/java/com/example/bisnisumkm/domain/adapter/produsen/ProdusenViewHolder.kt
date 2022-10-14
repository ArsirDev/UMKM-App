package com.example.bisnisumkm.domain.adapter.produsen

import androidx.recyclerview.widget.RecyclerView
import com.example.bisnisumkm.data.remote.dto.DataSearchProdusenItem
import com.example.bisnisumkm.databinding.ProdusenLayoutItemBinding

class ProdusenViewHolder(
    val binding: ProdusenLayoutItemBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(produsenItem: DataSearchProdusenItem) {
        with(binding) {
            tvName.text = produsenItem.name
            tvEmail.text = produsenItem.email
            tvAlamat.text = produsenItem.alamat
        }
    }
}