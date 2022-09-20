package com.example.cabifymobilechallenge.domain.model

data class ShoppingCart(
    val products: List<Product> = emptyList(),
    val discounts: List<Discount> = emptyList()
) {

    fun subTotal() = products.sumOf { it.price }

    fun getDiscount() =
        discounts.filter { it.active }.sumOf { it.calculateDiscount(products) }

    fun total(): Double {
        return subTotal() - getDiscount()
    }

}