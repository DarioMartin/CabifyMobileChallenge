package com.example.cabifymobilechallenge.data.local

import com.example.cabifymobilechallenge.data.local.entity.DiscountEntity
import com.example.cabifymobilechallenge.data.local.entity.ProductEntity
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.model.Product

fun ProductEntity.toDomain(): Product {
    return when (this.type) {
        "VOUCHER" -> Product.Voucher(name = this.name, price = this.price)
        "TSHIRT" -> Product.TShirt(name = this.name, price = this.price)
        "MUG" -> Product.Mug(name = this.name, price = this.price)
        else -> throw InvalidProductException()
    }
}

fun Product.toEntity(): ProductEntity {
    val type = when (this) {
        is Product.Voucher -> "VOUCHER"
        is Product.TShirt -> "TSHIRT"
        is Product.Mug -> "MUG"
    }
    return ProductEntity(type = type, name = this.name, price = this.price)
}

class InvalidProductException : Exception("Invalid product code")


fun DiscountEntity.toDomain(): Discount {
    return when (this.id) {
        Discount.TwoPer1VoucherPromo::class.simpleName -> Discount.TwoPer1VoucherPromo.apply {
            active = active
        }
        Discount.TShirtBulkPromo::class.simpleName -> Discount.TShirtBulkPromo.apply {
            active = active
        }
        else -> throw InvalidDiscountException()
    }
}

fun Discount.toEntity(): DiscountEntity {
    val id = when (this) {
        Discount.TShirtBulkPromo -> this::class.simpleName
        Discount.TwoPer1VoucherPromo -> this::class.simpleName
    }
    return DiscountEntity(id = id ?: "", active = this.active)
}

class InvalidDiscountException : Exception("Invalid discount id")
