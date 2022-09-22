package com.example.cabifymobilechallenge.domain.model

sealed class Product(val name: String, price: Double, count: Int = 0) {

    var count = count
        set(value) {
            require(value >= 0) { "Product count can't be negative" }
            field = value
        }

    var price = price
        set(value) {
            require(value >= 0) { "Product price can't be negative" }
            field = value
        }

    init {
        require(count >= 0) { "Product count can't be negative" }
        require(price >= 0) { "Product price can't be negative" }
    }

    fun increment() {
        count++
    }

    fun decrement() {
        if (count > 0) count--
    }

    class Voucher(name: String, price: Double, count: Int = 0) : Product(name, price, count)
    class TShirt(name: String, price: Double, count: Int = 0) : Product(name, price, count)
    class Mug(name: String, price: Double, count: Int = 0) : Product(name, price, count)
}

