package com.example.cabifymobilechallenge.data

import com.example.cabifymobilechallenge.data.remote.RemoteProduct
import com.example.cabifymobilechallenge.domain.model.Product

fun RemoteProduct.toDomainProduct(): Product {
    return when (this.code) {
        "VOUCHER" -> Product.Voucher(name = this.name, price = this.price)
        "TSHIRT" -> Product.TShirt(name = this.name, price = this.price)
        "MUG" -> Product.Mug(name = this.name, price = this.price)
        else -> throw InvalidProductException()
    }
}

class InvalidProductException : Exception("Invalid product code")
