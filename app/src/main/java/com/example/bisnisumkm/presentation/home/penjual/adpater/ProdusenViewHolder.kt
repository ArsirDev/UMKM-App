package com.example.bisnisumkm.presentation.home.penjual.adpater

import androidx.recyclerview.widget.RecyclerView
import com.example.bisnisumkm.data.remote.dto.DataAllDetailProdusenRequestResponseItem
import com.example.bisnisumkm.databinding.ProdusenLayoutItemBinding

class ProdusenViewHolder(
    private val binding: ProdusenLayoutItemBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(dataAllDetailProdusenRequestResponseItem: DataAllDetailProdusenRequestResponseItem) {
        with(binding) {
            tvName.text = dataAllDetailProdusenRequestResponseItem.nameProdusen
            tvAlamat.text = dataAllDetailProdusenRequestResponseItem.alamatProdusen
            tvEmail.text = dataAllDetailProdusenRequestResponseItem.emailProdusen
        }
    }
}