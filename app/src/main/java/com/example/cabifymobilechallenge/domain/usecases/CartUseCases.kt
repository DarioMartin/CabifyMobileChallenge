package com.example.cabifymobilechallenge.domain.usecases

data class CartUseCases(
    val getCartProductsUseCase: GetCartProductsUseCase,
    val getAvailableDiscountsUseCase: GetAvailableDiscountsUseCase,
    val activateDiscountUseCase: ActivateDiscountUseCase,
    val deactivateDiscountUseCase: DeactivateDiscountUseCase,
    val getCartDiscountsUseCase: GetCartDiscountsUseCase
)