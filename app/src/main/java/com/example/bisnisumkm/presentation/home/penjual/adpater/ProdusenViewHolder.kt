package com.example.bisnisumkm.presentation.home.penjual.adpater

import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.bisnisumkm.R
import com.example.bisnisumkm.data.remote.dto.DataAllDetailProdusenRequestResponseItem
import com.example.bisnisumkm.databinding.ProdusenLayoutItemBinding
import com.example.bisnisumkm.util.removeView
import com.example.bisnisumkm.util.showView

class ProdusenViewHolder(
    val binding: ProdusenLayoutItemBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(dataAllDetailProdusenRequestResponseItem: DataAllDetailProdusenRequestResponseItem) {
        with(binding) {
            tvName.text = dataAllDetailProdusenRequestResponseItem.nameProdusen
            tvAlamat.text = dataAllDetailProdusenRequestResponseItem.alamatProdusen
            tvEmail.text = dataAllDetailProdusenRequestResponseItem.emailProdusen
            when(dataAllDetailProdusenRequestResponseItem.statusPenitipan) {
                "MENUNGGU" -> tvStatus.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
                "DITOLAK" -> {
                    btnDelete.showView()
                    tvStatus.setTextColor(ContextCompat.getColor(binding.root.context, R.color.red_color))
                }
                "SELESAI" -> {
                    btnDelete.showView()
                    tvStatus.setTextColor(ContextCompat.getColor(binding.root.context, R.color.red_color))
                }
                else -> tvStatus.setTextColor(ContextCompat.getColor(binding.root.context, R.color.green_color))
            }
            tvStatus.text = dataAllDetailProdusenRequestResponseItem.statusPenitipan
        }
    }
}