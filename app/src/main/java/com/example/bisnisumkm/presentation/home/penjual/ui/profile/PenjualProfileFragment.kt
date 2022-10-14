package com.example.bisnisumkm.presentation.home.penjual.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.bisnisumkm.R
import com.example.bisnisumkm.data.remote.dto.DataLoginPenjual
import com.example.bisnisumkm.databinding.FragmentPenjualProfileBinding
import com.example.bisnisumkm.presentation.welcome.WelcomeActivity
import com.example.bisnisumkm.util.SESSION
import com.example.bisnisumkm.util.SessionManager
import com.example.bisnisumkm.util.fromJson
import com.example.bisnisumkm.util.setOnClickListenerWithDebounce

class PenjualProfileFragment : Fragment(R.layout.fragment_penjual_profile) {

    private var _binding: FragmentPenjualProfileBinding? = null

    private val binding get() = _binding as FragmentPenjualProfileBinding

    private lateinit var sessionManager: SessionManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initInstance(view)
        super.onViewCreated(view, savedInstanceState)
        initArgument()
        initView()
    }

    private fun initView() {
        binding.btnLogout.setOnClickListenerWithDebounce {
            sessionManager.logout().run {
                startActivity(Intent(requireContext(), WelcomeActivity::class.java))
                requireActivity().finishAffinity()
            }
        }
    }

    private fun initArgument() {
        with(binding) {
            tvName.text = sessionManager.Name
            tvAlamat.text = sessionManager.address
            tvMail.text = sessionManager.Email
            tvPhone.text = sessionManager.phone
        }
    }


    private fun initInstance(view: View) {
        _binding = FragmentPenjualProfileBinding.bind(view)
        sessionManager = SessionManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}