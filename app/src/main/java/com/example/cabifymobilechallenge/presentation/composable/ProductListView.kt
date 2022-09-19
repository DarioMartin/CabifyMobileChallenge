package com.example.cabifymobilechallenge.presentation.composable

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cabifymobilechallenge.R
import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.presentation.viewmodel.ProductListViewModel
import com.example.cabifymobilechallenge.presentation.viewmodel.UIState

@Composable
fun ProductListView(viewModel: ProductListViewModel = hiltViewModel(), onCheckOut: () -> Unit) {

    when (val uiState = viewModel.uiState.value) {
        is UIState.Content -> ProductList(
            products = uiState.products,
            onCheckOut = { onCheckOut() })
        UIState.Error -> Message(
            title = stringResource(R.string.product_list_error_title),
            body = stringResource(R.string.product_list_error_body)
        )
        UIState.Empty -> Message(
            title = stringResource(R.string.product_empty_list_title),
            body = stringResource(R.string.product_empty_list_body)
        )
        UIState.Loading -> ProgressIndicator()
    }

}

@Composable
fun ProductList(
    products: List<Product>,
    viewModel: ProductListViewModel = hiltViewModel(),
    onCheckOut: () -> Unit
) {
    val context = LocalContext.current

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
                        ProductListItem(
                            product = it,
                            itemCount = viewModel.itemCount(it),
                            currency = viewModel.getCurrency(),
                            onAdd = { viewModel.addProduct(it) },
                            onRemove = { viewModel.removeProduct(it) }
                        )
                    }
                }
            )

            Shadow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .align(Alignment.BottomCenter),
            )
        }

        Column(modifier = Modifier.padding(18.dp)) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = !viewModel.isCartEmpty(),
                onClick = { onCheckOut() }) {
                Text(text = stringResource(R.string.proceed_to_checkout))
            }
        }
    }

}

