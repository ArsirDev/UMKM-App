package com.example.bisnisumkm.presentation.home.penjual.ui.laporan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bisnisumkm.data.remote.dto.GeneralResponse
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

    private val _delete = MutableLiveData<Result<GeneralResponse>>()

    fun fetchLaporanPenjualan(penjual_name: String) = viewModelScope.launch {
        _laporan.postValue(Result.Loading())

        val laporan = repository.getLaporanPenjual(penjual_name)

        _laporan.postValue(laporan)
    }

    fun getLaporanPenjualan(): LiveData<Result<GetLaporanResponse>> = _laporan

    fun fetchDeleteLaporanPenjual(
        id: Int
    ) = viewModelScope.launch {
        _delete.postValue(Result.Loading())

        val delete = repository.deleteLaporanPenjualRequest(id)

        _delete.postValue(delete)
    }

    fun getDeleteLaporanPenjual(): LiveData<Result<GeneralResponse>> = _delete
}