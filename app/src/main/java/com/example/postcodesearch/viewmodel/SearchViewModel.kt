package com.example.postcodesearch.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postcodesearch.data.AddressData
import com.example.postcodesearch.data.CallResult
import com.example.postcodesearch.data.CallStateResult
import com.example.postcodesearch.domain.repository.AddressRepository
import com.example.postcodesearch.domain.useCase.DownloadFileFromServer
import com.example.postcodesearch.domain.useCase.GetDataFromLocalStorage
import com.example.postcodesearch.domain.useCase.SaveDataInLocalStorage
import com.example.postcodesearch.domain.useCase.SearchForAddress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val downloadFile: DownloadFileFromServer,
    private val saveDataInLocalstorage: SaveDataInLocalStorage,
    private val getDataFromLocalStorage: GetDataFromLocalStorage,
    private val searchForAddress: SearchForAddress,
    private val addressRepository: AddressRepository,
) : ViewModel() {

    private val _addressesState = MutableLiveData<CallStateResult>()
    fun addressesState(): LiveData<CallStateResult> = _addressesState

    fun downloadFileAndSave() {
        downloadFile().onEach { result ->
            when (result) {
                is CallResult.Loading -> {
                    _addressesState.postValue(CallStateResult.Loading)
                }
                is CallResult.Error -> {
                    _addressesState.postValue(
                        CallStateResult.Error(
                            result.message ?: ""
                        )
                    )
                }
                is CallResult.Success -> {
                    Log.d("Call", result.data.toString())
                    _addressesState.postValue(
                        result.data?.let {
                            CallStateResult.OnRemoteAddressFileReceived(it)
                        }
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun saveData(addressList: MutableList<AddressData>) {
        saveDataInLocalstorage(addressList).onEach { result ->
            when (result) {
                is CallResult.Loading -> {
                    _addressesState.postValue(CallStateResult.Loading)
                }
                is CallResult.Success -> {
                    _addressesState.postValue(CallStateResult.OnDataSaved)
                }
                is CallResult.Error -> {
                    _addressesState.postValue(
                        CallStateResult.Error(
                            result.message ?: ""
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getDataFromLocal() {
        getDataFromLocalStorage().onEach { result ->
            when (result) {
                is CallResult.Loading -> {
                    _addressesState.postValue(CallStateResult.Loading)
                }
                is CallResult.Success -> {
                    _addressesState.postValue(
                        result.data?.let {
                            CallStateResult.OnAddressesFetchedFromLocal(
                                it
                            )
                        }
                    )
                }
                is CallResult.Error -> {
                    _addressesState.postValue(
                        CallStateResult.Error(
                            result.message ?: ""
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun searchAddress(query: String){
        searchForAddress(query).onEach { result ->
            when (result) {
                is CallResult.Loading -> {
                    _addressesState.postValue(CallStateResult.Loading)
                }
                is CallResult.Success -> {
                    _addressesState.postValue(
                        result.data?.let { CallStateResult.OnQueryFinished(it) }
                    )
                }
                is CallResult.Error -> {
                    _addressesState.postValue(
                        CallStateResult.Error(
                            result.message ?: "Error while searching address"
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}
