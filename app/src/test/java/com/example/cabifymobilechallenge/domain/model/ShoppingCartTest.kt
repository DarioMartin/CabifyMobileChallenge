package com.example.cabifymobilechallenge.domain.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

internal class ShoppingCartTest {

    //https://github.com/cabify/MobileChallenge

    private val voucher = Product.Voucher("Test Voucher", 5.0)
    private val tShirt = Product.TShirt("Test T-Shirt", 20.0)
    private val mug = Product.Mug("Test Mug", 7.5)

    private val discounts: List<Discount> =
        listOf(
            Discount.TwoPer1VoucherPromo.apply { active = true },
            Discount.TShirtBulkPromo.apply { active = true }
        )

    @Test
    fun `Test empty cart total`() {
        val shoppingCart = ShoppingCart()
        assertThat(shoppingCart.total()).isEqualTo(0)
    }

    @Test
    fun `Test empty cart with discounts total`() {
        val shoppingCart = ShoppingCart(discounts = discounts)
        assertThat(shoppingCart.total()).isEqualTo(0)
    }

    @Test
    fun `First test case`() {
        val products = listOf(voucher, tShirt, mug)
        val shoppingCart = ShoppingCart(products, discounts)

        assertThat(shoppingCart.subTotal()).isEqualTo(32.50)
        assertThat(shoppingCart.total()).isEqualTo(32.50)
    }

    @Test
    fun `Second test case`() {
        val products = listOf(voucher, tShirt, voucher)
        val shoppingCart = ShoppingCart(products, discounts)

        assertThat(shoppingCart.subTotal()).isEqualTo(30.00)
        assertThat(shoppingCart.total()).isEqualTo(25.00)
    }

    @Test
    fun `Third test case`() {
        val products = listOf(tShirt, tShirt, tShirt, voucher, tShirt)
        val shoppingCart = ShoppingCart(products, discounts)

        assertThat(shoppingCart.subTotal()).isEqualTo(85.00)
        assertThat(shoppingCart.total()).isEqualTo(81.00)
    }

    @Test
    fun `Fourth test case`() {
        val products = listOf(voucher, tShirt, voucher, voucher, mug, tShirt, tShirt)
        val shoppingCart = ShoppingCart(products, discounts)

        assertThat(shoppingCart.subTotal()).isEqualTo(82.5)
        assertThat(shoppingCart.total()).isEqualTo(74.50)
    }

}