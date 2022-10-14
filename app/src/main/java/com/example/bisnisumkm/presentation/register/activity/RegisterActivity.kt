package com.example.bisnisumkm.presentation.register.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.bisnisumkm.data.remote.dto.RegisterResponse
import com.example.bisnisumkm.databinding.ActivityRegisterBinding
import com.example.bisnisumkm.presentation.register.viewmodel.RegisterViewModel
import com.example.bisnisumkm.presentation.welcome.WelcomeActivity
import com.example.bisnisumkm.util.MESSAGE.STATUS_ERROR
import com.example.bisnisumkm.util.MESSAGE.STATUS_SUCCESS
import com.example.bisnisumkm.util.Result
import com.example.bisnisumkm.util.removeView
import com.example.bisnisumkm.util.setOnClickListenerWithDebounce
import com.example.bisnisumkm.util.showView
import com.example.bisnisumkm.util.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private var _binding: ActivityRegisterBinding? = null

    private val binding get() = _binding as ActivityRegisterBinding

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInstance()
        setContentView(binding.root)
        initLaunch()
        initView()
    }

    private fun initLaunch() {
        observerRegisterResult?.let {
            viewModel.getRegister().observe(this, it)
        }

        lifecycleScope.launch {
            viewModel.uiEvent.collectLatest { message ->
                snackbar(binding.root, message, STATUS_ERROR)
            }
        }
    }

    private fun initView() {

        binding.btnRegister.setOnClickListenerWithDebounce {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val alamat = binding.etAlamat.text.toString().trim()
            val number_phone = binding.etNumberPhone.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val password_confirmation = binding.etConfirmPassword.text.toString().trim()
            val checkRbStatus = binding.rgStatus.checkedRadioButtonId
            onValidation(
                name,
                email,
                alamat,
                number_phone,
                password,
                password_confirmation,
                checkRbStatus
            )
        }
    }

    private fun onValidation(
        name: String,
        email: String,
        alamat: String,
        number_phone: String,
        password: String,
        password_confirmation: String,
        checkRbStatus: Int
    ) {
        if (name.isEmpty() || email.isEmpty() || alamat.isEmpty() || checkRbStatus <= 0 || number_phone.isEmpty() || password.isEmpty() || password_confirmation.isEmpty()) {
            snackbar(binding.root, "Data Tidak boleh Kosong", STATUS_ERROR)
            return
        }

        val emailsPattern = Patterns.EMAIL_ADDRESS
        if (!emailsPattern.matcher(email).matches()) {
            snackbar(binding.root, "Email tidak valid", STATUS_ERROR)
            return
        }

        if (password != password_confirmation) {
            snackbar(binding.root, "Password tidak sama", STATUS_ERROR)
            return
        }

        val phonePatterns = Patterns.PHONE
        if (!phonePatterns.matcher(number_phone).matches()) {
            snackbar(binding.root, "Phone tidak valid", STATUS_ERROR)
            return
        }

        val rb: RadioButton = findViewById(checkRbStatus)
        viewModel.setRegister(
            name,
            email,
            alamat,
            rb.text.toString().trim(),
            number_phone,
            password,
            password_confirmation
        )
    }

    private var observerRegisterResult: Observer<Result<RegisterResponse>>? = Observer { result ->
        lifecycleScope.launchWhenCreated {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    when (result) {
                        is Result.Loading -> {
                            binding.pbLoading.showView()
                        }
                        is Result.Success -> {
                            binding.pbLoading.removeView()
                            Log.e(this@RegisterActivity.toString(), "Success")
                            result.data?.message?.let { message ->
                                snackbar(binding.root, message, STATUS_SUCCESS)
                                delay(1000)
                                toStart()
                            }
                        }
                        is Result.Error -> {
                            binding.pbLoading.removeView()
                            result.data?.message?.let { message ->
                                snackbar(binding.root, message, STATUS_ERROR)
                            } ?: result.message?.let { message ->
                                snackbar(binding.root, message, STATUS_ERROR)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun toStart() {
        startActivity(Intent(this, WelcomeActivity::class.java))
        finishAffinity()
    }

    private fun initInstance() {
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}