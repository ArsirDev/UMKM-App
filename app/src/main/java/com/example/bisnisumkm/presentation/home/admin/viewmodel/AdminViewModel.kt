package com.example.bisnisumkm.presentation.home.admin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bisnisumkm.data.remote.dto.SearchProdusenResponse
import com.example.bisnisumkm.domain.repository.AppsRepository
import com.example.bisnisumkm.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val repository: AppsRepository
): ViewModel(){

    private val _produsen = MutableLiveData<Result<SearchProdusenResponse>>()

    fun setSearch(
        search: String
    ) = viewModelScope.launch {
        _produsen.postValue(Result.Loading())

        val produsen = repository.getSearchProdusen(search)

        _produsen.postValue(produsen)
    }

    fun getProdusen(): LiveData<Result<SearchProdusenResponse>> = _produsen

}