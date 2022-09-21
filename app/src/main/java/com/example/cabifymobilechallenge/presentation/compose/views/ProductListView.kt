package com.example.cabifymobilechallenge.presentation.compose.views

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
import com.example.cabifymobilechallenge.presentation.compose.components.Message
import com.example.cabifymobilechallenge.presentation.compose.components.ProductListItem
import com.example.cabifymobilechallenge.presentation.compose.components.ProgressIndicator
import com.example.cabifymobilechallenge.presentation.compose.components.Shadow
import com.example.cabifymobilechallenge.presentation.viewmodel.ProductListViewModel
import com.example.cabifymobilechallenge.presentation.viewmodel.UIState

@Composable
fun ProductListView(viewModel: ProductListViewModel = hiltViewModel(), onCheckOut: () -> Unit) {

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.errorEvents.collect {
            Toast.makeText(
                context,
                context.getString(R.string.error_updating_quantity),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    when (viewModel.uiState.value) {
        is UIState.Success -> ProductList(
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
    viewModel: ProductListViewModel = hiltViewModel(),
    onCheckOut: () -> Unit
) {
    val products = viewModel.products

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
                            currency = viewModel.currency,
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
                enabled = products.any { it.count > 0 },
                onClick = { onCheckOut() }) {
                Text(text = stringResource(R.string.proceed_to_checkout))
            }
        }
    }

}

