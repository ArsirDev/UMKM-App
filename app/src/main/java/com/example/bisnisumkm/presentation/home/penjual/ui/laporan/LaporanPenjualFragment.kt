package com.example.bisnisumkm.presentation.home.penjual.ui.laporan

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bisnisumkm.R
import com.example.bisnisumkm.data.remote.dto.GeneralResponse
import com.example.bisnisumkm.data.remote.dto.GetLaporanResponse
import com.example.bisnisumkm.databinding.FragmentLaporanPenjualBinding
import com.example.bisnisumkm.presentation.home.penjual.adpater.PenjualLaporanAdapter
import com.example.bisnisumkm.util.*
import com.example.bisnisumkm.util.MESSAGE.STATUS_ERROR
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LaporanPenjualFragment : Fragment(R.layout.fragment_laporan_penjual) {

    private var _binding: FragmentLaporanPenjualBinding? = null

    private val binding get() = _binding as FragmentLaporanPenjualBinding

    private val viewModel: LaporanPenjualanViewModel by viewModels()

    private lateinit var penjualLaporanAdapter: PenjualLaporanAdapter

    private lateinit var sessionManager: SessionManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initInstance(view)
        super.onViewCreated(binding.root, savedInstanceState)
        iniLaunch()
        initAdapter()
        initView()
    }

    private fun initView() {
        sessionManager.Name?.let { name ->
            viewModel.fetchLaporanPenjualan(
                name
            )
        }
    }

    private fun initAdapter() {
        penjualLaporanAdapter.let { adapter ->
            binding.rvLaporan.apply {
                this.adapter = adapter
                this.layoutManager = LinearLayoutManager(requireContext())
                this.addItemDecoration(MarginItemDecorationVertical(16))
                ViewCompat.setNestedScrollingEnabled(this, true)
            }

            adapter.setOnItemClickListener { id ->
                viewModel.fetchDeleteLaporanPenjual(id)
            }
        }
    }

    private fun iniLaunch() {
        observerLaporanPenjualan?.let {
            viewModel.getLaporanPenjualan().observe(viewLifecycleOwner, it)
        }

        observerDeleteLaporan?.let {
            viewModel.getDeleteLaporanPenjual().observe(viewLifecycleOwner, it)
        }
    }

    private var observerDeleteLaporan: Observer<Result<GeneralResponse>>? = Observer { result ->
        when(result) {
            is Result.Loading -> {
                binding.pbLoading.showView()
            }
            is Result.Success -> {
                binding.pbLoading.removeView()
                result.data?.message?.let { msg ->
                    snackbar(binding.root, msg, MESSAGE.STATUS_SUCCESS)
                } ?: result.message?.let { msg ->
                    snackbar(binding.root, msg, MESSAGE.STATUS_SUCCESS)
                }
                findNavController().currentDestination?.apply {
                    findNavController().popBackStack()
                    findNavController().navigate(this.id)
                }
            }
            is Result.Error -> {
                binding.pbLoading.removeView()
                result.data?.message?.let { msg ->
                    snackbar(binding.root, msg, STATUS_ERROR)
                } ?: result.message?.let { msg ->
                    snackbar(binding.root, msg, STATUS_ERROR)
                }
            }
        }
    }

    private var observerLaporanPenjualan: Observer<Result<GetLaporanResponse>>? =
        Observer { result ->
            lifecycleScope.launchWhenStarted {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        when (result) {
                            is Result.Loading -> {
                                binding.pbLoading.showView()
                            }
                            is Result.Success -> {
                                binding.pbLoading.removeView()
                                result.data?.data?.let { item ->
                                    if (item.isEmpty()) {
                                        binding.icEmpty.showView()
                                        return@let
                                    }
                                    penjualLaporanAdapter.differ.submitList(item)
                                }
                            }
                            is Result.Error -> {
                                binding.pbLoading.removeView()
                                result.message?.let { msg ->
                                    snackbar(binding.root, msg, STATUS_ERROR)
                                } ?: result.data?.message?.let { msg ->
                                    snackbar(binding.root, msg, STATUS_ERROR)
                                }
                            }
                        }
                    }
                }
            }
        }

    private fun initInstance(view: View) {
        _binding = FragmentLaporanPenjualBinding.bind(view)
        penjualLaporanAdapter = PenjualLaporanAdapter.Instance()
        sessionManager = SessionManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}