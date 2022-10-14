package com.example.bisnisumkm.presentation.home.penjual.ui.produsen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bisnisumkm.data.remote.dto.GeneralResponse
import com.example.bisnisumkm.data.remote.dto.GetAllDetailProdusenRequestResponse
import com.example.bisnisumkm.data.remote.dto.GetSpesificDetailProdusenRequestResponse
import com.example.bisnisumkm.data.remote.dto.LaporanResponse
import com.example.bisnisumkm.domain.repository.AppsRepository
import com.example.bisnisumkm.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProdusenPenjualViewModel @Inject constructor(
    private val repository: AppsRepository
) : ViewModel() {

    val _produsen = MutableLiveData<Result<GetAllDetailProdusenRequestResponse>>()

    val _specificDetailProdusen = MutableLiveData<Result<GetSpesificDetailProdusenRequestResponse>>()

    val _updateStatus = MutableLiveData<Result<GeneralResponse>>()

    val _laporan = MutableLiveData<Result<LaporanResponse>>()

    val _deleteRequest = MutableLiveData<Result<GeneralResponse>>()

    fun setProdusen(
        search: String
    ) = viewModelScope.launch {
        _produsen.postValue(Result.Loading())
        val produsen = repository.getAllDetailRequestProdusen(search)
        _produsen.postValue(produsen)
    }

    fun getProdusen(): LiveData<Result<GetAllDetailProdusenRequestResponse>> = _produsen

    fun setSpecificDetailProdusen(
        id: Int
    ) = viewModelScope.launch {
        _specificDetailProdusen.postValue(Result.Loading())
        val produsen = repository.getSprcificDetailRequestProdusen(id)
        _specificDetailProdusen.postValue(produsen)
    }

    fun getSpecifictDetailProdusen(): LiveData<Result<GetSpesificDetailProdusenRequestResponse>> = _specificDetailProdusen


    fun setUpdateStatus(
        id: Int,
        status_penitipan: String
    ) = viewModelScope.launch {
        _updateStatus.postValue(Result.Loading())

        val update_status = repository.updateStatusRequest(
            id,
            status_penitipan
        )

        _updateStatus.postValue(update_status)
    }

    fun getUpdatestatus(): LiveData<Result<GeneralResponse>> = _updateStatus


    fun onValidationLaporan(
        produsen_name: String,
        penjual_name: String,
        product_name: String,
        name_toko: String,
        qty: String,
        sisa_product: String,
        laku_product: String,
        status: String
    ){
        if (produsen_name.isEmpty() || penjual_name.isEmpty() || product_name.isEmpty() || name_toko.isEmpty() || qty.isEmpty() || sisa_product.isEmpty() || laku_product.isEmpty() || status.isEmpty()) {
            _laporan.postValue(Result.Error(null, "Data tidak boleh kosong"))
            return
        }

        setLaporan(
            produsen_name,
            penjual_name,
            product_name,
            name_toko,
            qty,
            sisa_product,
            laku_product,
            status
        )
    }

    fun setLaporan(
        produsen_name: String,
        penjual_name: String,
        product_name: String,
        name_toko: String,
        qty: String,
        sisa_product: String,
        laku_product: String,
        status: String
    ) = viewModelScope.launch {
        _laporan.postValue(Result.Loading())
        val laporan = repository.setLaporan(
            produsen_name,
            penjual_name,
            product_name,
            name_toko,
            qty,
            sisa_product,
            laku_product,
            status
        )
        _laporan.postValue(laporan)
    }

    fun getLaporan(): LiveData<Result<LaporanResponse>> = _laporan

    fun setDeleteRequest(
        id: Int
    ) = viewModelScope.launch {
        _deleteRequest.postValue(Result.Loading())

        val deleteRequest = repository.deleteProdusenRequest(id)

        _deleteRequest.postValue(deleteRequest)
    }

    fun getDeleteRequest(): LiveData<Result<GeneralResponse>> = _deleteRequest

}