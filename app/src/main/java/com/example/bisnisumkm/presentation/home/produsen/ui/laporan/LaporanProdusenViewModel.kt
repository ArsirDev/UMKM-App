package com.example.bisnisumkm.presentation.home.produsen.ui.laporan

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
class LaporanProdusenViewModel@Inject constructor(
    private val repository: AppsRepository
): ViewModel() {

    private val _laporan = MutableLiveData<Result<GetLaporanResponse>>()

    fun fetchLaporanProdusen(produsen_name: String) = viewModelScope.launch {
        _laporan.postValue(Result.Loading())

        val laporan = repository.getLaporanProdusen(produsen_name)

        _laporan.postValue(laporan)
    }

    fun getLaporanProdusen(): LiveData<Result<GetLaporanResponse>> = _laporan
}