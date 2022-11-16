package com.example.bisnisumkm.presentation.home.produsen.ui.penitipan.ui.main

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
import com.example.bisnisumkm.data.remote.dto.GetAllStatusItem
import com.example.bisnisumkm.data.remote.dto.GetAllStatusResponse
import com.example.bisnisumkm.databinding.FragmentApproveBinding
import com.example.bisnisumkm.presentation.home.produsen.ui.penitipan.DetailPenitipanActivity
import com.example.bisnisumkm.presentation.home.produsen.ui.penitipan.adapter.aprrove.ApproveAdapter
import com.example.bisnisumkm.presentation.home.produsen.ui.penitipan.viewmodel.StatusViewModel
import com.example.bisnisumkm.util.*
import com.example.bisnisumkm.util.SESSION.PASSDATA
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint()
class ApproveFragment : Fragment() {

    private var _binding: FragmentApproveBinding? = null

    private val binding get() = _binding as FragmentApproveBinding

    private val viewModel: StatusViewModel by viewModels()

    private lateinit var approveAdapter: ApproveAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentApproveBinding.inflate(inflater, container, false)
        approveAdapter = ApproveAdapter.instance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initLaunch()
    }

    private fun initLaunch() {
        observerApproveStatus?.let { response ->
            viewModel.getApprove().observe(viewLifecycleOwner, response)
        }
    }

    private var observerApproveStatus: Observer<Result<GetAllStatusResponse>>? = Observer { result ->
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
                                approveAdapter.differ.submitList(item)
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
        approveAdapter.let { adapter ->
            binding.rvApprove.apply {
                this.adapter = adapter
                layoutManager = LinearLayoutManager(requireContext())
                this.addItemDecoration(MarginItemDecorationVertical(16))
                ViewCompat.setNestedScrollingEnabled(this, true)
            }
            adapter.setOnItemClickListener { dataItem ->
                startActivity(Intent(requireContext(), DetailPenitipanActivity::class.java).putExtra(PASSDATA, dataItem.toJson(GetAllStatusItem::class.java)))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun instance() = ApproveFragment()
    }
}