package com.example.bisnisumkm.presentation.home.penjual.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.bisnisumkm.data.remote.dto.DataSpesificDetailProdusenRequestResponse
import com.example.bisnisumkm.data.remote.dto.GeneralResponse
import com.example.bisnisumkm.data.remote.dto.GetSpesificDetailProdusenRequestResponse
import com.example.bisnisumkm.data.remote.dto.SetLaporanResponse
import com.example.bisnisumkm.databinding.ActivityDetailProdusenPenjualBinding
import com.example.bisnisumkm.presentation.home.penjual.activity.PenjualHomeActivity
import com.example.bisnisumkm.presentation.home.penjual.ui.produsen.ProdusenPenjualViewModel
import com.example.bisnisumkm.util.*
import com.example.bisnisumkm.util.MESSAGE.STATUS_ERROR
import com.example.bisnisumkm.util.MESSAGE.STATUS_SUCCESS
import com.example.bisnisumkm.util.SESSION.ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailProdusenPenjualActivity : AppCompatActivity() {

    private var _binding: ActivityDetailProdusenPenjualBinding? = null

    private val binding get() = _binding as ActivityDetailProdusenPenjualBinding

    private val viewModel: ProdusenPenjualViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInstance()
        setContentView(binding.root)
        iniLaunch()
        initIntent()
    }

    private fun initIntent() {
        intent.extras?.getInt(ID)?.let { id ->
            viewModel.setSpecificDetailProdusen(id)
        }
    }

    private fun iniLaunch() {
        observerGetSpesificDetailRequest?.let {
            viewModel.getSpecifictDetailProdusen().observe(this, it)
        }

        observerUpdateStatus?.let {
            viewModel.getUpdatestatus().observe(this, it)
        }

        observerSetLaporan?.let {
            viewModel.getLaporan().observe(this, it)
        }
    }

    private var observerGetSpesificDetailRequest: Observer<Result<GetSpesificDetailProdusenRequestResponse>>? =
        Observer { result ->
            lifecycleScope.launchWhenStarted {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        when (result) {
                            is Result.Loading -> {
                                binding.pbLoading.showView()
                            }
                            is Result.Success -> {
                                binding.pbLoading.removeView()
                                result.data?.dataSpesificDetailProdusenRequestResponse?.let { item ->
                                    initView(item)
                                }
                            }
                            is Result.Error -> {
                                binding.pbLoading.removeView()
                            }
                        }
                    }
                }
            }
        }

    private var observerUpdateStatus: Observer<Result<GeneralResponse>>? = Observer { result ->
        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    when(result) {
                        is Result.Loading -> {
                            binding.pbLoading.showView()
                        }
                        is Result.Success -> {
                            binding.pbLoading.removeView()
                            result.message?.let { message ->
                                snackbar(binding.root, message, STATUS_SUCCESS)
                            } ?: result.data?.message?.let { message ->
                                snackbar(binding.root, message, STATUS_SUCCESS)
                            }
                            delay(1000)
                            direction()
                        }
                        is Result.Error -> {
                            binding.pbLoading.removeView()
                            result.message?.let { message ->
                                snackbar(binding.root, message, STATUS_ERROR)
                            } ?: result.data?.message?.let { message ->
                                snackbar(binding.root, message, STATUS_ERROR)
                            }
                        }
                    }
                }
            }
        }
    }

    private var observerSetLaporan: Observer<Result<SetLaporanResponse>>? = Observer { result ->
        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    when(result) {
                        is Result.Loading ->{
                            binding.pbLoading.showView()

                        }
                        is Result.Success ->{
                            binding.pbLoading.removeView()
                            result.message?.let { message ->
                                snackbar(binding.root, message, STATUS_SUCCESS)
                            } ?: result.data?.message?.let { message ->
                                snackbar(binding.root, message, STATUS_SUCCESS)
                            }
                            delay(1000)
                            direction()
                        }
                        is Result.Error ->{
                            binding.pbLoading.removeView()
                            result.message?.let { message ->
                                snackbar(binding.root, message, STATUS_SUCCESS)
                            } ?: result.data?.message?.let { message ->
                                snackbar(binding.root, message, STATUS_SUCCESS)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun direction() {
        startActivity(Intent(this, PenjualHomeActivity::class.java))
        finishAffinity()
    }

    private fun initView(item: DataSpesificDetailProdusenRequestResponse) {
        with(binding) {
            ivStore.loadImage(item.imageProdusen)
            tvProductName.text = item.productName
            tvProdusenName.text = item.nameProdusen
            tvAlamatProdusen.text = item.alamatProdusen
            tvProdusenPhone.text = item.numberPhoneProdusen
            tvProdusenQty.text = item.qty
            if (item.statusPenitipan == "DITERIMA") {
                lyAction.removeView()
                lyInput.showView()
            } else {
                lyAction.showView()
                lyInput.removeView()
            }

            ivBack.setOnClickListenerWithDebounce {
                startActivity(Intent(this@DetailProdusenPenjualActivity, PenjualHomeActivity::class.java))
                finishAffinity()
            }

            btnApprove.setOnClickListenerWithDebounce {
                viewModel.setUpdateStatus(
                    item.id,
                    status_penitipan = "DITERIMA"
                )
            }

            btnDecline.setOnClickListenerWithDebounce {
                lifecycleScope.launch {
                    viewModel.setUpdateStatus(
                        item.id,
                        status_penitipan = "DITOLAK"
                    )
                    delay(1000)
                    direction()
                }
            }

            btnSelesai.setOnClickListenerWithDebounce {
                val sisaBarang = etSisa.text.toString().trim()
                val barangLaku = etLaku.text.toString().trim()
                val barangRusak = etRusak.text.toString().trim()
                val barangExpired = etExpired.text.toString().trim()
                val keuntunganProdusen = (item.harga.toInt() *  barangLaku.toInt())
                if (sisaBarang.isEmpty() || barangLaku.isEmpty()) {
                    snackbar(binding.root, "Field tidak boleh kosong", STATUS_ERROR)
                    return@setOnClickListenerWithDebounce
                }
                if (sisaBarang.toInt() > item.qty.toInt() || barangLaku.toInt() > item.qty.toInt()) {
                    snackbar(binding.root, "Sisa Barang atau Barang Laku tidak bisa melebihi stok barang", STATUS_ERROR)
                    return@setOnClickListenerWithDebounce
                }
                viewModel.onValidationLaporan(
                    produsen_name = item.nameProdusen,
                    penjual_name =  item.namePenjual,
                    product_name = item.productName,
                    name_toko = item.nameToko,
                    qty = item.qty,
                    harga = item.harga,
                    sisa_product = sisaBarang,
                    laku_product = barangLaku,
                    keuntungan_produsen = keuntunganProdusen.toString(),
                    barang_rusak = barangRusak,
                    expired = barangExpired,
                    tanggal_nitip = item.createdAt.convertDate(),
                    tanggal_pengambilan = item.tanggalPengambilan,
                    status = "SELESAI"
                )

                viewModel.setUpdateStatus(
                    item.id,
                    status_penitipan = "SELESAI"
                )
            }
        }
    }

    private fun initInstance() {
        _binding = ActivityDetailProdusenPenjualBinding.inflate(layoutInflater)
    }
}