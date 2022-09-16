package com.example.cabifymobilechallenge.presentation.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.presentation.viewmodel.CartViewModel
import com.example.cabifymobilechallenge.presentation.viewmodel.CartUIState

@Composable
fun CheckoutView() {

    val viewModel: CartViewModel = hiltViewModel()
    val uiState = viewModel.uiState.value

    when (uiState) {
        is CartUIState.Content -> DetailedList(
            uiState.cartProducts,
            uiState.cartDiscounts,
            uiState.availableDiscounts
        )
        CartUIState.Error -> Message(title = "Error", body = "Error")
        CartUIState.Loading -> ProgressIndicator()
    }
}

@Composable
fun DetailedList(
    cartProducts: List<Product>,
    cartDiscounts: List<Discount>,
    availableDiscounts: List<Discount>
) {
    val viewModel: CartViewModel = hiltViewModel()

    Column() {
        Products(cartProducts)
        Discounts(availableDiscounts, cartDiscounts)
        Text(text = viewModel.getTotalPrice().toString())
    }
}

@Composable
fun Products(products: List<Product>) {
    if (products.isEmpty()) Text(text = "Empty cart, there are no items in your cart yet.")
    else LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        content = {
            items(products) {
                CartProductListItem(product = it)
            }
        }
    )
}

@Composable
fun Discounts(availableDiscounts: List<Discount>, cartDiscounts: List<Discount>) {
    if (availableDiscounts.isEmpty()) Text(text = "No discounts available")
    else LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        content = {
            items(availableDiscounts) {
                DiscountListItem(discount = it, checked = cartDiscounts.contains(it))
            }
        }
    )

}

@Composable
fun DiscountListItem(discount: Discount, checked: Boolean) {
    val viewModel: CartViewModel = hiltViewModel()
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = getDiscountName(discount))
        Checkbox(checked = checked, onCheckedChange = { isChecked ->
            if (isChecked) viewModel.addDiscount(discount) else viewModel.removeDiscount(discount)
        })
    }
}

fun getDiscountName(discount: Discount): String {
    return when (discount) {
        Discount.TwoPer1VoucherPromo -> "2 Per 1 Voucher Promo"
        Discount.BulkPromo -> "Bulk Promo"
    }
}

@Composable
fun CartProductListItem(product: Product) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = product.name)
        Text(text = product.price.toString())
    }
}
