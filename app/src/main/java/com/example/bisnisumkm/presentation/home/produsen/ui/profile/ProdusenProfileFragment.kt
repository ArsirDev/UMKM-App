package com.example.bisnisumkm.presentation.home.produsen.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.bisnisumkm.R
import com.example.bisnisumkm.data.remote.dto.DataLogin
import com.example.bisnisumkm.databinding.FragmentProdusenProfileBinding
import com.example.bisnisumkm.presentation.welcome.WelcomeActivity
import com.example.bisnisumkm.util.P_E_M
import com.example.bisnisumkm.util.SESSION
import com.example.bisnisumkm.util.SESSION.PRODUSEN_LOGIN
import com.example.bisnisumkm.util.SessionManager
import com.example.bisnisumkm.util.SimpleName
import com.example.bisnisumkm.util.fromJson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProdusenProfileFragment : Fragment(R.layout.fragment_produsen_profile) {

    private var _binding: FragmentProdusenProfileBinding? = null

    private val binding get() = _binding as FragmentProdusenProfileBinding

    private lateinit var sessionManager: SessionManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initInstance(view)
        super.onViewCreated(binding.root, savedInstanceState)
        initArgument()
        initView()
    }

    private fun initView() {
        binding.btnLogout.setOnClickListener {
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
        _binding = FragmentProdusenProfileBinding.bind(view)
        sessionManager = SessionManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}