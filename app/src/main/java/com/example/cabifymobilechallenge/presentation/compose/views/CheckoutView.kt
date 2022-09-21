package com.example.cabifymobilechallenge.presentation.compose.views

import android.icu.util.Currency
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cabifymobilechallenge.R
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.domain.model.ShoppingCart
import com.example.cabifymobilechallenge.presentation.compose.components.ProgressIndicator
import com.example.cabifymobilechallenge.presentation.compose.components.Shadow
import com.example.cabifymobilechallenge.presentation.formatAsCurrency
import com.example.cabifymobilechallenge.presentation.getDiscountName
import com.example.cabifymobilechallenge.presentation.viewmodel.CartUIState
import com.example.cabifymobilechallenge.presentation.viewmodel.CartViewModel

@Composable
fun CheckoutView(onPlaceOrder: () -> Unit) {

    val context = LocalContext.current
    val viewModel: CartViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.updateErrorFlow.collect {
            Toast.makeText(
                context,
                context.getString(R.string.error_updating_quantity),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

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

        val products = viewModel.products
        val discounts = viewModel.discounts

        Summary(
            shoppingCart = ShoppingCart(products, discounts),
            currency = viewModel.currency
        ) { onPlaceOrder() }
    }
}

@Composable
private fun Summary(
    shoppingCart: ShoppingCart,
    currency: Currency,
    onPlaceOrder: () -> Unit
) {

    Column(modifier = Modifier.padding(18.dp)) {

        SumUp(
            stringResource(id = R.string.subtotal),
            shoppingCart.subTotal(),
            currency
        )

        Spacer(modifier = Modifier.height(6.dp))

        SumUp(
            stringResource(id = R.string.discount),
            shoppingCart.getDiscount(),
            currency
        )

        Divider(modifier = Modifier.padding(vertical = 18.dp), thickness = 1.dp)

        Total(shoppingCart.total(), currency)

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
    when (viewModel.uiState) {
        CartUIState.Loading -> item { ProgressIndicator() }
        is CartUIState.Error -> item { Text(text = stringResource(R.string.error_get_products)) }
        is CartUIState.Success -> {
            val products = viewModel.products
            if (products.isEmpty()) item { Text(text = stringResource(R.string.empty_cart_message)) }
            else products.map {
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
    when (viewModel.uiState) {
        CartUIState.Loading -> item { ProgressIndicator() }
        is CartUIState.Error -> item { Text(text = stringResource(R.string.error_get_discounts)) }
        is CartUIState.Success -> {
            val discounts = viewModel.discounts
            if (discounts.isEmpty()) item { Text(text = stringResource(R.string.no_discounts_available)) }
            else {
                item {
                    Text(
                        text = stringResource(id = R.string.available_discounts),
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                discounts.map {
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