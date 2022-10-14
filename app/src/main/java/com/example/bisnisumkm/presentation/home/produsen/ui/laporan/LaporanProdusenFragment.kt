package com.example.bisnisumkm.presentation.home.produsen.ui.laporan

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bisnisumkm.R
import com.example.bisnisumkm.data.remote.dto.GetLaporanResponse
import com.example.bisnisumkm.databinding.FragmentLaporanProdusenBinding
import com.example.bisnisumkm.presentation.home.produsen.adapter.LaporanProdusenAdapter
import com.example.bisnisumkm.util.MESSAGE
import com.example.bisnisumkm.util.MarginItemDecorationVertical
import com.example.bisnisumkm.util.Result
import com.example.bisnisumkm.util.SessionManager
import com.example.bisnisumkm.util.removeView
import com.example.bisnisumkm.util.showView
import com.example.bisnisumkm.util.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LaporanProdusenFragment : Fragment(R.layout.fragment_laporan_produsen) {

    private var _binding: FragmentLaporanProdusenBinding? = null

    private val binding get() = _binding as FragmentLaporanProdusenBinding

    private val viewModel: LaporanProdusenViewModel by viewModels()

    private lateinit var laporanProdusenAdapter: LaporanProdusenAdapter

    private lateinit var sessionManager: SessionManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initInstance(view)
        super.onViewCreated(binding.root, savedInstanceState)
        initLaunch()
        initAdapter()
        initView()
    }

    private fun initView() {
        sessionManager.Name?.let { name ->
            viewModel.fetchLaporanProdusen(name)
        }
    }

    private fun initAdapter() {
        laporanProdusenAdapter.let { adapter ->
            binding.rvLaporan.apply {
                this.adapter = adapter
                this.layoutManager = LinearLayoutManager(requireContext())
                this.addItemDecoration(MarginItemDecorationVertical(16))
                ViewCompat.setNestedScrollingEnabled(this, true)
            }
        }
    }

    private fun initLaunch() {
        observerLaporanProdusen?.let {
            viewModel.getLaporanProdusen().observe(viewLifecycleOwner, it)
        }
    }

    private var observerLaporanProdusen: Observer<Result<GetLaporanResponse>>? = Observer { result ->
        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    when(result) {
                        is Result.Loading -> {
                            binding.pbLoading.showView()
                        }
                        is Result.Success -> {
                            binding.pbLoading.removeView()
                            result.data?.dataGetLaporanItem?.let { item ->
                                if (item.isEmpty()) {
                                    binding.icEmpty.showView()
                                    return@let
                                }
                                laporanProdusenAdapter.differ.submitList(item)
                            }
                        }
                        is Result.Error -> {
                            binding.pbLoading.removeView()
                            result.message?.let { msg ->
                                snackbar(binding.root, msg, MESSAGE.STATUS_ERROR)
                            } ?: result.data?.message?.let { msg ->
                                snackbar(binding.root, msg, MESSAGE.STATUS_ERROR)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initInstance(view: View) {
        _binding = FragmentLaporanProdusenBinding.bind(view)
        laporanProdusenAdapter = LaporanProdusenAdapter.Instance()
        sessionManager = SessionManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}