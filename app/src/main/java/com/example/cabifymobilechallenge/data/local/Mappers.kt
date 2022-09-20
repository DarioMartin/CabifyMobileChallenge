package com.example.cabifymobilechallenge.data.local

import com.example.cabifymobilechallenge.data.local.entity.ProductEntity
import com.example.cabifymobilechallenge.domain.model.Product

fun ProductEntity.toDomainProduct(): Product {
    return when (this.type) {
        "VOUCHER" -> Product.Voucher(name = this.name, price = this.price)
        "TSHIRT" -> Product.TShirt(name = this.name, price = this.price)
        "MUG" -> Product.Mug(name = this.name, price = this.price)
        else -> throw InvalidProductException()
    }
}

fun Product.toProductEntity(): ProductEntity {
    val type = when (this) {
        is Product.Voucher -> "VOUCHER"
        is Product.TShirt -> "TSHIRT"
        is Product.Mug -> "MUG"
    }
    return ProductEntity(type = type, name = this.name, price = this.price)
}

class InvalidProductException : Exception("Invalid product code")
