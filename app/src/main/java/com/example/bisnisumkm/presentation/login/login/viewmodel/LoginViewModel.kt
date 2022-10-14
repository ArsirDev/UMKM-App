package com.example.bisnisumkm.presentation.login.login.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bisnisumkm.data.remote.dto.LoginResponse
import com.example.bisnisumkm.domain.repository.AppsRepository
import com.example.bisnisumkm.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AppsRepository
): ViewModel() {

    private val _login = MutableLiveData<Result<LoginResponse>>()

    fun onValidation(
        email: String,
        password: String
    ) {
        if(email.isEmpty() || password.isEmpty()) {
            _login.postValue(Result.Error(null, "Field tidak boleh kosong"))
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _login.postValue(Result.Error(null,"Email tidak valid"))
            return
        }

        setLogin(
            email,
            password
        )
    }

    private fun setLogin(
        email: String,
        password: String
    ) = viewModelScope.launch {
        _login.postValue(Result.Loading())

        val login = repository.getLogin(
            email, password
        )

        _login.postValue(login)
    }

    fun getLogin(): LiveData<Result<LoginResponse>> = _login

}