package com.example.cabifymobilechallenge.domain.usecases

data class ProductListUseCases(
    val getAvailableProductsUseCase: GetAvailableProductsUseCase,
    val addProductToCartUseCase: AddProductToCartUseCase,
    val removeProductsFromCartUseCase: RemoveProductFromCartUseCase,
    val clearCartUseCase: ClearCartUseCase
)
