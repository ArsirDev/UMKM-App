package com.example.bisnisumkm.presentation.home.penjual.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.bisnisumkm.R
import com.example.bisnisumkm.databinding.ActivityPenjualHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PenjualHomeActivity : AppCompatActivity() {

    private var _binding: ActivityPenjualHomeBinding? = null

    private val binding get() = _binding as ActivityPenjualHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInstance()
        setContentView(binding.root)
        initNavigation()
    }

    private fun initNavigation() {
        val navController = findNavController(R.id.nav_host_fragment_activity_penjual_home)
        navController.navigate(R.id.navigation_penjual_produsen)
        binding.navView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.navigation_penjual_produsen -> {
                    navController.navigate(R.id.navigation_penjual_produsen)
                }
                R.id.navigation_penjual_laporan -> {
                    navController.navigate(R.id.navigation_penjual_laporan)
                }
                R.id.navigation_penjual_profile -> {
                    navController.navigate(R.id.navigation_penjual_profile)
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun initInstance() {
        _binding = ActivityPenjualHomeBinding.inflate(layoutInflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}