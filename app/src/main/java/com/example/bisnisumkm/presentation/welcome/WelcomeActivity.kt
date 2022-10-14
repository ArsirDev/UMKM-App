package com.example.bisnisumkm.presentation.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bisnisumkm.databinding.ActivityWelcomeBinding
import com.example.bisnisumkm.presentation.login.penjual.activity.PenjualActivity
import com.example.bisnisumkm.presentation.login.login.activity.LoginActivity
import com.example.bisnisumkm.presentation.register.activity.RegisterActivity
import com.example.bisnisumkm.util.setOnClickListenerWithDebounce

class WelcomeActivity : AppCompatActivity() {

    private var _binding: ActivityWelcomeBinding? = null

    private val binding get() = _binding as ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInstance()
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.btnProdusen.setOnClickListenerWithDebounce {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.btnPenjual.setOnClickListenerWithDebounce {
            startActivity(Intent(this, PenjualActivity::class.java))
        }
        binding.btnRegisterProdusen.setOnClickListenerWithDebounce {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun initInstance() {
        _binding = ActivityWelcomeBinding.inflate(layoutInflater)
    }
}