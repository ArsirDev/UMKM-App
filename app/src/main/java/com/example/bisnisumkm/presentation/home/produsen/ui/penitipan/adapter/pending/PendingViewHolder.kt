package com.example.bisnisumkm.presentation.home.produsen.ui.penitipan.adapter.pending

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.bisnisumkm.R
import com.example.bisnisumkm.data.remote.dto.GetAllStatusItem
import com.example.bisnisumkm.databinding.PenjualOnProdusenItemLayoutBinding
import com.example.bisnisumkm.util.loadImage
import com.example.bisnisumkm.util.showView

class PendingViewHolder(
    val binding: PenjualOnProdusenItemLayoutBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(getAllStatusItem: GetAllStatusItem) {
        with(binding) {
            tvStatus.showView()
            ivImage.loadImage(getAllStatusItem.imagePenjual)
            tvStore.text = getAllStatusItem.nameToko
            tvName.text = getAllStatusItem.namePenjual
            tvAlamat.text = getAllStatusItem.alamatPenjual
            when(getAllStatusItem.statusPenitipan) {
                "MENUNGGU" -> tvStatus.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
                "DITERIMA" -> tvStatus.setTextColor(ContextCompat.getColor(binding.root.context, R.color.green_color))
            }
            tvStatus.text = getAllStatusItem.statusPenitipan
        }
    }
}