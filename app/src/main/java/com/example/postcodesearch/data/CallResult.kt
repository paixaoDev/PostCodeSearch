package com.example.postcodesearch.data

sealed class CallResult<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : CallResult<T>(data)
    class Error<T>(message: String, data: T? = null) : CallResult<T>(data, message)
    class Loading<T>(data: T? = null) : CallResult<T>(data)
}