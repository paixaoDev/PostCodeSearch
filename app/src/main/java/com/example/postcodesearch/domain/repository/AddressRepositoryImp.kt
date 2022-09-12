package com.example.postcodesearch.domain.repository

import com.example.postcodesearch.data.AddresDataDao
import com.example.postcodesearch.data.AddressData
import com.example.postcodesearch.domain.remote.GitHubApi
import okhttp3.ResponseBody
import javax.inject.Inject


class AddressRepositoryImp @Inject constructor(
    private val gitHubApi: GitHubApi,
    private val dao: AddresDataDao
) : AddressRepository {

    override suspend fun getAddressFromApi(): ResponseBody {
        return gitHubApi.getPostalCodes()
    }

    override suspend fun getAddressesFromLocalDatabase(): List<AddressData> {
        return dao.getAllAddressFromRemote()
    }

    override suspend fun saveAddresses(list: List<AddressData>) {
        return dao.insertAddress(list)
    }

    override suspend fun searchForAddress(queryString: String): List<AddressData> {
        return dao.searchAddress(queryString)
    }
}