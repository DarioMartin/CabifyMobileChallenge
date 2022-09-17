package com.example.cabifymobilechallenge.presentation.composable

import android.icu.util.Currency
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun CheckoutView() {

    val viewModel: CartViewModel = hiltViewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(modifier = Modifier.weight(6F)) {

            Products(
                modifier = Modifier.weight(1F),
                uiState = viewModel.uiState.value,
                currency = viewModel.getCurrency()
            )

            Divider(modifier = Modifier.padding(vertical = 18.dp), thickness = 0.5.dp)

            Discounts(
                modifier = Modifier.weight(1F),
                uiState = viewModel.uiState.value
            )

            Divider(modifier = Modifier.padding(vertical = 18.dp), thickness = 0.5.dp)

            SumUp(
                stringResource(id = R.string.subtotal),
                viewModel.getSubTotalPrice(),
                viewModel.getCurrency()
            )

            Spacer(modifier = Modifier.height(6.dp))

            SumUp(
                stringResource(id = R.string.discount),
                viewModel.getDiscountAmount(),
                viewModel.getCurrency()
            )

            Divider(modifier = Modifier.padding(vertical = 18.dp), thickness = 0.5.dp)
        }

        Column(
            modifier = Modifier
                .weight(1F)
                .background(color = MaterialTheme.colors.surface)
        ) {

            Total(viewModel.getTotalPrice(), viewModel.getCurrency())

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { }
            ) {
                Text(text = stringResource(R.string.place_order))
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
fun Products(uiState: CartUIState, currency: Currency, modifier: Modifier = Modifier) {
    when (uiState) {
        CartUIState.Loading -> ProgressIndicator()
        is CartUIState.Error -> {}
        is CartUIState.Content -> {
            if (uiState.cartProducts.isEmpty()) Text(text = stringResource(R.string.empty_cart_message))
            else LazyColumn(
                modifier = modifier.fillMaxWidth(),
                contentPadding = PaddingValues(top = 12.dp, end = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                content = {
                    items(uiState.cartProducts) {
                        CartProductListItem(product = it, currency)
                    }
                }
            )
        }
    }
}

@Composable
fun Discounts(uiState: CartUIState, modifier: Modifier = Modifier) {
    when (uiState) {
        CartUIState.Loading -> ProgressIndicator()
        is CartUIState.Error -> {
        }
        is CartUIState.Content -> {
            if (uiState.availableDiscounts.isEmpty()) Text(text = stringResource(R.string.no_discounts_available))
            else {
                Column(modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(id = R.string.available_discounts),
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.SemiBold
                    )
                    LazyColumn(
                        content = {
                            items(uiState.availableDiscounts) {
                                DiscountListItem(
                                    discount = it,
                                    checked = uiState.cartDiscounts.contains(it)
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DiscountListItem(discount: Discount, checked: Boolean) {
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
        Checkbox(checked = checked, onCheckedChange = { isChecked ->
            if (isChecked) viewModel.addDiscount(discount) else viewModel.removeDiscount(discount)
        })
    }
}

@Composable
fun CartProductListItem(product: Product, currency: Currency) {
    Row(
        modifier = Modifier.fillMaxWidth(),
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