package com.example.cabifymobilechallenge.presentation.viewmodel

import android.icu.util.Currency
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.domain.usecases.CartUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    var currency: Currency,
    private val useCases: CartUseCases
) : ViewModel() {

    val uiState = mutableStateOf<CartUIState>(CartUIState.Loading)
    val products = mutableStateListOf<Product>()
    val discounts = mutableStateListOf<Discount>()

    private var _errorEvents = Channel<Unit>()
    var errorEvents = _errorEvents.receiveAsFlow()

    init {
        uiState.value = CartUIState.Loading

        loadAvailableDiscounts()
        loadCartProducts()
    }

    private fun loadCartProducts() {
        viewModelScope.launch {
            uiState.value =
                when (val result = useCases.getCartProductsUseCase()) {
                    is Response.Error -> CartUIState.Error
                    is Response.Success -> {
                        products.clear()
                        products.addAll(result.data ?: emptyList())
                        CartUIState.Success
                    }
                }
        }
    }

    private fun loadAvailableDiscounts() {
        viewModelScope.launch {
            uiState.value = when (val result = useCases.getAvailableDiscountsUseCase()) {
                is Response.Error -> CartUIState.Error
                is Response.Success -> {
                    discounts.clear()
                    discounts.addAll(result.data ?: emptyList())
                    CartUIState.Success
                }
            }
        }
    }

    fun addDiscount(discount: Discount) {
        viewModelScope.launch {
            when (useCases.activateDiscountUseCase(discount)) {
                is Response.Error -> _errorEvents.send(Unit)
                is Response.Success -> updateDiscount(discount) { it.active = true }
            }
        }
    }

    fun removeDiscount(discount: Discount) {
        viewModelScope.launch {
            when (useCases.deactivateDiscountUseCase(discount)) {
                is Response.Error -> _errorEvents.send(Unit)
                is Response.Success -> updateDiscount(discount) { it.active = false }
            }
        }
    }

    private fun updateDiscount(discount: Discount, action: (Discount) -> Unit) {
        discounts.firstOrNull { it == discount }?.let {
            val index = discounts.indexOf(it)
            discounts.remove(it)
            action(it)
            discounts.add(index, it)
        }
    }

}

sealed class CartUIState {
    object Loading : CartUIState()
    object Error : CartUIState()
    object Success : CartUIState()
}