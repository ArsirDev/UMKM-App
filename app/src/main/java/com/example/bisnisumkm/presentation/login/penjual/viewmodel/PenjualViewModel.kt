package com.example.bisnisumkm.presentation.login.penjual.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bisnisumkm.data.remote.dto.AllTokolResponse
import com.example.bisnisumkm.data.remote.dto.LoginPenjualResponse
import com.example.bisnisumkm.domain.repository.AppsRepository
import com.example.bisnisumkm.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PenjualViewModel @Inject constructor(
    private val repository: AppsRepository
): ViewModel() {

    private val _allToko = MutableLiveData<Result<AllTokolResponse>>()

    private val _login = MutableLiveData<Result<LoginPenjualResponse>>()

    init {
        setAllToko()
    }

    private fun setAllToko() = viewModelScope.launch {
        val toko = repository.getAllToko()
        _allToko.postValue(toko)
    }

    fun onValidation(
        nama_toko: String,
        password: String
    ) {
        if (nama_toko.isEmpty() || password.isEmpty()) {
            _login.postValue(Result.Error(null, "Field tidak boleh kosong"))

            return
        }

        setLogin(
            nama_toko,
            password
        )
    }

    private fun setLogin(
        nama_toko: String,
        password: String
    ) = viewModelScope.launch {

        _login.postValue(Result.Loading())

        val login = repository.getPenjualLogin(
            nama_toko,
            password
        )

        _login.postValue(login)
    }

    fun getAllToko(): LiveData<Result<AllTokolResponse>> = _allToko

    fun getLogin(): LiveData<Result<LoginPenjualResponse>> = _login
}