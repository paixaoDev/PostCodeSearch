package com.example.postcodesearch.data

import okhttp3.ResponseBody

sealed class CallStateResult {
    object Loading : CallStateResult()
    data class OnRemoteAddressFileReceived(val data: ResponseBody) : CallStateResult()
    data class OnAddressesFetchedFromLocal(val list: List<AddressData>) : CallStateResult()
    data class OnQueryFinished(val queryList: List<AddressData>) : CallStateResult()
    object OnDataSaved : CallStateResult()
    data class Error(val message: String) : CallStateResult()
}