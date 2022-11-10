package com.example.bisnisumkm.presentation.home.admin.activity.ui.penjual

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bisnisumkm.data.remote.dto.RegisterResponse
import com.example.bisnisumkm.domain.repository.AppsRepository
import com.example.bisnisumkm.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RegisterPenjualViewModel @Inject constructor(
    private val repository: AppsRepository
): ViewModel() {

    private val _registerPenjual = MutableLiveData<Result<RegisterResponse>>()

    fun setDataPenjual(
        name: String,
        email: String,
        nama_toko: String,
        alamat: String,
        number_phone: String,
        status: String,
        image: File,
        password: String,
        password_confirmation: String
    ) = viewModelScope.launch {

        _registerPenjual.postValue(Result.Loading())

        val registerPenjual = repository.getPenjualRegister(
            name,
            email,
            nama_toko,
            alamat,
            number_phone,
            status,
            image,
            password,
            password_confirmation
        )
        _registerPenjual.postValue(registerPenjual)
    }

    fun getRegisterPenjual(): LiveData<Result<RegisterResponse>> = _registerPenjual
}