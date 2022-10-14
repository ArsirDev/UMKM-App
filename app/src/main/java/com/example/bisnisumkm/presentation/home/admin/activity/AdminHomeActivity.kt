package com.example.bisnisumkm.presentation.home.admin.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.example.bisnisumkm.R
import com.example.bisnisumkm.data.remote.dto.DataLogin
import com.example.bisnisumkm.databinding.ActivityAdminHomeBinding
import com.example.bisnisumkm.util.SESSION.ADMIN_LOGIN
import com.example.bisnisumkm.util.fromJson
import com.example.bisnisumkm.util.toJson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getStringExtra("ADMIN_LOGIN")?.fromJson<DataLogin>()?.let { response ->
            binding.navView.setOnItemSelectedListener { menuItem ->
                when(menuItem.itemId) {
                    R.id.navigation_produsen -> {
                        findNavController(R.id.nav_host_fragment_activity_admin_home).navigate(R.id.navigation_produsen)
                    }
                    R.id.navigation_penjual -> {
                        findNavController(R.id.nav_host_fragment_activity_admin_home).navigate(R.id.navigation_penjual)
                    }
                    R.id.navigation_profile -> {
                        findNavController(R.id.nav_host_fragment_activity_admin_home).navigate(R.id.navigation_profile, bundleOf(ADMIN_LOGIN to response.toJson(DataLogin::class.java)))
                    }
                }
                return@setOnItemSelectedListener true
            }
        }
    }
}