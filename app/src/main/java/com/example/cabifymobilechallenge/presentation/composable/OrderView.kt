package com.example.cabifymobilechallenge.presentation.composable

import androidx.compose.runtime.Composable

@Composable
fun OrderView(onBuyAgain: () -> Unit) {

    Message(
        title = "Order completed",
        body = "Your order has been completed successfully",
        actionName = "Buy again",
        action = onBuyAgain
    )

}