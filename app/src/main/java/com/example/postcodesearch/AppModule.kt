package com.example.postcodesearch

import com.example.postcodesearch.domain.remote.GitHubApi
import com.example.postcodesearch.domain.repository.AddressRepository
import com.example.postcodesearch.domain.repository.AddressRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGitHubApi(): GitHubApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
            .create(GitHubApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAddressRepository(
        api: GitHubApi,
    ): AddressRepository {
        return AddressRepositoryImp(api)
    }
}