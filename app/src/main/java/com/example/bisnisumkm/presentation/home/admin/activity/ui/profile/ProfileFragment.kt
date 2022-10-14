package com.example.bisnisumkm.presentation.home.admin.activity.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.bisnisumkm.R
import com.example.bisnisumkm.data.remote.dto.DataLogin
import com.example.bisnisumkm.databinding.FragmentProfileBinding
import com.example.bisnisumkm.presentation.welcome.WelcomeActivity
import com.example.bisnisumkm.util.SESSION.ADMIN_LOGIN
import com.example.bisnisumkm.util.SessionManager
import com.example.bisnisumkm.util.fromJson
import com.example.bisnisumkm.util.setOnClickListenerWithDebounce
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding as FragmentProfileBinding

    private lateinit var sessionManager: SessionManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initInstace(view)
        super.onViewCreated(binding.root, savedInstanceState)
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
        arguments?.getString(ADMIN_LOGIN)?.fromJson<DataLogin>()?.let { response ->
            with(binding) {
                tvName.text = response.name
                tvAlamat.text = response.alamat
                tvMail.text = response.email
                tvPhone.text = response.numberPhone
            }
        }
    }

    private fun initInstace(view: View) {
        _binding = FragmentProfileBinding.bind(view)
        sessionManager = SessionManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}