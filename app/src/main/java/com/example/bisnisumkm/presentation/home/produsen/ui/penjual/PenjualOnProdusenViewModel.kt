package com.example.bisnisumkm.presentation.home.produsen.ui.penjual

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bisnisumkm.data.remote.dto.SearchPenjualResponse
import com.example.bisnisumkm.domain.repository.AppsRepository
import com.example.bisnisumkm.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PenjualOnProdusenViewModel @Inject constructor(
    private val repository: AppsRepository
): ViewModel() {

    private val _penjual = MutableLiveData<Result<SearchPenjualResponse>>()

    fun setSearchPenjual(
        search: String
    ) = viewModelScope.launch {
        _penjual.postValue(Result.Loading())

        val penjual = repository.getSearchPenjual(search)

        _penjual.postValue(penjual)
    }

    fun getSearchPenjual(): LiveData<Result<SearchPenjualResponse>> = _penjual
}