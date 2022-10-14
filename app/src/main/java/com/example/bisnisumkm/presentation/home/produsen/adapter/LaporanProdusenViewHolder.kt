package com.example.bisnisumkm.presentation.home.produsen.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.bisnisumkm.data.remote.dto.DataGetLaporanItem
import com.example.bisnisumkm.databinding.LaporanItemLayoutBinding
import com.example.bisnisumkm.util.convertDate

class LaporanProdusenViewHolder(
    private val binding: LaporanItemLayoutBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(dataLaporanItemPenjulan: DataGetLaporanItem) {
        with(binding) {
            tvDate.text = dataLaporanItemPenjulan.updatedAt.convertDate()
            tvTaken.text = String.format("Pada %s telah di ambil", dataLaporanItemPenjulan.nameToko)
            tvProduct.text = String.format("Nama Produk %s", dataLaporanItemPenjulan.productName)
            tvQty.text = String.format("Jumlah Penitipan %s pcs", dataLaporanItemPenjulan.qty)
            tvSisa.text = String.format("Jumlah Sisa %s pcs", dataLaporanItemPenjulan.sisaProduct)
            tvLaku.text = String.format("Jumlah Laku %s pcs", dataLaporanItemPenjulan.lakuProduct)
            tvStatus.text = dataLaporanItemPenjulan.status
        }
    }
}