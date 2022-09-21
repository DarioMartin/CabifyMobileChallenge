package com.example.cabifymobilechallenge.domain.usecases

data class ProductListUseCases(
    val getProductsUseCase: GetProductsUseCase,
    val addProductToCartUseCase: AddProductToCartUseCase,
    val removeProductsFromCartUseCase: RemoveProductFromCartUseCase,
    val clearCartUseCase: ClearCartUseCase
)
