package com.example.cabifymobilechallenge.domain.model

sealed class Product(val name: String, val price: Double, var count: Int = 0) {

    fun increment() {
        count++
    }

    fun decrement() {
        if (count > 0) count--
    }

    class Voucher(name: String, price: Double) : Product(name, price)
    class TShirt(name: String, price: Double) : Product(name, price)
    class Mug(name: String, price: Double) : Product(name, price)
}

