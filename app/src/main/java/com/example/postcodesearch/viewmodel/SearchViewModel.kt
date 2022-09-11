package com.example.postcodesearch.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postcodesearch.data.CallResult
import com.example.postcodesearch.domain.repository.AddressRepository
import com.example.postcodesearch.domain.useCase.DownloadFileFromServer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val downloadFile: DownloadFileFromServer,
    private val addressRepository: AddressRepository
) : ViewModel() {


    fun downloadFileAndSave() {
        downloadFile().onEach { result ->
            when (result) {
                is CallResult.Loading -> {

                }
                is CallResult.Error -> {

                }
                is CallResult.Success -> {
                    Log.d("Call", result.data.toString())
                }
            }
        }.launchIn(viewModelScope)
    }
}
