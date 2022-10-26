package com.example.bisnisumkm.presentation.login.login.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.bisnisumkm.data.remote.dto.DataLogin
import com.example.bisnisumkm.data.remote.dto.LoginResponse
import com.example.bisnisumkm.databinding.ActivityLoginBinding
import com.example.bisnisumkm.presentation.home.admin.activity.AdminHomeActivity
import com.example.bisnisumkm.presentation.home.produsen.activity.HomeProdusenActivity
import com.example.bisnisumkm.presentation.login.login.viewmodel.LoginViewModel
import com.example.bisnisumkm.util.MESSAGE.STATUS_ERROR
import com.example.bisnisumkm.util.P_E_M
import com.example.bisnisumkm.util.Result
import com.example.bisnisumkm.util.SESSION.ADMIN_LOGIN
import com.example.bisnisumkm.util.SESSION.PRODUSEN_LOGIN
import com.example.bisnisumkm.util.SessionManager
import com.example.bisnisumkm.util.SimpleName
import com.example.bisnisumkm.util.removeView
import com.example.bisnisumkm.util.showView
import com.example.bisnisumkm.util.snackbar
import com.example.bisnisumkm.util.toJson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null

    private val binding get() = _binding as ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInstance()
        setContentView(binding.root)
        initLaunch()
        initView()
    }

    private fun initView() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            viewModel.onValidation(
                email,
                password
            )
        }
    }

    private fun initLaunch() {
        observerLoginResponse?.let {
            viewModel.getLogin().observe(this, it)
        }
    }

    private var observerLoginResponse: Observer<Result<LoginResponse>>? = Observer { result ->
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
                            result.data?.data?.let { response ->

                                val name = response.name
                                val email = response.email
                                val address = response.alamat
                                val status = response.status
                                val phone = response.numberPhone
                                val token = response.token
                                sessionManager.createAuthSession(
                                    name,
                                    email,
                                    address,
                                    status,
                                    phone,
                                    token
                                )
                                if (response.status == "Admin") {
                                    startActivity(Intent(this@LoginActivity, AdminHomeActivity::class.java).apply {
                                        putExtra(ADMIN_LOGIN, response.toJson(DataLogin::class.java))
                                    })
                                    finishAffinity()
                                } else {
                                    startActivity(Intent(this@LoginActivity, HomeProdusenActivity::class.java).apply {
                                        putExtra(PRODUSEN_LOGIN, response.toJson(DataLogin::class.java))
                                    })
                                    finishAffinity()
                                }
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


    private fun initInstance() {
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        sessionManager = SessionManager(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}