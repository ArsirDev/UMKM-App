package com.example.bisnisumkm.presentation.home.produsen.detail

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.format.Formatter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.bisnisumkm.data.remote.dto.DataDetailPenjual
import com.example.bisnisumkm.data.remote.dto.DetailPenjualResponse
import com.example.bisnisumkm.data.remote.dto.SetRequestProdusenResponse
import com.example.bisnisumkm.databinding.ActivityDetailPenjualOnProdusenBinding
import com.example.bisnisumkm.presentation.home.produsen.activity.HomeProdusenActivity
import com.example.bisnisumkm.util.*
import com.example.bisnisumkm.util.MESSAGE.STATUS_ERROR
import com.example.bisnisumkm.util.MESSAGE.STATUS_SUCCESS
import com.example.bisnisumkm.util.SESSION.ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class DetailPenjualOnProdusenActivity : AppCompatActivity() {

    private var _binding: ActivityDetailPenjualOnProdusenBinding? = null

    private val binding get() = _binding as ActivityDetailPenjualOnProdusenBinding

    private val viewModel: DetailProdusenRequestViewModel by viewModels()

    private var newImage: File? = null

    private var permissionRequest: ActivityResultLauncher<Array<String>>? = null

    private lateinit var imageLauncher: ActivityResultLauncher<String?>

    private var count: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInstance()
        setContentView(binding.root)
        initIntent()
        initLaunch()
        initCheckPermission()
        initImageActivityResultLauncher()
        initView()
    }

    private fun initView() {
        binding.cvProductRequest.setOnClickListenerWithDebounce {
            try {
                imageLauncher.launch("image/*")
            } catch (e: Exception) {
                initCheckPermission()
            }
        }

        binding.btnMinus.setOnClickListener {
            if (count <= 0) {
                snackbar(binding.root, "Request barang tidak boleh minus", STATUS_ERROR)
                return@setOnClickListener
            }
            count--
            binding.tvQty.text = count.toString()
        }

        binding.btnPlus.setOnClickListener {
            count++
            binding.tvQty.text = count.toString()
        }

        binding.ivBack.setOnClickListenerWithDebounce {
            onBack()
        }

        binding.ivPengambilan.setOnClickListenerWithDebounce {
            val calendar = Calendar.getInstance()
            val years = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val day = calendar[Calendar.DAY_OF_MONTH]
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->
                    binding.tvPengambilan.text = dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
                },
                years, month, day
            )
            datePickerDialog.show()
        }
    }

    private fun initImageActivityResultLauncher() {
        permissionRequest =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                it?.entries?.forEach { permission ->
                }
            }

        imageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            result?.let { uri ->
                val file = getFileFromContentUri(uri)
                newImage = file
                val fileFormatted = Formatter.formatShortFileSize(this, file.length())
                val fileSizeNum = fileFormatted.replace(" ", "").dropLast(2).toDouble()
                val fileSizeUnit = fileFormatted.replace(" ", "").takeLast(2)
                if (fileSizeNum > 2.0 && fileSizeUnit.contains("mb", true)) {
                    snackbar(binding.root, "Gambar Melebihi 2Mb", STATUS_ERROR)
                    newImage = null
                } else {
                    binding.ivProductRequest.loadImage(uri.toString(), DiskCacheStrategy.RESOURCE)
                }
            }
        }
    }

    private fun initCheckPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissions =
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET)
            permissionRequest?.launch(permissions)
        }
    }


    private fun initIntent() {
        intent.extras?.getInt(ID)?.let { id ->
            viewModel.onValidation(id)
        }
    }

    private fun initLaunch() {
        observerDetailPenjualResponse?.let {
            viewModel.getDetail().observe(this, it)
        }

        observerSetRequestProdusen?.let {
            viewModel.getRequestProdusen().observe(this, it)
        }
    }

    private var observerDetailPenjualResponse: Observer<Result<DetailPenjualResponse>>? =
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
                                result.data?.dataDetailPenjual?.let { data ->
                                    initDataView(data)
                                }
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

    private var observerSetRequestProdusen: Observer<Result<SetRequestProdusenResponse>>? =
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
                                result.message?.let { message ->
                                    snackbar(binding.root, message, STATUS_SUCCESS)
                                } ?: result.data?.message?.let { message ->
                                    snackbar(binding.root, message, STATUS_SUCCESS)
                                }
                                delay(500)
                                onBack()
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

    private fun onBack() {
        startActivity(Intent(this, HomeProdusenActivity::class.java))
        finishAffinity()
    }

    private fun initDataView(data: DataDetailPenjual) {
        with(binding) {
            ivStore.loadImage(data.image)
            tvStoreName.text = data.nameToko
            tvSellerName.text = data.namePenjual
            tvAlamatSeller.text = data.alamatPenjual
            tvSellerPhone.text = data.numberPhonePenjual
            tvProdusenPerson.text = data.nameProdusen
            tvProdusenAlamat.text = data.alamatProdusen
            tvProdusenPhone.text = data.numberPhoneProdusen
        }

        with(binding) {
            binding.btnRequest.setOnClickListenerWithDebounce {
                val productName = etProductName.text.toString().trim()
                val qty = tvQty.text.toString()
                val price = etProdusenPrice.text.toString().trim()
                val tanggalPengambilan = tvPengambilan.text.toString()
                viewModel.onRequestValidation(
                    data.idPenjual.toString(),
                    data.idProdusen.toString(),
                    data.namePenjual,
                    data.nameProdusen,
                    data.nameToko,
                    productName,
                    data.emailProdusen,
                    data.alamatProdusen,
                    data.alamatPenjual,
                    data.numberPhoneProdusen,
                    data.numberPhonePenjual,
                    tanggalPengambilan,
                    qty,
                    price,
                    newImage,
                    data.image,
                    "MENUNGGU"
                )
            }
        }
    }

    private fun initInstance() {
        _binding = ActivityDetailPenjualOnProdusenBinding.inflate(layoutInflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}