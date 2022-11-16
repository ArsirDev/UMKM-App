package com.example.bisnisumkm.presentation.home.produsen.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.bisnisumkm.R
import com.example.bisnisumkm.databinding.ActivityHomeProdusenBinding
import com.example.bisnisumkm.presentation.home.produsen.ui.penjual.PenjualOnProdusenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeProdusenActivity : AppCompatActivity() {

    private var _binding: ActivityHomeProdusenBinding? = null

    private val binding get() = _binding as ActivityHomeProdusenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInstance()
        setContentView(binding.root)
        val navController = findNavController(R.id.nav_host_fragment_activity_produsen_home)
        navController.navigate(R.id.navigation_produsen_penjual)
        initNavigation(navController)
    }

    private fun initNavigation(navController: NavController) {
        binding.navView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.navigation_produsen_penjual -> {
                    navController.navigate(R.id.navigation_produsen_penjual)
                }
                R.id.navigation_penitipan -> {
                    navController.navigate(R.id.navigation_penitipan)
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