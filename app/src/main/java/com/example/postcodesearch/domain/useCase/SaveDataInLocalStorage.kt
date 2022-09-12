package com.example.postcodesearch.domain.useCase

import android.util.Log
import com.example.postcodesearch.data.AddressData
import com.example.postcodesearch.data.CallResult
import com.example.postcodesearch.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class SaveDataInLocalStorage @Inject constructor(
    private val remoteApi: AddressRepository
) {
    operator fun invoke(addressList: MutableList<AddressData>): Flow<CallResult<Unit>> = flow {
        try {
            emit(CallResult.Loading())
            remoteApi.saveAddresses(addressList)
            emit(CallResult.Success(Unit))
        } catch (e: Exception) {
            emit(CallResult.Error(e.localizedMessage))
            Log.e("Wtest", "Database Error: $e}")
        }
    }
}