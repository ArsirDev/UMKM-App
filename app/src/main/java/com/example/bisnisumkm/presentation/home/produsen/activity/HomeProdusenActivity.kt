package com.example.bisnisumkm.presentation.home.produsen.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.example.bisnisumkm.R
import com.example.bisnisumkm.data.remote.dto.DataLogin
import com.example.bisnisumkm.databinding.ActivityHomeProdusenBinding
import com.example.bisnisumkm.util.P_E_M
import com.example.bisnisumkm.util.SESSION.PRODUSEN_LOGIN
import com.example.bisnisumkm.util.SimpleName
import com.example.bisnisumkm.util.fromJson
import com.example.bisnisumkm.util.toJson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeProdusenActivity : AppCompatActivity() {

    private var _binding: ActivityHomeProdusenBinding? = null

    private val binding get() = _binding as ActivityHomeProdusenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInstance()
        setContentView(binding.root)
        initNavigation()
    }

    private fun initNavigation() {
        val navController = findNavController(R.id.nav_host_fragment_activity_produsen_home)
        navController.navigate(R.id.navigation_produsen_penjual)
        binding.navView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.navigation_produsen_penjual -> {
                    navController.navigate(R.id.navigation_produsen_penjual)
                }
                R.id.navigation_produsen_laporan -> {
                    navController.navigate(R.id.navigation_produsen_laporan)
                }
                R.id.navigation_produsen_profile -> {
                    navController.navigate(R.id.navigation_produsen_profile)
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun initInstance() {
        _binding = ActivityHomeProdusenBinding.inflate(layoutInflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}