package com.example.cabifymobilechallenge.domain.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

internal class DiscountTest {

    @Test
    fun `Test 2x1 voucher empty list`() {
        val discount = Discount.TwoPer1VoucherPromo
        val products = listOf<Product>()

        assertThat(discount.calculateDiscount(products)).isEqualTo(0.0)
    }

    @Test
    fun `Test 2x1 voucher 1 item`() {
        val discount = Discount.TwoPer1VoucherPromo
        val price = 10.0
        val products = List(1) { Product.Voucher("Test", price = price) }

        assertThat(discount.calculateDiscount(products)).isEqualTo(0.0)
    }

    @Test
    fun `Test 2x1 voucher 2 items`() {
        val discount = Discount.TwoPer1VoucherPromo
        val price = 10.0
        val products = List(2) { Product.Voucher("Test", price = price) }

        assertThat(discount.calculateDiscount(products)).isEqualTo(price)
    }

    @Test
    fun `Test 2x1 voucher 3 items`() {
        val discount = Discount.TwoPer1VoucherPromo
        val price = 10.0
        val products = List(3) { Product.Voucher("Test", price = price) }

        assertThat(discount.calculateDiscount(products)).isEqualTo(price)
    }

    @Test
    fun `Test 2x1 voucher 20 items`() {
        val discount = Discount.TwoPer1VoucherPromo
        val price = 10.0
        val products = List(20) { Product.Voucher("Test", price = price) }

        assertThat(discount.calculateDiscount(products)).isEqualTo(price * 10)
    }

    @Test
    fun `Test 2x1 voucher 20 wrong items`() {
        val discount = Discount.TwoPer1VoucherPromo
        val price = 10.0
        val products = List(20) { Product.Mug("Test", price = price) }

        assertThat(discount.calculateDiscount(products)).isEqualTo(0.0)
    }

    @Test
    fun `Test Bulk TShirt promo empty list`() {
        val discount = Discount.TShirtBulkPromo
        val products = listOf<Product>()

        assertThat(discount.calculateDiscount(products)).isEqualTo(0.0)
    }

    @Test
    fun `Test Bulk TShirt promo 1 item`() {
        val discount = Discount.TShirtBulkPromo
        val price = 10.0
        val products = List(1) { Product.TShirt("Test", price = price) }

        assertThat(discount.calculateDiscount(products)).isEqualTo(0.0)
    }

    @Test
    fun `Test Bulk TShirt promo 2 items`() {
        val discount = Discount.TShirtBulkPromo
        val price = 10.0
        val products = List(2) { Product.TShirt("Test", price = price) }

        assertThat(discount.calculateDiscount(products)).isEqualTo(0.0)
    }

    @Test
    fun `Test Bulk TShirt promo 3 items`() {
        val discount = Discount.TShirtBulkPromo
        val price = 10.0
        val products = List(3) { Product.TShirt("Test", price = price) }

        assertThat(discount.calculateDiscount(products)).isEqualTo(3.0)
    }

    @Test
    fun `Test  Bulk TShirt 20 items`() {
        val discount = Discount.TShirtBulkPromo
        val price = 10.0
        val products = List(20) { Product.TShirt("Test", price = price) }

        assertThat(discount.calculateDiscount(products)).isEqualTo(20.0)
    }

    @Test
    fun `Test  Bulk TShirt 20 wrong items`() {
        val discount = Discount.TShirtBulkPromo
        val price = 10.0
        val products = List(20) { Product.Mug("Test", price = price) }

        assertThat(discount.calculateDiscount(products)).isEqualTo(0.0)
    }


}