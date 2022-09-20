package com.example.cabifymobilechallenge.di

import com.example.cabifymobilechallenge.BuildConfig
import com.example.cabifymobilechallenge.data.local.MockedLocalDataSource
import com.example.cabifymobilechallenge.data.remote.ServerDataSource
import com.example.cabifymobilechallenge.data.remote.StoreApi
import com.example.cabifymobilechallenge.data.repository.ILocalDataSource
import com.example.cabifymobilechallenge.data.repository.IRemoteDataSource
import com.example.cabifymobilechallenge.data.repository.StoreRepositoryImpl
import com.example.cabifymobilechallenge.domain.repository.IStoreRepository
import com.example.cabifymobilechallenge.domain.usecases.*
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
    fun providesRemoteDataSource(): IRemoteDataSource {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return ServerDataSource(retrofit.create(StoreApi::class.java))
    }

    @Provides
    @Singleton
    fun providesLocalDataSource(): ILocalDataSource {
        return MockedLocalDataSource()
    }

    @Provides
    @Singleton
    fun providesStoreRepository(
        remoteDataSource: IRemoteDataSource,
        localDataSource: ILocalDataSource
    ): IStoreRepository {
        return StoreRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Provides
    @Singleton
    fun providesGetProductsUseCase(repository: IStoreRepository): GetProductsUseCase {
        return GetProductsUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesProductListUseCases(repository: IStoreRepository): ProductListUseCases {
        return ProductListUseCases(
            getProductsUseCase = GetProductsUseCase(repository),
            addProductToCartUseCase = AddProductToCartUseCase(repository),
            removeProductsFromCartUseCase = RemoveProductFromCartUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun providesCartUseCases(repository: IStoreRepository): CartUseCases {
        return CartUseCases(
            getCartProductsUseCase = GetCartProductsUseCase(repository),
            getAvailableDiscountsUseCase = GetAvailableDiscountsUseCase(repository),
            addDiscountToCartUseCase = AddDiscountToCartUseCase(repository),
            removeDiscountFromCartUseCase = RemoveDiscountFromCartUseCase(repository),
            getCartDiscountsUseCase = GetCartDiscountsUseCase(repository),
            getCartUseCase = GetCartUseCase(repository)
        )
    }
}