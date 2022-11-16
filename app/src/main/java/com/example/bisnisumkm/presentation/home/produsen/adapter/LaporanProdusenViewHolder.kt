package com.example.bisnisumkm.presentation.home.produsen.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.bisnisumkm.data.remote.dto.DataGetLaporanItem
import com.example.bisnisumkm.databinding.LaporanItemLayoutBinding
import com.example.bisnisumkm.util.convertDate

class LaporanProdusenViewHolder(
    val binding: LaporanItemLayoutBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(dataLaporanItemPenjulan: DataGetLaporanItem) {
        with(binding) {
            tvDate.text = dataLaporanItemPenjulan.updatedAt.convertDate()
            tvTaken.text = String.format("Pada %s telah di ambil", dataLaporanItemPenjulan.nameToko)
            tvProduct.text = String.format("Nama Produk %s", dataLaporanItemPenjulan.productName)
            tvQty.text = String.format("Jumlah Penitipan %s pcs", dataLaporanItemPenjulan.qty)
            tvSisa.text = String.format("Jumlah Sisa %s pcs", dataLaporanItemPenjulan.sisaProduct)
            tvRusak.text = String.format("Jumlah Rusak %s pcs", dataLaporanItemPenjulan.barangRusak)
            tvExpired.text = String.format("Jumlah Expired %s pcs", dataLaporanItemPenjulan.expired)
            tvLaku.text = String.format("Jumlah Laku %s pcs", dataLaporanItemPenjulan.lakuProduct)
            tvKeuntungan.text = String.format("Keuntungan: %s", dataLaporanItemPenjulan.harga.toInt() * dataLaporanItemPenjulan.lakuProduct.toInt())
            tvTanggalPenitipan.text = String.format("Tanggal Penitipan %s",dataLaporanItemPenjulan.tanggalNitip)
            tvTanggalPengambilan.text = String.format("Tanggal Pengambilan %s",dataLaporanItemPenjulan.tanggalPengambilan)
            tvStatus.text = dataLaporanItemPenjulan.status
        }
    }
}