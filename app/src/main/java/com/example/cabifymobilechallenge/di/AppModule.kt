package com.example.cabifymobilechallenge.di

import android.icu.util.Currency
import com.example.cabifymobilechallenge.data.repository.ILocalDataSource
import com.example.cabifymobilechallenge.data.repository.IRemoteDataSource
import com.example.cabifymobilechallenge.data.repository.StoreRepositoryImpl
import com.example.cabifymobilechallenge.domain.repository.IStoreRepository
import com.example.cabifymobilechallenge.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppCurrency(): Currency = Currency.getInstance("EUR")

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
            activateDiscountUseCase = ActivateDiscountUseCase(repository),
            deactivateDiscountUseCase = DeactivateDiscountUseCase(repository),
            getCartDiscountsUseCase = GetCartDiscountsUseCase(repository),
            getCartUseCase = GetCartUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideProcessOrderUseCase(repository: IStoreRepository) = ProcessOrderUseCase(repository)
}