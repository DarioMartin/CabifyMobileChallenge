package com.example.cabifymobilechallenge.presentation

import android.icu.util.Currency
import androidx.annotation.StringRes
import com.example.cabifymobilechallenge.R
import com.example.cabifymobilechallenge.domain.model.Discount
import java.util.*

@StringRes
fun getDiscountName(discount: Discount): Int {
    return when (discount) {
        Discount.TwoPer1VoucherPromo -> R.string.two_x_one_voucher_promo
        Discount.TShirtBulkPromo -> R.string.t_shirt_bulk_promo
    }
}

fun Double.formatAsCurrency(currency: Currency): String {

    val stringBuilder = StringBuilder()
    val formatter = Formatter(stringBuilder, Locale.getDefault())
    formatter.format("%(,.2f", this)

    return "${stringBuilder}${currency.symbol}"
}