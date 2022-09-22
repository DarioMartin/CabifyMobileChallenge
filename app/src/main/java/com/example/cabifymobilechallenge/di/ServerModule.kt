package com.example.cabifymobilechallenge.di

import com.example.cabifymobilechallenge.BuildConfig
import com.example.cabifymobilechallenge.data.remote.ServerDataSource
import com.example.cabifymobilechallenge.data.remote.StoreApi
import com.example.cabifymobilechallenge.data.repository.IRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServerModule {

    @Provides
    @Singleton
    fun providesRemoteDataSource(): IRemoteDataSource {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return ServerDataSource(retrofit.create(StoreApi::class.java))
    }

}