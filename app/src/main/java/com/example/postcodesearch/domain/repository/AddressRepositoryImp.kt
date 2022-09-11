package com.example.postcodesearch.domain.repository

import com.example.postcodesearch.domain.remote.GitHubApi
import okhttp3.ResponseBody
import javax.inject.Inject


class AddressRepositoryImp @Inject constructor(
    private val gitHubApi: GitHubApi
) : AddressRepository {

    override suspend fun getAddressFromApi(): ResponseBody {
        return gitHubApi.getPostalCodes()
    }
}