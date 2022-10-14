package com.example.bisnisumkm.presentation.login.penjual.activity

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.bisnisumkm.data.remote.dto.AllTokolResponse
import com.example.bisnisumkm.data.remote.dto.LoginPenjualResponse
import com.example.bisnisumkm.databinding.ActivityPenjualBinding
import com.example.bisnisumkm.presentation.home.penjual.activity.PenjualHomeActivity
import com.example.bisnisumkm.presentation.login.penjual.viewmodel.PenjualViewModel
import com.example.bisnisumkm.util.MESSAGE.STATUS_ERROR
import com.example.bisnisumkm.util.Result
import com.example.bisnisumkm.util.SessionManager
import com.example.bisnisumkm.util.removeView
import com.example.bisnisumkm.util.showView
import com.example.bisnisumkm.util.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PenjualActivity : AppCompatActivity() {

    private var _binding: ActivityPenjualBinding? = null

    private val binding get() = _binding as ActivityPenjualBinding

    private val viewModel: PenjualViewModel by viewModels()

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInstance()
        setContentView(binding.root)
        iniLaunch()
        initView()
    }

    private fun initView() {
        binding.btnLogin.setOnClickListener {
            val namaToko = binding.spNamaToko.selectedItem.toString()
            val password = binding.etPassword.text.toString().trim()
            viewModel.onValidation(namaToko, password)
        }
    }

    private fun iniLaunch() {
        observerAllTokoResponse?.let {
            viewModel.getAllToko().observe(this, it)
        }

        observeLoginResposen?.let {
            viewModel.getLogin().observe(this, it)
        }
    }

    private var observeLoginResposen: Observer<Result<LoginPenjualResponse>>? = Observer { result ->
        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    when(result) {
                        is Result.Loading -> {
                            binding.pbLoading.showView()
                        }
                        is Result.Success -> {
                            binding.pbLoading.removeView()
                            delay(1000)
                            result.data?.dataLoginPenjual?.let { dataLoginPenjual ->
                                val name = dataLoginPenjual.name
                                val store_name = dataLoginPenjual.namaToko
                                val email = dataLoginPenjual.email
                                val address = dataLoginPenjual.alamat
                                val status = dataLoginPenjual.status
                                val phone = dataLoginPenjual.numberPhone
                                val token = dataLoginPenjual.token

                                sessionManager.savePenjualLogin(
                                    name,
                                    store_name,
                                    email,
                                    address,
                                    status,
                                    phone,
                                    token
                                )
                                toHomePenjual()
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

    private fun toHomePenjual() {
        startActivity(Intent(this, PenjualHomeActivity::class.java))
        finishAffinity()
    }

    private var observerAllTokoResponse: Observer<Result<AllTokolResponse>>? = Observer { result ->
        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    when (result) {
                        is Result.Loading -> {
                        }
                        is Result.Success -> {
                            result.data?.data?.let { dataItem ->
                                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                                    this@PenjualActivity,
                                    R.layout.simple_spinner_dropdown_item,
                                    dataItem.map {
                                        it.namaToko
                                    }
                                )
                                binding.spNamaToko.adapter = adapter
                            } ?: run {
                                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                                    this@PenjualActivity,
                                    R.layout.simple_spinner_dropdown_item,
                                    listOf("Tidak ada toko yang terdaftar")
                                )
                                binding.spNamaToko.adapter = adapter
                            }
                        }
                        is Result.Error -> {
                        }
                    }
                }
            }
        }
    }

    private fun initInstance() {
        _binding = ActivityPenjualBinding.inflate(layoutInflater)
        sessionManager = SessionManager(this)
    }
}