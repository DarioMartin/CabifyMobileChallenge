package com.example.cabifymobilechallenge.domain.model

import com.google.common.truth.Truth
import org.junit.Assert.fail
import org.junit.Test


internal class ProductTest {

    @Test
    fun `Test negative price`() {
        try {
            Product.Voucher(name = "Test voucher", price = -10.0, count = 0)
            fail("method should throw IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            assert(true)
        }
    }

    @Test
    fun `Test negative count`() {
        try {
            Product.Voucher(name = "Test voucher", price = 10.0, count = -10)
            fail("method should throw IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            assert(true)
        }
    }

    @Test
    fun `Test set negative price`() {
        try {
            val product = Product.Voucher(name = "Test voucher", price = 10.0)
            product.price = -10.0
            fail("method should throw IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            assert(true)
        }
    }

    @Test
    fun `Test set negative count`() {
        try {
            val product = Product.Voucher(name = "Test voucher", price = 10.0)
            product.count = -10
            fail("method should throw IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            assert(true)
        }
    }

    @Test
    fun `Test decrement 0 count`() {
        val product = Product.Voucher(name = "Test voucher", price = 10.0)
        product.decrement()
        Truth.assertThat(product.count).isEqualTo(0)
    }

    @Test
    fun `Test decrement count`() {
        val product = Product.Voucher(name = "Test voucher", price = 10.0, count = 9)
        product.decrement()
        Truth.assertThat(product.count).isEqualTo(8)
    }

    @Test
    fun `Test increment count`() {
        val product = Product.Voucher(name = "Test voucher", price = 10.0, count = 9)
        product.increment()
        Truth.assertThat(product.count).isEqualTo(10)
    }
}