package com.example.cabifymobilechallenge.domain.model

data class ShoppingCart(
    val products: List<Product> = emptyList(),
    val discounts: List<Discount> = emptyList()
) {

    fun total(): Double {
        val totalDiscount = discounts.sumOf { it.calculateDiscount(products) }
        return products.sumOf { it.price } - totalDiscount
    }

}