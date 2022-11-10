package com.example.bisnisumkm.presentation.home.produsen.ui.penitipan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bisnisumkm.data.remote.dto.GetAllStatusResponse
import com.example.bisnisumkm.domain.repository.AppsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.bisnisumkm.util.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatusViewModel @Inject constructor (
    private val repository: AppsRepository
): ViewModel() {

    private val _approve = MutableLiveData<Result<GetAllStatusResponse>>()

    private val _pending = MutableLiveData<Result<GetAllStatusResponse>>()

    init {
        fetchApprove()
        fetchPending()
    }

    private fun fetchApprove() = viewModelScope.launch {
        _approve.postValue(Result.Loading())

        val approve = repository.getAllStatusPenitipan("DITERIMA")

        _approve.postValue(approve)
    }

    private fun fetchPending() = viewModelScope.launch {
        _pending.postValue(Result.Loading())

        val pending = repository.getAllStatusPenitipan("MENUNGGU")

        _pending.postValue(pending)
    }

    fun getApprove(): LiveData<Result<GetAllStatusResponse>> = _approve

    fun getPending(): LiveData<Result<GetAllStatusResponse>> = _pending
}