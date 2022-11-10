package com.example.bisnisumkm.presentation.home.produsen.ui.penitipan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bisnisumkm.databinding.FragmentPenitipanBinding
import com.example.bisnisumkm.presentation.home.produsen.adapter.PenitipanAdapter
import com.example.bisnisumkm.presentation.home.produsen.ui.penitipan.ui.main.ApproveFragment
import com.example.bisnisumkm.presentation.home.produsen.ui.penitipan.ui.main.PendingFragment
import com.google.android.material.tabs.TabLayoutMediator

class PenitipanFragment : Fragment() {

    private var _binding: FragmentPenitipanBinding? = null

    private val binding get() = _binding as FragmentPenitipanBinding

    private lateinit var penitipanAdapter: PenitipanAdapter

    private val animalsArray = arrayOf(
        "Penitipan Diterima",
        "Penitipan Tertunda",
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPenitipanBinding.inflate(inflater, container, false)
        val fragmentList = listOf(
            ApproveFragment.instance(),
            PendingFragment.instance()
        )
        penitipanAdapter = PenitipanAdapter(fragmentList ,childFragmentManager, lifecycle)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViePager()
    }

    private fun initViePager() {
        val viewPager = binding.viewPager
        val tabLayout = binding.tabs
        viewPager.adapter = penitipanAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = animalsArray[position]
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}