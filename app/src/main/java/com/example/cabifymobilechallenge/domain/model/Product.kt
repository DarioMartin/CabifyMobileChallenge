package com.example.cabifymobilechallenge.domain.model

sealed class Product(val name: String, val price: Double) {
    class Voucher(name: String, price: Double) : Product(name, price)
    class TShirt(name: String, price: Double) : Product(name, price)
    class Mug(name: String, price: Double) : Product(name, price)
}

//CURRENCY

