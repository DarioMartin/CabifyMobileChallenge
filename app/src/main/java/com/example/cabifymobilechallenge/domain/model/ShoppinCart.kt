package com.example.cabifymobilechallenge.domain.model

class ShoppingCart(private val products: List<Product>, private val discounts: List<Discount>) {

    fun total(): Double {
        val totalDiscount = discounts.sumOf { it.discount(products) }
        return products.sumOf { it.price } - totalDiscount
    }

}