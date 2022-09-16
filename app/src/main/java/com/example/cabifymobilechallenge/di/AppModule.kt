package com.example.cabifymobilechallenge.di

import com.example.cabifymobilechallenge.data.repository.MockStoreRepositoryImpl
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