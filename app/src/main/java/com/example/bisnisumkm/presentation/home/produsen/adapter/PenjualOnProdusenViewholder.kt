package com.example.bisnisumkm.presentation.home.produsen.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.bisnisumkm.data.remote.dto.DataSearchPenjualItem
import com.example.bisnisumkm.databinding.PenjualOnProdusenItemLayoutBinding
import com.example.bisnisumkm.util.loadImage

class PenjualOnProdusenViewholder(
    private val binding: PenjualOnProdusenItemLayoutBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: DataSearchPenjualItem) {
        with(binding) {
            ivImage.loadImage(item.image)
            tvStore.text = item.namaToko
            tvAlamat.text = item.alamat
            tvName.text = item.name
        }
    }
}