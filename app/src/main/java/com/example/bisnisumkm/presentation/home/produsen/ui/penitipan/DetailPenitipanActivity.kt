package com.example.bisnisumkm.presentation.home.produsen.ui.penitipan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.bisnisumkm.R
import com.example.bisnisumkm.data.remote.dto.GetAllStatusItem
import com.example.bisnisumkm.databinding.ActivityDetailPenitipanBinding
import com.example.bisnisumkm.util.SESSION.PASSDATA
import com.example.bisnisumkm.util.convertDate
import com.example.bisnisumkm.util.fromJson
import com.example.bisnisumkm.util.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPenitipanActivity : AppCompatActivity() {

    private var _binding: ActivityDetailPenitipanBinding? = null

    private val binding get() = _binding as ActivityDetailPenitipanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInstance()
        setContentView(binding.root)
        initIntent()
    }

    private fun initIntent() {
        intent.extras?.getString(PASSDATA)?.fromJson<GetAllStatusItem>()?.let { data ->
            with(binding) {
                status.text = if (data.statusPenitipan == "DITERIMA") {
                    status.setTextColor(ContextCompat.getColor(this@DetailPenitipanActivity ,R.color.green_color))
                    "DITERIMA"
                } else {
                    status.setTextColor(ContextCompat.getColor(this@DetailPenitipanActivity ,R.color.white))
                    "MENUGGU"
                }
                ivImage.loadImage(data.imagePenjual)
                tvProductName.text = data.productName
                tvToko.text = data.nameToko
                tvAddressToko.text = data.alamatPenjual
                tvProdusen.text = data.nameProdusen
                tvQty.text = data.qty
                tvPrize.text = data.harga
                tvPhone.text = data.numberPhonePenjual
                tvPenitipan.text = data.createdAt.convertDate()
                tvPengambilan.text = data.tanggalPengambilan
            }
        }
    }

    private fun initInstance() {
        _binding = ActivityDetailPenitipanBinding.inflate(layoutInflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}