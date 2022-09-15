package com.example.cabifymobilechallenge.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cabifymobilechallenge.R
import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.presentation.theme.CabifyMobileChallengeTheme
import com.example.cabifymobilechallenge.presentation.viewmodel.ProductListViewModel
import com.example.cabifymobilechallenge.presentation.viewmodel.UIState

@Composable
fun ProductListView(viewModel: ProductListViewModel = hiltViewModel(), onCheckOut: () -> Unit) {

    when (val uiState = viewModel.uiState.value) {
        is UIState.Content -> ProductList(uiState.products) { onCheckOut() }
        UIState.Empty -> Message(title = "Empty", body = "Empty")
        UIState.Error -> Message(title = "Error", body = "Error")
        UIState.Loading -> ProgressIndicator()
    }

}

@Composable
fun ProductList(products: List<Product>, onCheckOut: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth(),
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 16.dp,
                end = 12.dp,
                bottom = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            content = {
                items(products) {
                    ProductListItem(product = it)
                }
            }
        )

        Divider(color = MaterialTheme.colors.onBackground, thickness = 1.dp)

        Spacer(modifier = Modifier.width(12.dp))

        Button(onClick = { onCheckOut() }) {
            Text(text = "Proceed to checkout")
        }

        Spacer(modifier = Modifier.width(12.dp))

    }

}

@Composable
fun ProductListItem(product: Product, viewModel: ProductListViewModel = hiltViewModel()) {

    val itemCount = viewModel.itemCount(product)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {

        Row() {
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1F),
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null
            )

            TabRowDefaults.Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp),
                color = MaterialTheme.colors.primary
            )

            Spacer(modifier = Modifier.width(6.dp))
        }


        Text(
            modifier = Modifier.weight(1F),
            text = product.name,
            style = MaterialTheme.typography.body1,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = product.price.toString(),
            style = MaterialTheme.typography.body1,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.width(18.dp))

        Row() {

            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(onClick = {
                        viewModel.removeProduct(product)
                    }),
                imageVector = Icons.Outlined.Delete,
                contentDescription = "Delete item"
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = itemCount.toString(),
                style = MaterialTheme.typography.body1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.width(12.dp))

            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(onClick = {
                        viewModel.addProduct(product)
                    }),
                imageVector = Icons.Outlined.Add,
                contentDescription = "Add item"
            )
        }
    }
}

@Preview
@Composable
fun CharacterListItemPrev() {
    CabifyMobileChallengeTheme {
        ProductListItem(
            Product.Voucher(
                name = "Voucher",
                price = 100.0
            )
        )
    }
}
