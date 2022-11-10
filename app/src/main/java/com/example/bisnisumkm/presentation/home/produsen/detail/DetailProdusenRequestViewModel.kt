package com.example.bisnisumkm.presentation.home.produsen.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bisnisumkm.data.remote.dto.DetailPenjualResponse
import com.example.bisnisumkm.data.remote.dto.SetRequestProdusenResponse
import com.example.bisnisumkm.domain.repository.AppsRepository
import com.example.bisnisumkm.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DetailProdusenRequestViewModel @Inject constructor(
    private val repository: AppsRepository
) : ViewModel() {

    private val _getDetailPenjual = MutableLiveData<Result<DetailPenjualResponse>>()

    private val _setDetailProdusenRequest = MutableLiveData<Result<SetRequestProdusenResponse>>()

    fun onValidation(id: Int) {
        if (id.toString().isEmpty()) {
            _getDetailPenjual.postValue(Result.Error(null, "Field tidak boleh kosong"))
            return
        }

        setDetailPenjual(id)
    }

    private fun setDetailPenjual(id: Int) = viewModelScope.launch {

        _getDetailPenjual.postValue(Result.Loading())

        val detailPenjual = repository.getDetailPenjual(id)

        _getDetailPenjual.postValue(detailPenjual)
    }

    fun getDetail(): LiveData<Result<DetailPenjualResponse>> = _getDetailPenjual

    fun onRequestValidation(
        id_penjual: String,
        id_produsen: String,
        name_penjual: String,
        name_produsen: String,
        name_toko: String,
        product_name: String,
        email_produsen: String,
        alamat_produsen: String,
        alamat_penjual: String,
        number_phone_produsen: String,
        number_phone_penjual: String,
        tanggal_pengambilan: String,
        qty: String,
        harga: String,
        image_produsen: File?,
        image_penjual: String,
        status_penitipan: String,
    ) {
        if (
            id_penjual.isEmpty() ||
            id_produsen.isEmpty() ||
            name_penjual.isEmpty() ||
            name_produsen.isEmpty() ||
            name_toko.isEmpty() ||
            product_name.isEmpty() ||
            email_produsen.isEmpty() ||
            alamat_produsen.isEmpty() ||
            alamat_penjual.isEmpty() ||
            number_phone_produsen.isEmpty() ||
            number_phone_penjual.isEmpty() ||
            tanggal_pengambilan.isEmpty() ||
            qty.isEmpty() ||
            harga.isEmpty() ||
            image_produsen == null||
            image_penjual.isEmpty() ||
            status_penitipan.isEmpty()
        ) {
            _setDetailProdusenRequest.postValue(Result.Error(null, "Field tidak boleh kosong"))
            return
        }

        setRequestProdusen(
            id_penjual,
            id_produsen,
            name_penjual,
            name_produsen,
            name_toko,
            product_name,
            email_produsen,
            alamat_produsen,
            alamat_penjual,
            number_phone_produsen,
            number_phone_penjual,
            tanggal_pengambilan,
            qty,
            harga,
            image_produsen,
            image_penjual,
            status_penitipan
        )
    }

    private fun setRequestProdusen(
        id_penjual: String,
        id_produsen: String,
        name_penjual: String,
        name_produsen: String,
        name_toko: String,
        product_name: String,
        email_produsen: String,
        alamat_produsen: String,
        alamat_penjual: String,
        number_phone_produsen: String,
        number_phone_penjual: String,
        tanggal_pengambilan: String,
        qty: String,
        harga: String,
        image_produsen: File,
        image_penjual: String,
        status_penitipan: String
    ) = viewModelScope.launch {

        _setDetailProdusenRequest.postValue(Result.Loading())

        val setRequestProdusen = repository.setDetailProdusenRequest(
            id_penjual,
            id_produsen,
            name_penjual,
            name_produsen,
            name_toko,
            product_name,
            email_produsen,
            alamat_produsen,
            alamat_penjual,
            number_phone_produsen,
            number_phone_penjual,
            tanggal_pengambilan,
            qty,
            harga,
            image_produsen,
            image_penjual,
            status_penitipan
        )
        _setDetailProdusenRequest.postValue(setRequestProdusen)
    }

    fun getRequestProdusen(): LiveData<Result<SetRequestProdusenResponse>> = _setDetailProdusenRequest

}