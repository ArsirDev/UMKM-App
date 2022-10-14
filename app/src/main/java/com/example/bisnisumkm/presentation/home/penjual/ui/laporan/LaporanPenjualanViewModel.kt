package com.example.bisnisumkm.presentation.home.penjual.ui.laporan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bisnisumkm.data.remote.dto.GetLaporanResponse
import com.example.bisnisumkm.domain.repository.AppsRepository
import com.example.bisnisumkm.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaporanPenjualanViewModel @Inject constructor(
    private val repository: AppsRepository
): ViewModel() {

    private val _laporan = MutableLiveData<Result<GetLaporanResponse>>()

    fun fetchLaporanPenjualan(penjual_name: String) = viewModelScope.launch {
        _laporan.postValue(Result.Loading())

        val laporan = repository.getLaporanPenjual(penjual_name)

        _laporan.postValue(laporan)
    }

    fun getLaporanPenjualan(): LiveData<Result<GetLaporanResponse>> = _laporan
}