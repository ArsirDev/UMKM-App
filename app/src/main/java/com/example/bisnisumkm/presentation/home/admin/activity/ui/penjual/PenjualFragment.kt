package com.example.bisnisumkm.presentation.home.admin.activity.ui.penjual

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.text.format.Formatter
import android.util.Patterns
import android.view.View
import android.widget.RadioButton
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.bisnisumkm.R
import com.example.bisnisumkm.data.remote.dto.RegisterResponse
import com.example.bisnisumkm.databinding.FragmentPenjualBinding
import com.example.bisnisumkm.util.MESSAGE.STATUS_ERROR
import com.example.bisnisumkm.util.MESSAGE.STATUS_SUCCESS
import com.example.bisnisumkm.util.P_E_M
import com.example.bisnisumkm.util.Result
import com.example.bisnisumkm.util.SimpleName
import com.example.bisnisumkm.util.getFileFromContentUri
import com.example.bisnisumkm.util.loadImage
import com.example.bisnisumkm.util.removeView
import com.example.bisnisumkm.util.setOnClickListenerWithDebounce
import com.example.bisnisumkm.util.showView
import com.example.bisnisumkm.util.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class PenjualFragment : Fragment(R.layout.fragment_penjual) {

    private var _binding: FragmentPenjualBinding? = null

    private val binding get() = _binding!!

    private var newImage: File? = null

    private var permissionRequest: ActivityResultLauncher<Array<String>>? = null

    private lateinit var imageLauncher: ActivityResultLauncher<String?>

    private val viewModel: RegisterPenjualViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initInstance(view)
        super.onViewCreated(binding.root, savedInstanceState)
        initLaunch()
        initCheckPermission()
        initImageActivityResultLauncher()
        initView()
    }

    private fun initLaunch() {
        observerRegisterPenjualResponse?.let {
            viewModel.getRegisterPenjual().observe(viewLifecycleOwner, it)
        }
    }

    private var observerRegisterPenjualResponse: Observer<Result<RegisterResponse>>? = Observer { result ->
        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    when(result) {
                        is Result.Loading -> {
                            binding.pbLoading.showView()
                        }
                        is Result.Success -> {
                            binding.pbLoading.removeView()
                            result.message?.let { message ->
                                snackbar(binding.root, message, STATUS_SUCCESS)
                            } ?: result.data?.message?.let { message ->
                                snackbar(binding.root, message, STATUS_SUCCESS)
                            }
                            delay(1000)
                            cleared()
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

    private fun cleared() {
        findNavController().navigate(R.id.navigation_penjual)
    }

    private fun initView() {
        binding.cvToko.setOnClickListenerWithDebounce {
            try {
                imageLauncher.launch("image/*")
            } catch (e: Exception) {
                initCheckPermission()
            }
        }

        binding.btnLogin.setOnClickListenerWithDebounce {
            val name = binding.etNama.text.toString()
            val email = binding.etEmail.text.toString()
            val nama_toko = binding.etNamaToko.text.toString()
            val alamat = binding.etAlamat.text.toString()
            val phone = binding.etNumberPhone.text.toString()
            val checkRbStatus = binding.rgStatus.checkedRadioButtonId
            val password = binding.etPassword.text.toString()

            onValidation(
                name,
                email,
                nama_toko,
                alamat,
                phone,
                checkRbStatus,
                newImage,
                password,
                password
            )
        }
    }

    private fun onValidation(
        name: String,
        email: String,
        namaToko: String,
        alamat: String,
        phone: String,
        checkRbStatus: Int,
        image: File?,
        password: String,
        password_confirmation: String
    ) {
        if (
            name.isEmpty() ||
            email.isEmpty() ||
            namaToko.isEmpty() ||
            alamat.isEmpty() ||
            phone.isEmpty() ||
            checkRbStatus <= 0 ||
            image == null ||
            password.isEmpty() ||
            password_confirmation.isEmpty()
        ) {
            snackbar(binding.root, "Field tidak boleh kosong", STATUS_ERROR)
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            snackbar(binding.root, "Email tidak valid", STATUS_ERROR)
            return
        }

        val rb: RadioButton =  requireActivity().findViewById(checkRbStatus)
        val status = rb.text.toString()

        viewModel.setDataPenjual(
            name,
            email,
            namaToko,
            alamat,
            phone,
            status,
            image,
            password,
            password_confirmation
        )
    }

    private fun initImageActivityResultLauncher() {
        permissionRequest = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            it?.entries?.forEach { permission ->
            }
        }

        imageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            result?.let { uri ->
                val file = requireContext().getFileFromContentUri(uri)
                newImage = file
                val fileFormatted = Formatter.formatShortFileSize(requireContext(), file.length())
                val fileSizeNum = fileFormatted.replace(" ", "").dropLast(2).toDouble()
                val fileSizeUnit = fileFormatted.replace(" ", "").takeLast(2)
                if (fileSizeNum > 2.0 && fileSizeUnit.contains("mb", true)) {
                    snackbar(binding.root, "Gambar Melebihi 2Mb", STATUS_ERROR)
                    newImage = null
                } else {
                    binding.ivToko.loadImage(uri.toString(), DiskCacheStrategy.RESOURCE)
                }
            }
        }
    }

    private fun initCheckPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET)
            permissionRequest?.launch(permissions)
        }
    }

    private fun initInstance(view: View) {
        _binding = FragmentPenjualBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        newImage = null
    }
}