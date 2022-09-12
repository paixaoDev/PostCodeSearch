package com.example.postcodesearch.domain.repository

import com.example.postcodesearch.data.AddressData
import okhttp3.ResponseBody

interface AddressRepository {
    suspend fun getAddressFromApi(): ResponseBody
    suspend fun getAddressesFromLocalDatabase(): List<AddressData>
    suspend fun saveAddresses(list: List<AddressData>)
    suspend fun searchForAddress(queryString: String): List<AddressData>
}