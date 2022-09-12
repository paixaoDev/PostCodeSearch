package com.example.postcodesearch.domain.useCase

import android.util.Log
import com.example.postcodesearch.data.AddressData
import com.example.postcodesearch.data.CallResult
import com.example.postcodesearch.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDataFromLocalStorage @Inject constructor(
    private val repository: AddressRepository
) {

    operator fun invoke(): Flow<CallResult<List<AddressData>>> = flow {
        try {
            emit(CallResult.Loading())
            val results = repository.getAddressesFromLocalDatabase()
            emit(CallResult.Success(results))
        } catch (e: Exception) {
            emit(CallResult.Error(e.localizedMessage))
            Log.e("Wtest", "Database Error: $e}")
        }
    }
}