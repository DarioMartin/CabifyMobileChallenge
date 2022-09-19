package com.example.cabifymobilechallenge.presentation.composable

import androidx.annotation.DrawableRes
import com.example.cabifymobilechallenge.R
import com.example.cabifymobilechallenge.domain.model.Product

@DrawableRes
fun getProductResource(product: Product): Int {
    return when (product) {
        is Product.Mug -> R.drawable.ic_mug
        is Product.TShirt -> R.drawable.ic_t_shirt
        is Product.Voucher -> R.drawable.ic_voucher
    }
}