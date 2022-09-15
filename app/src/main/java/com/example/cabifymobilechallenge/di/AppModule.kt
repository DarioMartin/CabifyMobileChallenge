package com.example.cabifymobilechallenge.di

import com.example.cabifymobilechallenge.data.repository.MockStoreRepositoryImpl
import com.example.cabifymobilechallenge.data.repository.StoreRepositoryImpl
import com.example.cabifymobilechallenge.domain.repository.IStoreRepository
import com.example.cabifymobilechallenge.domain.usecases.GetProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesIStoreRepository(): IStoreRepository {
        return MockStoreRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providesGetProductsUseCase(repository: IStoreRepository): GetProductsUseCase {
        return GetProductsUseCase(repository)
    }
}