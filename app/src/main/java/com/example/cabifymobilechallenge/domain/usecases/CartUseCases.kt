package com.example.cabifymobilechallenge.domain.usecases

data class CartUseCases(
    val getCartProductsUseCase: GetCartProductsUseCase,
    val getAvailableDiscountsUseCase: GetAvailableDiscountsUseCase,
    val addDiscountToCartUseCase: AddDiscountToCartUseCase,
    val removeDiscountFromCartUseCase: RemoveDiscountFromCartUseCase,
    val getCartDiscountsUseCase: GetCartDiscountsUseCase,
    val getCartUseCase: GetCartUseCase
)