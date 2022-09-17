package com.example.cabifymobilechallenge.domain.model

sealed class Discount {

    open fun calculateDiscount(products: List<Product>): Double {
        return 0.0
    }

    object TwoPer1VoucherPromo : Discount() {
        override fun calculateDiscount(products: List<Product>): Double {
            val group = 2
            val free = 1

            val freeItems: Int = products.filterIsInstance<Product.Voucher>().size / group * free

            return products.take(freeItems).sumOf { it.price }
        }
    }

    object TShirtBulkPromo : Discount() {
        override fun calculateDiscount(products: List<Product>): Double {
            val tShirts = products.filterIsInstance<Product.TShirt>()
            return if (tShirts.size >= 3) 1.0 * tShirts.size else 0.0
        }
    }

}