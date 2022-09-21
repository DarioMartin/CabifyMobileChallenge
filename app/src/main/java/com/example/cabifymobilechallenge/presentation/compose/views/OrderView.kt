package com.example.cabifymobilechallenge.presentation.compose.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cabifymobilechallenge.R
import com.example.cabifymobilechallenge.presentation.compose.components.Message
import com.example.cabifymobilechallenge.presentation.compose.components.ProgressIndicator
import com.example.cabifymobilechallenge.presentation.viewmodel.OrderUIState
import com.example.cabifymobilechallenge.presentation.viewmodel.OrderViewModel

@Composable
fun OrderView(onBuyAgain: () -> Unit) {

    val viewModel: OrderViewModel = hiltViewModel()

    when (viewModel.uiState) {
        OrderUIState.Error -> Message(
            title = stringResource(R.string.order_error_title),
            body = stringResource(R.string.order_error_body),
            actionName = stringResource(R.string.try_again),
            action = { viewModel.processOrder() }
        )
        OrderUIState.Loading -> ProgressIndicator()
        OrderUIState.Success -> Message(
            title = stringResource(R.string.order_success_title),
            body = stringResource(R.string.order_success_body),
            actionName = stringResource(R.string.buy_again),
            action = onBuyAgain
        )
    }


}