package com.example.bisnisumkm.presentation.register.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bisnisumkm.data.remote.dto.RegisterResponse
import com.example.bisnisumkm.domain.repository.AppsRepository
import com.example.bisnisumkm.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AppsRepository
): ViewModel() {

    private val _register = MutableLiveData<Result<RegisterResponse>>()

    private val _uiEvent = MutableSharedFlow<String>()

    val uiEvent get() = _uiEvent.asSharedFlow()

    fun setRegister(
        name: String,
        email: String,
        alamat: String,
        status: String,
        number_phone: String,
        password: String,
        password_confirmation: String
    ) = viewModelScope.launch {

        _register.postValue(Result.Loading())

        val register = repository.getRegister(
            name,
            email,
            alamat,
            status,
            number_phone,
            password,
            password_confirmation
        )

        _register.postValue(register)
    }

    fun getRegister(): LiveData<Result<RegisterResponse>> = _register
}