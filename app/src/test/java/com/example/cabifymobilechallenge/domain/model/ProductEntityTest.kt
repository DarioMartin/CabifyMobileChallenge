package com.example.cabifymobilechallenge.domain.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

internal class ProductEntityTest {

    //https://github.com/cabify/MobileChallenge

    private val voucher = Product.Voucher("Test Voucher", 5.0)
    private val tShirt = Product.TShirt("Test T-Shirt", 20.0)
    private val mug = Product.Mug("Test Mug", 7.5)

    private val discounts: List<Discount> =
        listOf(
            Discount.TwoPer1VoucherPromo,
            Discount.TShirtBulkPromo
        )

    @Test
    fun `First test case`() {
        val products = listOf(voucher, tShirt, mug)
        val shoppingCart = ShoppingCart(products, discounts)
        val total = shoppingCart.total()
        assertThat(total).isEqualTo(32.50)
    }

    @Test
    fun `Second test case`() {
        val products = listOf(voucher, tShirt, voucher)
        val shoppingCart = ShoppingCart(products, discounts)
        val total = shoppingCart.total()
        assertThat(total).isEqualTo(25.00)
    }

    @Test
    fun `Third test case`() {
        val products = listOf(tShirt, tShirt, tShirt, voucher, tShirt)
        val shoppingCart = ShoppingCart(products, discounts)
        val total = shoppingCart.total()
        assertThat(total).isEqualTo(81.00)
    }

    @Test
    fun `Fourth test case`() {
        val products = listOf(voucher, tShirt, voucher, voucher, mug, tShirt, tShirt)
        val shoppingCart = ShoppingCart(products, discounts)
        val total = shoppingCart.total()
        assertThat(total).isEqualTo(74.50)
    }

}