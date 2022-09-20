package com.example.cabifymobilechallenge.data.local

import com.example.cabifymobilechallenge.data.local.entity.DiscountEntity
import com.example.cabifymobilechallenge.data.local.entity.ProductEntity
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.model.Product

fun ProductEntity.toDomain(): Product? {
    return when (this.type) {
        "VOUCHER" -> Product.Voucher(name = this.name, price = this.price)
        "TSHIRT" -> Product.TShirt(name = this.name, price = this.price)
        "MUG" -> Product.Mug(name = this.name, price = this.price)
        else -> null
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


fun DiscountEntity.toDomain(): Discount? {
    val discount = when (this.id) {
        Discount.TwoPer1VoucherPromo::class.simpleName -> Discount.TwoPer1VoucherPromo
        Discount.TShirtBulkPromo::class.simpleName -> Discount.TShirtBulkPromo
        else -> null
    }

    return discount?.apply { active = this@toDomain.active }
}

fun Discount.toEntity(): DiscountEntity {
    return DiscountEntity(id = this::class.simpleName ?: "", active = this.active)
}
