package com.example.bisnisumkm.presentation.home.produsen.ui.penjual

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bisnisumkm.R
import com.example.bisnisumkm.data.remote.dto.SearchPenjualResponse
import com.example.bisnisumkm.databinding.FragmentPenjualOnProdusenBinding
import com.example.bisnisumkm.presentation.home.produsen.adapter.PenjualOnProdusenAdapter
import com.example.bisnisumkm.presentation.home.produsen.detail.DetailPenjualOnProdusenActivity
import com.example.bisnisumkm.util.*
import com.example.bisnisumkm.util.MESSAGE.STATUS_ERROR
import com.example.bisnisumkm.util.SESSION.ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PenjualOnProdusenFragment : Fragment(R.layout.fragment_penjual_on_produsen) {

    private var _binding: FragmentPenjualOnProdusenBinding? = null

    private val binding get() = _binding as FragmentPenjualOnProdusenBinding

    private val viewModel: PenjualOnProdusenViewModel by viewModels()

    private lateinit var penjualOnProdusenAdapter: PenjualOnProdusenAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initInstance(inflater, container)
        viewModel.setSearchPenjual(" ")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLaunch()
        initAdapter()
        initView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.setSearchPenjual(" ")
    }

    private fun initView() {
        binding.tlSearch.setStartIconOnClickListener {
            val search = binding.etSearch.text.toString().trim()
            viewModel.setSearchPenjual(search)
        }
    }

    private fun initLaunch() {
        observerPenjualOnProduseResponse.let {
            viewModel.getSearchPenjual().observe(viewLifecycleOwner, it)
        }
    }

    private var observerPenjualOnProduseResponse: Observer<Result<SearchPenjualResponse>> = Observer { result ->
        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    when(result) {
                        is Result.Loading -> {
                            binding.pbLoading.showView()
                        }
                        is Result.Success -> {
                            binding.pbLoading.removeView()
                            result.data?.dataSearchPenjual?.dataSearchPenjualItem?.let { item ->
                                if (item.isEmpty()) {
                                    binding.icEmpty.showView()
                                    binding.rvPenjual.removeView()
                                    return@let
                                }
                                binding.icEmpty.removeView()
                                binding.rvPenjual.showView()
                                penjualOnProdusenAdapter.differ.submitList(item)
                            }
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

    private fun initAdapter() {
        penjualOnProdusenAdapter.let { adapter ->
            binding.rvPenjual.apply {
                this.adapter = adapter
                this.layoutManager = LinearLayoutManager(requireContext())
                this.addItemDecoration(MarginItemDecorationVertical(16))
                ViewCompat.setNestedScrollingEnabled(this, true)
            }
            adapter.setOnItemClickListener { id ->
                startActivity(Intent(requireContext(), DetailPenjualOnProdusenActivity::class.java).putExtra(ID, id))
            }
        }
    }

    private fun initInstance(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentPenjualOnProdusenBinding.inflate(inflater, container,false)
        penjualOnProdusenAdapter = PenjualOnProdusenAdapter.instance()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}