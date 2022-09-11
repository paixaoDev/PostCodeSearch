package com.example.postcodesearch.domain.useCase

import com.example.postcodesearch.data.CallResult
import com.example.postcodesearch.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DownloadFileFromServer @Inject constructor(
    private val repository: AddressRepository
) {
    operator fun invoke(): Flow<CallResult<ResponseBody>> = flow {
        try {
            emit(CallResult.Loading())
            //TODO verify if file exists
            val addressFile = repository.getAddressFromApi()
            emit(CallResult.Success(addressFile))
        } catch (e: HttpException) {
            emit(CallResult.Error(e.localizedMessage ?: ""))
        } catch (e: IOException) {
            emit(CallResult.Error(""))
        }
    }
}