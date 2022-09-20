package com.example.cabifymobilechallenge.presentation.composable

import android.icu.util.Currency
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cabifymobilechallenge.R
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.presentation.formatAsCurrency
import com.example.cabifymobilechallenge.presentation.getDiscountName
import com.example.cabifymobilechallenge.presentation.viewmodel.CartUIState
import com.example.cabifymobilechallenge.presentation.viewmodel.CartViewModel

@Composable
fun CheckoutView(onPlaceOrder: () -> Unit) {

    val viewModel: CartViewModel = hiltViewModel()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(18.dp)
            ) {

                paintProducts(viewModel)

                item {
                    Divider(
                        modifier = Modifier.padding(vertical = 18.dp),
                        thickness = 0.5.dp
                    )
                }
                paintDiscounts(viewModel)
            }

            Shadow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .align(Alignment.BottomCenter),
            )
        }

        Summary(onPlaceOrder = onPlaceOrder)
    }
}

@Composable
private fun Summary(viewModel: CartViewModel = hiltViewModel(), onPlaceOrder: () -> Unit) {

    Column(modifier = Modifier.padding(18.dp)) {

        SumUp(
            stringResource(id = R.string.subtotal),
            viewModel.getSubTotalPrice(),
            viewModel.currency
        )

        Spacer(modifier = Modifier.height(6.dp))

        SumUp(
            stringResource(id = R.string.discount),
            viewModel.getDiscountAmount(),
            viewModel.currency
        )

        Divider(modifier = Modifier.padding(vertical = 18.dp), thickness = 1.dp)

        Total(viewModel.getTotalPrice(), viewModel.currency)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onPlaceOrder() }
        ) {
            Text(text = stringResource(R.string.place_order))
        }
    }
}

private fun LazyListScope.paintProducts(viewModel: CartViewModel) {
    when (val uiState = viewModel.uiState.value) {
        CartUIState.Loading -> item { ProgressIndicator() }
        is CartUIState.Error -> item { Text(text = "Error") }
        is CartUIState.Content -> {
            if (uiState.shoppingCart.products.isEmpty()) item { Text(text = stringResource(R.string.empty_cart_message)) }
            else
                uiState.shoppingCart.products.map {
                    item {
                        CartProductListItem(
                            product = it,
                            viewModel.currency
                        )
                    }
                }
        }
    }
}

private fun LazyListScope.paintDiscounts(viewModel: CartViewModel) {
    when (val uiState = viewModel.uiState.value) {
        CartUIState.Loading -> item { ProgressIndicator() }
        is CartUIState.Error -> item { Text(text = "Error") }
        is CartUIState.Content -> {
            if (uiState.availableDiscounts.isEmpty()) item { Text(text = stringResource(R.string.no_discounts_available)) }
            else {
                item {
                    Text(
                        text = stringResource(id = R.string.available_discounts),
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                uiState.availableDiscounts.map {
                    item {
                        DiscountListItem(discount = it)
                    }
                }
            }
        }
    }
}

@Composable
private fun SumUp(name: String, value: Double, currency: Currency) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Light
        )
        Text(
            text = value.formatAsCurrency(currency),
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun Total(totalPrice: Double, currency: Currency) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = stringResource(id = R.string.total),
            style = MaterialTheme.typography.body1
        )
        Text(
            text = totalPrice.formatAsCurrency(currency),
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Medium
        )
    }
}


@Composable
fun DiscountListItem(discount: Discount) {
    val viewModel: CartViewModel = hiltViewModel()
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = getDiscountName(discount)),
            style = MaterialTheme.typography.body1
        )
        Checkbox(checked = discount.active, onCheckedChange = { isChecked ->
            if (isChecked) viewModel.addDiscount(discount) else viewModel.removeDiscount(discount)
        })
    }
}

@Composable
fun CartProductListItem(product: Product, currency: Currency) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = product.name,
            style = MaterialTheme.typography.body1
        )
        Text(
            text = product.price.formatAsCurrency(currency),
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Medium
        )
    }
}


@Preview
@Composable
fun TotalPreview() {
    MaterialTheme {
        Total(totalPrice = 209.50, Currency.getInstance("EUR"))
    }
}