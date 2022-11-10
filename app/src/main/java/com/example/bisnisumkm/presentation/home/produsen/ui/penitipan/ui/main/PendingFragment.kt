package com.example.bisnisumkm.presentation.home.produsen.ui.penitipan.ui.main

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
import com.example.bisnisumkm.data.remote.dto.GetAllStatusResponse
import com.example.bisnisumkm.databinding.FragmentPendingBinding
import com.example.bisnisumkm.presentation.home.produsen.ui.penitipan.adapter.pending.PendingAdapter
import com.example.bisnisumkm.presentation.home.produsen.ui.penitipan.viewmodel.StatusViewModel
import com.example.bisnisumkm.util.MarginItemDecorationVertical
import dagger.hilt.android.AndroidEntryPoint
import com.example.bisnisumkm.util.Result
import com.example.bisnisumkm.util.removeView
import com.example.bisnisumkm.util.showView
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PendingFragment : Fragment() {

    private var _binding: FragmentPendingBinding?  = null

    private val binding get() = _binding as FragmentPendingBinding

    private val viewModel: StatusViewModel by viewModels()

    private lateinit var pendingAdapter: PendingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPendingBinding.inflate(inflater, container, false)
        pendingAdapter = PendingAdapter.instance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initLaunch()
    }

    private fun initLaunch() {
        obeserverPending?.let { response ->
            viewModel.getPending().observe(viewLifecycleOwner, response)
        }
    }

    private var obeserverPending: Observer<Result<GetAllStatusResponse>>? = Observer { result ->
        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    when(result) {
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
                                pendingAdapter.differ.submitList(item)
                            }
                        }
                        is Result.Error -> {
                            binding.pbLoading.removeView()
                        }
                    }
                }
            }
        }
    }

    private fun initAdapter() {
        pendingAdapter.let { adapter ->
            binding.rvPending.apply {
                this.adapter = adapter
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(MarginItemDecorationVertical(16))
                ViewCompat.setNestedScrollingEnabled(this, true)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun instance() = PendingFragment()
    }
}