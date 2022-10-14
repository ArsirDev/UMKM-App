package com.example.bisnisumkm.presentation.home.penjual.ui.produsen

import android.content.Intent
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
import com.example.bisnisumkm.data.remote.dto.GetAllDetailProdusenRequestResponse
import com.example.bisnisumkm.databinding.FragmentProdusenOnPenjualBinding
import com.example.bisnisumkm.presentation.home.penjual.adpater.ProdusenAdapter
import com.example.bisnisumkm.presentation.home.penjual.detail.DetailProdusenPenjualActivity
import com.example.bisnisumkm.util.MESSAGE.STATUS_ERROR
import com.example.bisnisumkm.util.MarginItemDecorationVertical
import com.example.bisnisumkm.util.Result
import com.example.bisnisumkm.util.SESSION.ID
import com.example.bisnisumkm.util.SessionManager
import com.example.bisnisumkm.util.removeView
import com.example.bisnisumkm.util.showView
import com.example.bisnisumkm.util.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProdusenPenjualFragment : Fragment(R.layout.fragment_produsen_on_penjual) {

    private var _binding: FragmentProdusenOnPenjualBinding? = null

    private val binding get() = _binding as FragmentProdusenOnPenjualBinding

    private val viewModel: ProdusenPenjualViewModel by viewModels()

    private lateinit var produsenAdapter: ProdusenAdapter

    private lateinit var sessionManager: SessionManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        iniInstance(view)
        super.onViewCreated(binding.root, savedInstanceState)
        initLaunch()
        initAdapter()
        initView()
    }

    private fun initView() {
        sessionManager.Store_name?.let { namaToko ->
            viewModel.setProdusen(namaToko)
        }
    }

    override fun onResume() {
        super.onResume()
        sessionManager.Store_name?.let { namaToko ->
            viewModel.setProdusen(namaToko)
        }
    }

    private fun initLaunch() {
        observerProdusenPenjualResponse?.let {
            viewModel.getProdusen().observe(viewLifecycleOwner, it)
        }
    }

    private var observerProdusenPenjualResponse: Observer<Result<GetAllDetailProdusenRequestResponse>>? = Observer { result ->
        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    when(result) {
                        is Result.Loading ->  {
                            binding.pbLoading.showView()
                        }
                        is Result.Success ->  {
                            binding.pbLoading.removeView()
                            result.data?.dataAllDetailProdusenRequestResponseItem?.let { item ->
                                if (item.isEmpty()) {
                                    binding.icEmpty.showView()
                                    return@let
                                }
                                produsenAdapter.differ.submitList(item)
                            }
                        }
                        is Result.Error ->  {
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

    private fun initAdapter() {
        produsenAdapter.let { adapter ->
            binding.rvProdusen.apply {
                this.adapter = adapter
                this.layoutManager = LinearLayoutManager(requireContext())
                this.addItemDecoration(MarginItemDecorationVertical(16))
                ViewCompat.setNestedScrollingEnabled(this, true)
            }
            adapter.setOnItemClickListener { id ->
                startActivity(Intent(requireContext(), DetailProdusenPenjualActivity::class.java).putExtra(ID, id))
            }
        }
    }

    private fun iniInstance(view: View) {
        _binding = FragmentProdusenOnPenjualBinding.bind(view)
        produsenAdapter = ProdusenAdapter.instance()
        sessionManager = SessionManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}