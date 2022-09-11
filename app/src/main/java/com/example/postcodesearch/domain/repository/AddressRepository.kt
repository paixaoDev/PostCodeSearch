package com.example.postcodesearch.domain.repository

import okhttp3.ResponseBody

interface AddressRepository {
    suspend fun getAddressFromApi(): ResponseBody
}