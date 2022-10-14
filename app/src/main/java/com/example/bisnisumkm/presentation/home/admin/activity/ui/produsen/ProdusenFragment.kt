package com.example.bisnisumkm.presentation.home.admin.activity.ui.produsen

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
import com.example.bisnisumkm.data.remote.dto.SearchProdusenResponse
import com.example.bisnisumkm.databinding.FragmentProdusenBinding
import com.example.bisnisumkm.domain.adapter.produsen.ProdusenAdapter
import com.example.bisnisumkm.presentation.home.admin.viewmodel.AdminViewModel
import com.example.bisnisumkm.util.MESSAGE.STATUS_ERROR
import com.example.bisnisumkm.util.MarginItemDecorationVertical
import com.example.bisnisumkm.util.Result
import com.example.bisnisumkm.util.removeView
import com.example.bisnisumkm.util.showView
import com.example.bisnisumkm.util.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProdusenFragment : Fragment(R.layout.fragment_produsen) {

    private var _binding: FragmentProdusenBinding? = null

    private val binding get() = _binding as FragmentProdusenBinding

    private val viewModel: AdminViewModel by viewModels()

    private lateinit var produsenAdapter: ProdusenAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initInstance(view)
        super.onViewCreated(binding.root, savedInstanceState)
        initAdapter()
        initLaunch()
        initView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.setSearch("")
    }

    private fun initView() {
        binding.tlSearch.setStartIconOnClickListener {
            val search = binding.etSearch.text.toString().trim()
            viewModel.setSearch(
                search
            )
        }
    }

    private fun initLaunch() {
        observerSearchProdusen?.let {
            viewModel.getProdusen().observe(viewLifecycleOwner, it)
        }
    }

    private var observerSearchProdusen: Observer<Result<SearchProdusenResponse>>? = Observer { result ->
        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    when(result) {
                        is Result.Loading -> {
                            binding.pbLoading.showView()
                        }
                        is Result.Success -> {
                            binding.pbLoading.removeView()
                            result.data?.dataSearchProdusen?.let { item ->
                                if (item.dataItem.isEmpty()) {
                                    binding.icEmpty.showView()
                                    return@let
                                }
                                produsenAdapter.differ.submitList(item.dataItem)
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
        produsenAdapter.let { adapter ->
            binding.rvProdusen.apply {
                this.adapter = adapter
                this.layoutManager = LinearLayoutManager(requireContext())
                this.addItemDecoration(MarginItemDecorationVertical(16))
                ViewCompat.setNestedScrollingEnabled(this, true)
            }
        }
    }

    private fun initInstance(view: View) {
        _binding = FragmentProdusenBinding.bind(view)
        produsenAdapter = ProdusenAdapter.instance()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}