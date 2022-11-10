package com.example.bisnisumkm.presentation.home.penjual.adpater

import androidx.recyclerview.widget.RecyclerView
import com.example.bisnisumkm.data.remote.dto.DataGetLaporanItem
import com.example.bisnisumkm.databinding.LaporanItemLayoutBinding
import com.example.bisnisumkm.util.convertDate

class PenjualLaporanViewHolder(
    val binding: LaporanItemLayoutBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(dataLaporanItemPenjulan: DataGetLaporanItem) {
        with(binding) {
            tvDate.text = dataLaporanItemPenjulan.updatedAt.convertDate()
            tvTaken.text = String.format("Telah Diambil oleh %s", dataLaporanItemPenjulan.produsenName)
            tvProduct.text = String.format("Nama Produk %s", dataLaporanItemPenjulan.productName)
            tvQty.text = String.format("Jumlah Penitipan %s pcs", dataLaporanItemPenjulan.qty)
            tvSisa.text = String.format("Jumlah Sisa %s pcs", dataLaporanItemPenjulan.sisaProduct)
            tvLaku.text = String.format("Jumlah Laku %s pcs", dataLaporanItemPenjulan.lakuProduct)
            tvKeuntungan.text = String.format("Uang yang harus diberikan %s", dataLaporanItemPenjulan.harga.toInt() * dataLaporanItemPenjulan.lakuProduct.toInt())
            tvTanggalPengambilan.text = String.format("Tanggal Pengambilan %s",dataLaporanItemPenjulan.tanggalPengambilan)
            tvTanggalPenitipan.text = String.format("Tanggal Penitipan %s",dataLaporanItemPenjulan.tanggalNitip)
            tvStatus.text = dataLaporanItemPenjulan.status
        }
    }
}