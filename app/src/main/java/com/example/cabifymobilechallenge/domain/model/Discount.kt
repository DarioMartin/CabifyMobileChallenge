package com.example.cabifymobilechallenge.domain.model

sealed class Discount(var active: Boolean = false) : DiscountCalculator {

    object TwoPer1VoucherPromo : Discount() {
        override fun calculateDiscount(products: List<Product>): Double {
            val group = 2
            val free = 1
            val vouchers = products.filterIsInstance<Product.Voucher>()
            val freeItems: Int = vouchers.size / group * free
            return vouchers.take(freeItems).sumOf { it.price }
        }
    }

    object TShirtBulkPromo : Discount() {
        override fun calculateDiscount(products: List<Product>): Double {
            val tShirts = products.filterIsInstance<Product.TShirt>()
            return if (tShirts.size >= 3) 1.0 * tShirts.size else 0.0
        }
    }

}

interface DiscountCalculator {
    fun calculateDiscount(products: List<Product>): Double
}
