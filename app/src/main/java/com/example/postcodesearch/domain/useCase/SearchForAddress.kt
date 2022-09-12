package com.example.postcodesearch.domain.useCase

import com.example.postcodesearch.data.AddressData
import com.example.postcodesearch.data.CallResult
import com.example.postcodesearch.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchForAddress @Inject constructor(
    private val repository: AddressRepository
) {
    operator fun invoke(query: String): Flow<CallResult<List<AddressData>>> = flow {
        if (query.isBlank()) {
            return@flow
        }
        try {
            emit(CallResult.Loading())
            val results = repository.searchForAddress(query)
            emit(CallResult.Success(results))
        } catch (e: Exception) {
            emit(CallResult.Error(e.localizedMessage))
        }
    }
}