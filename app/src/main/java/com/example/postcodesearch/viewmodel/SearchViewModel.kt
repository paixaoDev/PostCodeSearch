package com.example.postcodesearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postcodesearch.data.AddressData
import com.example.postcodesearch.data.CallStateResult
import com.example.postcodesearch.domain.repository.AddressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: AddressRepository
) : ViewModel() {

    private val _addressesState = MutableLiveData<CallStateResult>()
    fun addressesState(): LiveData<CallStateResult> = _addressesState

    fun downloadFileAndSave() {
        viewModelScope.launch {
            _addressesState.postValue(CallStateResult.Loading)
            kotlin.runCatching { repository.getAddressFromApi() }
                .onSuccess { result ->
                    _addressesState.postValue(CallStateResult.OnRemoteAddressFileReceived(result))
                }
                .onFailure { error ->
                    _addressesState.postValue(
                        CallStateResult.Error(error.message ?: "")
                    )
                }
        }
    }

    fun saveData(addressList: MutableList<AddressData>) {
        viewModelScope.launch {
            _addressesState.postValue(CallStateResult.Loading)
            kotlin.runCatching { repository.saveAddresses(addressList) }
                .onSuccess {
                    _addressesState.postValue(CallStateResult.OnDataSaved)
                }
                .onFailure { error ->
                    _addressesState.postValue(
                        CallStateResult.Error(error.message ?: "")
                    )
                }
        }
    }

    fun getDataFromLocal() {
        viewModelScope.launch {
            _addressesState.postValue(CallStateResult.Loading)
            kotlin.runCatching { repository.getAddressesFromLocalDatabase() }
                .onSuccess { result ->
                    _addressesState.postValue(CallStateResult.OnAddressesFetchedFromLocal(result))
                }
                .onFailure { error ->
                    _addressesState.postValue(
                        CallStateResult.Error(error.message ?: "")
                    )
                }
        }
    }

    fun searchAddress(query: String){
        if(query.isBlank()){
            getDataFromLocal()
            return
        }
        viewModelScope.launch {
            _addressesState.postValue(CallStateResult.Loading)
            kotlin.runCatching { repository.searchForAddress(query) }
                .onSuccess { result ->
                    _addressesState.postValue(CallStateResult.OnQueryFinished(result))
                }
                .onFailure { error ->
                    _addressesState.postValue(
                        CallStateResult.Error(error.message ?: "")
                    )
                }
        }
    }
}
